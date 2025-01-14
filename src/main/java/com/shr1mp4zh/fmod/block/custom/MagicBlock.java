package com.shr1mp4zh.fmod.block.custom;

import com.shr1mp4zh.fmod.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
     * 在MagicBlock上丢first_item会被转化成cauliflower
     */
    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isClient) {
            if (entity instanceof ItemEntity item) {//把掉落在它上面的FIRST_ITEM 转化为 钻石
                if (item.getStack().getItem() == ModItems.FIRST_ITEM) {
                    item.setStack(new ItemStack(Items.DIAMOND, item.getStack().getCount()));
                }
                if (item.getStack().getItem() == ModItems.STARLIGHT_ASHES) {//把掉落在它上面的STARLIGHT_ASHES 转化为 岩浆桶
                    item.setStack(new ItemStack(Items.NETHER_STAR, item.getStack().getCount()));
                }
            }
            super.onSteppedOn(world, pos, state, entity);
        }
    }

    public MagicBlock(Settings settings) {
        super(settings);
    }
}
