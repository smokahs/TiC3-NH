package com.tic3nh.tree;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/** Sign block entities. Vanilla's types only accept vanilla sign blocks, so paperbark brings its own. */
public final class PaperbarkSigns {

    private PaperbarkSigns() {}

    public static class Standing extends SignBlockEntity {

        public Standing(BlockPos pos, BlockState state) {
            super(Paperbark.SIGN_ENTITY.get(), pos, state);
        }
    }

    public static class Hanging extends HangingSignBlockEntity {

        public Hanging(BlockPos pos, BlockState state) {
            super(Paperbark.HANGING_SIGN_ENTITY.get(), pos, state);
        }
    }
}
