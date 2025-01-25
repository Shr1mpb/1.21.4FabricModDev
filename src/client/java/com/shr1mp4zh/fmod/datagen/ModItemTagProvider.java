package com.shr1mp4zh.fmod.datagen;

import com.shr1mp4zh.fmod.item.ModItems;
import com.shr1mp4zh.fmod.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {


    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        //配置magic_block转换的物品
        getOrCreateTagBuilder(ModTags.Items.MAGIC_BLOCK_TRANSFORMABLE)
                .add(ModItems.STARLIGHT_ASHES);
    }
}
