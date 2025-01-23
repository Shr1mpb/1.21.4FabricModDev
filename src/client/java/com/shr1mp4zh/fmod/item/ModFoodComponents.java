package com.shr1mp4zh.fmod.item;

import net.minecraft.component.type.FoodComponent;

public class ModFoodComponents {
    public static final FoodComponent CAULIFLOWER = createFoodComponent(3, 1.5f, true);


    /**
     * 辅助方法 用于创建新的自定义食物(无药水效果)
     * @param nutrition 恢复饥饿值
     * @param saturation 恢复饱食度
     * @param  alwaysEdible 一直可食用 (这里对于没有任何效果的食物而言，一般建议设置为false)
     * @return 返回FoodComponent对象
     */
    private static FoodComponent createFoodComponent(int nutrition,float saturation,boolean alwaysEdible) {
        FoodComponent.Builder builder = new FoodComponent.Builder().nutrition(nutrition).saturationModifier(saturation);
        if (alwaysEdible) {
            builder.alwaysEdible();
        }
        return builder.build();
    }


}

