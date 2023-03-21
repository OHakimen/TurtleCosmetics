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

    protected static void clear(){
        overlays.clear();
    }

    /**
     *  Gets all overlays
     * @return a <code>List</code> of all overlays registered;
     */
    public static List<Overlay> getOverlays(){
        return overlays.subList(0,overlays.size());
    }
    /**
     * Register a Overlay
     * @param overlay an overlay to add
     */
    public static void addOverlay(Overlay overlay){
        overlays.add(overlay);
    }

    /**
     * Register a list of overlays
     * @param newOverlays a list of overlays to add
     */
    public static void addOverlays(List<Overlay> newOverlays){
        overlays.addAll(newOverlays);
    }
    /**
     * Get an overlay by index
     * @param index the list index of the Overlay
     * @return an Overlay got from the List using the index
     */
    public static Overlay getOverlay(int index){
        return overlays.get(index);
    }
}
