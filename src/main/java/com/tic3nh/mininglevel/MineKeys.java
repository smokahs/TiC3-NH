package com.tic3nh.mininglevel;

import net.minecraft.resources.ResourceLocation;

import com.tic3nh.TiC3NH;
import com.tic3nh.config.Cfg;

public final class MineKeys {

    private MineKeys() {}

    public static final ResourceLocation BOOSTED = new ResourceLocation(TiC3NH.MOD_ID, "boosted");

    public static final ResourceLocation BOOST_XP = new ResourceLocation(TiC3NH.MOD_ID, "boost_xp");

    public static final ResourceLocation BOOST_HEAD_MATERIAL =
            new ResourceLocation(TiC3NH.MOD_ID, "boost_head_material");

    public static final ResourceLocation BOOST_REQ_REF = new ResourceLocation(TiC3NH.MOD_ID, "boost_req_ref");

    // set during a rebuild, acted on the next inventory tick: modifiers cannot be touched mid-rebuild
    public static final ResourceLocation DROP_MOB_HEAD = new ResourceLocation(TiC3NH.MOD_ID, "drop_mob_head");

    public static float xpPerBoostLevelMultiplier() {
        return Cfg.xpPerBoostLevelMultiplier();
    }
}
