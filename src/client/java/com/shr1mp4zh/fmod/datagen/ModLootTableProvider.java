package com.shr1mp4zh.fmod.datagen;

import com.shr1mp4zh.fmod.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    //注意这里要把构造器设置为public
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        //添加掉落 (自使用datagen以来的简单的新的方块)
        addDrop(ModBlocks.FIRST_FENCE);
        addDrop(ModBlocks.FIRST_SLAB, slabDrops(ModBlocks.FIRST_SLAB));
        addDrop(ModBlocks.FIRST_STAIR);
        addDrop(ModBlocks.FIRST_BUTTON);
        addDrop(ModBlocks.FIRST_WALL);
        addDrop(ModBlocks.HEAVEN_LAMP_BLOCK);
    }
}
