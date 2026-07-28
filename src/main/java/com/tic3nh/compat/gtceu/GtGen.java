package com.tic3nh.compat.gtceu;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.resources.ResourceLocation;

import com.tic3nh.TiC3NH;
import com.tic3nh.compat.gtceu.GtAccess.GtMat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

public final class GtGen {

    private GtGen() {}

    private static final Logger LOG = LogUtils.getLogger();
    private static final String NS = TiC3NH.MOD_ID;

    public record Generated(Map<ResourceLocation, byte[]> data, Map<ResourceLocation, byte[]> assets) {}

    private static final Set<String> SKIP = Set.of(
            "aluminum", "amethyst", "amethyst_bronze", "ancient", "ancient_hide", "bamboo", "blaze",
            "blazewood", "blazing_bone", "blood", "bloodbone", "bone", "bronze", "cactus", "chain",
            "chorus", "cinderslime", "clay", "cobalt", "constantan", "copper", "darkthread",
            "dragon_scale", "earthslime", "electrum", "end_rod", "ender_pearl", "enderslime",
            "enderslime_vine", "feather", "fiery", "flint", "glass", "glowstone", "gold", "gunpowder",
            "hepatizon", "honey", "ice", "ichor", "ichorskin", "invar", "iron", "ironwood", "knightly",
            "knightmetal", "lead", "leather", "leaves", "magma", "magnetite", "manyullyn", "nahuatl",
            "necronium", "necrotic_bone", "obsidian", "osmium", "paper", "pewter", "phantom", "pig_iron",
            "plated_slimewood", "platinum", "prismarine", "quartz", "queens_slime", "rock", "rose_gold",
            "rotten_flesh", "scorched_stone", "seared_stone", "shulker", "silver", "skyslime",
            "skyslime_vine", "slimeball", "slimeskin", "slimesteel", "slimewood", "steel", "steeleaf",
            "string", "tungsten", "twisting_vine", "treated_wood", "venombone", "vine", "weeping_vine",
            "whitestone", "wood", "wool",

            "netherite"
    );

    private static final Map<String, String> ALIAS = Map.of("aluminium", "aluminum");

    public static Generated generate() {
        Map<ResourceLocation, byte[]> data = new HashMap<>();
        Map<ResourceLocation, byte[]> assets = new HashMap<>();
        List<GtMat> scanned = GtAccess.toolMaterials();
        List<String> generated = new ArrayList<>();
        int skipped = 0;
        int nonIngot = 0;
        int sort = 200;
        for (GtMat m : scanned) {
            String name = m.name();
            if (name == null || name.isEmpty()) {
                continue;
            }
            if (SKIP.contains(name) || SKIP.contains(ALIAS.getOrDefault(name, name))) {
                skipped++;
                continue;
            }

            if (!m.hasIngot()) {
                nonIngot++;
                continue;
            }
            put(data, "tinkering/materials/definition/" + name + ".json", definition(name, m, sort));
            put(data, "tinkering/materials/stats/" + name + ".json", stats(name, m));
            put(data, "tinkering/materials/traits/" + name + ".json", traits(name, m));
            put(assets, "tinkering/materials/" + name + ".json", renderInfo(m));
            generated.add(name + "(hl" + m.harvestLevel() + "→" + miningTier(name, m).replace("tic3nh:", "") + ")");
            sort++;
        }

        LOG.info("[TiC3-NH] GTCEu bridge: scanned {} toolable, skipped {} TiC-native + {} non-ingot, "
                + "generated {}: {}", scanned.size(), skipped, nonIngot, generated.size(), generated);
        return new Generated(data, assets);
    }

    private static void put(Map<ResourceLocation, byte[]> out, String path, JsonObject json) {
        out.put(new ResourceLocation(NS, path), json.toString().getBytes(StandardCharsets.UTF_8));
    }

    private static JsonObject definition(String name, GtMat m, int sort) {
        JsonObject cond = new JsonObject();
        cond.addProperty("type", "mantle:tag_filled");
        cond.addProperty("tag", "forge:ingots/" + name);

        JsonObject o = new JsonObject();
        o.add("condition", cond);
        o.addProperty("craftable", false);
        o.addProperty("hidden", false);
        o.addProperty("sortOrder", sort);
        o.addProperty("tier", Math.max(0, Math.min(5, m.harvestLevel())));
        return o;
    }

    private static JsonObject stats(String name, GtMat m) {
        JsonObject head = new JsonObject();
        head.addProperty("durability", m.headDurability());
        head.addProperty("melee_attack", round2(m.attackDamage()));
        head.addProperty("mining_speed", round2(m.harvestSpeed()));
        head.addProperty("mining_tier", miningTier(name, m));

        JsonObject handle = new JsonObject();
        handle.addProperty("durability", round2(Math.max(0.1f, Math.min(2.0f, m.headDurability() / 800.0f))));
        handle.addProperty("melee_damage", 0.0f);
        handle.addProperty("melee_speed", 0.0f);
        handle.addProperty("mining_speed", 0.0f);

        JsonObject stats = new JsonObject();
        stats.add("tconstruct:head", head);
        stats.add("tconstruct:handle", handle);
        stats.add("tconstruct:binding", new JsonObject());

        JsonObject o = new JsonObject();
        o.add("stats", stats);
        return o;
    }

    private static String miningTier(String name, GtMat m) {
        MatTable.Entry e = MatTable.get(name);
        return e != null ? "tic3nh:" + e.tier() : TierMap.tierId(name, m.harvestLevel(), m.durability());
    }

    private static JsonObject traits(String name, GtMat m) {
        JsonArray def = new JsonArray();
        MatTable.Entry e = MatTable.get(name);
        if (e != null && !e.traits().isEmpty()) {
            for (MatTable.Trait tr : e.traits()) {
                def.add(trait(tr.name(), tr.level()));
            }
        } else if (m.unbreakable()) {
            def.add(trait("tconstruct:unbreakable", 1));
        } else if (m.durabilityMultiplier() >= 2) {
            def.add(trait("tconstruct:reinforced", Math.min(5, m.durabilityMultiplier() - 1)));
        }
        JsonObject o = new JsonObject();
        o.add("default", def);
        return o;
    }

    private static JsonObject trait(String name, int level) {
        JsonObject t = new JsonObject();
        t.addProperty("name", name);
        t.addProperty("level", level);
        return t;
    }

    private static JsonObject renderInfo(GtMat m) {
        JsonArray fallbacks = new JsonArray();
        fallbacks.add("metal");

        JsonObject o = new JsonObject();
        o.addProperty("color", String.format("FF%06X", m.rgb() & 0xFFFFFF));
        o.add("fallbacks", fallbacks);
        return o;
    }

    private static float round2(float v) {
        return Math.round(v * 100f) / 100f;
    }
}
