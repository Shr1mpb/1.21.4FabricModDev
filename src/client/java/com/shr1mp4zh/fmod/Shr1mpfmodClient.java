package com.shr1mp4zh.fmod;

import com.shr1mp4zh.fmod.block.ModBlocks;
import com.shr1mp4zh.fmod.component.ModDataComponentTypes;
import com.shr1mp4zh.fmod.event.HammerUsageEvent;
import com.shr1mp4zh.fmod.item.ModItemGroups;
import com.shr1mp4zh.fmod.item.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;

public class Shr1mpfmodClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		ModItems.initialize();
		ModBlocks.initialize();
		ModItemGroups.initialize();
		ModDataComponentTypes.registerDataComponentTypes();

		//注册事件
		PlayerBlockBreakEvents.BEFORE.register(new HammerUsageEvent());

	}
}