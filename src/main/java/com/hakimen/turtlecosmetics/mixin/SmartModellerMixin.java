package com.hakimen.turtlecosmetics.mixin;


import com.hakimen.turtlecosmetics.api.Overlay;
import com.hakimen.turtlecosmetics.utils.Overlays;
import com.hakimen.turtlecosmetics.utils.RenderUtils;
import com.hakimen.turtlecosmetics.utils.TurtleModelCombination;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Transformation;
import com.mojang.math.Vector3f;
import dan200.computercraft.ComputerCraft;
import dan200.computercraft.api.turtle.ITurtleUpgrade;
import dan200.computercraft.api.turtle.TurtleSide;
import dan200.computercraft.client.render.TileEntityTurtleRenderer;
import dan200.computercraft.client.render.TransformedBakedModel;
import dan200.computercraft.client.render.TurtleSmartItemModel;
import dan200.computercraft.client.turtle.TurtleUpgradeModellers;
import dan200.computercraft.shared.computer.core.ComputerFamily;
import dan200.computercraft.shared.turtle.blocks.TileTurtle;
import dan200.computercraft.shared.turtle.items.ItemTurtle;
import dan200.computercraft.shared.util.Holiday;
import dan200.computercraft.shared.util.HolidayUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.BakedModelWrapper;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.*;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static dan200.computercraft.shared.turtle.core.TurtleBrain.NBT_OVERLAY;

@Pseudo
@Mixin(value = TurtleSmartItemModel.class, remap = false)
public abstract class SmartModellerMixin {

    @Shadow @Final private Map<TurtleModelCombination, List<BakedModel>> cachedModels;
    @Shadow @Final private static Transformation flip;

    @Shadow @Final private static Transformation identity;

    @Shadow @Final private BakedModel colourModel;

    @Shadow @Final private BakedModel familyModel;

    /**
     * @author
     * @reason
     */
    private List<BakedModel> buildModel( TurtleModelCombination combo )
    {
        Minecraft mc = Minecraft.getInstance();
        ModelManager modelManager = mc.getItemRenderer().getItemModelShaper().getModelManager();

        var transformation = combo.flip() ? flip : identity;
        ArrayList<BakedModel> parts = new ArrayList<>( 4 );
        parts.add( new TransformedBakedModel( combo.colour() ? colourModel : familyModel, transformation ) );

        ResourceLocation[] overlayModelLocation = RenderUtils.getTurtleOverlayModel( combo.overlay(), combo.christmas() );
        if( overlayModelLocation != null )
        {
            for (ResourceLocation resource:overlayModelLocation) {
                parts.add( new TransformedBakedModel( modelManager.getModel( resource ), transformation ) );
            }
        }
        if( combo.leftUpgrade() != null )
        {
            parts.add( new TransformedBakedModel( TurtleUpgradeModellers.getModel( combo.leftUpgrade(), null, TurtleSide.LEFT ) ).composeWith( transformation ) );
        }
        if( combo.rightUpgrade() != null )
        {
            parts.add( new TransformedBakedModel( TurtleUpgradeModellers.getModel( combo.rightUpgrade(), null, TurtleSide.RIGHT ) ).composeWith( transformation ) );
        }

        return parts;
    }
    public ResourceLocation[] getOverlay(@Nonnull ItemStack stack )
    {
        CompoundTag tag = stack.getTag();
        if( tag != null) {
            if(tag.contains( NBT_OVERLAY )) {
                return new ResourceLocation[]{new ResourceLocation(tag.getString(NBT_OVERLAY))};
            }else if(tag.contains("display")){
                CompoundTag nameTag = tag.getCompound("display");
                String name = nameTag.getString("Name");
                var toReturn = new ArrayList<ResourceLocation>();
                name = name.toLowerCase().replace("{\"text\":\"}","").replace("\"}","");
                for (Overlay over: Overlays.overlays) {
                    if(name.contains(over.getLabel())){
                        toReturn.add(over.getOverlay());
                    }
                }
                return toReturn.toArray(new ResourceLocation[toReturn.size()]);
            }
        }

        return null;
    }


    /**
     * @author
     * @reason
     */
    @Overwrite
    public List<BakedModel> getRenderPasses(ItemStack stack, boolean fabulous )
    {
        ItemTurtle turtle = (ItemTurtle) stack.getItem();

        int colour = turtle.getColour( stack );
        ITurtleUpgrade leftUpgrade = turtle.getUpgrade( stack, TurtleSide.LEFT );
        ITurtleUpgrade rightUpgrade = turtle.getUpgrade( stack, TurtleSide.RIGHT );
        ResourceLocation[] overlay = getOverlay( stack );
        boolean christmas = HolidayUtil.getCurrentHoliday() == Holiday.CHRISTMAS;
        String label = turtle.getLabel( stack );
        boolean flip = label != null && (label.equals( "Dinnerbone" ) || label.equals( "Grumm" ));

        TurtleModelCombination combo = new TurtleModelCombination( colour != -1, leftUpgrade, rightUpgrade, overlay, christmas, flip );
        return cachedModels.computeIfAbsent( combo, this::buildModel );
    }
}
