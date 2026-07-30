package com.tic3nh.data;

import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import com.tic3nh.TiC3NH;
import com.tic3nh.tree.Paperbark;

/**
 * Block tags for the paperbark set: the wood tags vanilla wood carries, plus our own log tag.
 *
 * <p>Not a provider of its own — a mod gets one block tag provider, so {@link TierTags} calls this.
 */
public final class PaperbarkTags {

    private PaperbarkTags() {}

    private static final ResourceLocation LOGS_ID = new ResourceLocation(TiC3NH.MOD_ID, "paperbark_logs");

    public static final TagKey<Block> LOGS_BLOCK = BlockTags.create(LOGS_ID);
    public static final TagKey<Item> LOGS_ITEM = ItemTags.create(LOGS_ID);

    /** Hands back the owning provider's {@code tag(...)}, which is protected to it. */
    public interface Sink {

        IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> tag(TagKey<Block> key);
    }

    public static void addTo(Sink sink) {
        Block log = Paperbark.LOG.get();
        Block wood = Paperbark.WOOD.get();
        Block strippedLog = Paperbark.STRIPPED_LOG.get();
        Block strippedWood = Paperbark.STRIPPED_WOOD.get();

        sink.tag(LOGS_BLOCK).add(log, wood, strippedLog, strippedWood);
        sink.tag(BlockTags.LOGS_THAT_BURN).addTag(LOGS_BLOCK);
        sink.tag(BlockTags.COMPLETES_FIND_TREE_TUTORIAL).add(log, wood, Paperbark.LEAVES.get());

        sink.tag(BlockTags.LEAVES).add(Paperbark.LEAVES.get());
        sink.tag(BlockTags.MINEABLE_WITH_HOE).add(Paperbark.LEAVES.get());
        sink.tag(BlockTags.SAPLINGS).add(Paperbark.SAPLING.get());
        sink.tag(BlockTags.FLOWER_POTS).add(Paperbark.POTTED_SAPLING.get());

        sink.tag(BlockTags.PLANKS).add(Paperbark.PLANKS.get());
        sink.tag(BlockTags.WOODEN_STAIRS).add(Paperbark.STAIRS.get());
        sink.tag(BlockTags.WOODEN_SLABS).add(Paperbark.SLAB.get());
        sink.tag(BlockTags.WOODEN_FENCES).add(Paperbark.FENCE.get());
        sink.tag(BlockTags.FENCE_GATES).add(Paperbark.FENCE_GATE.get());
        sink.tag(BlockTags.WOODEN_DOORS).add(Paperbark.DOOR.get());
        sink.tag(BlockTags.WOODEN_TRAPDOORS).add(Paperbark.TRAPDOOR.get());
        sink.tag(BlockTags.WOODEN_BUTTONS).add(Paperbark.BUTTON.get());
        sink.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(Paperbark.PRESSURE_PLATE.get());
        sink.tag(BlockTags.STANDING_SIGNS).add(Paperbark.SIGN.get());
        sink.tag(BlockTags.WALL_SIGNS).add(Paperbark.WALL_SIGN.get());
        sink.tag(BlockTags.CEILING_HANGING_SIGNS).add(Paperbark.HANGING_SIGN.get());
        sink.tag(BlockTags.WALL_HANGING_SIGNS).add(Paperbark.WALL_HANGING_SIGN.get());

        sink.tag(BlockTags.MINEABLE_WITH_AXE).add(
                log, wood, strippedLog, strippedWood,
                Paperbark.PLANKS.get(), Paperbark.STAIRS.get(), Paperbark.SLAB.get(),
                Paperbark.FENCE.get(), Paperbark.FENCE_GATE.get(), Paperbark.DOOR.get(),
                Paperbark.TRAPDOOR.get(), Paperbark.BUTTON.get(), Paperbark.PRESSURE_PLATE.get(),
                Paperbark.SIGN.get(), Paperbark.WALL_SIGN.get(),
                Paperbark.HANGING_SIGN.get(), Paperbark.WALL_HANGING_SIGN.get());
    }
}
