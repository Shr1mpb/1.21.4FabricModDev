package com.shr1mp4zh.fmod.item;

import com.shr1mp4zh.fmod.Shr1mpfmod;
import com.shr1mp4zh.fmod.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ModItemGroups {
    //仿照这里的语句以新建一个物品组
    public static final ItemGroup FIRST_ITEM_GROUP;
    static {
        ArrayList<Object> firstItemList = new ArrayList<>();
        firstItemList.add(ModItems.FIRST_ITEM);
        firstItemList.add(ModBlocks.FIRST_BLOCK);
        firstItemList.add(ModBlocks.SECOND_BLOCK);
        firstItemList.add(ModItems.CHISEL);
        firstItemList.add(ModBlocks.MAGIC_BLOCK);
        firstItemList.add(ModItems.CAULIFLOWER);
        firstItemList.add(ModItems.STARLIGHT_ASHES);
        firstItemList.add(ModBlocks.FIRST_STAIR);
        firstItemList.add(ModBlocks.FIRST_SLAB);
        firstItemList.add(ModBlocks.FIRST_BUTTON);
        firstItemList.add(ModBlocks.FIRST_FENCE);
        firstItemList.add(ModBlocks.FIRST_WALL);

        FIRST_ITEM_GROUP = registerItemGroup("first_item_group",firstItemList);
    }


    /**
     * 辅助方法 用于创建物品组
     * 创建出的物品组 显示的图标是itemList的首元素 显示的翻译是itemgroups.【MODID】.【itemGroupId】
     * @param itemGroupId 物品组的id
     * @param itemList 要加入的物品 可以是item Block等等
     * @return 返回创建的物品组
     */
    private static <T> ItemGroup registerItemGroup(String itemGroupId, List<T> itemList) {
        String translatePath = "itemgroups." + Shr1mpfmod.MOD_ID + "." + itemGroupId;
        return Registry.register(Registries.ITEM_GROUP,
                Identifier.of(Shr1mpfmod.MOD_ID, itemGroupId),
                FabricItemGroup.builder()
                        .icon(()->new ItemStack((ItemConvertible) itemList.getFirst()))
                        .displayName(Text.translatable(translatePath))
                        .entries(((displayContext, entries) -> {
                            for (T item : itemList) {
                                entries.add((ItemConvertible) item);
                            }
                        }))
                    .build() );
    }
    /**
     * 初始化 供入口类调用以加载该类
     */
    public static void initialize() {
        Shr1mpfmod.LOGGER.debug("Registering mod item groups for" + Shr1mpfmod.MOD_ID);
    }
}
