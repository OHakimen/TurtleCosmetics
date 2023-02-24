package com.hakimen.turtlecosmetics;

import com.hakimen.turtlecosmetics.utils.Config;
import com.hakimen.turtlecosmetics.utils.Overlays;
import com.mojang.logging.LogUtils;
import net.minecraft.client.resources.PackResourcesAdapterV4;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.VanillaPackResources;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TurtleCosmetics.MODID)
public class TurtleCosmetics {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "turtlecosmetics";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public TurtleCosmetics() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.clientSpec, "turtle-cosmetics-client.toml");

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


        // Register the commonSetup method for modloading
        modEventBus.addListener(this::client);



        MinecraftForge.EVENT_BUS.register(this);
    }

    private void client(final FMLClientSetupEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

}
