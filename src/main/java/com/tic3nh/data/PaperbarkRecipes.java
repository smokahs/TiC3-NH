package com.tic3nh.data;

import java.util.function.Consumer;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.crafting.Ingredient;

import com.tic3nh.tree.Paperbark;

/** Crafting for the paperbark set. Paper is not in here — bark only comes off with an axe. */
public final class PaperbarkRecipes extends RecipeProvider {

    public PaperbarkRecipes(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> out) {
        Ingredient planks = Ingredient.of(Paperbark.PLANKS.get());

        planksFromLogs(out, Paperbark.PLANKS.get(), PaperbarkTags.LOGS_ITEM, 4);
        woodFromLogs(out, Paperbark.WOOD.get(), Paperbark.LOG.get());
        woodFromLogs(out, Paperbark.STRIPPED_WOOD.get(), Paperbark.STRIPPED_LOG.get());

        stairBuilder(Paperbark.STAIRS.get(), planks)
                .group("wooden_stairs")
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(out);
        slab(out, RecipeCategory.BUILDING_BLOCKS, Paperbark.SLAB.get(), Paperbark.PLANKS.get());
        fenceBuilder(Paperbark.FENCE.get(), planks)
                .group("wooden_fence")
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(out);
        fenceGateBuilder(Paperbark.FENCE_GATE.get(), planks)
                .group("wooden_fence_gate")
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(out);
        doorBuilder(Paperbark.DOOR_ITEM.get(), planks)
                .group("wooden_door")
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(out);
        trapdoorBuilder(Paperbark.TRAPDOOR.get(), planks)
                .group("wooden_trapdoor")
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(out);
        buttonBuilder(Paperbark.BUTTON.get(), planks)
                .group("wooden_button")
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(out);
        pressurePlate(out, Paperbark.PRESSURE_PLATE.get(), Paperbark.PLANKS.get());
        signBuilder(Paperbark.SIGN_ITEM.get(), planks)
                .group("wooden_sign")
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(out);
        hangingSign(out, Paperbark.HANGING_SIGN_ITEM.get(), Paperbark.STRIPPED_LOG.get());
    }
}
