package com.shr1mp4zh.fmod.datagen;

import com.shr1mp4zh.fmod.item.ModItems;
import com.shr1mp4zh.fmod.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

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

        //配置可以修复heaven_tool的物品
        getOrCreateTagBuilder(ModTags.Items.REPAIRS_HEAVEN_ARMOR)
                .add(ModItems.FIRST_ITEM);

        //让工具限定附魔 需要将工具添加进入对应的Tag
        getOrCreateTagBuilder(ItemTags.SWORDS)
                .add(ModItems.HEAVEN_SWORD);
        getOrCreateTagBuilder(ItemTags.PICKAXES)
                .add(ModItems.HEAVEN_PICKAXE)
                .add(ModItems.HEAVEN_HAMMER);
        getOrCreateTagBuilder(ItemTags.AXES)
                .add(ModItems.HEAVEN_AXE);
        getOrCreateTagBuilder(ItemTags.SHOVELS)
                .add(ModItems.HEAVEN_SHOVEL);
        getOrCreateTagBuilder(ItemTags.HOES)
                .add(ModItems.HEAVEN_HOE);

    }
}
