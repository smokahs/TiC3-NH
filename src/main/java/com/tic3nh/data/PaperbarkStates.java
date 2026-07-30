package com.tic3nh.data;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallSignBlock;

import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import com.tic3nh.TiC3NH;
import com.tic3nh.tree.Paperbark;
import com.tic3nh.tree.PaperbarkLog;

/** Blockstates and block models for the paperbark set, peeling stages included. */
public final class PaperbarkStates extends BlockStateProvider {

    private static final String[] BARK_STAGES = {
        "paperbark_log", "paperbark_log_strip_25", "paperbark_log_strip_50", "paperbark_log_strip_75"
    };

    public PaperbarkStates(PackOutput output, ExistingFileHelper existing) {
        super(output, TiC3NH.MOD_ID, existing);
    }

    @Override
    protected void registerStatesAndModels() {
        ResourceLocation planks = modLoc("block/paperbark_planks");
        ResourceLocation bare = modLoc("block/stripped_paperbark_log");
        ResourceLocation bareTop = modLoc("block/stripped_paperbark_log_top");

        peeling(Paperbark.LOG.get(), "paperbark_log", modLoc("block/paperbark_log_top"));
        peeling(Paperbark.WOOD.get(), "paperbark_wood", null);

        axisBlock((RotatedPillarBlock) Paperbark.STRIPPED_LOG.get(), bare, bareTop);
        axisBlock((RotatedPillarBlock) Paperbark.STRIPPED_WOOD.get(), bare, bare);

        simpleBlock(Paperbark.LEAVES.get(), models()
                .withExistingParent("paperbark_leaves", mcLoc("block/leaves"))
                .texture("all", modLoc("block/paperbark_leaves"))
                .renderType("cutout_mipped"));

        simpleBlock(Paperbark.SAPLING.get(), models()
                .cross("paperbark_sapling", modLoc("block/paperbark_sapling"))
                .renderType("cutout"));

        simpleBlock(Paperbark.POTTED_SAPLING.get(), models()
                .singleTexture("potted_paperbark_sapling", mcLoc("block/flower_pot_cross"), "plant",
                        modLoc("block/paperbark_sapling"))
                .renderType("cutout"));

        simpleBlock(Paperbark.PLANKS.get());
        stairsBlock((StairBlock) Paperbark.STAIRS.get(), planks);
        slabBlock((SlabBlock) Paperbark.SLAB.get(), planks, planks);
        fenceBlock((FenceBlock) Paperbark.FENCE.get(), planks);
        fenceGateBlock((FenceGateBlock) Paperbark.FENCE_GATE.get(), planks);
        buttonBlock((ButtonBlock) Paperbark.BUTTON.get(), planks);
        pressurePlateBlock((PressurePlateBlock) Paperbark.PRESSURE_PLATE.get(), planks);

        // held-in-hand shapes the blockstate helpers above do not emit
        models().fenceInventory("paperbark_fence_inventory", planks);
        models().buttonInventory("paperbark_button_inventory", planks);

        doorBlockWithRenderType((DoorBlock) Paperbark.DOOR.get(), modLoc("block/paperbark_door_bottom"),
                modLoc("block/paperbark_door_top"), "cutout");
        trapdoorBlockWithRenderType((TrapDoorBlock) Paperbark.TRAPDOOR.get(), modLoc("block/paperbark_trapdoor"),
                true, "cutout");

        // signs are drawn by their block entity; the block model only supplies break particles
        signBlock((StandingSignBlock) Paperbark.SIGN.get(), (WallSignBlock) Paperbark.WALL_SIGN.get(), planks);
        ModelFile hanging = models().getBuilder("paperbark_hanging_sign").texture("particle", bare);
        simpleBlock(Paperbark.HANGING_SIGN.get(), hanging);
        simpleBlock(Paperbark.WALL_HANGING_SIGN.get(), hanging);
    }

    /** One model per bark stage; {@code top} null means bark on the cut ends too, as wood blocks want. */
    private void peeling(Block block, String base, ResourceLocation top) {
        ModelFile[] upright = new ModelFile[BARK_STAGES.length];
        ModelFile[] onSide = new ModelFile[BARK_STAGES.length];
        for (int stage = 0; stage < BARK_STAGES.length; stage++) {
            String name = stage == 0 ? base : base + "_strip_" + stage * 25;
            ResourceLocation side = modLoc("block/" + BARK_STAGES[stage]);
            ResourceLocation end = top == null ? side : top;
            upright[stage] = models().cubeColumn(name, side, end);
            onSide[stage] = models().cubeColumnHorizontal(name + "_horizontal", side, end);
        }

        getVariantBuilder(block).forAllStates(state -> {
            Direction.Axis axis = state.getValue(RotatedPillarBlock.AXIS);
            int stage = state.getValue(PaperbarkLog.STRIP);
            return ConfiguredModel.builder()
                    .modelFile(axis == Direction.Axis.Y ? upright[stage] : onSide[stage])
                    .rotationX(axis == Direction.Axis.Y ? 0 : 90)
                    .rotationY(axis == Direction.Axis.X ? 90 : 0)
                    .build();
        });
    }
}
