package com.shr1mp4zh.fmod.datagen;

import com.shr1mp4zh.fmod.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        //这里配置使用稿可采掘
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.FIRST_STAIR)
                .add(ModBlocks.FIRST_SLAB)
                .add(ModBlocks.FIRST_BUTTON)
                .add(ModBlocks.FIRST_WALL)
                .add(ModBlocks.FIRST_FENCE)
                .add(ModBlocks.FIRST_BLOCK)
                .add(ModBlocks.SECOND_BLOCK)
                .add(ModBlocks.HEAVEN_LAMP_BLOCK);
        //钻石等级采掘
        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.SECOND_BLOCK);

        //前三个不必须， FENCES、WALLS如果不加会导致不能相互连接
        getOrCreateTagBuilder(BlockTags.STAIRS).add(ModBlocks.FIRST_STAIR);
        getOrCreateTagBuilder(BlockTags.SLABS).add(ModBlocks.FIRST_SLAB);
        getOrCreateTagBuilder(BlockTags.BUTTONS).add(ModBlocks.FIRST_BUTTON);
        getOrCreateTagBuilder(BlockTags.FENCES).add(ModBlocks.FIRST_FENCE);
        getOrCreateTagBuilder(BlockTags.WALLS).add(ModBlocks.FIRST_WALL);


    }

}
