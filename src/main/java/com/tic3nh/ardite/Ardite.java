package com.tic3nh.ardite;

import java.util.function.Consumer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import com.tic3nh.TiC3NH;
import com.tic3nh.setup.reg;

// ardite: the nether metal tinkers 3 dropped. gtnh needs it for the rung between steel and cobalt,
// and for manyullyn, which tinkers 3 alloys out of ancient debris instead.
public final class Ardite {

    private Ardite() {}

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TiC3NH.MOD_ID);

    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, TiC3NH.MOD_ID);

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, TiC3NH.MOD_ID);

    private static final ResourceLocation STILL =
            new ResourceLocation(TiC3NH.MOD_ID, "block/molten_ardite_still");

    private static final ResourceLocation FLOW =
            new ResourceLocation(TiC3NH.MOD_ID, "block/molten_ardite_flow");

    public static final RegistryObject<Block> ORE = BLOCKS.register("ardite_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_QUARTZ_ORE)
                    .mapColor(MapColor.COLOR_ORANGE), UniformInt.of(2, 5)));

    public static final RegistryObject<Block> BLOCK = BLOCKS.register("ardite_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK)
                    .mapColor(MapColor.COLOR_ORANGE).sound(SoundType.METAL)));

    public static final RegistryObject<Block> RAW_BLOCK = BLOCKS.register("raw_ardite_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK)
                    .mapColor(MapColor.COLOR_ORANGE)));

    public static final RegistryObject<Item> ORE_ITEM = blockItem(ORE);
    public static final RegistryObject<Item> BLOCK_ITEM = blockItem(BLOCK);
    public static final RegistryObject<Item> RAW_BLOCK_ITEM = blockItem(RAW_BLOCK);

    public static final RegistryObject<Item> RAW = reg.ITEMS.register("raw_ardite",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> INGOT = reg.ITEMS.register("ardite_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> NUGGET = reg.ITEMS.register("ardite_nugget",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<FluidType> MOLTEN_TYPE = FLUID_TYPES.register("molten_ardite",
            () -> new FluidType(FluidType.Properties.create()
                    .temperature(1300).density(2000).viscosity(10000).lightLevel(11)
                    .canDrown(false).canSwim(false).canExtinguish(false).supportsBoating(false)
                    .pathType(null).motionScale(0.0023333333333333335D)) {

                @Override
                public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                    consumer.accept(new IClientFluidTypeExtensions() {

                        @Override
                        public ResourceLocation getStillTexture() {
                            return STILL;
                        }

                        @Override
                        public ResourceLocation getFlowingTexture() {
                            return FLOW;
                        }
                    });
                }
            });

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN = FLUIDS.register("molten_ardite",
            () -> new ForgeFlowingFluid.Source(Ardite.molten()));

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_FLOWING =
            FLUIDS.register("flowing_molten_ardite", () -> new ForgeFlowingFluid.Flowing(Ardite.molten()));

    public static final RegistryObject<LiquidBlock> MOLTEN_BLOCK = BLOCKS.register("molten_ardite",
            () -> new LiquidBlock(MOLTEN, BlockBehaviour.Properties.copy(Blocks.LAVA)
                    .mapColor(MapColor.COLOR_ORANGE).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Item> MOLTEN_BUCKET = reg.ITEMS.register("molten_ardite_bucket",
            () -> new BucketItem(MOLTEN, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    private static ForgeFlowingFluid.Properties molten() {
        return new ForgeFlowingFluid.Properties(MOLTEN_TYPE, MOLTEN, MOLTEN_FLOWING)
                .block(MOLTEN_BLOCK).bucket(MOLTEN_BUCKET);
    }

    private static RegistryObject<Item> blockItem(RegistryObject<Block> block) {
        return reg.ITEMS.register(block.getId().getPath(),
                () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void init(IEventBus modBus) {
        BLOCKS.register(modBus);
        FLUIDS.register(modBus);
        FLUID_TYPES.register(modBus);
    }
}
