package com.tic3nh.data;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import com.tic3nh.TiC3NH;
import com.tic3nh.mininglevel.GtnhTiers;

/**
 * Populates each GTNH tier's {@code tic3nh:needs_<name>_tool} block tag (DESIGN.md §3.1). Forge uses
 * these tags in {@code TierSortingRegistry} to decide which tier a block requires.
 *
 * <p>Starter coverage: vanilla ores/blocks mapped onto GTNH tiers. Broad modded-block coverage is the
 * documented extension point / config concern (DESIGN.md §8 risk #1). NOTE: a block already carried by
 * a vanilla {@code needs_*_tool} tag ends up requiring the MAX of vanilla and the tier added here, so
 * only raise requirements deliberately.
 */
public final class TierBlockTagsProvider extends BlockTagsProvider {

    public TierBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup,
                                 @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookup, TiC3NH.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // copper (tier 1): common early ores
        tag(GtnhTiers.COPPER.blockTag()).add(
                Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE,
                Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE,
                Blocks.LAPIS_ORE, Blocks.DEEPSLATE_LAPIS_ORE);

        // iron (tier 2): mid ores that vanilla gates behind an iron pick
        tag(GtnhTiers.IRON.blockTag()).add(
                Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE, Blocks.NETHER_GOLD_ORE,
                Blocks.REDSTONE_ORE, Blocks.DEEPSLATE_REDSTONE_ORE,
                Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE,
                Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE,
                Blocks.NETHER_QUARTZ_ORE);

        // obsidian (tier 5): vanilla's diamond-gated blocks
        tag(GtnhTiers.OBSIDIAN.blockTag()).add(
                Blocks.OBSIDIAN, Blocks.CRYING_OBSIDIAN, Blocks.ANCIENT_DEBRIS);

        // stone / tin / redstone / ardite / cobalt / manyullyn(+) intentionally left for
        // per-pack datapack tuning + the Phase 7 material bridge.
    }
}
