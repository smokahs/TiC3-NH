
''This page provides data and information about the Tinker's Construct tools. You may be looking for [[GT Tools|GregTech Tools]] instead.''[[File:BronzeTiConMaterialNEI.png|thumb|alt=Bronze Base Durability: 285, Handle Modifier: 1.25x, Full Durability: 356, Mining Speed: 6.5, Mining Level: 04-Redstone, Attack: 1.5 Hearts, Reinforced 1.  Bow and Arrow.  Draw Speed: 45, Arrow Speed 5.1, Weight: 3.2, Break Chance: 1.2|NEI Tool Materials tab for Bronze]]The '''Tinker's Construct''' (TiC) mod introduces customizable tools and weapons. These tools can be built out of multiple parts with different materials. Each material has its own stats and special attributes. Tool parts are created in the part builder, [[Smeltery]], or an [[Medium Voltage (MV)|MV]] extruder, and combined with others to build a tool in a tool station or tool forge.  

'''Spreadsheet''': [https://docs.google.com/spreadsheets/d/12i3di_dcMtQj3Pf-75DO3LivkWyiSuXNHc9LBEdCFV4/edit?usp=sharing Tinkers Tool Planner] for a full list of materials, stats, and a calculator.  

== Parts ==
Search the name of a part (ie. Pickaxe Head) in [[Not Enough Items|NEI]] to see all the available materials for that part, or view the uses of a TiC material and navigate to the "Tool Materials" tab to see its statistics and [[Tinkers Tools#Traits|traits]]. The image to the right shows the stats for bronze, for example. Different types of materials are made into tools in different ways:

* Non-metallic materials (ie. flint and stone) are made into tool parts via patterns in a part builder. These parts may also be used to make other tool parts as if they were the base material with a value equal to their cost. For example, two obsidian tool rods scavenged from villages can be crafted into an obsidian pickaxe head. Hold shift while hovering over a pattern to see all valid part builder materials.
* Lower-tech metals (ie. bronze and steel) must be cast in a mold on a casting table after being melted in a [[Smeltery]]. Thaumium is the only exception and made in the part builder instead.
* Higher-tech metals (ie. most GregTech materials) are made in an [[Medium Voltage (MV)|MV]]+ extruder with the same casts as the casting table.

=== Replacing Parts ===
{{important|Only fully repaired tools can replace parts}}Any part of a TiC tool may be replaced by combining it with a new piece in a tool station, tool forge, crafting station, or [[Adventure Backpacks|adventure backpack]]. The tool must be fully repaired and the replacement must not leave the tool with a negative number of [[Tinkers Tools#Modifiers|modifiers]], such as removing a thaumium binding from a tool with zero modifiers left. This also destroys the original part and removes any traits that it provided. Tools with multiple of the same part use the position of the replacement in the crafting grid to determine which one to replace. The additional modifiers granted from crafting a new tool completely out of magical wood or infinity are '''not''' subject to this restriction; those parts can be fully replaced without ever reducing the total number of modifiers.

== Repairs ==
A TiC tool may be repaired by combining it with the base form of the head's material in a tool station, tool forge, crafting station, or [[Adventure Backpacks|adventure backpack]]. For example, repair an iron pickaxe head with an iron ingot. The tool must be placed in the center of the crafting grid (on the pickaxe icon) for the crafting station and adventure backpack specifically. Larger repairs may be made by adding more ingots to the surrounding grid slots. The total durability restored depends on the amount of materials used, the base durability of the tool before modifiers, a multiplier that decreases with the number of previous repairs, and a multiplier that increases with the number of unused modifiers, as seen in the following equation.

{| width="100%" cellspacing="0" border="0"
| style="width:75%; padding:10px; vertical-align:top;" |<math>\text{Durability Restored} = \text{Ingots} \times (50 + 0.8 \times \text{Base Durability}) \times \text{Repair Multiplier} \times \text{Modifier Multiplier}</math>

The repair multiplier is increased every time the tool is repaired which means additional repairs restore 1% less durability down to a minimum of 50%. It is highly recommended to make larger repairs with more materials because they only increase the number of repairs by one for the whole operation. At best, each ingot or base material restores over 80% of the tool's base durability. At worst, each ingot or base material restores slightly over 28% of the tool's base durability.

<math>\text{Repair Multiplier} = \text{Max}(0.5, (100 - \text{Repair Count})/100)</math>
| style="width:25%; padding:10px; vertical-align:top;" |
{| class="wikitable" style="float:right; margin-left: 1em; text-align:center;"
| style="background-color:#12364B; color:white;" |'''Unused Modifiers'''
| style="background-color:#12364B; color:white;" |'''Modifier Multiplier'''
|-
|3+
|1.0
|-
|2
|0.9
|-
|1
|0.8
|-
|0
|0.7
|}
|}

== Levels ==
[[File:TiConIron1.png|thumb|NEI Tool Materials tab for Iron]]There are ''two'' separate leveling systems associated with TiC tools, listed below. For tools, experience increases by one XP per block harvested for both mining level and tool level simultaneously, if possible. For weapons, one XP is gained for each full heart of damage the weapon does, regardless if that damage is actually inflicted or not. Hitting a mob with ten hearts for eight hearts of damage twice will give 16 XP. 

=== Mining Level ===
A tool's mining level determines what blocks it can break. The [[WAILA]] tooltip displays the mining level requirement (ie. 05-Obsidian) while looking at a block. The text appears green if the current tool meets the requirement, or red if it does not. Mining level is only present on tools that break stone/ore blocks like Pickaxes and Hammers. 

Mining level can be increased ONE time by earning enough mining XP. See the tooltip of a tool for its current mining XP and the required amount to level-up. Replacing a tool head resets the mining XP back to zero. After leveling-up, the mining XP reads "Boosted" to indicate that it cannot be increased any further. The [[Not Enough Items|NEI]] Tool Materials tab always shows the boosted mining level of a material, but the extruder and [[smeltery]] tabs always show the unboosted mining level. 

Mining level can also be instantly boosted by having an item (Zombie Head 1, Skeleton Skull 2, Creeper Head 4, Wither Skull 7, or Nether Star 8) added to it. Each item only works on tools up to the mining level listed on the head and shows on the tool's icon. These items do not cost a modifier slot to apply but can only be added to fully repaired tools.
{{Important|All mining levels on this page are the BOOSTED mining levels. New tools and parts are always going to start one level down until enough mining XP has been earned.}}

=== Tool Level ===
Tool level is only for adding [[Tinkers Tools#Modifiers|modifier]] slots. The required amount of XP per level depends solely on the harvesting speed for tools and the damage dealt for weapons. Each level-up increases the required amount of XP by 20% and reduces the frequency at which modifier slots are awarded. The maximum tool level is 99 for a total of 26 modifiers.      

It is possible to use the [[Dynamism Tablet]] from [[Thaumcraft]] to automatically level TiC tools, but that still consumes durability and takes a lot of time. There is also the following command for instantly leveling a tool.

* <code>/leveluptool <name></code> increases the level of the held tool by one (must be op). The player name is optional.

{| width="100%" cellspacing="0" border="0"
| style="width:75%; padding:10px; vertical-align:top;" |
{| class="wikitable" width="100%" style="font-size:12px;"
| style="background-color:#12364B; color:white;" |'''Mining Level'''
| style="background-color:#12364B; color:white;" |'''Block Tier'''
| style="background-color:#12364B; color:white;" |'''Notable Materials'''
|-
| 00 || Stone || Netherrack, Paper, Magical Wood
|-
| 01 || Copper || Flint, Bone
|-
| 02 || Iron || Copper
|-
| 03 || Tin || Iron, Thaumium
|-
| 04 || Redstone || Bronze
|-
| 05 || Obsidian || Steel, Damascus Steel, Vanadiumsteel, Energetic Alloy
|-
| 06 || Ardite || Obzinite, Shadow Metal, Manasteel, Unstable, Vibrant Alloy
|-
| 07 || Cobalt || Ardite, Infinity, Oriharukon
|-
| 08 || Manyullyn || Cobalt, Neutronium
|-
| 09+ || --- || Manyullyn, Bedrockium, Draconium, Awakened Draconium, Infinity Catalyst, Ichorium, Gaia Spirit, Shirabon
|}
| style="width:25%; padding:10px; vertical-align:top;" |
{| class="wikitable" width="100%" style="font-size:12px;"
| style="background-color:#12364B; color:white;" |'''Tool Level'''
| style="background-color:#12364B; color:white;" |'''Modifier Slot'''
|-
| 2 to 3 || Every Level
|-
| 5 to 11 || Every 2 Levels
|-
| 14 to 20 || Every 3 Levels
|-
| 24 to 40 || Every 4 Levels
|-
| 45 to 99 || Every 5 Levels
|}
|}

== Pickaxe/Shovel/Axe/Mattock ==
The pickaxe is crucial for mining ores in the early game. The shovel is the ideal tool for collecting dirt, sand, clay, gravel, etc.. The axe is for harvesting trees. And the mattock is primarily for tilling dirt, although it can function as an axe too. Crafting these tools require some of the following components in a TOOL STATION:

* ''Head'' - Determines base durability, mining speed, and repair material.
* ''Binding'' - Potentially adds a trait.
* ''Rod'' - Boosts durability.

The head material should be something easily obtainable with a high durability and high mining speed. However, the experience required to level up a tool increases exponentially with mining speed so keep that in mind when choosing parts or modifiers. The reinforced traits do not stack and only the highest level is applied to the tool. See the following tables for a list of recommended materials.

The materials highlighted '''{{Color|text=BLUE|fg=#6495ED}}''' are unique. Netherrack parts have a very low durability and low mining speed, but they are incredibly easy to repair and therefore the perfect material for leveling. Unstable parts have a very low durability but can make a tool unbreakable if every part is made from it. Manyullyn parts are made from [[Smeltery]] alloying despite appearing as a higher tier recipe. Perditio parts have a very low durability but a very high mining speed which balances well with other high durability parts. Thaumium parts are made in the part builder despite coming from a metal ingot.

The recommended modifiers are lapis lazuli for fortune and redstone for haste. The reinforcement modifier may also be useful on the axe or mattock for automated farming. Mending moss is best left to the hammer, excavator, or crossbow bolts. The diamond modifier can increase the mining level by one per use up to 05-Obsidian, but is not recommended because the extra mining speed from haste is a much better investment.

{| width="100%" border="0"
| colspan="2" style="vertical-align: top;" |
{| class="mw-collapsible mw-collapsed wikitable" style="width:100%;"
| style="background-color:#12364B; color:white; text-align:left;" |'''Durability & Speed Equations'''
|-
|<math>\text{Durability} = \text{Head Durability} \times \text{Rod Durability Modifier}</math>
<math>\text{Speed} = \text{Head Speed}</math>
|}
|-
| style="width:53%; vertical-align:top; padding:6px;" |
{| class="wikitable"
| style="background-color:#12364B; color:white;" |'''Tier'''
| style="background-color:#12364B; color:white;" |'''Head'''
| style="background-color:#12364B; color:white;" |'''Durability'''
| style="background-color:#12364B; color:white;" |'''Speed'''
| style="background-color:#12364B; color:white;" |'''Mining Level'''
| style="background-color:#12364B; color:white;" |'''Trait'''
|-
| rowspan="6" style="background-color:#44657C; color:white; text-align:center;" |S
|'''{{Color|text=Netherrack|fg=#6495ED}}'''
|92
|4.56
|00-Stone
|Stonebound
|-
|Flint
|113
|4.00
|01-Copper
| -
|-
|Iron
|188
|6.00
|03-Tin
|Reinforced I
|-
|Bronze
|285
|6.50
|04-Redstone
|Reinforced I
|-
|Steel
|300
|7.00
|05-Obsidian
|Reinforced II
|-
|Alumite/Obzinite
|413
|7.90
|06-Ardite
|Reinforced II
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| rowspan="2" style="background-color:#44657C; color:white; text-align:center;" |LV
|'''{{Color|text=Unstable|fg=#6495ED}}'''
|75
|7.00
|06-Ardite
|Reinforced IV
|-
|Cobalt
|600
|11.0
|08-Manyullyn
| Reinforced II
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| rowspan="5" style="background-color:#44657C; color:white; text-align:center;" |MV
|'''{{Color|text=Perditio|fg=#6495ED}}'''
|48
|32.0
|06-Ardite
| -
|-
|Damascus Steel
|1,500
|8.00
|05-Obsidian
|Reinforced II
|-
|Mana Steel
|3,840
|8.00
|06-Ardite
| -
|-
|Vanadium Steel
|1,440
|18.0
|05-Obsidian
| -
|-
|Void Metal
|2,050
|32.0
|06-Ardite
| -
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |HV
|Vibrant Alloy
|3,036
|18.0
|06-Ardite
| -
|-
|Ademic Steel
|4,608
|12.0
|06-Ardite
| -
|-
|Shadow Metal
|6,144
|32.0
|06-Ardite
| -
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |EV
|Terrasteel
|7,680
|32.0
|07-Cobalt
| -
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| rowspan="4" style="background-color:#44657C; color:white; text-align:center;" |IV
|Oriharukon
|7,680
|32.0
|07-Cobalt
| -
|-
|HSS-E
|7,680
|32.0
|08-Manyullyn+
| -
|-
|Orichalcum
|15,360
|32.0
|00-Stone
| -
|-
|Elven Elementium
|24,576
|20.0
|08-Manyullyn+
| -
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |LuV
|Draconium
|24,576
|20.0
|08-Manyullyn+
| -
|-
|Adamantium Alloy
|76,800
|191
|07-Cobalt
| -
|-
|MAR-Ce-M200
|153,600
|150
|06-Ardite
| -
|}
| style="width:45%; vertical-align:top; padding:6px;" |
{| class="wikitable"
| style="background-color:#12364B; color:white;" |'''Tier'''
| style="background-color:#12364B; color:white;" |'''Tool Rod'''
| style="background-color:#12364B; color:white;" |'''Durability'''
| style="background-color:#12364B; color:white;" |'''Trait'''
|-
| rowspan="4" style="background-color:#44657C; color:white; text-align:center;" |S
|Bronze
|x1.25
|Reinforced I
|-
|Steel
|x1.30
|Reinforced II
|-
|Alumite/Obzinite
|x1.30
|Reinforced II
|-
|Green Slime Crystal
|x2.00
|Slimy
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| rowspan="2" style="background-color:#44657C; color:white; text-align:center;" |LV
|Cobalt
|x1.75
|Reinforced II
|-
|'''{{Color|text=Manyullyn|fg=#6495ED}}'''
|x2.50
| -
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |MV
|Vanadium Steel
|x2.50
| -
|-
|Mana Steel
|x3.50
| -
|-
|Damascus Steel
|x5.00
|Reinforced II
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |HV
|Reinforced
|x6.00
| -
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| rowspan="5" style="background-color:#44657C; color:white; text-align:center;" |IV
|Elven Elementium
|x6.50
| -
|-
|HSS-E
|x6.50
| -
|-
|HSS-S
|x7.50
| -
|-
|Ichorium
|x11.5
| -
|-
|Hikarium
|x12.5
| -
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |LuV
|Magneto Resonatic
|x15.5
| -
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |ZPM
|Artherium-Sn
|x17.5
| -
|}
{| class="wikitable"
| style="background-color:#12364B; color:white;" |'''Tier'''
| style="background-color:#12364B; color:white;" |'''Binding'''
| style="background-color:#12364B; color:white;" |'''Trait'''
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |S
|Bronze
|Reinforced I
|-
|Steel
|Reinforced II
|-
|Obsidian
|Reinforced III
|- style="background-color:#BBBBBB;"
| colspan="3" |
|-
| rowspan="2" style="background-color:#44657C; color:white; text-align:center;" |LV
|'''{{Color|text=Thaumium|fg=#6495ED}}'''
|Thaumic
|-
|'''{{Color|text=Unstable|fg=#6495ED}}'''
|Reinforced IV
|}
|}

== Hammer/Excavator/Lumber Axe ==
Hammers and Excavators greatly speed up mining/digging because they break blocks in a 3x3 area, or just a single block while sneaking. Lumber Axes greatly speed up the harvesting of trees because they break all adjacent logs at once. Crafting these tools requires some of the following components in a TOOL FORGE:

* ''Head'' - Determines base durability, mining speed, and repair material.
* ''Large Plate'' - Determines base durability and mining speed.
* ''Tough Binding'' - Boosts durability.
* ''Tough Rod'' - Boosts durability.

The head material should be something easily obtainable with a high durability and high mining speed. However, the experience required to level up a tool increases exponentially with mining speed so keep that in mind when choosing parts or modifiers. The reinforced traits do not stack and only the highest level is applied to the tool. See the following tables for a list of recommended materials.

The materials highlighted '''{{Color|text=BLUE|fg=#6495ED}}''' are unique. Netherrack parts have a very low durability and low mining speed, but they are incredibly easy to repair and therefore the perfect material for leveling. Unstable parts have a very low durability but can make a tool unbreakable if every part is made from it. Manyullyn parts are made from [[Smeltery]] alloying despite appearing as a higher tier recipe. Perditio parts have a very low durability but a very high mining speed which balances well with other high durability parts.

A very powerful strategy is to first craft the tool entirely out of magical wood for a free 8 modifiers that stay with the tool even as parts are replaced with other materials. Next, swap everything out for unstable parts to make the tool unbreakable. Level up the tool a few times while the mining speed is low and then add one fortune modifier and as many redstone modifiers as possible for an incredibly high mining speed. Don't forget to add the [[Tinkers Tools#Modifiers|bonus modifiers]] too.

The recommended modifiers are mending moss for passively restoring durability, lapis lazuli for fortune, and redstone for haste. The reinforcement modifier may also be useful on the lumber axe for chopping down larger trees (ie. sacred oak) or automated farming.

{| width="100%" border="0"
| colspan="2" style="vertical-align: top;" |
{| class="mw-collapsible mw-collapsed wikitable" style="width:90%;"
| style="background-color:#12364B; color:white; text-align:left;" |'''Durability & Speed Equations'''
|-
|<math>\text{Hammer Durability} = (\text{Head Durability} + \sum \text{Plate Durability}) \times \text{Rod Durability Modifier} \times 3</math><math>\text{Hammer Speed} = (\text{Head Speed} + \sum \text{Plate Speed}) \times 2/15</math>
|-
|<math>\text{Excavator Durability} = (\text{Head Durability} + \text{Plate Durability}) \times \sum \text{Rod/Binding Durability Modifiers} \times 99/64</math><math>\text{Excavator Speed} = (\text{Head Speed} + \text{Plate Speed}) \times 0.2 </math>
|-
|<math>\text{Lumber Axe Durability} = (\text{Head Durability} + \text{Plate Durability}) \times \sum \text{Rod/Binding Durability Modifiers} \times 45/32</math><math>\text{Lumber Axe Speed} = (\text{Head Speed} + \text{Plate Speed}) \times 0.2 </math>
|}
|-
| style="width:55%; vertical-align:top; padding:6px;" |
{| class="wikitable"
| style="background-color:#12364B; color:white;" |'''Tier'''
| style="background-color:#12364B; color:white;" |'''Head / Large Plate'''
| style="background-color:#12364B; color:white;" |'''Durability'''
| style="background-color:#12364B; color:white;" |'''Speed'''
| style="background-color:#12364B; color:white;" |'''Mining Level'''
| style="background-color:#12364B; color:white;" |'''Trait'''
|-
| rowspan="4" style="background-color:#44657C; color:white; text-align:center;" |S
|'''{{Color|text=Netherrack|fg=#6495ED}}'''
|92
|4.56
|00-Stone
|Stonebound
|-
|Bronze
|285
|6.50
|04-Redstone
|Reinforced I
|-
|Steel
|300
|7.00
|05-Obsidian
|Reinforced II
|-
|Alumite/Obzinite
|413
|7.90
|06-Ardite
|Reinforced II
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| rowspan="2" style="background-color:#44657C; color:white; text-align:center;" |LV
|'''{{Color|text=Unstable|fg=#6495ED}}'''
|75
|7.00
|06-Ardite
|Reinforced IV
|-
|Cobalt
|600
|11.0
|08-Manyullyn
| Reinforced II
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| rowspan="5" style="background-color:#44657C; color:white; text-align:center;" |MV
|'''{{Color|text=Perditio|fg=#6495ED}}'''
|48
|32.0
|06-Ardite
| -
|-
|Damascus Steel
|1,500
|8.00
|05-Obsidian
|Reinforced II
|-
|Mana Steel
|3,840
|8.00
|06-Ardite
| -
|-
|Vanadium Steel
|1,440
|18.0
|05-Obsidian
| -
|-
|Void Metal
|2,050
|32.0
|06-Ardite
| -
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |HV
|Vibrant Alloy
|3,036
|18.0
|06-Ardite
| -
|-
|Ademic Steel
|4,608
|12.0
|06-Ardite
| -
|-
|Shadow Metal
|6,144
|32.0
|06-Ardite
| -
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |EV
|Terrasteel
|7,680
|32.0
|07-Cobalt
| -
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| rowspan="4" style="background-color:#44657C; color:white; text-align:center;" |IV
|Oriharukon
|7,680
|32.0
|07-Cobalt
| -
|-
|HSS-E
|7,680
|32.0
|08-Manyullyn+
| -
|-
|Orichalcum
|15,360
|32.0
|00-Stone
| -
|-
|Elven Elementium
|24,576
|20.0
|08-Manyullyn+
| -
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |LuV
|Draconium
|24,576
|20.0
|08-Manyullyn+
| -
|-
|Adamantium Alloy
|76,800
|191
|07-Cobalt
| -
|-
|MAR-Ce-M200
|153,600
|150
|06-Ardite
| -
|}
| style="width:45%; vertical-align:top; padding:6px;" |
{| class="wikitable"
| style="background-color:#12364B; color:white;" |'''Tier'''
| style="background-color:#12364B; color:white;" |'''Tough Binding / Rod'''
| style="background-color:#12364B; color:white;" |'''Durability'''
| style="background-color:#12364B; color:white;" |'''Trait'''
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |S
|Bronze
|x1.25
|Reinforced I
|-
|Steel
|x1.30
|Reinforced II
|-
|Alumite/Obzinite
|x1.30
|Reinforced II
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |LV
|Magnetic Iron
|x1.50
| -
|-
|Cobalt
|x1.75
|Reinforced II
|-
|'''{{Color|text=Manyullyn|fg=#6495ED}}'''
|x2.50
| -
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |MV
|Vanadium Steel
|x2.50
| -
|-
|Mana Steel
|x3.50
| -
|-
|Damascus Steel
|x5.00
|Reinforced II
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |HV
|Reinforced
|x6.00
| -
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| rowspan="5" style="background-color:#44657C; color:white; text-align:center;" |IV
|Elven Elementium
|x6.50
| -
|-
|HSS-E
|x6.50
| -
|-
|HSS-S
|x7.50
| -
|-
|Ichorium
|x11.5
| -
|-
|Hikarium
|x12.5
| -
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |LuV
|Magneto Resonatic
|x15.5
| -
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |ZPM
|Artherium-Sn
|x17.5
| -
|}
|}
== Broadsword/Rapier ==
The broadsword is a basic melee weapon with the unique ability to block attacks on right-click, reducing their damage. The rapier is a slightly more agile melee weapon with the unique ability to pierce armor and lunge backwards on right-click. Crafting these weapons requires the following components in a TOOL STATION:

* ''1 Blade'' - Determines base durability, attack damage, and repair material.
* ''1 Guard'' - Potentially adds a trait.
* ''1 Tool Rod'' - Boosts durability.

These weapons can critically strike while the player is descending in the air (after jumping). The blade material should be something easily obtainable with a high durability and damage. The reinforced traits do not stack and only the highest level is applied to the weapon. See the following table for a list of recommended materials, or the crossbow page for a far superior weapon.

The materials highlighted '''{{Color|text=BLUE|fg=#6495ED}}''' are unique. Manyullyn parts are made from [[Smeltery]] alloying despite appearing as a higher tier recipe. Thaumium parts are made in the part builder despite coming from a metal ingot. Unstable parts can make a tool unbreakable if every part is made from it.

The recommended modifiers are lapis lazuli for looting, quartz for sharpness, and life steal for survivability. Mending moss is best left to crossbow bolts and beheading is best left to a cleaver. The reinforcement modifier may also be useful for automatic mob farms.

{| width="100%" border="0"
| colspan="2" style="vertical-align: top;" |
{| class="mw-collapsible mw-collapsed wikitable" style="width:90%;"
| style="background-color:#12364B; color:white; text-align:left;" |'''Durability & Attack Equations'''
|-
|<math>\text{Broadsword Durability} = \text{Blade Durability} \times \text{Rod Durability Modifier} \times 1.2</math><math>\text{Broadsword Attack} = 4 + \text{Blade Damage}</math>
|-
|<math>\text{Rapier Durability} = \text{Blade Durability} \times \text{Rod Durability Modifier} \times 0.7</math><math>\text{Rapier Attack} = 0.8 \times (2 + \text{Blade Damage})</math>
|}
|-
| style="width:44%; vertical-align:top; padding:6px;" |
{| class="wikitable"
| style="background-color:#12364B; color:white;" |'''Tier'''
| style="background-color:#12364B; color:white;" |'''Sword Blade'''
| style="background-color:#12364B; color:white;" |'''Durability'''
| style="background-color:#12364B; color:white;" |'''Damage'''
| style="background-color:#12364B; color:white;" |'''Trait'''
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |S
|Bronze
|285
|1.50
|Reinforced I
|-
|Steel
|300
|1.50
|Reinforced II
|-
|Alumite/Obzinite
|413
|2.00
|Reinforced II
|- style="background-color:#BBBBBB;"
| colspan="5" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |LV
|Cobalt
|600
|2.00
| Reinforced II
|- style="background-color:#BBBBBB;"
| colspan="5" |
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |MV
| style="text-align: center;" |Damascus Steel
|1,500
|2.50
| Reinforced II
|-
|Dark Steel
|1,561
|5.00
| -
|-
|Vanadium Steel
|1,440
|7.00
| -
|- style="background-color:#BBBBBB;"
| colspan="5" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |HV
|Meteoric Steel
|1,480
|12.0
|Reinforced I
|- style="background-color:#BBBBBB;"
| colspan="5" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |EV
|Tungstensteel
|1,920
|14.0
|Reinforced III
|- style="background-color:#BBBBBB;"
| colspan="5" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |IV
|Palladium
|2,200
|16.0
| -
|- style="background-color:#BBBBBB;"
| colspan="5" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |LuV
|Adamantium
|9,216
|24.0
| -
|}
{| class="wikitable"
| style="background-color:#12364B; color:white;" |'''Tier'''
| style="background-color:#12364B; color:white;" |'''Guard'''
| style="background-color:#12364B; color:white;" |'''Trait'''
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |S
|Bronze
|Reinforced I
|-
|Steel
|Reinforced II
|-
|Obsidian
|Reinforced III
|- style="background-color:#BBBBBB;"
| colspan="3" |
|-
| rowspan="2" style="background-color:#44657C; color:white; text-align:center;" |LV
|'''{{Color|text=Thaumium|fg=#6495ED}}'''
|Thaumic
|-
|'''{{Color|text=Unstable|fg=#6495ED}}'''
|Reinforced IV
|}
| style="width:60%; vertical-align:top; padding:6px;" |
{| class="wikitable"
| style="background-color:#12364B; color:white;" |'''Tier'''
| style="background-color:#12364B; color:white;" |'''Tool Rod'''
| style="background-color:#12364B; color:white;" |'''Durability'''
| style="background-color:#12364B; color:white;" |'''Trait'''
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |S
|Bronze
|x1.25
|Reinforced I
|-
|Steel
|x1.30
|Reinforced II
|-
|Alumite/Obzinite
|x1.30
|Reinforced II
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |LV
|Magnetic Iron
|x1.50
| -
|-
|Cobalt
|x1.75
|Reinforced II
|-
|'''{{Color|text=Manyullyn|fg=#6495ED}}'''
|x2.50
| -
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |MV
|Vanadium Steel
|x2.50
| -
|-
|Mana Steel
|x3.50
| -
|-
|Damascus Steel
|x5.00
|Reinforced II
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |HV
|Reinforced
|x6.00
| -
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| rowspan="5" style="background-color:#44657C; color:white; text-align:center;" |IV
|Elven Elementium
|x6.50
| -
|-
|HSS-E
|x6.50
| -
|-
|HSS-S
|x7.50
| -
|-
|Ichorium
|x11.5
| -
|-
|Hikarium
|x12.5
| -
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |LuV
|Magneto Resonatic
|x15.5
| -
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |ZPM
|Artherium-Sn
|x17.5
| -
|}
|}
== Cleaver ==
The Cleaver is not a very good melee weapon because its slow attacks significantly reduce its overall damage output. However, it comes with a built-in 20% beheading chance that can be further increased with beheading modifiers. This is extremely useful for collecting wither skeleton skulls in the nether while building the NASA workbench. An alternative is the Skullfire Sword, but that requires a fair amount of progression into [[Thaumcraft]]. Crafting a Cleaver requires the following components in a TOOL FORGE:

* ''1 Large Sword Blade'' - Determines base durability, attack damage, and repair material.
* ''1 Large Plate'' - Determines base durability and attack damage.
* ''2 Tough Rods'' - Boosts durability.

This weapon can critically strike while the player is descending in the air (after jumping). The blade material should be something easily obtainable with a high durability and high damage. The reinforced traits do not stack and only the highest level is applied to the tool. See the following tables for a list of recommended materials. The materials highlighted '''{{Color|text=BLUE|fg=#6495ED}}''' are unique. Manyullyn parts are made from [[Smeltery]] alloying despite appearing as a higher tier recipe.

A very powerful strategy is to first craft the tool entirely out of magical wood for a free 8 modifiers that stay with the tool even as parts are replaced with other materials. Next, swap out everything for higher durability and higher damage parts. Add one fortune modifier and as many beheading modifiers as possible for an easy 90% beheading chance without ever hitting a single mob. Don't forget to add the [[Tinkers Tools#Modifiers|bonus modifiers]] too. There is also a quest reward at the very end of the [[Twilight Forest]] that awards a 20 modifier cleaver. The quest itself only requires a Lamp of Cinders from the giant obsidian vaults near the final castle.

The recommended modifiers are lapis lazuli for fortune, beheading for increased skull drop rates, and quartz for sharpness. Again, the beheading modifier stacks with the built-in 20% beheading chance. The reinforcement modifier may also be useful on the Cleaver for an automatic mob farm.

{| width="100%" border="0"
| colspan="2" style="vertical-align: top;" |
{| class="mw-collapsible mw-collapsed wikitable" style="width:90%;"
| style="background-color:#12364B; color:white; text-align:left;" |'''Durability & Attack Equations'''
|-
|<math>\text{Cleaver Durability} = (\text{Blade Durability} + \text{Plate Durabilty}) \times \sum \text{Rod Durability Modifiers} \times 45/32</math><math>\text{Cleaver Attack} = 5 + 0.7 \times (\text{Blade Damage} + \text{Plate Damage})</math>
|}
|-
| style="width:40%; vertical-align:top; padding:6px;" |
{| class="wikitable"
| style="background-color:#12364B; color:white;" |'''Tier'''
| style="background-color:#12364B; color:white;" |'''Large Blade / Plate'''
| style="background-color:#12364B; color:white;" |'''Durability'''
| style="background-color:#12364B; color:white;" |'''Damage'''
| style="background-color:#12364B; color:white;" |'''Trait'''
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |S
|Bronze
|285
|1.50
|Reinforced I
|-
|Steel
|300
|1.50
|Reinforced II
|-
|Alumite/Obzinite
|413
|2.00
|Reinforced II
|- style="background-color:#BBBBBB;"
| colspan="5" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |LV
|Cobalt
|600
|2.00
| Reinforced II
|- style="background-color:#BBBBBB;"
| colspan="5" |
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |MV
|Damascus Steel
|1,500
|2.50
| Reinforced II
|-
|Dark Steel
|1,561
|5.00
| -
|-
|Vanadium Steel
|1,440
|7.00
| -
|- style="background-color:#BBBBBB;"
| colspan="5" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |HV
|Meteoric Steel
|1,480
|12.0
|Reinforced I
|- style="background-color:#BBBBBB;"
| colspan="5" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |EV
|Tungstensteel
|1,920
|14.0
|Reinforced III
|- style="background-color:#BBBBBB;"
| colspan="5" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |IV
|Palladium
|2,200
|16.0
| -
|- style="background-color:#BBBBBB;"
| colspan="5" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |LuV
|Adamantium
|9,216
|24.0
| -
|}
| style="width:45%; vertical-align:top; padding:6px;" |
{| class="wikitable"
| style="background-color:#12364B; color:white;" |'''Tier'''
| style="background-color:#12364B; color:white;" |'''Tough Rod'''
| style="background-color:#12364B; color:white;" |'''Durability'''
| style="background-color:#12364B; color:white;" |'''Trait'''
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |S
|Bronze
|x1.25
|Reinforced I
|-
|Steel
|x1.30
|Reinforced II
|-
|Alumite/Obzinite
|x1.30
|Reinforced II
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |LV
|Magnetic Iron
|x1.50
| -
|-
|Cobalt
|x1.75
|Reinforced II
|-
|'''{{Color|text=Manyullyn|fg=#6495ED}}'''
|x2.50
| -
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |MV
|Vanadium Steel
|x2.50
| -
|-
|Mana Steel
|x3.50
| -
|-
|Damascus Steel
|x5.00
|Reinforced II
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |HV
|Reinforced
|x6.00
| -
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| rowspan="5" style="background-color:#44657C; color:white; text-align:center;" |IV
|Elven Elementium
|x6.50
| -
|-
|HSS-E
|x6.50
| -
|-
|HSS-S
|x7.50
| -
|-
|Ichorium
|x11.5
| -
|-
|Hikarium
|x12.5
| -
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |LuV
|Magneto Resonatic
|x15.5
| -
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |ZPM
|Artherium-Sn
|x17.5
| -
|}
|}
== Crossbow ==
A crossbow is the ultimate early-game weapon for killing mobs from a safe distance. The damage is much higher than any type of sword and scales well into the higher tiers. Bolts are also a highly sustainable ammunition. Crafting a crossbow requires the following components in a TOOL FORGE:

* 1 ''Crossbow Limb'' - Determines base durability, draw speed, arrow speed, and the repair material.
* 1 ''Crossbow Body'' - Boosts durability. Does NOT affect draw speed or arrow speed despite what the tooltip might say.
* 1 ''Bowstring'' - Boosts draw speed and arrow speed.
* 1 ''Tough Binding'' - Potentially adds a trait. Does NOT affect durability despite what the tooltip might say.

The materials highlighted '''{{Color|text=BLUE|fg=#6495ED}}''' are unique. Blue slime crystal, carbon, energetic alloy, and osmiridium parts all have a slightly lower bolt speed and/or durability but a significantly faster draw speed which is typically better for combat. Manyullyn parts are made from [[Smeltery]] alloying despite appearing as a higher tier recipe. Thaumium parts are made in the part builder despite coming from a metal ingot. Unstable parts can make a tool unbreakable if every part is made from it.

The recommended modifiers are redstone for reducing the draw speed down to the minimum 0.25 seconds, lapis lazuli for looting, and reinforcement for more durability. Quartz only increases the melee damage of the crossbow--put that on the bolts instead.

{| width="100%" border="0"
| colspan="2" style="vertical-align: top;" |
{| class="mw-collapsible mw-collapsed wikitable" style="width:90%;"
| style="background-color:#12364B; color:white; text-align:left;" |'''Durability, Draw Speed, and Bolt Speed Equations'''
|-
|<math>\text{Durability} = \text{Limb Durability} \times \text{Body Durability Modifier}</math><math>\text{Draw Speed} = \text{Limb Draw Speed} \times \text{String Draw Speed Modifier} \times 15/8</math>

<math>\text{Bolt Speed} = \text{Limb Bolt Speed} \times \text{String Bolt Speed Modifier} \times 1.5</math>
|}
|-
| style="width:54%; vertical-align:top; padding:6px;" |
{| class="wikitable"
| style="background-color:#12364B; color:white;" |'''Tier'''
| style="background-color:#12364B; color:white;" |'''Crossbow Limb'''
| style="background-color:#12364B; color:white;" |'''Durability'''
| style="background-color:#12364B; color:white;" |'''Bolt Speed'''
| style="background-color:#12364B; color:white;" |'''Draw'''
| style="background-color:#12364B; color:white;" |'''Trait'''
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |S
|'''{{Color|text=Blue Slime Crystal|fg=#6495ED}}'''
|900
|4.00
|1.05s
|Slimy
|-
|Bronze
|285
|5.10
|2.25s
|Reinforced I
|-
|Steel
|300
|5.50
|3.00s
|Reinforced II
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| rowspan="2" style="background-color:#44657C; color:white; text-align:center;" |LV
|'''{{Color|text=Carbon|fg=#6495ED}}'''
|48
|4.80
|0.85s
| -
|-
|Cobalt
|600
|5.30
|2.25s
|Reinforced II
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |MV
|'''{{Color|text=Energetic Alloy|fg=#6495ED}}'''
|1,805
|4.60
|1.00s
| -
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| rowspan="4" style="background-color:#44657C; color:white; text-align:center;" |IV
|'''{{Color|text=Osmiridium|fg=#6495ED}}'''
|2,400
|5.00
|0.65s
|Reinforced III
|-
|HSS-E
|7,680
|6.50
|3.50s
| -
|-
|HSS-S
|7,680
|7.50
|4.00s
| -
|-
|Ichorium
|637,500
|11.5
|6.00s
| -
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| rowspan="2" style="background-color:#44657C; color:white; text-align:center;" |LuV
|Duranium
|30,720
|10.5
|5.50s
| -
|-
|Gaia Spirit
|637,500
|11.5
|6.00s
| -
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| rowspan="2" style="background-color:#44657C; color:white; text-align:center;" |UHV
|Infinity
|Infinity
|60.0
|0.50s
|Cosmic
|}
{| class="wikitable"
| style="background-color:#12364B; color:white;" |'''Tier'''
| style="background-color:#12364B; color:white;" |'''Tough Binding'''
| style="background-color:#12364B; color:white;" |'''Trait'''
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |S
|Bronze
|Reinforced I
|-
|Steel
|Reinforced II
|-
|Obsidian
|Reinforced III
|- style="background-color:#BBBBBB;"
| colspan="3" |
|-
| rowspan="2" style="background-color:#44657C; color:white; text-align:center;" |LV
|'''{{Color|text=Thaumium|fg=#6495ED}}'''
|Thaumic
|-
|'''{{Color|text=Unstable|fg=#6495ED}}'''
|Reinforced IV
|}
| style="width:46%; vertical-align:top; padding:6px;" |
{| class="wikitable"
| style="background-color:#12364B; color:white;" |'''Tier'''
| style="background-color:#12364B; color:white;" |'''Crossbow Body'''
| style="background-color:#12364B; color:white;" |'''Durability'''
| style="background-color:#12364B; color:white;" |'''Trait'''
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |S
|Bronze
|x1.25
|Reinforced I
|-
|Steel
|x1.30
|Reinforced II
|-
|Alumite/Obzinite
|x1.30
|Reinforced II
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |LV
|Magnetic Iron
|x1.50
| -
|-
|Cobalt
|x1.75
|Reinforced II
|-
|'''{{Color|text=Manyullyn|fg=#6495ED}}'''
|x2.50
| -
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |MV
|Vanadium Steel
|x2.50
| -
|-
|Mana Steel
|x3.50
| -
|-
|Damascus Steel
|x5.00
|Reinforced II
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |HV
|Reinforced
|x6.00
| -
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| rowspan="5" style="background-color:#44657C; color:white; text-align:center;" |IV
|Elven Elementium
|x6.50
| -
|-
|HSS-E
|x6.50
| -
|-
|HSS-S
|x7.50
| -
|-
|Ichorium
|x11.5
| -
|-
|Hikarium
|x12.5
| -
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |LuV
|Magneto Resonatic
|x15.5
| -
|- style="background-color:#BBBBBB;"
| colspan="4" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |ZPM
|Artherium-Sn
|x17.5
| -
|}
{| class="wikitable" style="width:82%;"
| style="background-color:#12364B; color:white;" |'''Tier'''
| style="background-color:#12364B; color:white;" |'''Bowstring'''
| style="background-color:#12364B; color:white;" |'''Bolt Speed'''
| style="background-color:#12364B; color:white;" |'''Draw'''
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |S
|Bowstring
|x1.00
|x1.00
|-
|Enchanted Bowstring
|x0.90
|x0.80
|-
|Fiery Bowstring
|x1.20
|x1.10
|}
|}
== Bolts ==
Bolts are the only ammunition for the crossbow. They are highly sustainable and even retrievable if they do not break after firing. Crafting a set of crossbow bolts requires the following components. Cast the (molten) bolt tip onto the tool rod with the [[Smeltery]] or an [[Medium Voltage (MV)|MV]] fluid solidifier BEFORE joining it with the fletching in the TOOL FORGE.
* 1 ''Bolt Tip'' - (Head) Determines base durability (ammo), attack damage, weight, and repair material.
* 1 ''Tool Rod'' - (Handle) Boosts durability (ammo), break chance, and weight.
* 1 ''Fletching'' - (Accessory) Boosts durability (ammo), break chance, and accuracy.
Bolts have +50% armor penetration and can critically strike if shot while the player is falling in the air, such as the second half of a jump. Weight affects the gravity/range of bolts and increases their armor piercing damage, but if it exceeds the base damage than the bolt loses all of it to become 100% armor piercing damage. Tungstensteel bolt tips, for example, do not shoot very far because they are incredibly heavy but they also deal an extraordinary amount of damage.

The materials highlighted '''{{Color|text=BLUE|fg=#6495ED}}''' are unique. Blue slime crystal, carbon, energetic alloy, and osmiridium parts all have a much lower weight and/or break chance. Manyullyn parts are made from [[Smeltery]] alloying despite appearing as a higher tier recipe.

The recommended modifiers are moss for passively restoring durability, quartz for sharpness, and reinforcement for a 10% chance to not consume ammo per level. Luck has no effect on crossbow bolts--put that on the crossbow instead.
{| width="100%" border="0"
| colspan="2" style="vertical-align: top;" |
{| class="mw-collapsible mw-collapsed wikitable" style="width:90%;"
| style="background-color:#12364B; color:white; text-align:left;" |'''Durability, Attack, Weight, Accuracy, and Break Chance Equations'''
|-
|<math>\text{Durability} = \text{Tip Durability} \times \text{Rod Durability Modifier} \times \text{Fletching Durability Modifier} \times 0.1</math><math>\text{Attack} = \text{Tip Damage}</math>

<math>\text{Weight} = \text{Tip Weight} + 1.5 \times \text{Rod Weight}</math>

<math>\text{Armor Piercing} = \text{Weight} - 0.7</math>

<math>\text{Accuracy}% = 0.5 \times (1 + \text{Fletching Accuracy}%)</math>

<math>\text{Break}% = \text{Tip Break}% \times (0.15 \times \text{Rod Break}% + 2 \times \text{Fletching Break}%) \times 0.5</math>
|}
|-
| style="width:40%; vertical-align:top; padding:6px;" |
{| class="wikitable"
| style="background-color:#12364B; color:white;" |'''Tier'''
| style="background-color:#12364B; color:white;" |'''Bolt Tip'''
| style="background-color:#12364B; color:white;" |'''Durability'''
| style="background-color:#12364B; color:white;" |'''Damage'''
| style="background-color:#12364B; color:white;" |'''Weight'''
| style="background-color:#12364B; color:white;" |'''Break''' 
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |S
|Bronze
|285
|1.50
|3.20
|120%
|-
|Steel
|300
|1.50
|3.60
|90%
|-
|Alumite/Obzinite
|413
|2.00
|1.10
|70%
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |LV
|Cobalt
|600
|2.00
|3.00
|200%
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| rowspan="3" style="background-color:#44657C; color:white; text-align:center;" |MV
|Damascus Steel
|1,500
|2.50
|5.60
|90%
|-
|Dark Steel
|1,561
|5.00
|2.70
|90%
|-
|Vanadium Steel
|1,440
|7.00
|5.50
|90%
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |HV
|Meteoric Steel
|1,480
|12.0
|1.46
|0.9%
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |EV
|Tungstensteel
|1,920
|14.0
|178
|90%
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |IV
|Palladium
|2,200
|16.0
|2.00
|0.9%
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |LuV
|Adamantium
|9,216
|24.0
|2.00
|0.9%
|}
{| class="wikitable" style="width:97%;"
| style="background-color:#12364B; color:white;" |'''Tier'''
| style="background-color:#12364B; color:white;" |'''Fletching'''
| style="background-color:#12364B; color:white;" |'''Durability'''
| style="background-color:#12364B; color:white;" |'''Break'''
| style="background-color:#12364B; color:white;" |'''Accuracy'''
|-
| rowspan="4" style="background-color:#44657C; color:white; text-align:center;" |S
|Feather
|x1.00
|5%
|95%
|-
|Leaf
|x2.50
|0%
|75%
|-
|Slime Crystal
|x0.80
|0.5%
|100%
|-
|Slimeleaf
|x1.40
|2%
|98%
|}
| style="width:60%; vertical-align:top; padding:6px;" |
{| class="wikitable"
| style="background-color:#12364B; color:white;" |'''Tier'''
| style="background-color:#12364B; color:white;" |'''Tool Rod'''
| style="background-color:#12364B; color:white;" |'''Durability'''
| style="background-color:#12364B; color:white;" |'''Weight'''
| style="background-color:#12364B; color:white;" |'''Break'''
| style="background-color:#12364B; color:white;" |'''Trait'''
|-
| rowspan="4" style="background-color:#44657C; color:white; text-align:center;" |S
|Bronze
|x1.25
|3.20
|120%
|Reinforced I
|-
|Steel
|x1.30
|3.60
|90%
|Reinforced II
|-
|Alumite/Obzinite
|x1.30
|1.10
|70%
|Reinforced II
|-
|'''{{Color|text=Blue Slime Crystal|fg=#6495ED}}'''
|x1.50
|0.22
|0%
|Slimy
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| rowspan="4" style="background-color:#44657C; color:white; text-align:center;" |LV
|'''{{Color|text=Carbon|fg=#6495ED}}'''
|x1.50
|0.08
|90%
| ----
|-
|Magnetic Iron
|x1.50
|5.70
|90%
| ----
|-
|Cobalt
|x1.75
|3.00
|200%
|Reinforced II
|-
|'''{{Color|text=Manyullyn|fg=#6495ED}}'''
|x2.50
|2.25
|100%
| ----
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| rowspan="4" style="background-color:#44657C; color:white; text-align:center;" |MV
|'''{{Color|text=Energetic Alloy|fg=#6495ED}}'''
|x2.50
|0.11
|0.9%
| ----
|-
|Vanadium Steel
|x2.50
|5.50
|90%
| ----
|-
|Mana Steel
|x3.50
|9.80
|90%
| ----
|-
|Damascus Steel
|x5.00
|5.60
|90%
| Reinforced II
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |HV
|Reinforced
|x6.00
|9.80
|90%
| ----
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| rowspan="6" style="background-color:#44657C; color:white; text-align:center;" |IV
|'''{{Color|text=Osmiridium|fg=#6495ED}}'''
|x2.50
|0.19
|0.9%
| Reinforced III
|-
|Elven Elementium
|x6.50
|9.80
|90%
| ----
|-
|HSS-E
|x6.50
|8.10
|90%
| ----
|-
|HSS-S
|x7.50
|12.9
|90%
| ----
|-
|Ichorium
|x11.5
|9.80
|90%
| ----
|-
|Hikarium
|x12.5
|9.80
|90%
| ----
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |LuV
|Magneto Resonatic
|x15.5
|9.80
|90%
| ----
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |ZPM
|Artherium-Sn
|x17.5
|9.80
|90%
| ----
|- style="background-color:#BBBBBB;"
| colspan="6" |
|-
| style="background-color:#44657C; color:white; text-align:center;" |UHV
|Infinity
|x10.0
|4.00
|0%
|Cosmic
|}
|}
== Traits ==
Traits are native bonuses granted by the materials that comprise a TiC tool. They generally do NOT stack even if multiple parts of a tool are all made with the same material, although there are a few exceptions. Traits are lost/replaced when upgrading parts with new materials. See the following table for a full list of traits and their effects, or search for a trait in [[Not Enough Items|NEI]] to see which materials offer it.
{| class="wikitable" width="100%" style="font-size:12px;"
| style="width:15%; background-color:#12364B; color:white;" |'''Trait'''
| style="width:35%; background-color:#12364B; color:white;" |'''Effect'''
| style="width:17%; background-color:#12364B; color:white;" |'''Source Material'''
| style="width:25%; background-color:#12364B; color:white;" |'''Notes'''
|-
|'''Reinforced'''
| +10% chance per level to not consume durability on each use
|Iron, Bronze, Steel, Obsidian, Cobalt, etc.
|Stacks with Modifiers.
|-
|'''Mathematical!'''
|Unbreakable if the entire tool is made with the same material
|Unstable
|
|-
|'''Jagged'''
|Increases damage, but reduces mining speed as the tool loses durability
|Cactus
|
|-
|'''Serrated'''
|Increases damage, but reduces mining speed as the tool loses durability
|Dogbearium
|x4 Effective as Jagged.
|-
|'''Stonebound'''
| Increases mining speed, but reduces damage as the tool loses durability
|Netherrack, Ardite
|
|-
|'''Slimy'''
|Occasionally spawns blue slimes on use
|Green Slime, Blue Slime
|
|-
|'''Tasty'''
|Occasionally get bacon on use
|Pig Iron
|
|-
|Heavy
| -10% Movement speed and +50% knockback resistance
|Bedrockium
|
|-
|'''Supermassive'''
|Increased knockback
|Cosmic Neutronium
|
|-
|Unbreakable
|Unbreakable
|Infinity
|
|-
|'''Writable'''
| +1 Modifier Slot per part
|Paper
|Stacks with itself.
|-
|'''Thaumic'''
| +1 Modifier Slot, +2 Modifier Slots if three or more parts are made with the same material
|Thaumium
|
|-
|'''Modifiable'''
| +1 Modifier Slot per part, +8 Modifier Slots if the entire tool is made with the same material
|Magical Wood
|Stacks with itself.
|-
|'''Cosmic'''
| +5 Modifier Slots per part.
|Infinity
|Stacks with itself.
|}

== Modifiers ==
Modifiers are special upgrades or enchantments for TiC tools. They are applied by combining a tool with a particular consumable item inside a Crafting Station, Tool Station, Tool Forge, or [[Adventure Backpacks|Adventure Backpack]]. However, modifiers require an available slot on the tool and cannot be removed once applied. See the following table for a full list of modifiers and their requirements. Note that this completely replaces the vanilla enchantment system for TiC tools. 

Tools start with zero slots unless a material trait (ie. writable or thaumic) provides one directly. Add slots by increasing the [[#Tool Level|Tool Level]], applying one of the bonus modifiers below, or conducting the Blood Magic Ritual "Spell of the Diligent Tinkerer." Some [[Enhanced Lootbags]] also have a chance to drop a Creative Tool Modifier which can stack indefinitely for an unlimited number of modifier slots, although they are still consumed on each use.

{| class="wikitable" width="100%" style="font-size:12px;"
| style="width:15%; background-color:#12364B; color:white;" |'''Modifier'''
| style="width:35%; background-color:#12364B; color:white;" |'''Effect'''
| style="width:17%; background-color:#12364B; color:white;" |'''Cost'''
| style="width:8%; background-color:#12364B; color:white;" |'''Stackable'''
| style="width:25%; background-color:#12364B; color:white;" |'''Notes'''
|-
|'''Haste'''
|Increases mining/digging speed
|1-50 Redstone
|Yes
|Also works with Redstone blocks.
|-
|'''Luck'''
|Increases yield from ores OR drops from mobs
|1-450 Lapis Lazuli
|No
|Also works with Lapis Lazuli blocks. Adds level at 100/300/450 Lapis.
|-
|'''Auto-Repair'''
|Passively restores durability over time
|1 Ball of Moss
|Yes
|Also called Mending Moss. Sunlight accelerates the process.
|-
|'''Reinforced'''
| +10% chance per level to not consume durability on each use
|1 Reinforcement
|Yes
|Unbreakable at Reinforced X. Stacks with Material Traits.
|-
|'''Diamond'''
|(2.9) Adds +500 durability and increases mining level by 1 per diamond used, up to 05-Obsidian.
|1 Diamond
|Yes
|The durability increase is one-time only.
|-
|'''Emerald'''
|(2.9) Adds +50% durability and increases mining level by 1 per emerald used, up to 04-Redstone.
|1 Emerald
|Yes
|The durability increase is one-time only.
|-
|'''Silky'''
|Silk-Touch
|1 Silky Jewel
|No
|Not compatible with Auto-Smelt or Luck.
|-
|'''Auto-Smelt'''
|Smelts blocks as they are harvested and sets mobs on fire for 3 seconds.
|1 Lava Crystal
|No
|Stacks with Luck. Not compatible with Silky.
|-
|'''Mining Level Boost'''
|Increases mining level by 1. Does not use a modifier slot.
Only applicable if the tool has not reached its full mining level (Mining XP) yet. 
|1 Skull (any)
|No
|Different skulls are applicable up to different levels, as seen in NEI.
|-
|'''Sharpness'''
|Increases attack damage
|1-72 Quartz
|Yes
|Also works with Quartz blocks. Less effective on piercing weapons.
|-
|'''Beheading'''
| +10% chance for mobs to drop their head
|1 Obsidian, 1 Ender Pearl
|Yes
|Guaranteed at Beheading X. Stacks with Material Traits.
|-
|'''Life Steal'''
|Heals 1 heart on hit per Necrotic Bone
|1 Necrotic Bone
|Yes
|Only works for melee attacks. Does nothing on ammo.
|-
|'''Fiery'''
|Sets mobs on fire for 1 second per 5 Blaze Powder
|1-25 Blaze Powder
|Yes
|Stacks with Auto-Smelt.
|-
|'''Knockback'''
|Increases knockback
|1 Piston
|Yes
|
|-
|'''Bane of Arthropods'''
|Extra 2-4 damage against spiders per level
|1 Fermented Spider Eye
|Yes
|
|-
|'''Smite'''
|Extra 2-4 damage against undead per level
|1 Consecrated Soil
|Yes
|
|-
| style="color:#6495ED;" |'''Bonus'''
| +1 Modifier Slot
|1 Diamond, 1 Gold Block
|No
|One Time Use.
|-
| style="color:#6495ED;" |'''Bonus'''
| +1 Modifier Slot
|1 Enchanted Golden Apple, 1 Diamond Block
|No
|One Time Use.
|-
| style="color:#6495ED;" |'''Bonus'''
| +1 Modifier Slot
|1 Nether Star
|No
|One Time Use.
|-
| style="color:#6495ED;" |'''Bonus'''
| +5 Modifier Slots
|1 Infinity Catalyst
|No
|One Time Use.
|}

== Troubleshooting ==
'''1) Cannot Craft a Part.''' Some parts are made in the Part Builder, others are cast in the Smeltery, and many require an Extruder. Verify that you are using the correct one. Thaumium parts, for example, are <u>only</u> made in the Part Builder. Also check the material cost as some parts require more than one block/ingot to craft.

'''2) Cannot Swap a Part.''' Tool must be fully repaired before parts can be changed. If replacing a paper or thaumium part with something else, tool must have a free modifier slot.

'''3) Broken Tool.''' Restore durability by repairing the tool. Combine the tool with an ingot of the head material in a Crafting Station, Tool Station, Tool Forge, or [[Adventure Backpacks|Adventure Backpack]].

'''4) Cannot Add Modifiers.''' Ensure there is an available modifier slot. Some take one modifier per level.

'''5) Cannot Add More Redstone.''' Tools and weapons have a maximum limit, most notably the 0.25s draw speed for Crossbows.

[[Category:Tinker's Construct]]
[[Category:Tools]]
[[Category:Stone Age]]
