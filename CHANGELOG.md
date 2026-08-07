# Changelog

## v2.5.2 
1. Fixed mining levels to match gtnh on certain tiers

## v2.5.1
1. Flint tools now copper tier (still need mining boost)
2. The shift tooltip no longer shows a dead *Mining XP* line on tools that can't be boosted (stone-tier heads)

## v2.5.0
1. **Shovel reworked into a proper 3-part tool**: shovel head + tool handle + tool binding
    - New **Shovel Head** part with its own part builder pattern, so it's craftable like any other head (2 material cost)
    - Full casting support: gold cast, sand and red sand casts, smeltery part casting and composite casting
2. Tool parts now carry the gold *"Parts can be replaced"* tooltip, like GTNH's Iguana Tweaks

## v2.4.2
1. Paperbark trees no longer generate in water
2. Made much more rare

## v2.4.1
1. Fixed `newHorizonsMode` wiping your own entries out of the `excluded*` lists on every load

## v2.4.0
1. New **paperbark tree**, see readme
2. New `[paperbark]` config section with `paperPerStripChance` (33) and `barkRegrowChance` (25)

## v2.3.1
1. `newHorizonsMode` now writes its GTNH values straight into `tic3nh-common.toml` instead of layering them
2. Wooden pickaxe, axe, shovel, sword and hoe are exempt from the nerf under New Horizons Mode, so a fresh world can still reach a Crafting Station
3. Trimmed the `newHorizonsMode` config comment down to one line
4. Clay buckets now carry the Forge bucket tags (`forge:buckets` + `/empty`, `/water`, `/lava`, `/milk`), so bucket-aware recipes and machines from other mods accept them
5. Added `hungeroverhauled:healing_axe` to the tools New Horizons Mode leaves alone


## v2.3.0
1. New `newHorizonsMode` root config option (default off), one switch that flips the whole config to the GregTech New Horizons values instead of this mod's own
2. **Config keys renamed!** Any existing `tic3nh-common.toml` has to be rewritten by hand, Forge drops unknown keys on load and silently reverts them to default
    - `nerfVanillaTools` / `nerfVanillaHoes` / `nerfVanillaSwords` → `nerfRegularTools` / `nerfRegularHoes` / `nerfRegularSwords`
    - `excludedModTools` → `excludedMods`
    - `excludedToolsIsWhitelist` → `exclusionIsWhitelist`
    - renamed to match what they actually reach, it was never only *vanilla* tools
3. Whole new `[leveling]`, `[boost]` and `[bonuses]` sections, exposing what used to be hardcoded
    - `maxToolLevel`, `xpPerLevelMultiplier`, `xpRequiredToolsPercentage`, `xpRequiredWeaponsPercentage`, `miningSpeedDivider`, `modifiersAtLevels`, `extraModifiers`, plus a `toolLeveling` master switch
    - `randomBonuses`, `bonusesAtLevels`, `usefulBonuses`, `completelyRandomBonuses`, `usageBonusWeight`, and per-bonus allow/weight/useful tables (3 tool categories × 16 bonuses)
    - `pickaxeBoostRequired`, `allowLevelingBoost`, `addMobHeadBoost`, `xpPerBoostLevelMultiplier`, `xpRequiredPickBoostPercentage`
4. GTNH part-replacement penalties, all three on by default, so existing worlds feel this immediately
    - `xpPenalty` (33) burns a third of the progress toward the next level when a tool part is replaced, levels themselves are never lost
    - `pickBoostXpPenalty` (67) and `boostLostOnHeadChange` make a head swap cost the mining boost
    - `removeMobHead` hands the Reanimated modifier back so it has to be applied again
5. New `nerfRegularBows`, regular bows and crossbows can't be drawn at all
6. Exclusion lists are now per category: `excludedTools`, `excludedSwords`, `excludedHoes`, `excludedBows`, with `excludedMods` covering all four
7. New `miningSpeedPercentage`, the mining-speed counterpart to `durabilityPercentage`
8. Repair modifier penalty reworked and made configurable
    - counts the modifier levels actually spent instead of the free upgrade slots left, since leveling keeps handing out fresh slots and free-slot counting had stopped meaning anything
    - `repairPenaltyPerModifier` (0.1, so 3 modifiers repair at 70%) and `repairPenaltyFloor` (0.7)
9. New `startingUpgradeSlots`. `-1` keeps each tool type's own count (what Tinkers ships), GTNH starts every tool at 0 and hands out all 26 slots through leveling
10. Level 99 added to `modifiersAtLevels`, for the 26 modifiers the GTNH wiki quotes at max level
11. Fixed a usage bonus's weight being added even when its `useful` flag was off, so flipping a flag off never actually stopped the bonus rolling
12. Added tool interaction feedback strings

