package com.shr1mp4zh.fmod.block;

import com.shr1mp4zh.fmod.Shr1mpfmod;
import com.shr1mp4zh.fmod.block.custom.MagicBlock;
import com.shr1mp4zh.fmod.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
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
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.lang.reflect.InvocationTargetException;


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

    public static final Block FIRST_BLOCK;
    public static final Block SECOND_BLOCK;
    public static final Block MAGIC_BLOCK;
    static{
        BlockSettingUnion firstBlockSetting = BlockSettingUnion.createDefaultBlockSettingUnion("first_block",true);
        firstBlockSetting.getBlockSettings().strength(6f);
        FIRST_BLOCK = registerBlock(firstBlockSetting, ItemGroups.BUILDING_BLOCKS);

        BlockSettingUnion secondBlockSetting = BlockSettingUnion.createDefaultBlockSettingUnion("second_block",true);
        secondBlockSetting.getBlockSettings().sounds(BlockSoundGroup.DEEPSLATE).strength(12f);
        SECOND_BLOCK = registerExperienceBlock(secondBlockSetting, 10, 12, ItemGroups.NATURAL);

        BlockSettingUnion magicBlockSetting = BlockSettingUnion.createDefaultBlockSettingUnion("magic_block", false);
        MAGIC_BLOCK = registerCustomBlock(MagicBlock.class, magicBlockSetting);
    }


    /**
     * 注册Block  传入两个设置(封装在内部类中) 勿动
     * @param blockSettingUnion 内部类对象 包含两个设置
     * @param itemGroups 要加入的组
     * @return 返回添加的Block
     */
    @SafeVarargs
    private static Block registerBlock(BlockSettingUnion blockSettingUnion,RegistryKey<ItemGroup>... itemGroups) {
        AbstractBlock.Settings blockSettings = blockSettingUnion.getBlockSettings();
        Item.Settings itemSettings = blockSettingUnion.getItemSettings();
        Block block = new Block(blockSettings);
        registerBlockItem(block, itemSettings, itemGroups);
        return Registry.register(Registries.BLOCK, itemSettings.getModelId(), block);
    }

    /**
     * 注册自定义的Block  传入两个设置(封装在内部类中) 勿动
     *
     * @param blockClass        自定义Block的类型
     * @param blockSettingUnion 内部类对象 包含两个设置
     * @param itemGroups        要加入的组
     * @return 返回添加的Block
     */
    @SafeVarargs
    private static Block registerCustomBlock(Class blockClass, BlockSettingUnion blockSettingUnion, RegistryKey<ItemGroup>... itemGroups) {
        AbstractBlock.Settings blockSettings = blockSettingUnion.getBlockSettings();
        Item.Settings itemSettings = blockSettingUnion.getItemSettings();
        Block block;
        try {
            //用反射创建自定义方块类的运行时对象
            block = (Block) blockClass.getConstructor(AbstractBlock.Settings.class).newInstance(blockSettings);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        registerBlockItem(block, itemSettings, itemGroups);
        return Registry.register(Registries.BLOCK, itemSettings.getModelId(), block);
    }


    /**
     * 注册会掉落经验的Block 即ExperienceBlock 传入两个设置(封装在内部类中) 勿动
     * @param blockSettingUnion 内部类对象 包含两个设置
     * @param itemGroups 要加入的组
     * @param minExp 最小掉落的经验
     * @param maxExp 最多掉落的经验
     * @return 返回添加的Block
     */
    @SafeVarargs
    private static Block registerExperienceBlock(BlockSettingUnion blockSettingUnion, int minExp, int maxExp,RegistryKey<ItemGroup>... itemGroups) {
        AbstractBlock.Settings blockSettings = blockSettingUnion.getBlockSettings();
        Item.Settings itemSettings = blockSettingUnion.getItemSettings();
        Block block = new ExperienceDroppingBlock(UniformIntProvider.create(minExp,maxExp), blockSettings);
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
        return AbstractBlock.Settings.create().strength(3f)
                .sounds(BlockSoundGroup.AMETHYST_BLOCK).registryKey(createDefaultBlockRegistryKey(id));
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

        public static BlockSettingUnion createDefaultBlockSettingUnion(String id, boolean requiresTool) {
            return new BlockSettingUnion(id,requiresTool);
        }
        private BlockSettingUnion(String id,boolean requiresTool){
            this.blockSettings = createDefaultAbstractBlockSettings(id);
            this.itemSettings = ModItems.createDefaultItemSettings(id);
            if (requiresTool) {//需要工具时 再设置requiresTool()
                blockSettings.requiresTool();
            }
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
