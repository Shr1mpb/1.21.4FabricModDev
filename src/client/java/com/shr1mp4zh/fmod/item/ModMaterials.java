package com.shr1mp4zh.fmod.item;

import com.shr1mp4zh.fmod.Shr1mpfmod;
import com.shr1mp4zh.fmod.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.Map;

public class ModMaterials {
    public static class ModToolMaterials{
        //注册heaven_tools的原料
        public static final ToolMaterial HEAVEN_TOOL_MATERIAL = createToolMaterial(ModTags.Blocks.INCORRECT_FOR_HEAVEN_TOOL,
                2500, 12.0f, 5.0f, 25, ModTags.Items.REPAIRS_HEAVEN_ARMOR);


        /**
         * 辅助方法 用于创建工具的材料
         * @param incorrectFor 不能采掘的方块 标签
         * @param durability 耐久 钻石2031
         * @param speed 采掘速度 钻石8.0 金12.0 下界合金9.0
         * @param attackDamageBonus 额外伤害 钻石3.0 下界合金4.0
         * @param enchantmentValue 附魔属性 越高越容易附魔 钻石10 金22
         * @param repairItems 用于修补的材料 标签
         * @return 返回创建好的ToolMaterial
         */
        private static ToolMaterial createToolMaterial(TagKey<Block> incorrectFor, int durability,
                                                       float speed, float attackDamageBonus, int enchantmentValue,
                                                       TagKey<Item> repairItems) {
            return new ToolMaterial(incorrectFor, durability, speed, attackDamageBonus, enchantmentValue, repairItems);
        }
    }


    public static class ModArmorMaterials{
        //注册天外防具的材料
        public static final ArmorMaterial HEAVEN_ARMOR_MATERIAL = createArmorMaterial("heaven", 500, Map.of(
                EquipmentType.HELMET, 3,
                EquipmentType.CHESTPLATE, 8,
                EquipmentType.LEGGINGS, 6,
                EquipmentType.BOOTS, 3
        ), 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 3, 0, ModTags.Items.REPAIRS_HEAVEN_ARMOR);
        //TODO: 修复好了击退抗性后 为天外盔甲添加击退抗性

        /**
         * 辅助方法 用于创建防具的材料
         * @param armorPrefix 盔甲id的前缀 这里注意要和注册item时对应 并且不要与其他的东西冲突 这里的前缀是盔甲模型寻路时用到的
         * @param baseDurability 基础材料耐久度 每个部位的耐久根据它计算出来 建议配置为常量
         * @param map 仿照示例书写，给每个部位的盔甲一个防御值 钻石示例：3 8 6 3 共计20
         * @param enchantmentValue 附魔属性 越高越容易附魔 钻石10 金22
         * @param sound 穿戴的声音 从 SoundEvents类 中获取
         * @param toughness 盔甲韧性 钻石甲每件2
         * @param knockbackResistance 击退抗性 0.118Fabric版本貌似还有问题，会把这里填入的击退抗性乘以10 故暂先不用
         * @param repairIngredient 修复用的材料 标签
         * @return 返回创建好的ArmorMaterial
         */
        private static ArmorMaterial createArmorMaterial(String armorPrefix,int baseDurability, Map<EquipmentType, Integer> map,
                                                         int enchantmentValue,RegistryEntry<SoundEvent> sound,
                                                         int toughness,int knockbackResistance,
                                                         TagKey<Item> repairIngredient) {

            RegistryKey<EquipmentAsset> equipmentAssetRegistryKey = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Shr1mpfmod.MOD_ID, armorPrefix));
            return new ArmorMaterial(baseDurability, map, enchantmentValue, sound, toughness,
                    knockbackResistance, repairIngredient, equipmentAssetRegistryKey);
        }



    }


}
