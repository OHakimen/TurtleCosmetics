package com.hakimen.turtlecosmetics.utils;

import com.hakimen.turtlecosmetics.TurtleCosmetics;
import com.hakimen.turtlecosmetics.api.Overlay;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

public class Overlays {
    public static ArrayList<Overlay> overlays = new ArrayList<Overlay>();
    public static void load(){
        var cosmeticPaths = Config.cosmetics.get();
        var labels = Config.labelNames.get();
        for (int i = 0; i < cosmeticPaths.size(); i++) {
            var path = cosmeticPaths.get(i).split(":");
            var resource = new ResourceLocation(path[0],path[1]);
            overlays.add(new Overlay(labels.get(i),resource));
            TurtleCosmetics.LOGGER.info("[Overlays] Registered "+ resource + " with label "+labels.get(i));
        }
    }
}
