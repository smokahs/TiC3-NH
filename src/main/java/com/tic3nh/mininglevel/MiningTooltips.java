package com.tic3nh.mininglevel;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Tier;

import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.utils.HarvestTiers;

/**
 * Builds the mining-level tooltip lines in GTNH's exact wording (from
 * {@code IguanaTweaksTConstruct/LevelingTooltips}): {@code Mining Level: NN-Name},
 * {@code Mining XP: X / Y (Z%)}, and when boosted {@code Mining XP: }<gold>{@code Boosted}.
 */
public final class MiningTooltips {

    private MiningTooltips() {}

    /**
     * {@code Mining Level: NN-Name}. The tier is mapped through {@link GtnhTiers#displayFor} so vanilla
     * tiers render as GTNH names. Pass {@code null} for the tier's own color, or an override
     * (green/red) for the Jade block line.
     */
    public static Component miningLevel(Tier tier, ChatFormatting override) {
        Component name = HarvestTiers.getName(GtnhTiers.displayFor(tier).tier());
        if (override != null) {
            name = name.copy().withStyle(override);
        }
        return Component.translatable("tic3nh.tooltip.mining_level", name);
    }

    /** {@code Mining XP: X / Y (Z%)}, or {@code Mining XP: }<gold>{@code Boosted} once boosted. */
    public static Component miningXp(IToolStackView tool) {
        if (tool.getPersistentData().getBoolean(MiningKeys.BOOSTED)) {
            return Component.translatable("tic3nh.tooltip.mining_xp",
                    Component.translatable("tic3nh.tooltip.boosted").withStyle(ChatFormatting.GOLD));
        }
        int xp = tool.getPersistentData().getInt(MiningKeys.BOOST_XP);
        int required = MiningBoostModifier.requiredBoostXp(tool);
        float percent = required > 0 ? xp * 100f / required : 0f;
        return Component.translatable("tic3nh.tooltip.mining_xp",
                Component.literal(String.format("%d / %d (%.2f%%)", xp, required, percent)));
    }
}
