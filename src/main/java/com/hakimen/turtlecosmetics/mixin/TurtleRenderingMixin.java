package com.hakimen.turtlecosmetics.mixin;

import com.hakimen.turtlecosmetics.api.Overlays;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dan200.computercraft.api.turtle.TurtleSide;
import dan200.computercraft.client.platform.ClientPlatformHelper;
import dan200.computercraft.client.render.TurtleBlockEntityRenderer;
import dan200.computercraft.shared.computer.core.ComputerFamily;
import dan200.computercraft.shared.turtle.blocks.TurtleBlockEntity;
import dan200.computercraft.shared.util.Holiday;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;


import java.util.ArrayList;
import java.util.Arrays;

import static dan200.computercraft.client.render.TurtleBlockEntityRenderer.getTurtleModel;

@Mixin(value = TurtleBlockEntityRenderer.class, remap = false)
public abstract class TurtleRenderingMixin {
    @Shadow @Final private BlockEntityRenderDispatcher renderer;

    @Shadow @Final private Font font;

    @Shadow @Final private static ResourceLocation ELF_OVERLAY_MODEL;


    @Shadow protected abstract void renderUpgrade(PoseStack transform, MultiBufferSource buffers, int lightmapCoord, int overlayLight, TurtleBlockEntity turtle, TurtleSide side, float f);

    @Shadow protected abstract void renderModel(PoseStack transform, MultiBufferSource renderer, int lightmapCoord, int overlayLight, BakedModel model, @Nullable int[] tints);

    /**
     * @author JheffersonMarques
     * @reason changes to add the possibility to render multiple layers of overlays
     */
    @Overwrite(remap = false)
    public void render(TurtleBlockEntity turtle, float partialTicks, PoseStack transform, MultiBufferSource buffers, int lightmapCoord, int overlayLight) {
        transform.pushPose();

        // Translate the turtle first, so the label moves with it.
        var offset = turtle.getRenderOffset(partialTicks);
        transform.translate(offset.x, offset.y, offset.z);

        // Render the label
        var label = turtle.getLabel();
        var hit = renderer.cameraHitResult;
        if (label != null && hit.getType() == HitResult.Type.BLOCK && turtle.getBlockPos().equals(((BlockHitResult) hit).getBlockPos())) {
            var mc = Minecraft.getInstance();
            var font = this.font;

            transform.pushPose();
            transform.translate(0.5, 1.2, 0.5);
            transform.mulPose(mc.getEntityRenderDispatcher().cameraOrientation());
            transform.scale(-0.025f, -0.025f, 0.025f);

            var matrix = transform.last().pose();
            var opacity = (int) (mc.options.getBackgroundOpacity(0.25f) * 255) << 24;
            var width = -font.width(label) / 2.0f;
            // TODO: Check this looks okay
            font.drawInBatch(label, width, (float) 0, 0x20ffffff, false, matrix, buffers, Font.DisplayMode.SEE_THROUGH, opacity, lightmapCoord);
            font.drawInBatch(label, width, (float) 0, 0xffffffff, false, matrix, buffers, Font.DisplayMode.NORMAL, 0, lightmapCoord);

            transform.popPose();
        }

        // Then apply rotation and flip if needed.
        transform.translate(0.5f, 0.5f, 0.5f);
        var yaw = turtle.getRenderYaw(partialTicks);
        transform.mulPose(Axis.YP.rotationDegrees(180.0f - yaw));
        if (label != null && (label.equals("Dinnerbone") || label.equals("Grumm"))) {
            transform.scale(1.0f, -1.0f, 1.0f);
        }
        transform.translate(-0.5f, -0.5f, -0.5f);

        // Render the turtle
        var colour = turtle.getColour();
        var family = turtle.getFamily();
        var overlay = turtle.getOverlay();

        renderModel(transform, buffers, lightmapCoord, overlayLight, getTurtleModel(family, colour != -1), colour == -1 ? null : new int[]{ colour });

        // Render the overlay
        var overlayModels = getTurtleOverlayModel(label, overlay, Holiday.getCurrent() == Holiday.CHRISTMAS);
        if (overlayModels != null) {
            for (var overlayModel:overlayModels) {
                if(overlayModel != null){
                    renderModel(transform, buffers, lightmapCoord, overlayLight, overlayModel, null);
                }
            }
        }

        // Render the upgrades
        renderUpgrade(transform, buffers, lightmapCoord, overlayLight, turtle, TurtleSide.LEFT, partialTicks);
        renderUpgrade(transform, buffers, lightmapCoord, overlayLight, turtle, TurtleSide.RIGHT, partialTicks);

        transform.popPose();
    }
    void renderModel(PoseStack transform, MultiBufferSource buffers, int lightmapCoord, int overlayLight, ResourceLocation modelLocation, @javax.annotation.Nullable int[] tints) {
        var modelManager = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager();
        renderModel(transform, buffers, lightmapCoord, overlayLight, ClientPlatformHelper.get().getModel(modelManager, modelLocation), tints);
    }
    private static @javax.annotation.Nullable ResourceLocation[] getTurtleOverlayModel(String label,@javax.annotation.Nullable ResourceLocation overlay, boolean christmas) {
        if (overlay != null) return new ResourceLocation[]{ overlay };
        if (christmas) return new ResourceLocation[]{ ELF_OVERLAY_MODEL };
        if (label != null){
            String[] labels = label.split(" ");
            ResourceLocation[] overlays = new ResourceLocation[labels.length];
            for (int i = 0; i < labels.length; i++) {
                overlays[i] = Overlays.get(labels[i]);
            }
            return overlays;
        }
        return null;
    }
}
