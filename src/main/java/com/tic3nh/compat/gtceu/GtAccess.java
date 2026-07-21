package com.tic3nh.compat.gtceu;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraftforge.fml.ModList;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

public final class GtAccess {

    private static final Logger LOG = LogUtils.getLogger();

    private GtAccess() {}

    public record GtMat(String name, int durability, int durabilityMultiplier, float harvestSpeed,
                        float attackDamage, float attackSpeed, int harvestLevel, boolean unbreakable,
                        boolean magnetic, int enchantability, int rgb, boolean hasIngot) {

        public int headDurability() {
            return Math.max(1, Math.round(durability * (float) Math.max(1, durabilityMultiplier)));
        }
    }

    private static boolean initTried;
    private static boolean available;

    private static Object materialManager;
    private static Object toolKey;
    private static Object ingotKey;
    private static Method mGetRegisteredMaterials, mHasProperty, mGetProperty, mGetName, mGetMaterialRGB;
    private static Method mDurability, mDurMult, mHarvestSpeed, mAttackDamage, mAttackSpeed,
            mHarvestLevel, mUnbreakable, mMagnetic, mEnchantability;
    private static Field fDurMult;

    public static boolean isLoaded() {
        return ModList.get().isLoaded("gtceu");
    }

    private static synchronized boolean init() {
        if (initTried) {
            return available;
        }
        initTried = true;
        try {
            Class<?> apiCls = Class.forName("com.gregtechceu.gtceu.api.GTCEuAPI");
            materialManager = apiCls.getField("materialManager").get(null);
            if (materialManager == null) {
                throw new IllegalStateException("GTCEuAPI.materialManager not populated yet");
            }
            Class<?> matCls = Class.forName("com.gregtechceu.gtceu.api.data.chemical.material.Material");
            Class<?> keyCls = Class.forName("com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey");
            Class<?> toolCls = Class.forName("com.gregtechceu.gtceu.api.data.chemical.material.properties.ToolProperty");

            toolKey = keyCls.getField("TOOL").get(null);
            ingotKey = keyCls.getField("INGOT").get(null);
            mGetRegisteredMaterials = materialManager.getClass().getMethod("getRegisteredMaterials");
            mHasProperty = matCls.getMethod("hasProperty", keyCls);
            mGetProperty = matCls.getMethod("getProperty", keyCls);
            mGetName = matCls.getMethod("getName");
            mGetMaterialRGB = matCls.getMethod("getMaterialRGB");

            mDurability = toolCls.getMethod("getDurability");
            mHarvestSpeed = toolCls.getMethod("getHarvestSpeed");
            mAttackDamage = toolCls.getMethod("getAttackDamage");
            mAttackSpeed = toolCls.getMethod("getAttackSpeed");
            mHarvestLevel = toolCls.getMethod("getHarvestLevel");
            mUnbreakable = toolCls.getMethod("isUnbreakable");
            mMagnetic = toolCls.getMethod("isMagnetic");
            mEnchantability = toolCls.getMethod("getEnchantability");
            try {
                mDurMult = toolCls.getMethod("getDurabilityMultiplier");
            } catch (NoSuchMethodException noGetter) {
                fDurMult = toolCls.getDeclaredField("durabilityMultiplier");
                fDurMult.setAccessible(true);
            }
            available = true;
        } catch (Throwable t) {
            LOG.warn("[TiC3-NH] GTCEu material bridge unavailable ({}: {}); using static material set.",
                    t.getClass().getSimpleName(), t.getMessage());
            available = false;
        }
        return available;
    }

    public static List<GtMat> toolMaterials() {
        if (!isLoaded() || !init()) {
            return List.of();
        }
        List<GtMat> out = new ArrayList<>();
        try {
            Collection<?> mats = (Collection<?>) mGetRegisteredMaterials.invoke(materialManager);
            for (Object mat : mats) {
                try {
                    if (!(Boolean) mHasProperty.invoke(mat, toolKey)) {
                        continue;
                    }
                    Object tp = mGetProperty.invoke(mat, toolKey);
                    if (tp == null) {
                        continue;
                    }
                    int durMult = 1;
                    if (mDurMult != null) {
                        durMult = ((Number) mDurMult.invoke(tp)).intValue();
                    } else if (fDurMult != null) {
                        durMult = fDurMult.getInt(tp);
                    }
                    int rgb = 0xFFFFFF;
                    try {
                        rgb = ((Number) mGetMaterialRGB.invoke(mat)).intValue() & 0xFFFFFF;
                    } catch (Throwable noColor) {

                    }
                    out.add(new GtMat(
                            String.valueOf(mGetName.invoke(mat)),
                            ((Number) mDurability.invoke(tp)).intValue(),
                            Math.max(1, durMult),
                            ((Number) mHarvestSpeed.invoke(tp)).floatValue(),
                            ((Number) mAttackDamage.invoke(tp)).floatValue(),
                            ((Number) mAttackSpeed.invoke(tp)).floatValue(),
                            ((Number) mHarvestLevel.invoke(tp)).intValue(),
                            (Boolean) mUnbreakable.invoke(tp),
                            (Boolean) mMagnetic.invoke(tp),
                            ((Number) mEnchantability.invoke(tp)).intValue(),
                            rgb,
                            (Boolean) mHasProperty.invoke(mat, ingotKey)));
                } catch (Throwable perMaterial) {

                }
            }
        } catch (Throwable t) {
            LOG.warn("[TiC3-NH] GTCEu material scan failed: {}", t.toString());
        }
        return out;
    }
}
