package com.shr1mp4zh.fmod.component;

import com.shr1mp4zh.fmod.Shr1mpfmod;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
    public static final ComponentType<BlockPos> COORDINATES = registerDataComponent("coordinates", builder -> builder.codec(BlockPos.CODEC));


    private static <T>ComponentType<T> registerDataComponent(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(Shr1mpfmod.MOD_ID, name),
                builderOperator.apply(ComponentType.builder()).build());
    }

    public static void registerDataComponentTypes() {
        Shr1mpfmod.LOGGER.info("Registering Data Component Types for " + Shr1mpfmod.MOD_ID);
    }
}
