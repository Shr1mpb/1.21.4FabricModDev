package com.shr1mp4zh.fmod.util;

import com.shr1mp4zh.fmod.Shr1mpfmod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

/**
 * 这里是创建自定义标签(Tag)的工具类
 */
public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_HEAVEN_TOOLS = createTag("need_heaven_tools");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(Shr1mpfmod.MOD_ID, name));
        }
    }

    public static class Items {
//        public static final TagKey<Item>  =

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(Shr1mpfmod.MOD_ID, name));
        }
    }
}
