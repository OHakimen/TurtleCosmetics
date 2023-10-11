package com.hakimen.turtlecosmetics.utils;

import com.hakimen.turtlecosmetics.TurtleCosmetics;


import com.hakimen.turtlecosmetics.api.Overlays;
import com.hakimen.turtlecosmetics.config.ClientConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber( modid = TurtleCosmetics.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD )
public class RegisterAdditionalResources {

    @SubscribeEvent
    public static void registerModels( ModelEvent.RegisterAdditional event )
    {
        for (int i = 0; i < ClientConfig.labels.get().size(); i++) {
            Overlays.add(ClientConfig.labels.get().get(i),new ResourceLocation(ClientConfig.resourceLocations.get().get(i)));
        }

        System.out.println(Overlays.overlays);

        Overlays.overlays.forEach((key,value)->{
            event.register(value);
        });
    }

}
