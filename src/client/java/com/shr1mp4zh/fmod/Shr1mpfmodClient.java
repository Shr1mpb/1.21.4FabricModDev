package com.shr1mp4zh.fmod;

import com.shr1mp4zh.fmod.block.ModBlocks;
import com.shr1mp4zh.fmod.component.ModDataComponentTypes;
import com.shr1mp4zh.fmod.item.ModItemGroups;
import com.shr1mp4zh.fmod.item.ModItems;
import net.fabricmc.api.ClientModInitializer;

public class Shr1mpfmodClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		ModItems.initialize();
		ModBlocks.initialize();
		ModItemGroups.initialize();
		ModDataComponentTypes.registerDataComponentTypes();
	}
}