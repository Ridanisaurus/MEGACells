package gripe._90.megacells.datagen;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import appeng.api.features.P2PTunnelAttunement;

import gripe._90.megacells.MEGACells;
import gripe._90.megacells.definition.MEGABlocks;
import gripe._90.megacells.definition.MEGAItems;
import gripe._90.megacells.definition.MEGATags;

public class MEGATagProvider {
    public static class ItemTags extends IntrinsicHolderTagsProvider<Item> {
        public ItemTags(
                PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existing) {
            super(
                    output,
                    Registries.ITEM,
                    registries,
                    item -> BuiltInRegistries.ITEM.getResourceKey(item).orElseThrow(),
                    MEGACells.MODID,
                    existing);
        }

        @Override
        protected void addTags(@NotNull HolderLookup.Provider provider) {
            tag(MEGATags.SKY_STEEL_INGOT).add(MEGAItems.SKY_STEEL_INGOT.asItem());
            tag(MEGATags.SKY_STEEL_BLOCK_ITEM).add(MEGABlocks.SKY_STEEL_BLOCK.asItem());

            tag(P2PTunnelAttunement.getAttunementTag(P2PTunnelAttunement.ENERGY_TUNNEL))
                    .add(MEGABlocks.MEGA_ENERGY_CELL.asItem());

            tag(MEGATags.MEGA_INTERFACE).add(MEGABlocks.MEGA_INTERFACE.asItem(), MEGAItems.MEGA_INTERFACE.asItem());
            tag(MEGATags.MEGA_PATTERN_PROVIDER)
                    .add(MEGABlocks.MEGA_PATTERN_PROVIDER.asItem(), MEGAItems.MEGA_PATTERN_PROVIDER.asItem());

            tag(MEGATags.COMPRESSION_OVERRIDES)
                    .add(Items.QUARTZ)
                    .add(Items.GLOWSTONE_DUST)
                    .add(Items.AMETHYST_SHARD)
                    .add(Items.MAGMA_CREAM)
                    .add(Items.CLAY_BALL)
                    .add(Items.MELON_SLICE)
                    .add(Items.ICE, Items.PACKED_ICE)
                    .addOptionalTag(
                            ResourceLocation.fromNamespaceAndPath("functionalstorage", "ignore_crafting_check"));
        }

        @NotNull
        @Override
        public String getName() {
            return "Tags (Item)";
        }
    }

    public static class BlockTags extends IntrinsicHolderTagsProvider<Block> {
        public BlockTags(
                PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existing) {
            super(
                    output,
                    Registries.BLOCK,
                    registries,
                    block -> BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow(),
                    MEGACells.MODID,
                    existing);
        }

        @Override
        protected void addTags(@NotNull HolderLookup.Provider provider) {
            for (var block : MEGABlocks.getBlocks()) {
                tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE).add(block.block());
            }

            tag(MEGATags.SKY_STEEL_BLOCK).add(MEGABlocks.SKY_STEEL_BLOCK.block());
        }

        @NotNull
        @Override
        public String getName() {
            return "Tags (Block)";
        }
    }
}
