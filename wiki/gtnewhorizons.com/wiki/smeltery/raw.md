{{Infobox Multiblock
|name=Smeltery
|image=Smeltery.png
|source=[https://github.com/GTNewHorizons/TinkersConstruct/blob/master/src/main/java/tconstruct/smeltery/TinkerSmeltery.java Tinker’s Construct]
|type=Smeltery
|tier=[[Stone Age|Stone]]
|size=3-11 x 3-11 x 2-11
|energyHatches=N/A
|overclocks=N/A
|pollution=None
}}The '''Smeltery''' is a [[Stone Age|Stone]] tier [[Multiblock Machines|multiblock]] for casting and alloying metals into [[Tinkers Tools|Tinker's Construct]] (TiC) tool parts. The Smeltery precedes the [[Medium Voltage (MV)|MV]] Extruder, mass-processing dusts or ingots, recycling metal items, creating casts, molds or extruder shapes, and solidifying glass blocks. Its functions are taken over by various [[Singleblock Machines|singleblock]] machines in later [[tier]]s.

== Construction ==
The Smeltery can be built with any interior footprint from 1x1 up to 7x7, no max build height. The size of the interior determines how many items can be melted at once and its total fluid capacity. To be a valid structure the horizontal volume must be a square; same length and width, with any height of one or more. The bottom layer can be any block tagged as "smeltery structure block" in [[Not Enough Items|NEI]]. The Controller must face outwards, its back touching the Smeltery's interior.  At least one lava holding block (Seared Tank, Seared Glass or Seared Window) must be on the same y level as the Controller. Drains, Tanks, and the Controller must all go on a hollow layer above the base.  The large black square on Drains faces the interior, while the smaller round hole is the output side. The edges and corners are not necessary to have a valid Smeltery build.  

'''Structure'''
* Controller: {{Color|gray|Any side casing}}
* 2+ Smeltery Structure Block (or a tank/glass/window)
** Seared Bricks, Seared Stone, Seared Cobblestone, Seared Paver
** Cracked Seared Bricks, Seared Road, Fancy Seared Bricks, Chiseled Seared Bricks
* 1+ Seared Tank, Seared Glass or Seared Window: {{Color|gray|Any side casing}}
* 1+ Smeltery Drain: {{Color|gray|Any side casing, small round hole faces exterior}}
* 1+ Smeltery Faucet: {{Color|gray|Attach to smeltery drain hole, or use other piping}}
* 0+ Casting Table: {{Color|gray|Below faucet or piping, for casting ingots or tool parts}}
* 0+ Casting Basin: {{Color|gray|Below faucet or piping, for casting blocks}}

<gallery>
File:SmelterySmallest.png|alt=A 1x1 Smeltery with a lit controller, single empty space in the middle of four blocks, and a casting table to the front left with a light gold ingot cast.|1x1 Smeltery
File:SmelteryCrosssection.png|alt=Cross-section of a 2x2 Smeltery|2x2 cross-section
</gallery>

== Usage ==
[[File:SmelteryGUI.png|thumb|alt=Light grey GUI with individual copper ingots being melted on the left, each with a rising heat bar from red to yellow.  The middle has a scroll bar, then a large tank with pink and yellow fluids.  The tooltip to the right says Molten Aluminum Ingots: 141 Nuggets:2.  A lava fluid gauge is mostly hidden by the tooltip.|Smeltery GUI]]
[[File:SmelteryEnderman.png|thumb|alt=A endstone colored Enderman standing in a dark teal pool of fluid inside a Smeltery.|Melting Endermen for fun and profit!]]
The speed of the Smeltery is constant based only on the type of material being melted.  While processing, it consumes Lava at a rate of 30L/4s.  Number of items being processed does not affect the Smeltery's fuel consumption, thus a larger Smeltery is more efficient as the number of simultaneous items is equal to one per interior block volume. A 3x3 footprint is suggested as the standard size. 

Items to be melted can be manually placed into the Controller, or added via a Hopper, pipe or conduit. To change what fluid is output if there are multiple, open the Controller's GUI and right-click on the desired fluid in the tank.  Hold shift to see quantities displayed in mb instead of ingots/nuggets.  To output fluid, right-click on a Faucet with a valid cast or basin recipe for the bottom-most liquid in the Controller. Alternately, fluid pipes or fluid conduit can be used to move fluids from the Smeltery into Casting Basin(s) or Casting Table(s), or the Faucets can be pulsed with a Redstone Clock for continuous output.  Hoppers or other item logistics can also pull items out of Casting Basins and Casting Tables, for full automation.  Molten materials in the smeltery will damage any entities that fall in.  This can be used to harvest some fluids, such as Liquid Ender from Endermen, Glue from Horses, and Blood from the player/Villagers.

To use a Casting Table, Casts are required.  These can only be made out of Brass, which is an alloy of copper and zinc, or single-use casts can be made from four Clay in a crafting table.  The Smeltery automatically alloys any molten fluids in its inventory.  To make a cast, pour Molten Brass over an appropriate item inserted into the Casting Table with right-click.  The item will be lost in the process, but casts are infinitely reusable.  Use Cobblestone in the Part Builder whenever possible for sacrificial cast making parts.  

The Casting Basin makes blocks, but has very limited uses in GTNH.  The primary one is glass, followed by Brownstone for faster paths and Seared Stone for expanding the Smeltery.  Metal blocks are restricted to the Compressor.  Melting stone is slow and takes twenty blocks to yield one Seared Stone, but does have the advantage of being automatic vs. crafting more grout and bricks.

Molten metals from the Smeltery cannot be used in GregTech recipes, with the exception of crossbow bolts.  Bolts require Smeltery melted metal to craft.  To extract the molten metal from the Smeltery pour 1,000mb/L into an iron bucket right-clicked onto a Casting Table, or any amount into a fluid container such as Tank (BC Factory/Iron Tanks), Fluid Tank (EnderIO or GregTech), or Seared Tank (Tinkers).  Smeltery molten metals cannot be placed in IC2 cells but can be picked up with Large Fluid Cells from a tank.  Fluid pipes can also be used for logistics.

== Troubleshooting ==
[[File:SmelteryInvalid.png|thumb|right|alt=Waila Tooltip for the Smeltery Controller, listing its ID, "Invalid Structure" in italics, harvestable state, effective tool, hardness, blast resistance and source mod, with a blue border and an icon of the controller on the left.|Invalid structure warning in [[WAILA]]]]Players familiar with the TiC Smeltery in other packs may be caught out by some of the differences in GTNH. 

* Does ''not'' double ore -> ingot yield
* Metal blocks are only made with Compressors
* Casts can only be made with Brass, not Gold
* The Controller's GUI only shows one Seared Tank's lava at a time, though it will use them in series.
* Molten metals made in the Smeltery can't be used for GregTech recipes; use a Fluid Extractor.
** Exception: Making Bolts.  Bolts require metal melted in the Smeltery for the Fluid Solidifer recipes.
* Higher tier metal parts are made in the Extruder with casts, not the Smeltery
* [[Not Enough Items|NEI]] will only show wooden parts for making casts, but wooden parts cannot be crafted; use cobblestone.

The Smeltery does not require a Drain in order to be a valid structure - however at least one Drain is needed to get anything out of it. When correctly built the Controller will light up like a furnace and emit flame particles.  

Incomplete Structure Reasons:
* Controller is not oriented with the back touching the interior of the Smeltery
* No lava container block on the same y level as the Controller
* Invalid or missing blocks in the walls or base
* Block(s) inside the smeltery, often from Endermen

== Tips ==
* To melt mobs, there must be molten fluid in the Smeltery already.
* Chisel Clear Glass to get vanilla Glass and other variants.
* Smeltery contents is stored in the Controller.  Any other block can be added/removed and it will keep its contents.
* Universal Fluid Cells can pick up fluids from partially filled casts with Shift + right-click.
* Dump fluids into the Smeltery by right-clicking or piping into a Drain.
* One of the few GT compatible fluids the Smeltery can make is molten glass, but it has to be poured out into a bucket.

[[Category:Tinker's Construct]]
[[Category:Multiblocks]]
[[Category:Stone Age]]
