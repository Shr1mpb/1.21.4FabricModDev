package com.shr1mp4zh.fmod.item;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.registry.entry.RegistryEntry;
import org.jetbrains.annotations.Nullable;

/**
 * 创建药水效果的类 Effect类的参数的说明详见Effect类中的注释
 */
public class ModConsumableComponents {

    //这里是塞西莉亚花的效果：两分钟的发光 一秒的瞬间治疗 一分钟的速度II、夜视I、力量II、抗火I、抗性提升I
    public static final ConsumableComponent CAULIFLOWER = createConsumableComponent(null,
            new Effect(StatusEffects.GLOWING, 2400),
            new Effect(StatusEffects.INSTANT_HEALTH, 20, 5),
            new Effect(StatusEffects.SPEED, 1200, 1),
            new Effect(StatusEffects.NIGHT_VISION, 1200),
            new Effect(StatusEffects.STRENGTH, 1200, 1),
            new Effect(StatusEffects.FIRE_RESISTANCE, 1200),
            new Effect(StatusEffects.RESISTANCE, 1200)
    );


    /**
     * 创建自定义的添加药水效果的消耗品
     * @param consumeSeconds 消耗时间(吃掉或喝掉用的时间)可以留空默认
     * @param effects 传入Effect对象作为参数 Effect对象使用构造器就很方便 如果没有effects也可以设置吃掉的时间
     */
    private static ConsumableComponent createConsumableComponent(@Nullable Float consumeSeconds, Effect... effects) {
        ConsumableComponent.Builder builder = ConsumableComponent.builder();
        for (Effect effect : effects) {
            if (effect.amplifier != null) {
                 builder.consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(effect.statusEffect, effect.duration,effect.amplifier)));
            }else{
                 builder.consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(effect.statusEffect, effect.duration)));
            }
        }
        if (consumeSeconds != null) {
            builder.consumeSeconds(consumeSeconds);
        }
        return builder.build();
    }


    /**
     * 静态内部类Effect 用于当做传入createConsumableComponent()方法参数 或者使用add方法加入 哪个顺手按哪个来
     * 内外部类可以互相访问private成员 但是还是加上了
     */
    private static class Effect{
        /**
         * 自定义药水效果类 可以创建多个然后使用createConsumableComponent传入
         * 因为后面的三项可能是不需要的 所以设置了多个构造器
         *  statusEffect 药水效果 StatusEffects类通过获取
         *  duration 持续时间 单位：tick  默认1tick = 0.05s
         *  amplifier 升高的等级 可以留空默认(0) 游戏内药效等级 = amplifier + 1
         *
         */
        private RegistryEntry<StatusEffect> statusEffect;
        private int duration;
        private Integer amplifier = null;

        public Effect(RegistryEntry<StatusEffect> statusEffect, int duration) {
            this.statusEffect = statusEffect;
            this.duration = duration;
        }

        public Effect(RegistryEntry<StatusEffect> statusEffect, int duration, Integer amplifier) {
            this.statusEffect = statusEffect;
            this.duration = duration;
            this.amplifier = amplifier;
        }
    }

}
