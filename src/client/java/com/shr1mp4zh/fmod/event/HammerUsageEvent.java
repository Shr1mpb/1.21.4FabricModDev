package com.shr1mp4zh.fmod.event;


import com.shr1mp4zh.fmod.item.custom.HammerItem;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

//破坏3x3方块的事件 别忘了在Shr1mpfmodClient中注册
public class HammerUsageEvent implements PlayerBlockBreakEvents.Before{

    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    @Override
    public boolean beforeBlockBreak(World world, PlayerEntity player, BlockPos pos,
                                    BlockState state, @Nullable BlockEntity blockEntity) {
        ItemStack mainHandItem = player.getMainHandStack();

        if(mainHandItem.getItem() instanceof HammerItem hammer && player instanceof ServerPlayerEntity serverPlayer) {

            if(HARVESTED_BLOCKS.contains(pos)) {
                return true;
            }

            for(BlockPos position : HammerItem.getBlocksToBeDestroyed(1, pos, serverPlayer)) {
                if(pos == position || !hammer.isCorrectForDrops(mainHandItem, world.getBlockState(position))) {
                    continue;
                }

                HARVESTED_BLOCKS.add(position);
                //这里调用tryBreakBlock时会导致该方法被再次调用(递归了) 上面的contains(pos)为了在递归时直接调用方法来返回true破坏这个方块
                serverPlayer.interactionManager.tryBreakBlock(position);
                HARVESTED_BLOCKS.remove(position);
            }
        }

        return true;
    }

}
