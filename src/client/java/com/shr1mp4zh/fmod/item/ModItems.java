package com.shr1mp4zh.fmod.item;

import com.shr1mp4zh.fmod.Shr1mpfmod;
import com.shr1mp4zh.fmod.item.custom.ChiselItem;
import com.shr1mp4zh.fmod.item.custom.HammerItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class ModItems {

    /**
     * 下面开始注册物品 仿照这里的即可
     *      流程：
     *          定义好变量名、定义好物品名(就是这里的createDefaultItemSettings()的参数)、定义好要加入的物品组的名字
     *          要加入的物品组调用ItemGroups.XXX即可获取 可以加也可以不加(如要放进自己的自定义物品类中的情况) 例如这里的 ItemGroups.INGREDIENTS 是原材料
     *
     */
    //注册第一个物品 像这里既加入了原料物品组 又在ModItemGroups类中加入了自定义的物品组里
    public static final Item FIRST_ITEM = registerItem(createDefaultItemSettings("first_item"), ItemGroups.INGREDIENTS);
    //注册一个自定义物品 这里就没有加入任何物品组，只在ModItemGroups里加入了自定义的物品组
    public static final Item CHISEL = registerCustomItem(ChiselItem.class, createDefaultItemSettings("chisel").maxDamage(ChiselItem.MAX_DAMAGE).rarity(Rarity.RARE));
    //注册一个自定义食物 塞西莉亚花(有药效,并且设置了稀有度为Rarity.EPIC，堆叠数量最大16)
    public static final Item CAULIFLOWER = registerCustomFood(createDefaultItemSettings("cauliflower").rarity(Rarity.EPIC).maxCount(16), null, ModFoodComponents.CAULIFLOWER, ModConsumableComponents.CAULIFLOWER,true);
    //注册自定义燃料+堆肥 并设置最大堆叠数量为16 稀有度 并设置发光 设置燃料和堆肥的方法在本类中的最下方
    public static final Item STARLIGHT_ASHES = registerItem(createDefaultItemSettings("starlight_ashes").maxCount(16).rarity(Rarity.UNCOMMON).component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true));

    //注册天外工具
    public static final Item HEAVEN_SWORD = registerToolItem(SwordItem.class, ModMaterials.ModToolMaterials.HEAVEN_TOOL_MATERIAL, 6.0f, -2.0f, createDefaultItemSettings("heaven_sword"), ItemGroups.COMBAT);
    public static final Item HEAVEN_PICKAXE = registerToolItem(PickaxeItem.class, ModMaterials.ModToolMaterials.HEAVEN_TOOL_MATERIAL, 3.0f, -2.4f, createDefaultItemSettings("heaven_pickaxe"), ItemGroups.TOOLS);
    public static final Item HEAVEN_AXE = registerToolItem(AxeItem.class, ModMaterials.ModToolMaterials.HEAVEN_TOOL_MATERIAL, 9.0f, -2.8f, createDefaultItemSettings("heaven_axe"), ItemGroups.COMBAT);
    public static final Item HEAVEN_SHOVEL = registerToolItem(ShovelItem.class, ModMaterials.ModToolMaterials.HEAVEN_TOOL_MATERIAL, 2.0f, -1.6f, createDefaultItemSettings("heaven_shovel"), ItemGroups.TOOLS);
    public static final Item HEAVEN_HOE = registerToolItem(HoeItem.class, ModMaterials.ModToolMaterials.HEAVEN_TOOL_MATERIAL, 1.0f, -1.2f, createDefaultItemSettings("heaven_hoe"), ItemGroups.TOOLS);
    //自定义天外工具：锤
    public static final Item HEAVEN_HAMMER = registerToolItem(HammerItem.class, ModMaterials.ModToolMaterials.HEAVEN_TOOL_MATERIAL, 5.0f, -2.0f, createDefaultItemSettings("heaven_hammer"), ItemGroups.TOOLS);

    //下面都是封装好的方法 勿动
    /**
     * “入口”方法 勿动
     * 用于被 Shr1mpfmod 中的 onInitialize() 调用的方法 导致这个类被加载(这里是JVM的相关机制了 普通人不需要了解太深)
     * 加载的过程有 1.加载 2.链接(验证+准备+解析) 3.初始化
     * 这里初始化就已经注册了其他物品 注册燃料物品和堆肥物品时单独调用一个方法 放在下方
     */
    public static void initialize() {
        Shr1mpfmod.LOGGER.debug("Registering mod items and fuels for" + Shr1mpfmod.MOD_ID);
        registerFuels();
        registerCompostingItems();
    }
    /**
     * 注册物品的方法 勿动
     * @param settings 注册的物品的属性 通过下面的createDefaultItemSettings创建 需要传入id
     * @param itemGroups 要注册到的地方 可以为多个 通过ItemsGroups.XXX拿到
     * @return 返回注册的物品
     */
    @SafeVarargs
    private static Item registerItem(Item.Settings settings, RegistryKey<ItemGroup>... itemGroups) {
        Identifier identifier = settings.getModelId();
        Item registeredItem = Registry.register(Registries.ITEM, identifier, new Item(settings));
        for (RegistryKey<ItemGroup> itemGroup : itemGroups) {
            ItemGroupEvents.modifyEntriesEvent(itemGroup).register(fabricItemGroupEntries -> fabricItemGroupEntries.add(registeredItem));
        }
        return registeredItem;
    }
    /**
     * 注册自定义物品的方法 勿动
     * @param itemClass 注册自定义物品的类型
     * @param settings 注册的物品的属性 通过下面的createDefaultItemSettings创建 需要传入id
     * @param itemGroups 要注册到的地方 可以为多个 通过ItemsGroups.XXX拿到
     * @return 返回注册的物品
     */
    @SafeVarargs
    private static Item registerCustomItem(Class itemClass,Item.Settings settings, RegistryKey<ItemGroup>... itemGroups) {
        Identifier identifier = settings.getModelId();
        Item customItem;
        try {
            //这里用了一下反射 创建一下自定义物品的类(运行时类) 并把Settings封装进去
            customItem = (Item) itemClass.getConstructor(Item.Settings.class).newInstance(settings);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        Item registeredItem = Registry.register(Registries.ITEM, identifier, customItem);
        for (RegistryKey<ItemGroup> itemGroup : itemGroups) {
            ItemGroupEvents.modifyEntriesEvent(itemGroup).register(fabricItemGroupEntries -> fabricItemGroupEntries.add(registeredItem));
        }
        return registeredItem;
    }


    /**
     * 用于快速创建一个食物类型 如果没有药水效果可以留空关于药水的特性
     *
     * @param settings        注册的物品的属性 通过下面的createDefaultItemSettings创建 需要传入id
     * @param customItemClass 如果没有则留空 如果有(例如是药水时会用到其他的Item类或重写Item类的方法)则传入
     * @param foodComponent 创建的FoodComponent 在ModFoodComponent类中 用于填写食物的相关属性
     * @param consumableComponent 加入药水效果的ConsumableComponent 在ModConsumableComponents类中 用于填写消耗物的药效等
     * @param itemGroups      要加入的物品组
     * @return 返回Item类对象
     */
    @SafeVarargs
    private static Item registerCustomFood(Item.Settings settings, Class customItemClass, FoodComponent foodComponent, @Nullable ConsumableComponent consumableComponent,boolean glow, RegistryKey<ItemGroup>... itemGroups) {
        Identifier identifier = settings.getModelId();
        Item customFood;
        try {
            //根据是否传入customItem确定使用的构造器(创建的运行时类)
            Constructor constructor = Objects.requireNonNullElse(customItemClass, Item.class).getConstructor(Item.Settings.class);

            if (consumableComponent == null) {
                customFood = (Item) constructor.newInstance(settings.food(foodComponent));
            }else{
                if (glow){
                    customFood = (Item) constructor.newInstance(settings.food(foodComponent, consumableComponent).component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true));
                }else {
                    customFood = (Item) constructor.newInstance(settings.food(foodComponent, consumableComponent));
                }

            }

        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        Item registeredItem = Registry.register(Registries.ITEM, identifier, customFood);
        for (RegistryKey<ItemGroup> itemGroup : itemGroups) {
            ItemGroupEvents.modifyEntriesEvent(itemGroup).register(fabricItemGroupEntries -> fabricItemGroupEntries.add(registeredItem));
        }
        return registeredItem;
    }

    /**
     * 用于快速创建 工具物品 的方法
     * @param material 剑的材料 一般放在ModMaterials.ModToolMaterials中
     * @param attackDamage (攻击伤害 = 原料附加伤害 + 这里的伤害 + 1) 钻石是 3 + 3 + 1 = 7
     * @param attackSpeed 攻击速度!!负数 钻石剑 （-2.4F） 钻石斧（-3.0F） 越小越慢 注意是负数 游戏内显示的攻击速度是 4 + attackSpeed 越高越快
     * @param settings createDefaultItemSettings创建的settings
     * @param itemGroups 要加入的组
     * @return 返回创建好的工具
     */
    @SafeVarargs
    private static Item registerToolItem(Class toolClass,ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings, RegistryKey<ItemGroup>... itemGroups) {
        Identifier identifier = settings.getModelId();
        Item customItem;
        try {
            Constructor constructor = Objects.requireNonNull(toolClass).getConstructor(ToolMaterial.class,float.class,float.class,Item.Settings.class);
            customItem = (Item) constructor.newInstance(material, attackDamage, attackSpeed, settings);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        Item registeredItem = Registry.register(Registries.ITEM, identifier, customItem);
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
        return new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM,identifier));
    }

    /**
     * 注册燃料物品 把要注册的物品写在下面的lambda即可
     */
    public static void registerFuels() {
        FuelRegistryEvents.BUILD.register((builder, context) -> {
            // 可以一次在这个 lambda 中添加多个物品。 value的值是燃烧时间 单位是tick 经测试最大为烧制105个
            builder.add(STARLIGHT_ASHES, 200 * 100);
        });
    }

    /**
     * 注册堆肥物品 把要注册的物品写在下面即可
     */
    public static void registerCompostingItems() {
        // 这里仿照这个语句写 来注册可堆肥的物品 后面的Float类型是堆肥概率
        CompostingChanceRegistry.INSTANCE.add(STARLIGHT_ASHES, 1F);
    }
}