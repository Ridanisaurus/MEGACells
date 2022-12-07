package gripe._90.megacells.init.forge.client;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import appeng.core.definitions.BlockDefinition;

import gripe._90.megacells.definition.MEGABlocks;

public class InitRenderTypes {
    private static final BlockDefinition<?>[] CUTOUT_BLOCKS = {
            MEGABlocks.MEGA_CRAFTING_UNIT,
            MEGABlocks.CRAFTING_ACCELERATOR,
            MEGABlocks.CRAFTING_STORAGE_1M,
            MEGABlocks.CRAFTING_STORAGE_4M,
            MEGABlocks.CRAFTING_STORAGE_16M,
            MEGABlocks.CRAFTING_STORAGE_64M,
            MEGABlocks.CRAFTING_STORAGE_256M,
            MEGABlocks.CRAFTING_MONITOR
    };

    public static void init() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(InitRenderTypes::initRenderTypes);
    }

    private static void initRenderTypes(FMLClientSetupEvent event) {
        for (var definition : CUTOUT_BLOCKS) {
            ItemBlockRenderTypes.setRenderLayer(definition.block(), RenderType.cutout());
        }
    }
}