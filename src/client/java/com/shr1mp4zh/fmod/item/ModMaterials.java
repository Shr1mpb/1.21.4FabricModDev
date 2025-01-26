package com.shr1mp4zh.fmod.item;

import com.shr1mp4zh.fmod.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.TagKey;

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


}
