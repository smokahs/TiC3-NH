# A Tinkers' New Horizon

A **GregTech New Horizons-style Tinkers' Construct 3 addon** for **Minecraft 1.20.1 (Forge)**.
(mod id `tic3nh`; repo `TiC3-NH`.)

It recreates the GTNH tinker experience on modern TiC:

- **Numbered mining levels** — the GTNH 10-tier scheme (Stone → Manyullyn+), tools start one tier
  below their material until enough mining XP boosts them once; skull / diamond / emerald boosts.
- **Per-tool XP leveling** — tools gain XP from harvesting/combat, level up, and earn modifier
  (upgrade) slots on the GTNH curve (max level 99).
- **GTNH modifier set** — GTNH recipes/effects layered onto TiC3 modifiers.
- **Material bridge** — pulls tool materials from **GTCEu Modern** + **Monifactory**, mapping each
  material's GregTech harvest level onto a GTNH mining tier.

See **[DESIGN.md](DESIGN.md)** for the full architecture, the exact GTNH→TiC3 API mapping, and the
phased build plan. Mechanics are ported from GTNH's `IguanaTweaksTConstruct` + `TinkersGregworks`.

## Status

Phase 2 — 10 GTNH mining tiers registered + sorted, names/colors, and starter block tags. Feature
phases follow the plan in `DESIGN.md §9`.

## Building

Requires JDK 17.

```bash
./gradlew build
```

Dependencies (auto-resolved): Forge 47.4.0, Mantle 1.11.108, Tinkers' Construct 3.11.2.206, from the
DVS1 maven. GTCEu Modern is an optional runtime dependency for the material bridge.

## Testing

Built jar drops into the PrismLauncher test instance:
`%APPDATA%\PrismLauncher\instances\tinkers mod` (`.minecraft/mods`).

## Author

smokahs

## License

MIT
