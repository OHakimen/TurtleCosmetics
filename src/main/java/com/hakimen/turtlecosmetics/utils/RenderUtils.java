package com.hakimen.turtlecosmetics.utils;

import com.hakimen.turtlecosmetics.api.Overlay;
import dan200.computercraft.ComputerCraft;
import dan200.computercraft.api.ComputerCraftAPI;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

public class RenderUtils {

    private static final ResourceLocation ELF_OVERLAY_MODEL = new ResourceLocation( ComputerCraftAPI.MOD_ID, "block/turtle_elf_overlay" );


    public static ResourceLocation[] getTurtleOverlayModel( ResourceLocation overlay[], boolean christmas)
    {
        if( overlay != null ) return overlay;
        if( christmas ) return new ResourceLocation[]{ELF_OVERLAY_MODEL};
        return null;
    }
}
