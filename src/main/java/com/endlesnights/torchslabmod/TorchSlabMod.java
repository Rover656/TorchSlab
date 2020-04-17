package com.endlesnights.torchslabmod;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import com.endlesnights.torchslabmod.config.Config;
import com.endlesnights.torchslabmod.futuremc.FutureMCCompat;
import com.endlesnights.torchslabmod.vanilla.VanillaCompat;

@Mod(modid=TorchSlabMod.MODID, name=TorchSlabMod.NAME, version=TorchSlabMod.VERSION, acceptedMinecraftVersions="[" + TorchSlabMod.MC_VERSION + "]")
@EventBusSubscriber
public class TorchSlabMod
{
    public static final String MODID = "torchslabmod";
    public static final String NAME = "Torch Slab Mod";
    public static final String VERSION = "v1.5.2";
    public static final String MC_VERSION = "1.12.2";
    private static List<Supplier<ITorchSlabCompat>> compatList = new ArrayList<>();
    
    public static Configuration config;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
    	ModMetadata meta = event.getModMetadata();

		meta.authorList = Arrays.asList(new String[]{"EndlesNights"});
		meta.autogenerated = false;
		meta.description = "Allows for the placement of torches,slab blocks and onto the walls of slabsr and blocks";
		meta.modId = MODID;
		meta.name = NAME;
		meta.version = VERSION;
		meta.url = "https://www.curseforge.com/minecraft/mc-mods/torchslabs-mod";

		compatList.add(VanillaCompat::new);
		
		if(Loader.isModLoaded("futuremccore"))
			compatList.add(FutureMCCompat::new);
		else
			System.out.println("\nNOTLOADED\n");
		
		File directory = event.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "torchslabmod.cfg"));
		Config.readConfig();
	}
    
    public void postInit(FMLPostInitializationEvent event) {
    	if (config.hasChanged()) {
            config.save();
        }
    }
	
    @SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		for(Supplier<ITorchSlabCompat> compat : compatList)
		{
			compat.get().registerBlocks(event);
		}
	}

	@EventHandler
	public void onLoadComplete(FMLLoadCompleteEvent event)
	{
		for(Supplier<ITorchSlabCompat> compat : compatList)
		{
			compat.get().registerPlaceEntries();
		}
	}
}