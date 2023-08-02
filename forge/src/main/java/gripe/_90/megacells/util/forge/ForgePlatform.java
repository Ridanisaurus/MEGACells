package gripe._90.megacells.util.forge;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.LoadingModList;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;

import gripe._90.megacells.definition.MEGAItems;
import gripe._90.megacells.definition.MEGATranslations;
import gripe._90.megacells.util.Addons;
import gripe._90.megacells.util.Loaders;
import gripe._90.megacells.util.service.Platform;

public final class ForgePlatform implements Platform {
    @Override
    public Loaders getLoader() {
        return Loaders.FORGE;
    }

    @Override
    public CreativeModeTab getCreativeTab(CreativeModeTab.DisplayItemsGenerator display) {
        return CreativeModeTab.builder()
                .title(MEGATranslations.CreativeTab.text())
                .icon(() -> MEGAItems.ITEM_CELL_256M.stack(1))
                .displayItems(display)
                .build();
    }

    @Override
    public boolean isAddonLoaded(Addons addon) {
        if (ModList.get() == null) {
            return LoadingModList.get().getMods().stream().map(ModInfo::getModId).anyMatch(addon.getModId()::equals);
        }

        return ModList.get().isLoaded(addon.getModId());
    }
}
