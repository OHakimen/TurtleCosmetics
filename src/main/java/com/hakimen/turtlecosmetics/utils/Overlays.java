package com.hakimen.turtlecosmetics.utils;

import com.hakimen.turtlecosmetics.TurtleCosmetics;
import dan200.computercraft.client.ClientRegistry;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;

public class Overlays {
    public static HashMap<String, ResourceLocation> overlays = new HashMap<>();
    public static void load(){
        var cosmeticPaths = Config.cosmetics.get();
        var labels = Config.labelNames.get();
        for (int i = 0; i < cosmeticPaths.size(); i++) {
            var path = cosmeticPaths.get(i).split(":");
            var resource = new ResourceLocation(path[0],path[1]);
            overlays.put(labels.get(i),resource);
            TurtleCosmetics.LOGGER.info("[Overlays] Registered "+ resource + " with label "+labels.get(i));
        }
    }
}
