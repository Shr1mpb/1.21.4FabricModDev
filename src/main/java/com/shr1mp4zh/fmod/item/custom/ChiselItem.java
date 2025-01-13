package com.shr1mp4zh.fmod.item.custom;

import com.shr1mp4zh.fmod.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

import javax.swing.*;
import java.util.Map;
import java.util.Objects;

/**
 * 点石为砖工具
 * 这个类可以用作参考自定义物品
 *
 */
public class ChiselItem extends Item {
    private static final Map<Block, Block> CHISEL_MAP =
            Map.of(
                    Blocks.STONE, Blocks.STONE_BRICKS,
                    Blocks.END_STONE, Blocks.END_STONE_BRICKS,
                    Blocks.OAK_LOG, ModBlocks.FIRST_BLOCK,
                    Blocks.GOLD_BLOCK, Blocks.NETHERITE_BLOCK
            );

    public ChiselItem(Settings settings) {
        super(settings);
    }

    //字面意思 重写对着方块使用
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        //获取World
        World world = context.getWorld();
        //用世界和坐标获取被点击的Block
        Block clickedBlock = world.getBlockState(context.getBlockPos()).getBlock();
        if (CHISEL_MAP.containsKey(clickedBlock)) {
            if (!world.isClient) {//
                //点石成砖
                world.setBlockState(context.getBlockPos(), CHISEL_MAP.get(clickedBlock).getDefaultState());
                //设置耐久 这里要传入world和player是因为计算耐久度损耗的时候会用到
                context.getStack().damage(1, (ServerWorld) world, (ServerPlayerEntity) context.getPlayer(),
                        item -> Objects.requireNonNull(context.getPlayer()).sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND));

                world.playSound(null, context.getBlockPos(), SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS);

            }
        }

        //成功并且能够显示右击的动画
        return ActionResult.SUCCESS;
    }
}
