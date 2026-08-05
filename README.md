# A Tinkers' New Horizon
<a href='https://files.minecraftforge.net'><img alt="forge" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/supported/forge_vector.svg"></a>

A GregTech New Horizons inspired addon for Tinkers' Construct 3. Brings the GTNH tinkering experience to modern TiC: more harvesting levels, per-tool XP leveling, GTNH modifiers, and GTCEu Modern material compat!

Minecraft 1.20.1, Forge 47.4.0, MIT.

--------------------------------------

## About!

### - Mining Levels

Tools now have 11 custom mining tiers, incorporating the ones you love as well as more you've never heard of!

### - Per-Tool XP Leveling

Every tool gains XP as you use it and levels up on its own, granting modifier slots. This is built into all tools. Commands `/leveluptool` and `/toolxp` are included for testing and admin use.

The two different levels may be confusing, but the easiest way to think about it is 'Mining Level = Harvest Level' and 'Tool level = upgrades' the more you use a pick the better it gets!

### - Mining Boost

Tools start one tier below their intended harvest level, encouraging the player to use the tools! Treat them with care!

### - GTNH Modifier Set

The GTNH modifier lineup ported onto TiC3. (Most notably lapis = luck again! )

### - The Shovel

The classic Tinkers shovel is back. Built at the Tinker Station from a **Shovel Head** (new part, with its own pattern and gold/sand casts), a tool handle and a tool binding. It digs, it makes dirt paths, and it levels, boosts and takes modifiers like every other tool.

### - New Horizons Mode

One switch that flips the whole config to the GregTech New Horizons values instead of this mod's own, including the vanilla tool nerf: non-Tinkers tools mine nothing, so the pack starts with flint and patterns like the good old days.

```toml
newHorizonsMode = true
```

### - Clay Buckets

Smelt an unfired clay bucket and you have early liquid transport: water, lava and milk. The lava bucket is destroyed when emptied, exactly as painful as you remember.

### - Jade Compat

With Jade installed, sneak while looking at a block to see its effective tool, whether your held tool can harvest it, and the required mining level. 

### - Strict Tiers

Enable strict tiers in the config to strip vanilla mining levels off a lot of vanilla blocks, forcing the tiers outright

```toml
[tiers]
    strictTiers = true
```

### - GTNH Station Recipes

Re-gates the Tinkers tables the GTNH way.

```toml
[stations]
    gtnhStationRecipes = true
```

Patterns come from 4 paper + a stick instead of planks, the Crafting Station costs 3 patterns and loses its log and table shortcuts, the Part Builder wants sticks on top of its planks, and the Tinker Station chains off a finished Crafting Station.

### - Paperbark Tree

Take an axe (vanilla or Tinkers) to a paperbark log and the bark peels in four strips, 25%, 50%, 75%, then bare wood. Each strip has a 1-in-3 chance of a sheet of paper, so a whole log averages a little over one. Breaking a log gives another 10% chance of a sheet.

**The bark grows back. ONLY IF it is not fully stripped.**

Part-peeled logs can't be picked up, if you break one and you get the stripped log. Full wood set supported: planks, stairs, slab, fence, gate, door, trapdoor, button, pressure plate, signs and hanging signs.

```toml
[paperbark]
    paperPerStripChance = 33
    barkRegrowChance = 25
```

--------------------------------------

## Dependencies

Required: [Mantle](https://www.curseforge.com/minecraft/mc-mods/mantle) 1.11.104, [Tinkers' Construct](https://www.curseforge.com/minecraft/mc-mods/tinkers-construct) 3.11.2.166.

Optional: [GTCEu Modern](https://www.curseforge.com/minecraft/mc-mods/gtceu-modern) (material bridge), [Jade](https://www.curseforge.com/minecraft/mc-mods/jade) (mining-level tooltip), EMI (table-GUI fix).

--------------------------------------

## Building

Requires JDK 17.

```bash
./gradlew build
```

Jar lands in `build/libs/`. `./gradlew runData` regenerates the block tags in `src/generated/resources`.

--------------------------------------

## Installation

Drop `tic3nh-<version>.jar` into `mods/`, alongside Mantle and Tinkers' Construct. Optional integrations are soft dependencies and run cleanly with any subset.

--------------------------------------

## Issues

[GitHub issue tracker](https://github.com/smokahs/TiC3-NH/issues). Include MC + Forge version, TiC3-NH version, installed mod subset, crash log (if applicable), and `logs/latest.log`.
