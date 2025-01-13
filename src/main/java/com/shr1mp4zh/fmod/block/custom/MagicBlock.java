package com.shr1mp4zh.fmod.block.custom;

import com.shr1mp4zh.fmod.block.ModBlocks;
import com.shr1mp4zh.fmod.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MagicBlock extends Block {
    /**
     * 右键MagicBlock播放声音
     */
    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        world.playSound(player, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, 1f, 1f);
        if (!world.isClient) {//修复副手物品的耐久
            ItemStack mainHandStack = player.getMainHandStack(); // 获取副手物品

            // 检查副手物品是否为工具并且耐久值大于等于0
            if (mainHandStack.getItem() instanceof Item && mainHandStack.getDamage() > 0) {
                mainHandStack.setDamage(0); // 修复
                // 通知客户端更新物品状态
                player.sendEquipmentBreakStatus(mainHandStack.getItem(), EquipmentSlot.MAINHAND);
            }
        }
        return ActionResult.SUCCESS;
    }

    /**
     * 在MagicBlock上丢FirstItem会被转换成FirstBlock
     */
    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isClient) {
            if (entity instanceof ItemEntity item) {//把掉落在它上面的FIRST_ITEM转换成FIRST_BLOCK。
                if (item.getStack().getItem() == ModItems.FIRST_ITEM) {
                    item.setStack(new ItemStack(ModBlocks.FIRST_BLOCK, item.getStack().getCount()));
                }
            }
            super.onSteppedOn(world, pos, state, entity);
        }
    }

    public MagicBlock(Settings settings) {
        super(settings);
    }
}
