package com.tic3nh.data;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;

import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import com.tic3nh.TiC3NH;
import com.tic3nh.mininglevel.GtTiers;

public final class TierTags extends BlockTagsProvider {

    public TierTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup,
                                 @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookup, TiC3NH.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        // every tier gets a file even when empty, packs and gt ores append to them
        for (GtTiers.GtnhTier tier : GtTiers.TIERS) {
            tag(tier.blockTag());
        }

        tag(GtTiers.COPPER.blockTag()).add(
                Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE,
                Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE,
                Blocks.LAPIS_ORE, Blocks.DEEPSLATE_LAPIS_ORE);

        tag(GtTiers.IRON.blockTag()).add(
                Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE, Blocks.NETHER_GOLD_ORE,
                Blocks.REDSTONE_ORE, Blocks.DEEPSLATE_REDSTONE_ORE,
                Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE,
                Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE,
                Blocks.NETHER_QUARTZ_ORE);

        tag(GtTiers.OBSIDIAN.blockTag()).add(
                Blocks.OBSIDIAN, Blocks.CRYING_OBSIDIAN, Blocks.ANCIENT_DEBRIS,
                Blocks.RESPAWN_ANCHOR);

        tag(GtTiers.COBALT.blockTag()).add(
                Blocks.NETHERITE_BLOCK);

        tag(GtTiers.MANYULLYN.blockTag()).add(
                Blocks.REINFORCED_DEEPSLATE);

    }
}
