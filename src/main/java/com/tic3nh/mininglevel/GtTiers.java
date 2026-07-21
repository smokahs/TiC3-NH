package com.tic3nh.mininglevel;

import java.util.List;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import com.tic3nh.TiC3NH;

public final class GtTiers {

    private GtTiers() {}

    public record GtnhTier(String name, ResourceLocation id, TagKey<Block> blockTag, ForgeTier tier) {}

    public static final GtnhTier STONE          = make("stone",          1,  131, 4.0f, 1.0f,  5);
    public static final GtnhTier COPPER         = make("copper",         1,  190, 5.0f, 1.0f,  8);
    public static final GtnhTier IRON           = make("iron",           2,  250, 6.0f, 2.0f, 14);
    public static final GtnhTier TIN            = make("tin",            2,  300, 6.0f, 2.0f, 12);
    public static final GtnhTier REDSTONE       = make("redstone",       2,  400, 6.5f, 2.0f, 16);
    public static final GtnhTier OBSIDIAN       = make("obsidian",       3, 1000, 7.0f, 3.0f, 10);
    public static final GtnhTier ARDITE         = make("ardite",         3, 1200, 7.5f, 3.0f, 12);
    public static final GtnhTier COBALT         = make("cobalt",         3, 1300, 8.0f, 3.0f, 14);
    public static final GtnhTier MANYULLYN      = make("manyullyn",      4, 1561, 8.5f, 4.0f, 10);

    public static final GtnhTier DARCONITE      = make("darconite",      5, 2100,  9.0f, 4.0f, 15);
    public static final GtnhTier MONIUM         = make("monium",         5, 2600, 10.0f, 5.0f, 18);

    public static final List<GtnhTier> TIERS = List.of(
            STONE, COPPER, IRON, TIN, REDSTONE, OBSIDIAN, ARDITE, COBALT, MANYULLYN, DARCONITE, MONIUM);

    public static GtnhTier displayFor(Tier tier) {
        java.util.List<Tier> sorted = TierSortingRegistry.getSortedTiers();
        int idx = sorted.indexOf(tier);
        GtnhTier best = STONE;
        for (GtnhTier g : TIERS) {
            if (sorted.indexOf(g.tier()) <= idx) {
                best = g;
            } else {
                break;
            }
        }
        return best;
    }

    private static GtnhTier make(String name, int level, int uses, float speed, float atk, int ench) {
        ResourceLocation id = new ResourceLocation(TiC3NH.MOD_ID, name);
        TagKey<Block> tag = TagKey.create(Registries.BLOCK,
                new ResourceLocation(TiC3NH.MOD_ID, "needs_" + name + "_tool"));
        ForgeTier tier = new ForgeTier(level, uses, speed, atk, ench, tag, () -> Ingredient.EMPTY);
        return new GtnhTier(name, id, tag, tier);
    }

    public static void register() {

        reg(STONE,          List.<Object>of(Tiers.WOOD),                    List.<Object>of(Tiers.IRON));
        reg(COPPER,         List.<Object>of(STONE.tier()),                  List.<Object>of(Tiers.IRON));
        reg(IRON,           List.<Object>of(COPPER.tier(), Tiers.IRON),     List.<Object>of(Tiers.DIAMOND));
        reg(TIN,            List.<Object>of(IRON.tier()),                   List.<Object>of(Tiers.DIAMOND));
        reg(REDSTONE,       List.<Object>of(TIN.tier()),                    List.<Object>of(Tiers.DIAMOND));
        reg(OBSIDIAN,       List.<Object>of(REDSTONE.tier(), Tiers.DIAMOND), List.<Object>of(Tiers.NETHERITE));
        reg(ARDITE,         List.<Object>of(OBSIDIAN.tier()),               List.<Object>of(Tiers.NETHERITE));
        reg(COBALT,         List.<Object>of(ARDITE.tier()),                 List.<Object>of(Tiers.NETHERITE));
        reg(MANYULLYN,      List.<Object>of(COBALT.tier()),                    List.<Object>of(Tiers.NETHERITE));

        reg(DARCONITE,      List.<Object>of(MANYULLYN.tier(), Tiers.NETHERITE), List.<Object>of());
        reg(MONIUM,         List.<Object>of(DARCONITE.tier(), Tiers.NETHERITE), List.<Object>of());

        TiC3NH.LOGGER.info("Registered {} GTNH harvest tiers", TIERS.size());
    }

    private static void reg(GtnhTier t, List<Object> after, List<Object> before) {
        TierSortingRegistry.registerTier(t.tier(), t.id(), after, before);
    }
}
