package com.hakimen.turtlecosmetics.api;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;

public class Overlays {
    public static final HashMap<String, ResourceLocation> overlays = new HashMap<>();

    public static void add(String label, ResourceLocation resourceLocation){
        overlays.put(label.toLowerCase(),resourceLocation);
    }

    public static ResourceLocation get(String label){
        return overlays.getOrDefault(label.toLowerCase(),null);
    }

    public static boolean containsLabel(String label){
        return overlays.containsKey(label.toLowerCase());
    }
}
