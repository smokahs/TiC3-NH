package com.tic3nh.compat.gtceu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public final class MatTable {

    private MatTable() {}

    public record Trait(String name, int level) {}

    public record Entry(String tier, List<Trait> traits) {}

    private static Trait t(String name, int level) {
        return new Trait(name, level);
    }

    private static Entry mat(String tier, Trait... traits) {
        return new Entry(tier, List.of(traits));
    }

    private static Trait reinforced(int level) {
        return t("tconstruct:reinforced", level);
    }

    private static final Map<String, Entry> TABLE = new HashMap<>();

    static {

        TABLE.put("damascus_steel", mat("tin", reinforced(2), t("tconstruct:lightweight", 1)));
        TABLE.put("vanadium_steel", mat("obsidian", reinforced(1), t("tconstruct:ductile", 1)));
        TABLE.put("adamantium",     mat("cobalt",   reinforced(3), t("tconstruct:solid", 1)));
        TABLE.put("neutronium",     mat("manyullyn", reinforced(5), t("tconstruct:solid", 1)));
        TABLE.put("hsse",           mat("ardite", reinforced(2), t("tconstruct:momentum", 1)));
        TABLE.put("hss_e",          mat("ardite", reinforced(2), t("tconstruct:momentum", 1)));

        TABLE.put("wrought_iron",    mat("tin",     reinforced(1), t("tconstruct:magnetic", 1)));
        TABLE.put("sterling_silver", mat("tin",     reinforced(1)));
        TABLE.put("cobalt_brass",    mat("tin",      reinforced(1)));
        TABLE.put("stainless_steel", mat("tin", reinforced(1), t("tconstruct:solid", 1)));
        TABLE.put("titanium",        mat("obsidian", reinforced(1), t("tconstruct:lightweight", 1)));
        TABLE.put("tungsten",        mat("obsidian", reinforced(2)));
        TABLE.put("hssg",            mat("obsidian", reinforced(1), t("tconstruct:momentum", 1)));
        TABLE.put("hss_g",           mat("obsidian", reinforced(1), t("tconstruct:momentum", 1)));
        TABLE.put("ultimet",         mat("obsidian", reinforced(2), t("tconstruct:solid", 1)));
        TABLE.put("tungsten_steel",  mat("ardite", reinforced(2), t("tconstruct:momentum", 1)));
        TABLE.put("blue_steel",      mat("tin", reinforced(2), t("tconstruct:ductile", 1)));
        TABLE.put("red_steel",       mat("tin", reinforced(2), t("tconstruct:momentum", 1)));
        TABLE.put("hsss",            mat("ardite", reinforced(2), t("tconstruct:kinetic", 1)));
        TABLE.put("osmiridium",      mat("ardite",   reinforced(2), t("tconstruct:lightweight", 1)));
        TABLE.put("tungsten_carbide", mat("ardite",  reinforced(3)));
        TABLE.put("naquadah",        mat("ardite",   reinforced(2), t("tconstruct:kinetic", 1)));
        TABLE.put("duranium",        mat("ardite",   reinforced(3), t("tconstruct:ductile", 1)));
        TABLE.put("tritanium",       mat("ardite",   reinforced(3), t("tconstruct:lightweight", 1)));
        TABLE.put("naquadah_alloy",  mat("cobalt", reinforced(2), t("tconstruct:kinetic", 1)));
        TABLE.put("naquadah_enriched", mat("ardite", reinforced(3), t("tconstruct:kinetic", 1)));
        TABLE.put("darmstadtium",    mat("darconite", reinforced(4)));
        TABLE.put("iridium",         mat("ardite",   reinforced(3), t("tconstruct:lightweight", 1)));
        TABLE.put("end_steel",       mat("ardite",   reinforced(2)));
        TABLE.put("trinium",         mat("monium",   reinforced(4), t("tconstruct:solid", 1)));
        TABLE.put("bedrockium",      mat("darconite", reinforced(4), t("tconstruct:solid", 1)));
    }

    @Nullable
    public static Entry get(String materialName) {
        return TABLE.get(materialName);
    }
}
