package com.shr1mp4zh.fmod.datagen;

import com.shr1mp4zh.fmod.block.ModBlocks;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        //使用基准的方块来创建NonBlock-Blocks 这里用到的“基准方块”FIRST_BLOCK的json文件都会被创建
        //此外注意 这个类会自动创建现有的方块对应到items中的json文件！！！无论它是不是在这段代码中

        //以first_block为例，在本类注册了，刚开始有 blockstates和items 下的第一层， models/block 和 models/item 下的第二层 ，还有textures第三层
        //注册后，前三个都被自动创建
        //再以magic_block(或second_block)为例，在本类没有创建，但是它们的第2个(即items)被自动创建了。
        BlockStateModelGenerator.BlockTexturePool blockTexturePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.FIRST_BLOCK);

        blockTexturePool.stairs(ModBlocks.FIRST_STAIR);
        blockTexturePool.wall(ModBlocks.FIRST_WALL);
        blockTexturePool.button(ModBlocks.FIRST_BUTTON);
        blockTexturePool.fence(ModBlocks.FIRST_FENCE);
        blockTexturePool.slab(ModBlocks.FIRST_SLAB);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }
}

