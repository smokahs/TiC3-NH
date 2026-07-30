package com.tic3nh.tree;

import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

import net.minecraftforge.common.extensions.IForgeBlock;

/**
 * Block flavours for the paperbark set.
 *
 * <p>Two things every one of these needs and no vanilla superclass gives a modded block: fire, since
 * {@code FireBlock}'s burn table is populated for vanilla blocks only, and — for the four sign blocks
 * — our own block entity type, because the vanilla ones only accept vanilla sign blocks.
 */
public final class Wood {

    private Wood() {}

    /** Vanilla plank odds (encouragement 5, flammability 20), re-added per block. */
    public interface Burns extends IForgeBlock {

        @Override
        default int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction face) {
            return 5;
        }

        @Override
        default int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction face) {
            return 20;
        }
    }

    public static class Plain extends Block implements Burns {

        public Plain(BlockBehaviour.Properties props) {
            super(props);
        }
    }

    /** Stripped logs and wood: plain pillars, no bark left to peel. */
    public static class Log extends RotatedPillarBlock implements Burns {

        public Log(BlockBehaviour.Properties props) {
            super(props);
        }
    }

    public static class Leaves extends LeavesBlock implements Burns {

        public Leaves(BlockBehaviour.Properties props) {
            super(props);
        }

        @Override
        public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction face) {
            return 30;
        }

        @Override
        public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction face) {
            return 60;
        }
    }

    public static class Stairs extends StairBlock implements Burns {

        public Stairs(Supplier<BlockState> base, BlockBehaviour.Properties props) {
            super(base, props);
        }
    }

    public static class Slab extends SlabBlock implements Burns {

        public Slab(BlockBehaviour.Properties props) {
            super(props);
        }
    }

    public static class Fence extends FenceBlock implements Burns {

        public Fence(BlockBehaviour.Properties props) {
            super(props);
        }
    }

    public static class Gate extends FenceGateBlock implements Burns {

        public Gate(BlockBehaviour.Properties props, WoodType type) {
            super(props, type);
        }
    }

    public static class Door extends DoorBlock implements Burns {

        public Door(BlockBehaviour.Properties props, BlockSetType type) {
            super(props, type);
        }
    }

    public static class Trapdoor extends TrapDoorBlock implements Burns {

        public Trapdoor(BlockBehaviour.Properties props, BlockSetType type) {
            super(props, type);
        }
    }

    public static class Button extends ButtonBlock implements Burns {

        public Button(BlockBehaviour.Properties props, BlockSetType type) {
            super(props, type, 30, true);
        }
    }

    public static class Plate extends PressurePlateBlock implements Burns {

        public Plate(BlockBehaviour.Properties props, BlockSetType type) {
            super(Sensitivity.EVERYTHING, props, type);
        }
    }

    public static class Sign extends StandingSignBlock implements Burns {

        public Sign(BlockBehaviour.Properties props, WoodType type) {
            super(props, type);
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new PaperbarkSigns.Standing(pos, state);
        }
    }

    public static class WallSign extends WallSignBlock implements Burns {

        public WallSign(BlockBehaviour.Properties props, WoodType type) {
            super(props, type);
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new PaperbarkSigns.Standing(pos, state);
        }
    }

    public static class HangingSign extends CeilingHangingSignBlock implements Burns {

        public HangingSign(BlockBehaviour.Properties props, WoodType type) {
            super(props, type);
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new PaperbarkSigns.Hanging(pos, state);
        }
    }

    public static class WallHangingSign extends WallHangingSignBlock implements Burns {

        public WallHangingSign(BlockBehaviour.Properties props, WoodType type) {
            super(props, type);
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new PaperbarkSigns.Hanging(pos, state);
        }
    }
}
