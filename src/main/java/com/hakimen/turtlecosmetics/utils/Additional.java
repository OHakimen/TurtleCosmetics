package com.hakimen.turtlecosmetics.utils;

import com.hakimen.turtlecosmetics.TurtleCosmetics;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber( modid = TurtleCosmetics.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD )
public class Additional {

    @SubscribeEvent
    public static void registerModels( ModelEvent.RegisterAdditional event )
    {
        Overlays.load();
        for (ResourceLocation overlay : Overlays.overlays.values() ){
            event.register(overlay);
        }
    }

}
