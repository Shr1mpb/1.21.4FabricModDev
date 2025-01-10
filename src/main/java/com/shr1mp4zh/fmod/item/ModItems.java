package com.shr1mp4zh.fmod.item;

import com.shr1mp4zh.fmod.Shr1mpfmod;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;

public class ModItems {

    /**
     * 下面开始注册物品 仿照这里的即可
     *      流程：
     *          定义好变量名、定义好物品名(就是这里的createDefaultItemSettings()的参数)、定义好要加入的物品组的名字
     *          要加入的物品组调用ItemGroups.XXX即可获取 例如这里的 ItemGroups.INGREDIENTS 是原材料
     *
     */
    //注册第一个物品
    public static final Item FIRST_ITEM = registerItem(createDefaultItemSettings("first_item"), ItemGroups.INGREDIENTS);





    //下面都是封装好的方法 勿动
    /**
     * “入口”方法 勿动
     * 用于被 Shr1mpfmod 中的 onInitialize() 调用的方法 导致这个类被加载(这里是JVM的相关机制了 普通人不需要了解太深)
     * 加载的过程有 1.加载 2.链接(验证+准备+解析) 3.初始化
     */
    public static void initialize() {
        Shr1mpfmod.LOGGER.debug("Registering mod items for" + Shr1mpfmod.MOD_ID);
    }
    /**
     * 注册物品的方法 勿动
     * @param settings 注册的物品的属性 通过下面的createDefaultItemSettings创建 需要传入id
     * @param itemGroups 要注册到的地方 可以为多个 通过ItemsGroups.XXX拿到
     * @return 返回注册的物品
     */
    public static Item registerItem(Item.Settings settings, RegistryKey<ItemGroup>... itemGroups) {
        Identifier identifier = settings.getModelId();
        Item registeredItem = Registry.register(Registries.ITEM, identifier, new Item(settings));
        for (RegistryKey<ItemGroup> itemGroup : itemGroups) {
            ItemGroupEvents.modifyEntriesEvent(itemGroup).register(fabricItemGroupEntries -> fabricItemGroupEntries.add(registeredItem));
        }
        return registeredItem;
    }

    /**
     * 根据物品名字创建一个带有ID的Item.Settings 勿动
     * @param id 物品ID
     * @return 返回Item.Settings
     */
    public static Item.Settings createDefaultItemSettings(String id) {
        Identifier identifier = Identifier.of(Shr1mpfmod.MOD_ID, id);
        return new Item.Settings().registryKey(RegistryKey.of(RegistryKey.ofRegistry(identifier),identifier));
    }
}