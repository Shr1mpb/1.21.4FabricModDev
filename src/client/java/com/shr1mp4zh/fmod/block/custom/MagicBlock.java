package com.shr1mp4zh.fmod.block.custom;

import com.shr1mp4zh.fmod.item.ModItems;
import com.shr1mp4zh.fmod.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class MagicBlock extends Block {
    /**
     * 右键MagicBlock播放声音
     * 并且MagicBlock可以修复物品的耐久
     */
    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        world.playSound(player, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, 2f, 1f);
        if (!world.isClient) {//修复主手物品的耐久
            ItemStack mainHandStack = player.getMainHandStack(); // 获取主手物品

            // 检查主手物品是否为工具并且耐久值大于等于0
            if (mainHandStack.isDamageable() && mainHandStack.getDamage() > 0) {
                mainHandStack.setDamage(0); // 修复
            }
        }
        if (!world.isClient) {//修复副手物品的耐久
            ItemStack offHandStack = player.getOffHandStack(); // 获取副手物品

            // 检查副手物品是否为工具并且耐久值大于等于0
            if (offHandStack.isDamageable() && offHandStack.getDamage() > 0) {
                offHandStack.setDamage(0); // 修复
            }
        }
        return ActionResult.SUCCESS;
    }

    /**
     * 在MagicBlock上丢starlight_ashes会被转化成cauliflower
     */
    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isClient) {
            //更新：加一层自定义标签判断
            if (entity instanceof ItemEntity item) {
                if (item.getStack().isIn(ModTags.Items.MAGIC_BLOCK_TRANSFORMABLE)) {
                        item.setStack(new ItemStack(ModItems.CAULIFLOWER, item.getStack().getCount()));
                }
            }
            super.onSteppedOn(world, pos, state, entity);
        }
    }

    /**
     * 添加物品说明的示例
     */
    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(Text.translatable("tooltip.shr1mpfmod.magic_block.tooltip"));
        super.appendTooltip(stack, context, tooltip, options);
    }

    public MagicBlock(Settings settings) {
        super(settings);
    }
}
