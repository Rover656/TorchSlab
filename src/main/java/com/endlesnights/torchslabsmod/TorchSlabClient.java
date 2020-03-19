package com.endlesnights.torchslabsmod;

import com.endlesnights.torchslabsmod.event.PlaceHandlerLanternSlab;
import com.endlesnights.torchslabsmod.event.PlaceHandlerTorchSlab;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;

@EventBusSubscriber(modid=TorchSlabsMod.MODID, bus=Bus.MOD, value=Dist.CLIENT)
public class TorchSlabClient
{
	@SubscribeEvent
	public static void onInterModProcess(InterModProcessEvent event)
	{
		for(Block b : PlaceHandlerTorchSlab.getPlaceEntryBlocks())
		{
			RenderTypeLookup.setRenderLayer(b, RenderType.func_228641_d_());
		}
		for(Block b : PlaceHandlerLanternSlab.getPlaceEntryBlocks())
		{
			RenderTypeLookup.setRenderLayer(b, RenderType.func_228641_d_());
		}
	}
}