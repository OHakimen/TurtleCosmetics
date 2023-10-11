package com.hakimen.turtlecosmetics.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class ClientConfig {
    public final static ForgeConfigSpec.Builder clientConfigBuilder = new ForgeConfigSpec.Builder();

    public final static ForgeConfigSpec clientConfigSpec;

    public final static ForgeConfigSpec.ConfigValue<List<String>> labels;
    public final static ForgeConfigSpec.ConfigValue<List<String>> resourceLocations;

    static {
        clientConfigBuilder.push("Client Side Configs for CC Cosmetics");

        resourceLocations = clientConfigBuilder.comment("Paths for resource locations (namespace:resource)").define("resources", new ArrayList<>());
        labels = clientConfigBuilder.comment("Labels for the overlays (has to be same size as the paths)").define("label", new ArrayList<>());

        clientConfigBuilder.pop();
        clientConfigSpec = clientConfigBuilder.build();
    }

}
