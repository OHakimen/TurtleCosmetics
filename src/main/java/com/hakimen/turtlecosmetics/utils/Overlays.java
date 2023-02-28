package com.hakimen.turtlecosmetics.utils;

import com.hakimen.turtlecosmetics.TurtleCosmetics;
import com.hakimen.turtlecosmetics.api.Overlay;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Overlays {
    static ArrayList<Overlay> overlays = new ArrayList<Overlay>();
    public static void load(){
        var cosmeticPaths = Config.cosmetics.get();
        var labels = Config.labelNames.get();
        for (int i = 0; i < cosmeticPaths.size(); i++) {
            var path = cosmeticPaths.get(i).split(":");
            var resource = new ResourceLocation(path[0],path[1]);
            overlays.add(new Overlay(labels.get(i),resource));
            TurtleCosmetics.LOGGER.info("[CC Cosmetics] Registered "+ resource + " with label "+labels.get(i));
        }
    }

    public static void clear(){
        overlays.clear();
    }
    public static List<Overlay> getOverlays(){
        return overlays.subList(0,overlays.size());
    }

    public static void addOverlay(Overlay overlay){
        overlays.add(overlay);
    }
    public static void addOverlays(List<Overlay> newOverlays){
        overlays.addAll(newOverlays);
    }

    public static Overlay getOverlay(int index){
        return overlays.get(index);
    }
}
