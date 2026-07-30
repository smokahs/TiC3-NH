package com.tic3nh.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import com.tic3nh.TiC3NH;

/** Item models for the paperbark set: mostly the block model, flat sprites for the rest. */
public final class PaperbarkItems extends ItemModelProvider {

    public PaperbarkItems(PackOutput output, ExistingFileHelper existing) {
        super(output, TiC3NH.MOD_ID, existing);
    }

    @Override
    protected void registerModels() {
        for (String name : new String[] {
            "paperbark_log", "paperbark_wood", "stripped_paperbark_log", "stripped_paperbark_wood",
            "paperbark_leaves", "paperbark_planks", "paperbark_stairs", "paperbark_slab",
            "paperbark_fence_gate", "paperbark_pressure_plate"
        }) {
            withExistingParent(name, modLoc("block/" + name));
        }

        withExistingParent("paperbark_fence", modLoc("block/paperbark_fence_inventory"));
        withExistingParent("paperbark_button", modLoc("block/paperbark_button_inventory"));
        withExistingParent("paperbark_trapdoor", modLoc("block/paperbark_trapdoor_bottom"));

        flat("paperbark_sapling", modLoc("block/paperbark_sapling"));
        flat("paperbark_door", modLoc("item/paperbark_door"));
        flat("paperbark_sign", modLoc("item/paperbark_sign"));
        flat("paperbark_hanging_sign", modLoc("item/paperbark_hanging_sign"));
    }

    private void flat(String name, ResourceLocation texture) {
        withExistingParent(name, mcLoc("item/generated")).texture("layer0", texture);
    }
}
