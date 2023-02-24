package com.hakimen.turtlecosmetics.utils;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.resource.*;

import java.util.ArrayList;
import java.util.List;

public class Config {
    public static final ForgeConfigSpec.Builder clientConfig = new ForgeConfigSpec.Builder();;
    public static final ForgeConfigSpec clientSpec;
    public static ForgeConfigSpec.ConfigValue<List<String>> cosmetics;
    public static ForgeConfigSpec.ConfigValue<List<String>> labelNames;

    static {
        clientConfig.push("Client Side Configs for Turtle Cosmetics");

        cosmetics = clientConfig.comment("Paths for resource locations (namespace:resource)").define("resources", new ArrayList<>());
        labelNames = clientConfig.comment("Labels for the overlays (has to be same size as the paths)").define("label", new ArrayList<>());

        clientConfig.pop();
        clientSpec = clientConfig.build();
    }

}
