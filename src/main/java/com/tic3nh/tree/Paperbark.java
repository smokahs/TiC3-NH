package com.tic3nh.tree;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import com.tic3nh.TiC3NH;
import com.tic3nh.setup.reg;

/**
 * The paperbark tree: GTNH's early-game paper source, rebuilt for TiC3-NH.
 *
 * <p>Grows and looks like an oak with pale papery bark. Paper comes off the trunk with an axe rather
 * than out of a crafting grid — see {@link PaperbarkLog} — and the tree carries a full wood set so the
 * logs are worth something once they are bare.
 */
public final class Paperbark {

    private Paperbark() {}

    public static final BlockSetType SET = BlockSetType.register(new BlockSetType("tic3nh:paperbark"));

    /** Namespaced so signs resolve to our own textures under {@code entity/signs/paperbark.png}. */
    public static final WoodType WOOD_TYPE = WoodType.register(new WoodType("tic3nh:paperbark", SET));

    public static final ResourceKey<ConfiguredFeature<?, ?>> TREE = ResourceKey.create(
            Registries.CONFIGURED_FEATURE, new ResourceLocation(TiC3NH.MOD_ID, "paperbark_tree"));

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TiC3NH.MOD_ID);

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TiC3NH.MOD_ID);

    // logs

    public static final RegistryObject<Block> STRIPPED_LOG = BLOCKS.register("stripped_paperbark_log",
            () -> new Wood.Log(log(MapColor.SAND, MapColor.SAND)));

    public static final RegistryObject<Block> STRIPPED_WOOD = BLOCKS.register("stripped_paperbark_wood",
            () -> new Wood.Log(log(MapColor.SAND, MapColor.SAND)));

    public static final RegistryObject<Block> LOG = BLOCKS.register("paperbark_log",
            () -> new PaperbarkLog(log(MapColor.SAND, MapColor.TERRACOTTA_WHITE), STRIPPED_LOG));

    public static final RegistryObject<Block> WOOD = BLOCKS.register("paperbark_wood",
            () -> new PaperbarkLog(log(MapColor.TERRACOTTA_WHITE, MapColor.TERRACOTTA_WHITE), STRIPPED_WOOD));

    // the growing parts

    public static final RegistryObject<Block> LEAVES = BLOCKS.register("paperbark_leaves",
            () -> new Wood.Leaves(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .strength(0.2F)
                    .randomTicks()
                    .sound(SoundType.GRASS)
                    .noOcclusion()
                    .isValidSpawn((state, level, pos, type) -> type == EntityType.OCELOT || type == EntityType.PARROT)
                    .isSuffocating((state, level, pos) -> false)
                    .isViewBlocking((state, level, pos) -> false)
                    .isRedstoneConductor((state, level, pos) -> false)
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> SAPLING = BLOCKS.register("paperbark_sapling",
            () -> new SaplingBlock(new PaperbarkTree(), BlockBehaviour.Properties.of()
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> POTTED_SAPLING = BLOCKS.register("potted_paperbark_sapling",
            () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SAPLING,
                    BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));

    // the building set

    public static final RegistryObject<Block> PLANKS = BLOCKS.register("paperbark_planks",
            () -> new Wood.Plain(planks()));

    public static final RegistryObject<Block> STAIRS = BLOCKS.register("paperbark_stairs",
            () -> new Wood.Stairs(() -> PLANKS.get().defaultBlockState(), planks()));

    public static final RegistryObject<Block> SLAB = BLOCKS.register("paperbark_slab",
            () -> new Wood.Slab(planks()));

    public static final RegistryObject<Block> FENCE = BLOCKS.register("paperbark_fence",
            () -> new Wood.Fence(planks()));

    public static final RegistryObject<Block> FENCE_GATE = BLOCKS.register("paperbark_fence_gate",
            () -> new Wood.Gate(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SAND)
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .ignitedByLava(), WOOD_TYPE));

    public static final RegistryObject<Block> DOOR = BLOCKS.register("paperbark_door",
            () -> new Wood.Door(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SAND)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(3.0F)
                    .noOcclusion()
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY), SET));

    public static final RegistryObject<Block> TRAPDOOR = BLOCKS.register("paperbark_trapdoor",
            () -> new Wood.Trapdoor(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SAND)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(3.0F)
                    .noOcclusion()
                    .isValidSpawn((state, level, pos, type) -> false)
                    .ignitedByLava(), SET));

    public static final RegistryObject<Block> BUTTON = BLOCKS.register("paperbark_button",
            () -> new Wood.Button(BlockBehaviour.Properties.of()
                    .noCollission()
                    .strength(0.5F)
                    .pushReaction(PushReaction.DESTROY), SET));

    public static final RegistryObject<Block> PRESSURE_PLATE = BLOCKS.register("paperbark_pressure_plate",
            () -> new Wood.Plate(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SAND)
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS)
                    .noCollission()
                    .strength(0.5F)
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY), SET));

    // signs

    public static final RegistryObject<Block> SIGN = BLOCKS.register("paperbark_sign",
            () -> new Wood.Sign(sign(), WOOD_TYPE));

    public static final RegistryObject<Block> WALL_SIGN = BLOCKS.register("paperbark_wall_sign",
            () -> new Wood.WallSign(sign().lootFrom(SIGN), WOOD_TYPE));

    public static final RegistryObject<Block> HANGING_SIGN = BLOCKS.register("paperbark_hanging_sign",
            () -> new Wood.HangingSign(sign(), WOOD_TYPE));

    public static final RegistryObject<Block> WALL_HANGING_SIGN = BLOCKS.register("paperbark_wall_hanging_sign",
            () -> new Wood.WallHangingSign(sign().lootFrom(HANGING_SIGN), WOOD_TYPE));

    public static final RegistryObject<BlockEntityType<PaperbarkSigns.Standing>> SIGN_ENTITY = BLOCK_ENTITIES
            .register("paperbark_sign", () -> BlockEntityType.Builder
                    .of(PaperbarkSigns.Standing::new, SIGN.get(), WALL_SIGN.get()).build(null));

    public static final RegistryObject<BlockEntityType<PaperbarkSigns.Hanging>> HANGING_SIGN_ENTITY = BLOCK_ENTITIES
            .register("paperbark_hanging_sign", () -> BlockEntityType.Builder
                    .of(PaperbarkSigns.Hanging::new, HANGING_SIGN.get(), WALL_HANGING_SIGN.get()).build(null));

    // items — block items live in the mod's one item register

    public static final RegistryObject<Item> LOG_ITEM = item(LOG);
    public static final RegistryObject<Item> WOOD_ITEM = item(WOOD);
    public static final RegistryObject<Item> STRIPPED_LOG_ITEM = item(STRIPPED_LOG);
    public static final RegistryObject<Item> STRIPPED_WOOD_ITEM = item(STRIPPED_WOOD);
    public static final RegistryObject<Item> LEAVES_ITEM = item(LEAVES);
    public static final RegistryObject<Item> SAPLING_ITEM = item(SAPLING);
    public static final RegistryObject<Item> PLANKS_ITEM = item(PLANKS);
    public static final RegistryObject<Item> STAIRS_ITEM = item(STAIRS);
    public static final RegistryObject<Item> SLAB_ITEM = item(SLAB);
    public static final RegistryObject<Item> FENCE_ITEM = item(FENCE);
    public static final RegistryObject<Item> FENCE_GATE_ITEM = item(FENCE_GATE);
    public static final RegistryObject<Item> TRAPDOOR_ITEM = item(TRAPDOOR);
    public static final RegistryObject<Item> BUTTON_ITEM = item(BUTTON);
    public static final RegistryObject<Item> PRESSURE_PLATE_ITEM = item(PRESSURE_PLATE);

    public static final RegistryObject<Item> DOOR_ITEM = reg.ITEMS.register("paperbark_door",
            () -> new DoubleHighBlockItem(DOOR.get(), new Item.Properties()));

    public static final RegistryObject<Item> SIGN_ITEM = reg.ITEMS.register("paperbark_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16), SIGN.get(), WALL_SIGN.get()));

    public static final RegistryObject<Item> HANGING_SIGN_ITEM = reg.ITEMS.register("paperbark_hanging_sign",
            () -> new HangingSignItem(HANGING_SIGN.get(), WALL_HANGING_SIGN.get(),
                    new Item.Properties().stacksTo(16)));

    /** Pale bark on the sides, sand-coloured wood on the cut ends, like birch. */
    private static BlockBehaviour.Properties log(MapColor top, MapColor side) {
        return BlockBehaviour.Properties.of()
                .mapColor(state -> state.getValue(PaperbarkLog.AXIS) == net.minecraft.core.Direction.Axis.Y
                        ? top
                        : side)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(SoundType.WOOD)
                .ignitedByLava();
    }

    private static BlockBehaviour.Properties planks() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.SAND)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F, 3.0F)
                .sound(SoundType.WOOD)
                .ignitedByLava();
    }

    private static BlockBehaviour.Properties sign() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.SAND)
                .forceSolidOn()
                .instrument(NoteBlockInstrument.BASS)
                .noCollission()
                .strength(1.0F)
                .ignitedByLava();
    }

    private static RegistryObject<Item> item(RegistryObject<Block> block) {
        return reg.ITEMS.register(block.getId().getPath(),
                () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void init(IEventBus modBus) {
        BLOCKS.register(modBus);
        BLOCK_ENTITIES.register(modBus);
    }

    /** Vanilla's flower pot only learns about modded plants when told, on the main thread. */
    public static void registerFlowerPot() {
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(SAPLING.getId(), POTTED_SAPLING);
    }
}
