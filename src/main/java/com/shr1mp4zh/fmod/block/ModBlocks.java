package com.shr1mp4zh.fmod.block;

import com.shr1mp4zh.fmod.Shr1mpfmod;
import com.shr1mp4zh.fmod.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
/*
    Block的注册较为复杂 这里也封装成了一行代码
        传入内部类对象(包含blockSettings 和 itemSettings) 和 要加入的组 即可
        blockSettings用于设置方块相关的属性 itemSettings用于设置物品相关的属性 可以使用get方法得到以后进行设置
        创建默认的BlockSettingUnion的方法是 BlockSettingUnion.createDefaultBlockSettingUnion("id")
    解释大致如下：
        注册Block 需要一个AbstractBlockSettings类的参数 注册block时要向注册表注册block ; 注册Block紧接着的是要注册BlockItem
        注册BlockItem 与注册Item类似 最后可以将item加入到创造模式物品栏中
 */
public class ModBlocks {

    private static final Block FIRST_BLOCK = registerBlock(BlockSettingUnion.createDefaultBlockSettingUnion("first_block"), ItemGroups.BUILDING_BLOCKS);
    private static final Block SECOND_BLOCK;
    static{
        BlockSettingUnion secondBlockSetting = BlockSettingUnion.createDefaultBlockSettingUnion("second_block");
        secondBlockSetting.getBlockSettings().sounds(BlockSoundGroup.NETHER_GOLD_ORE).strength(2f);
        SECOND_BLOCK =  registerBlock(secondBlockSetting, ItemGroups.NATURAL);
    }
    /**
     * 注册Block 传入两个设置(封装在内部类中) 勿动
     * @param blockSettingUnion 内部类对象 包含两个设置
     * @param itemGroups 要加入的组
     * @return 返回添加的Block
     */
    @SafeVarargs
    private static Block registerBlock(BlockSettingUnion blockSettingUnion, RegistryKey<ItemGroup>... itemGroups) {
        AbstractBlock.Settings blockSettings = blockSettingUnion.getBlockSettings();
        Item.Settings itemSettings = blockSettingUnion.getItemSettings();
        Block block = new Block(blockSettings);
        registerBlockItem(block, itemSettings, itemGroups);
        return Registry.register(Registries.BLOCK, itemSettings.getModelId(), block);
    }


    /**
     * 创建默认的方块设置 在调用BlockSettingUnion.createDefaultBlockSettingUnion()时被创建 封装在BlockSettingUnion对象中
     * 这里默认设置 强度为4f 需要工具 声音为 BlockSoundGroup.AMETHYST_BLOCK (紫水晶声音)
     * @param id 方块id
     * @return 返回设置
     */
    public static AbstractBlock.Settings createDefaultAbstractBlockSettings(String id) {
        return AbstractBlock.Settings.create().strength(4f)
                .requiresTool().sounds(BlockSoundGroup.AMETHYST_BLOCK).registryKey(createDefaultBlockRegistryKey(id));
    }


    /**
     * 辅助方法 用于将Block对应的BlockItem注册并添加到物品栏中 勿动
     * @param block block
     * @param itemGroups 要注册的物品栏
     */
    @SafeVarargs
    private static void registerBlockItem(Block block, Item.Settings itemSettings, RegistryKey<ItemGroup>... itemGroups) {
        Identifier identifier = itemSettings.getModelId();
        BlockItem registeredBlockItem = Registry.register(Registries.ITEM, identifier,
                new BlockItem(block, itemSettings));
        for (RegistryKey<ItemGroup> itemGroup : itemGroups) {
            ItemGroupEvents.modifyEntriesEvent(itemGroup).register(fabricItemGroupEntries -> fabricItemGroupEntries.add(registeredBlockItem));
        }
    }



    /**
     * 传入id 注册默认的 RegistryKey<Block> 勿动
     * @param id 物品id
     * @return 返回生成的 RegistryKey<Block> 对象
     */
    private static RegistryKey<Block> createDefaultBlockRegistryKey(String id) {
        Identifier identifier = Identifier.of(Shr1mpfmod.MOD_ID, id);
        return RegistryKey.of(RegistryKeys.BLOCK, identifier);
    }


    /**
     * 用于被 Shr1mpfmod类调用 以注册方块 勿动
     */
    public static void initialize() {
        Shr1mpfmod.LOGGER.debug("Registering mod blocks for" + Shr1mpfmod.MOD_ID);
    }


    /**
     * 静态内部类 封装了Block注册时要求的两个settings 勿动
     */
    static class BlockSettingUnion {
        AbstractBlock.Settings blockSettings;
        Item.Settings itemSettings;

        public static BlockSettingUnion createDefaultBlockSettingUnion(String id) {
            return new BlockSettingUnion(id);
        }
        private BlockSettingUnion(String id){
            this.blockSettings = createDefaultAbstractBlockSettings(id);
            this.itemSettings = ModItems.createDefaultItemSettings(id);
        }

        public AbstractBlock.Settings getBlockSettings() {
            return blockSettings;
        }

        public Item.Settings getItemSettings() {
            return itemSettings;
        }

        public void setAnotherSettings(AbstractBlock.Settings blockSettings) {
            this.blockSettings = blockSettings;
        }

        public void setAnotherItemSettings(Item.Settings itemSettings) {
            this.itemSettings = itemSettings;
        }
    }
}
