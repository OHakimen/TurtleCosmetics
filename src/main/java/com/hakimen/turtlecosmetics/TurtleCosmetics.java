package com.hakimen.turtlecosmetics;

import com.hakimen.turtlecosmetics.utils.Config;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

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
        MinecraftForge.EVENT_BUS.register(this);
    }


}
