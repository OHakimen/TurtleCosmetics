package com.hakimen.turtlecosmetics.utils;

import com.hakimen.turtlecosmetics.TurtleCosmetics;

import com.hakimen.turtlecosmetics.api.Overlay;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber( modid = TurtleCosmetics.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD )
public class RegisterAdditionalResources {

    @SubscribeEvent
    public static void registerModels( ModelEvent.RegisterAdditional event )
    {
        Overlays.clear();
        Overlays.load();
        for (Overlay overlay : Overlays.overlays ){
            event.register(overlay.getOverlay());
        }
    }

}
