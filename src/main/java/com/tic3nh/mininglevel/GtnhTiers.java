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

/**
 * The eleven GTNH-style harvest tiers (DESIGN.md §3.1). Each is a vanilla {@link Tier} carried by a
 * {@link ForgeTier}, sorted into Forge's {@link TierSortingRegistry} so TiC3 (which orders tiers by
 * that registry, never a raw int) and cross-mod tools order correctly.
 *
 * <p>Levels 00-08 are GTNH's exact named scheme (Stone…Manyullyn — GTNH's top named level). GTNH's
 * {@code 09+} is unnamed; here it becomes two Monifactory tiers, {@code 09-Darconite} and
 * {@code 10-Monium}. Display names use GTNH's {@code NN-Name} form (see lang).
 *
 * <p>Ordering intent: {@code stone/copper} bracket into [WOOD, IRON), {@code iron/tin/redstone} into
 * (IRON, DIAMOND), {@code obsidian..manyullyn} into (DIAMOND, NETHERITE), and {@code darconite/monium}
 * above NETHERITE. Constraints are acyclic (chain via {@code after} + a few vanilla anchors).
 *
 * <p>Tier stats here are only fallbacks — real tool stats come from TiC materials (Phase 7). Each
 * tier owns a {@code tic3nh:needs_<name>_tool} block tag (Forge's "requires this tier" tag),
 * populated by datagen.
 */
public final class GtnhTiers {

    private GtnhTiers() {}

    /** One GTNH tier: its id, the Forge tier object, and its "needs this tier" block tag. */
    public record GtnhTier(String name, ResourceLocation id, TagKey<Block> blockTag, ForgeTier tier) {}

    //                                        name              lvl  uses  speed  atk  ench
    public static final GtnhTier STONE          = make("stone",          1,  131, 4.0f, 1.0f,  5);
    public static final GtnhTier COPPER         = make("copper",         1,  190, 5.0f, 1.0f,  8);
    public static final GtnhTier IRON           = make("iron",           2,  250, 6.0f, 2.0f, 14);
    public static final GtnhTier TIN            = make("tin",            2,  300, 6.0f, 2.0f, 12);
    public static final GtnhTier REDSTONE       = make("redstone",       2,  400, 6.5f, 2.0f, 16);
    public static final GtnhTier OBSIDIAN       = make("obsidian",       3, 1000, 7.0f, 3.0f, 10);
    public static final GtnhTier ARDITE         = make("ardite",         3, 1200, 7.5f, 3.0f, 12);
    public static final GtnhTier COBALT         = make("cobalt",         3, 1300, 8.0f, 3.0f, 14);
    public static final GtnhTier MANYULLYN      = make("manyullyn",      4, 1561, 8.5f, 4.0f, 10);
    // 09+ in GTNH is unnamed; here it's two Monifactory tiers.
    public static final GtnhTier DARCONITE      = make("darconite",      5, 2100,  9.0f, 4.0f, 15);
    public static final GtnhTier MONIUM         = make("monium",         5, 2600, 10.0f, 5.0f, 18);

    /** All eleven tiers, lowest → highest. */
    public static final List<GtnhTier> TIERS = List.of(
            STONE, COPPER, IRON, TIN, REDSTONE, OBSIDIAN, ARDITE, COBALT, MANYULLYN, DARCONITE, MONIUM);

    /**
     * The GTNH tier to <em>display</em> for an arbitrary sorted tier: the highest GTNH tier at or below
     * it. Lets vanilla tiers used by TiC's built-in materials (e.g. Rock = {@code minecraft:stone})
     * and cross-mod blocks render as a GTNH name instead of a raw vanilla one. Floors at Stone.
     */
    public static GtnhTier displayFor(Tier tier) {
        java.util.List<Tier> sorted = TierSortingRegistry.getSortedTiers();
        int idx = sorted.indexOf(tier);
        GtnhTier best = STONE;
        for (GtnhTier g : TIERS) {
            if (sorted.indexOf(g.tier()) <= idx) {
                best = g;
            } else {
                break; // TIERS is ascending, so once past idx we are done
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

    /**
     * Register + sort all ten tiers. Must run on the sync work queue during
     * {@code FMLCommonSetupEvent} ({@code event.enqueueWork(...)}) — {@link TierSortingRegistry}
     * is not thread-safe.
     */
    public static void register() {
        //   tier            after (must come above)                       before (must come below)
        reg(STONE,          List.<Object>of(Tiers.WOOD),                    List.<Object>of(Tiers.IRON));
        reg(COPPER,         List.<Object>of(STONE.tier()),                  List.<Object>of(Tiers.IRON));
        reg(IRON,           List.<Object>of(COPPER.tier(), Tiers.IRON),     List.<Object>of(Tiers.DIAMOND));
        reg(TIN,            List.<Object>of(IRON.tier()),                   List.<Object>of(Tiers.DIAMOND));
        reg(REDSTONE,       List.<Object>of(TIN.tier()),                    List.<Object>of(Tiers.DIAMOND));
        reg(OBSIDIAN,       List.<Object>of(REDSTONE.tier(), Tiers.DIAMOND), List.<Object>of(Tiers.NETHERITE));
        reg(ARDITE,         List.<Object>of(OBSIDIAN.tier()),               List.<Object>of(Tiers.NETHERITE));
        reg(COBALT,         List.<Object>of(ARDITE.tier()),                 List.<Object>of(Tiers.NETHERITE));
        reg(MANYULLYN,      List.<Object>of(COBALT.tier()),                    List.<Object>of(Tiers.NETHERITE));
        // NB: after/before may only reference tiers ALREADY registered (Forge requirement). Register
        // low→high so each `after` points at the prior tier; never forward-reference an own tier in
        // `before` (that crashed the client: darconite before monium, monium not yet registered).
        reg(DARCONITE,      List.<Object>of(MANYULLYN.tier(), Tiers.NETHERITE), List.<Object>of());
        reg(MONIUM,         List.<Object>of(DARCONITE.tier(), Tiers.NETHERITE), List.<Object>of());

        TiC3NH.LOGGER.info("Registered {} GTNH harvest tiers", TIERS.size());
    }

    private static void reg(GtnhTier t, List<Object> after, List<Object> before) {
        TierSortingRegistry.registerTier(t.tier(), t.id(), after, before);
    }
}
