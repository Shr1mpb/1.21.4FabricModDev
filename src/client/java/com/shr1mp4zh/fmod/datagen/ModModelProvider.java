package com.shr1mp4zh.fmod.datagen;

import com.shr1mp4zh.fmod.block.ModBlocks;
import com.shr1mp4zh.fmod.block.custom.HeavenLampBlock;
import com.shr1mp4zh.fmod.item.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.client.data.*;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Identifier;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    /**
     *    方块模型json文件生成 还需要少量手动json
     *    以first_block为例，在本类注册了，刚开始有 blockstates和items 下的第一层， models/block 和 models/item 下的第二层 ，还有textures第三层
     *         注册后，前三个都被自动创建
     *         未注册的物品也会自动注册一项：例如
     *          再以magic_block(或second_block)为例，在本类没有创建，但是它们都是方块，它们的第2个(即items)被自动创建了。
     *          因此，加入datagen后需要对已有的json文件都进行一波更改
     */
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        //使用基准的方块来创建NonBlock-Blocks 这里用到的“基准方块”FIRST_BLOCK的json文件都会被创建
        //此外注意 这个类会自动创建现有的方块对应到items中的json文件！！！无论它是不是在这段代码中


        BlockStateModelGenerator.BlockTexturePool blockTexturePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.FIRST_BLOCK);

        blockTexturePool.stairs(ModBlocks.FIRST_STAIR);
        blockTexturePool.wall(ModBlocks.FIRST_WALL);
        blockTexturePool.button(ModBlocks.FIRST_BUTTON);
        blockTexturePool.fence(ModBlocks.FIRST_FENCE);
        blockTexturePool.slab(ModBlocks.FIRST_SLAB);

        //创建可变状态方块 天外灯块
        create2StatesBlock(ModBlocks.HEAVEN_LAMP_BLOCK, HeavenLampBlock.CLICKED, blockStateModelGenerator);
    }



    /**
     * 物品模型json文件生成 这里生成完了就没有手动配置的地方了
     * 生成物品模型的json文件 仿照书写即可
     * 一般来说 普通的物品使用Models.GENERATED 工具使用Models.HANDHELD
     */
    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        //天外工具 * 5
        itemModelGenerator.register(ModItems.HEAVEN_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.HEAVEN_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.HEAVEN_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.HEAVEN_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.HEAVEN_HOE, Models.HANDHELD);

        //自定义工具(其实和上方写法一样)
        itemModelGenerator.register(ModItems.HEAVEN_HAMMER, Models.HANDHELD);

    }


    /**
     * 快速创建两种状态的方块的方法
     * 注意：这个方法创建的方块对材质命名有要求，状态1是xxx.png，状态2是xxx_on.png
     * @param block 方块
     * @param property 改变状态的属性 类型为BooleanProperty
     * @param blockStateModelGenerator generate方法的参数传进来
     */
    private static void create2StatesBlock(Block block, BooleanProperty property, BlockStateModelGenerator blockStateModelGenerator) {
        Identifier lampOffIdentifier = TexturedModel.CUBE_ALL.upload(block, blockStateModelGenerator.modelCollector);
        Identifier lampOnIdentifier = blockStateModelGenerator.createSubModel(block, "_on", Models.CUBE_ALL, TextureMap::all);
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
                .coordinate(BlockStateModelGenerator.createBooleanModelMap(property, lampOnIdentifier, lampOffIdentifier)));
    }
}

