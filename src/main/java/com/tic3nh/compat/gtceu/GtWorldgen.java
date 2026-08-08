package com.tic3nh.compat.gtceu;

import java.util.Set;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import com.gregtechceu.gtceu.api.data.worldgen.SimpleWorldGenLayer;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

// gt pins its builtin "stone" layer to minecraft:overworld, so the twilight forest needs its own
// layer before any vein json naming it can load. the SimpleWorldGenLayer constructor self-registers.
// every gtceu type in here is compileOnly, so only call in from behind GtAccess.isLoaded().
public final class GtWorldgen {

    private static final Logger LOG = LogUtils.getLogger();

    // must match the "layer" field in datapacks/twilight_ores/**/gtceu/ore_veins/twilight/*.json
    public static final String LAYER = "twilight";

    private static final ResourceLocation DIMENSION = new ResourceLocation("twilightforest:twilight_forest");

    private GtWorldgen() {}

    public static void registerLayer() {
        if (!GtAccess.isLoaded()) {
            return;
        }
        try {
            register();
            LOG.info("TiC3-NH registered the {} worldgen layer for {}", LAYER, DIMENSION);
        } catch (Throwable t) {
            LOG.warn("TiC3-NH could not register the {} worldgen layer, twilight ore veins will not generate",
                    LAYER, t);
        }
    }

    // split out so the gtceu classes only load once the guard above has passed
    private static void register() {
        // tf terrain is plain minecraft:stone from y -32 up, so the vanilla stone rule test fits
        new SimpleWorldGenLayer(LAYER,
                () -> new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES),
                Set.of(DIMENSION));
    }
}
