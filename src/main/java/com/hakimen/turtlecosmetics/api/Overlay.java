package com.hakimen.turtlecosmetics.api;

import net.minecraft.resources.ResourceLocation;

public class Overlay {
    String label;
    ResourceLocation overlayLocation;

    public Overlay(String label, ResourceLocation overlayLocation) {
        this.label = label;
        this.overlayLocation = overlayLocation;
    }

    public String getLabel() {
        return label;
    }

    public ResourceLocation getOverlay() {
        return overlayLocation;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setOverlayLocation(ResourceLocation overlayLocation) {
        this.overlayLocation = overlayLocation;
    }
}
