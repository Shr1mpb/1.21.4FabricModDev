package com.shr1mp4zh.fmod;

import com.shr1mp4zh.fmod.datagen.ModBlockTagProvider;
import com.shr1mp4zh.fmod.datagen.ModItemTagProvider;
import com.shr1mp4zh.fmod.datagen.ModLootTableProvider;
import com.shr1mp4zh.fmod.datagen.ModModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class Shr1mpfmodDataGeneratorClient implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModItemTagProvider::new);
        pack.addProvider(ModBlockTagProvider::new);
        pack.addProvider(ModLootTableProvider::new);
        pack.addProvider(ModModelProvider::new);
    }
}
