package com.tic3nh.data;

import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import net.minecraftforge.registries.RegistryObject;

import com.tic3nh.tree.Paperbark;
import com.tic3nh.tree.PaperbarkLog;

/** Loot for the paperbark set. Part-peeled logs are not collectable — they hand over a stripped log. */
public final class PaperbarkLoot extends LootTableProvider {

    public PaperbarkLoot(PackOutput output) {
        super(output, Set.of(), List.of(new SubProviderEntry(Blocks::new, LootContextParamSets.BLOCK)));
    }

    private static final class Blocks extends BlockLootSubProvider {

        /** Chance of a sheet of paper when a paperbark log is broken, peeled or not. */
        private static final float PAPER_ON_BREAK = 0.1F;

        private Blocks() {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected void generate() {
            add(Paperbark.LOG.get(), peeling(Paperbark.LOG.get(), Paperbark.STRIPPED_LOG.get()));
            add(Paperbark.WOOD.get(), peeling(Paperbark.WOOD.get(), Paperbark.STRIPPED_WOOD.get()));

            dropSelf(Paperbark.STRIPPED_LOG.get());
            dropSelf(Paperbark.STRIPPED_WOOD.get());
            dropSelf(Paperbark.SAPLING.get());
            dropSelf(Paperbark.PLANKS.get());
            dropSelf(Paperbark.STAIRS.get());
            dropSelf(Paperbark.FENCE.get());
            dropSelf(Paperbark.FENCE_GATE.get());
            dropSelf(Paperbark.TRAPDOOR.get());
            dropSelf(Paperbark.BUTTON.get());
            dropSelf(Paperbark.PRESSURE_PLATE.get());
            dropSelf(Paperbark.SIGN.get());
            dropSelf(Paperbark.HANGING_SIGN.get());

            add(Paperbark.SLAB.get(), createSlabItemTable(Paperbark.SLAB.get()));
            add(Paperbark.DOOR.get(), createDoorTable(Paperbark.DOOR.get()));
            // twice vanilla's sapling odds: this tree is a supply line, not scenery
            add(Paperbark.LEAVES.get(), createLeavesDrops(Paperbark.LEAVES.get(), Paperbark.SAPLING.get(),
                    0.1F, 0.125F, 0.16666667F, 0.2F));
            add(Paperbark.POTTED_SAPLING.get(), createPotFlowerItemTable(Paperbark.SAPLING.get()));
        }

        /**
         * Bark intact drops the log itself, any peeled stage drops the fully stripped one, and either
         * way a tenth of the bark comes off as paper on the way down.
         */
        private LootTable.Builder peeling(Block log, Block stripped) {
            LootItemCondition.Builder intact = LootItemBlockStatePropertyCondition
                    .hasBlockStateProperties(log)
                    .setProperties(StatePropertiesPredicate.Builder.properties()
                            .hasProperty(PaperbarkLog.STRIP, 0));
            return LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(applyExplosionCondition(log, LootItem.lootTableItem(log)))
                            .when(intact))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(applyExplosionCondition(stripped, LootItem.lootTableItem(stripped)))
                            .when(intact.invert()))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .when(LootItemRandomChanceCondition.randomChance(PAPER_ON_BREAK))
                            .add(applyExplosionCondition(Items.PAPER, LootItem.lootTableItem(Items.PAPER))));
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return StreamSupport.stream(Paperbark.BLOCKS.getEntries().spliterator(), false)
                    .map(RegistryObject::get)
                    .map(block -> (Block) block)
                    .toList();
        }
    }
}
