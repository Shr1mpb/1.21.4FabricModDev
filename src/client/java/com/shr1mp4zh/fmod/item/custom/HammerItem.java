package com.shr1mp4zh.fmod.item.custom;

import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.List;

public class HammerItem extends MiningToolItem {
    //注意这里的构造器要和ModItems类中的registerToolItem方法中利用反射时使用的参数一致，是material、attackDamage、attackSpeed、settings
    //这里指定hammer可破坏类型是镐(PICKAXE_MINEABLE)
    public HammerItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, BlockTags.PICKAXE_MINEABLE, attackDamage, attackSpeed, settings);
    }

    //获取破坏的方块 3x3
    public static List<BlockPos> getBlocksToBeDestroyed(int range, BlockPos initalBlockPos, ServerPlayerEntity player) {
        List<BlockPos> positions = new ArrayList<>();
        HitResult hit = player.raycast(20, 0, false);
        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult) hit;

            if (blockHit.getSide() == Direction.DOWN || blockHit.getSide() == Direction.UP) {
                for (int x = -range; x <= range; x++) {
                    for (int y = -range; y <= range; y++) {
                        positions.add(new BlockPos(initalBlockPos.getX() + x, initalBlockPos.getY(), initalBlockPos.getZ() + y));
                    }
                }
            }

            if (blockHit.getSide() == Direction.NORTH || blockHit.getSide() == Direction.SOUTH) {
                for (int x = -range; x <= range; x++) {
                    for (int y = -range; y <= range; y++) {
                        positions.add(new BlockPos(initalBlockPos.getX() + x, initalBlockPos.getY() + y, initalBlockPos.getZ()));
                    }
                }
            }

            if (blockHit.getSide() == Direction.EAST || blockHit.getSide() == Direction.WEST) {
                for (int x = -range; x <= range; x++) {
                    for (int y = -range; y <= range; y++) {
                        positions.add(new BlockPos(initalBlockPos.getX(), initalBlockPos.getY() + y, initalBlockPos.getZ() + x));
                    }
                }
            }
        }
        return positions;
    }
}