## v2.2.0
1. Fixed the unfired clay bucket recipe
    - with GTCEu Modern installed it takes 5 `gtceu:clay_dust` (GregTech turns clay balls into dust, so the old recipe was uncraftable)
    - without GTCEu, the clay ball recipe stays exactly as it was

## v2.1.0
1. Bows and crossbows now earn tool XP, one per shot, Multishot's extra arrows don't count
2. Added Clay Buckets!
    - unfired clay bucket → smelt → clay bucket
    - holds water, lava and milk, and you can milk a cow with it
    - the lava bucket is destroyed when emptied, water and milk hand the empty bucket back
    - behaves like a vanilla bucket as a crafting remainder and in cauldrons
3. New `strictTiers` config option, strips the vanilla mining levels off a pile of vanilla blocks so the custom tiers do the gating outright (iron ore needs Copper, diamond ore needs Iron, obsidian needs Obsidian). Ships as a built-in datapack
4. New `gtnhStationRecipes` config option, re-gates the Tinkers tables the GTNH way
    - patterns come from 4 paper + a stick instead of planks
    - the Crafting Station costs 3 patterns and loses its log and table shortcuts
    - the Part Builder wants sticks on top of its planks
    - the Tinker Station chains off a finished Crafting Station
5. New `nerfVanillaSwords`, non-Tinkers swords drop to bare-hand damage (the attack damage attribute is stripped) and get the red nerfed tooltip
6. More mining tier quirks
    - the GTCEu material bridge now picks *inside* a tier band by durability, so one harvest level can map to several tiers: iron/tin/redstone, obsidian/ardite, cobalt, manyullyn, darconite, monium
    - every tier ships a block tag file now even when it's empty, so packs and GT ores have something to append to
    - Respawn Anchor → Obsidian, Netherite Block → Cobalt, Reinforced Deepslate → Manyullyn
7. More modifier recipes
    - Haste from redstone dust or a redstone block (harvest tools and chestplates)
    - Sharpness from a quartz gem or a quartz block
    - Critical from flint + 2 quartz
    - Mining Level Boost from a creeper head (3) and a wither skeleton skull (5 and 6)
8. Reinforced back to its old-TiC behaviour: 10% tool damage reduction per level, up to 10 levels, applied with obsidian. TiC's emerald reinforcement recipe stays disabled
9. Silky Jewel is now a shaped recipe, 8 silky cloth around an emerald
10. Fixed the reinforcement casting values
11. Added the MIT license file

## v2.0.0
1. **Per-tool XP leveling!** Every tool earns XP from what you actually do with it and levels on its own, granting upgrade slots as it goes
    - random level-up bonuses, weighted by how the tool has been used
    - level and XP shown in the tooltip
    - `/leveluptool` and `/toolxp` commands for testing and admin use
2. Reworked the mining boost into its own second level
    - tools sit one tier below their real harvest level until the boost is earned by mining
    - HUD readout while mining, plus tooltips for the tier and the boost
    - mob heads can grant the boost outright (zombie, skeleton, creeper, wither skeleton, nether star)
3. **GTCEu Modern material bridge**, replacing the 17 hand-written GTNH material files (blue steel, titanium, tungsten steel, neutronium, naquadah alloy, HSS-E, and the rest)
    - reads GregTech's materials at runtime and generates the Tinkers materials, stats, traits and molten fluid tags into a dynamic datapack
    - dedupes against the materials TiC already ships, maps GT harvest levels onto the custom tiers
    - runs cleanly with GTCEu absent, it's a soft dependency
4. The GTNH modifier set, ported onto TiC3
    - Lapis → Luck, in three levels again (and a gem recipe)
    - Silky Jewel → Silk Touch
    - Ball of Moss → Moss
    - Obsidian → Reinforced *and* Beheading
    - Piston → Knockback
    - Necrotic Bone → Lifesteal
    - the TiC recipes these replace (luck, silky, killager, knockback, necrotic, severing, recapitated, reinforced) are disabled rather than left as duplicates
5. New items: Silky Jewel, Ball of Moss, Reinforcement
6. New config file: `[nerf]` (make non-Tinkers tools useless, with exclusion lists) and `[repair]` (`durabilityPercentage`, `repairModifierPenalty`)
7. Added a Critical modifier and its combat handling
8. EMI compat for the Tinkers table GUI, and the Jade mining-level provider rewritten
9. Reworked the block tier tags into a proper data generator

## v1.0.0
1. Release!
2. 11 custom mining tiers replacing vanilla's five, with the GTNH names and ordering
3. Block tags per tier, generated, with vanilla ores slotted into the right tier
4. Mining Boost modifier and the unboosted tier penalty, so a tool has to earn its real harvest level
5. Mining level tooltips on tools and blocks
6. Jade compat, sneak at a block for its effective tool, whether your held tool can harvest it, and the required mining level
7. GTNH stat overrides for the Tinkers material lineup, plus 17 GTNH materials added outright with their molten fluid tags and colors
