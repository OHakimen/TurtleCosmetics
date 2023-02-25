package com.hakimen.turtlecosmetics.utils;

import dan200.computercraft.api.turtle.ITurtleUpgrade;
import net.minecraft.resources.ResourceLocation;

public record TurtleModelCombination(
        boolean colour,
        ITurtleUpgrade leftUpgrade,
        ITurtleUpgrade rightUpgrade,
        ResourceLocation[] overlay,
        boolean christmas,
        boolean flip
)
{
}
