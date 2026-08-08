package com.tic3nh.data;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import com.tic3nh.TiC3NH;
import com.tic3nh.mininglevel.GtTiers;
import com.tic3nh.mininglevel.GtTiers.GtnhTier;

// block gating for the eleven tiers, ported from gtnh HarvestLevelTweaks. see TIER-MAP.md.
// forge:ores/* and forge:storage_blocks/* stand in for gtnh's oredict prefixes, so one entry covers
// gtceu, thermal, ad astra, create and tinkers at once. all optional, missing mods just drop out.
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

        // 0 stone: the floor, nothing gated

        // 1 copper
        ores(GtTiers.COPPER,
                "coal", "lignite", "copper", "zinc", "tetrahedrite", "chalcopyrite", "malachite",
                "bornite", "chalcocite", "bauxite", "aluminum", "aluminium", "apatite", "sulfur",
                "saltpeter", "niter", "salt", "rock_salt", "graphite", "oilsands", "mica", "talc",
                "soapstone", "calcite", "gypsum", "realgar", "asbestos", "diatomite", "trona",
                "electrotine", "basaltic_mineral_sand", "granitic_mineral_sand", "garnet_sand",
                "glauconite_sand", "fullers_earth", "bentonite", "azurite", "zincite", "borax");
        storage(GtTiers.COPPER,
                "coal", "copper", "raw_copper", "zinc", "raw_zinc", "aluminum", "aluminium", "brass");
        blocks(GtTiers.COPPER,
                Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE, Blocks.COAL_BLOCK,
                Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE,
                Blocks.COPPER_BLOCK, Blocks.RAW_COPPER_BLOCK);

        // 2 iron
        ores(GtTiers.IRON,
                "iron", "pyrite", "magnetite", "hematite", "goethite", "yellow_limonite",
                "brown_limonite", "banded_iron", "vanadium_magnetite", "silver", "lapis", "lazurite",
                "molybdenite", "molybdenum", "neodymium", "bastnasite", "monazite", "beryllium",
                "lithium", "barite", "alunite", "kyanite", "lepidolite", "magnesite", "pollucite",
                "powellite", "spodumene", "stibnite", "zeolite", "tricalcium_phosphate",
                "almandine", "grossular", "pyrope", "spessartine", "argentite", "arsenopyrite",
                "fluorite", "gallite", "briartite", "xenotime");
        storage(GtTiers.IRON, "iron", "raw_iron", "silver", "raw_silver", "lapis", "wrought_iron");
        blocks(GtTiers.IRON,
                Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE, Blocks.IRON_BLOCK,
                Blocks.RAW_IRON_BLOCK, Blocks.IRON_BARS,
                Blocks.LAPIS_ORE, Blocks.DEEPSLATE_LAPIS_ORE, Blocks.LAPIS_BLOCK);

        // 3 tin: gold, redstone and the tin/lead/nickel band
        ores(GtTiers.TIN,
                "tin", "cassiterite", "cassiterite_sand", "gold", "lead", "galena", "nickel",
                "garnierite", "pentlandite", "sphalerite", "electrum", "osmium", "redstone",
                "ilmenite", "rutile", "chromite", "chrome", "titanite", "wulfenite");
        storage(GtTiers.TIN,
                "gold", "raw_gold", "redstone", "tin", "raw_tin", "lead", "raw_lead", "nickel",
                "raw_nickel", "invar", "electrum", "steel", "constantan", "bronze", "osmium");
        blocks(GtTiers.TIN,
                Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE, Blocks.NETHER_GOLD_ORE,
                Blocks.GILDED_BLACKSTONE, Blocks.GOLD_BLOCK, Blocks.RAW_GOLD_BLOCK,
                Blocks.REDSTONE_ORE, Blocks.DEEPSLATE_REDSTONE_ORE, Blocks.REDSTONE_BLOCK);

        // 4 redstone: the gem tier
        ores(GtTiers.REDSTONE,
                "diamond", "emerald", "ruby", "sapphire", "green_sapphire", "amethyst", "cinnabar",
                "pyrolusite", "manganese", "red_garnet", "yellow_garnet", "opal", "topaz",
                "blue_topaz", "tantalite", "thorium", "columbite");
        storage(GtTiers.REDSTONE,
                "diamond", "emerald", "ruby", "sapphire", "titanium", "stainless_steel",
                "damascus_steel", "vanadium_steel");
        blocks(GtTiers.REDSTONE,
                Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.DIAMOND_BLOCK,
                Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE, Blocks.EMERALD_BLOCK);
        ids(GtTiers.REDSTONE,
                "gtceu:red_granite", "gtceu:red_granite_cobblestone", "gtceu:polished_red_granite",
                "gtceu:red_granite_bricks", "gtceu:reinforced_stone");

        // 5 obsidian
        ores(GtTiers.OBSIDIAN,
                "quartz", "nether_quartz", "certus_quartz", "quartzite", "sodalite", "tungstate",
                "scheelite", "tungsten", "wolframite", "stolzite");
        storage(GtTiers.OBSIDIAN,
                "quartz", "certus_quartz", "tungsten", "tungsten_steel", "ultimet", "blue_steel",
                "red_steel");
        blocks(GtTiers.OBSIDIAN,
                Blocks.OBSIDIAN, Blocks.CRYING_OBSIDIAN, Blocks.ENCHANTING_TABLE,
                Blocks.NETHER_QUARTZ_ORE, Blocks.QUARTZ_BLOCK,
                Blocks.ANCIENT_DEBRIS, Blocks.NETHERITE_BLOCK, Blocks.RESPAWN_ANCHOR);
        // gtnh had ae2 certus ore, sky stone and sky chests all at obsidian. 1.20.1 ae2 dropped the
        // ore, budding quartz is the source now
        ids(GtTiers.OBSIDIAN,
                "ae2:flawless_budding_quartz", "ae2:flawed_budding_quartz",
                "ae2:chipped_budding_quartz", "ae2:damaged_budding_quartz",
                "ae2:sky_stone_block", "ae2:smooth_sky_stone_block", "ae2:sky_stone_brick",
                "ae2:sky_stone_small_brick", "ae2:sky_stone_chest", "ae2:smooth_sky_stone_chest",
                "twilightforest:deadrock", "twilightforest:cracked_deadrock",
                "twilightforest:weathered_deadrock", "twilightforest:aurora_block");
        tags(GtTiers.OBSIDIAN, "twilightforest:mazestone", "twilightforest:castle_blocks");

        // 6 ardite: uranium, the platinum group and the moon
        ores(GtTiers.ARDITE,
                "uranium", "uraninite", "pitchblende", "plutonium", "carnotite", "olivine",
                "platinum", "palladium", "sheldonite", "cuprorhodsite", "sperrylite", "laurite", "ardite",
                "iridosmine", "osmiridium", "desh", "dilithium");
        storage(GtTiers.ARDITE,
                "uranium", "plutonium", "platinum", "palladium", "desh", "raw_desh", "naquadah",
                "osmiridium", "tungsten_carbide", "adamantium");

        // 7 cobalt
        ores(GtTiers.COBALT,
                "cobalt", "cobaltite", "iridium", "cooperite", "naquadah", "snowchestite",
                "titanium", "ostrum");
        storage(GtTiers.COBALT,
                "ardite", "raw_ardite", "cobalt", "raw_cobalt", "iridium", "ostrum", "raw_ostrum", "duranium", "tritanium",
                "knightmetal");
        ids(GtTiers.COBALT, "twilightforest:fiery_block", "twilightforest:knightmetal_block");

        // 8 manyullyn
        ores(GtTiers.MANYULLYN, "calorite");
        storage(GtTiers.MANYULLYN,
                "manyullyn", "hepatizon", "queens_slime", "soulsteel", "cinderslime", "calorite",
                "raw_calorite", "naquadah_alloy", "naquadah_enriched", "hsse", "hss_e");
        blocks(GtTiers.MANYULLYN, Blocks.REINFORCED_DEEPSLATE);

        // 9 darconite
        storage(GtTiers.DARCONITE,
                "darconite", "darmstadtium", "bedrockium", "neutronium");
        ids(GtTiers.DARCONITE,
                "gtceu:fusion_casing_mk2", "gtceu:fusion_coil", "gtceu:superconducting_coil");

        // 10 monium
        storage(GtTiers.MONIUM, "monium", "omnium", "infinity");
        ids(GtTiers.MONIUM, "gtceu:fusion_casing_mk3");

        // a mod gets exactly one block tag provider, so the paperbark wood tags ride along here
        PaperbarkTags.addTo(this::tag);
    }

    private void ores(GtnhTier tier, String... materials) {
        for (String material : materials) {
            tag(tier.blockTag()).addOptionalTag(new ResourceLocation("forge", "ores/" + material));
        }
    }

    private void storage(GtnhTier tier, String... materials) {
        for (String material : materials) {
            tag(tier.blockTag())
                    .addOptionalTag(new ResourceLocation("forge", "storage_blocks/" + material));
        }
    }

    private void blocks(GtnhTier tier, Block... blocks) {
        tag(tier.blockTag()).add(blocks);
    }

    private void ids(GtnhTier tier, String... ids) {
        for (String id : ids) {
            tag(tier.blockTag()).addOptional(new ResourceLocation(id));
        }
    }

    private void tags(GtnhTier tier, String... ids) {
        for (String id : ids) {
            tag(tier.blockTag()).addOptionalTag(new ResourceLocation(id));
        }
    }
}
