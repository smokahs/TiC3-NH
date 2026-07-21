package com.tic3nh.compat.gtceu;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;

import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import com.tic3nh.TiC3NH;

@Mod.EventBusSubscriber(modid = TiC3NH.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class GtDedupe {

    private GtDedupe() {}

    private static final Set<String> DUP_METALS = Set.of("cobalt", "steel", "rose_gold");

    private static final String[] EXTRA_ITEMS = {"raw_cobalt", "raw_cobalt_block"};

    private static Set<Item> hiddenItems;

    public static boolean isActive() {
        return GtAccess.isLoaded();
    }

    private static Set<Item> hiddenItems() {
        Set<Item> items = hiddenItems;
        if (items == null) {
            items = new HashSet<>();
            for (String metal : DUP_METALS) {
                addItem(items, metal + "_ingot");
                addItem(items, metal + "_nugget");
                addItem(items, metal + "_block");
            }
            for (String id : EXTRA_ITEMS) {
                addItem(items, id);
            }
            hiddenItems = items;
        }
        return items;
    }

    private static void addItem(Set<Item> set, String path) {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation("tconstruct", path));
        if (item != null && item != Items.AIR) {
            set.add(item);
        }
    }

    public static boolean isHiddenItem(Item item) {
        return isActive() && hiddenItems().contains(item);
    }

    public static boolean isHiddenFluid(Fluid fluid) {
        if (!isActive()) {
            return false;
        }
        ResourceLocation id = ForgeRegistries.FLUIDS.getKey(fluid);
        if (id == null || !id.getNamespace().equals("tconstruct")) {
            return false;
        }
        String path = id.getPath();
        for (String metal : DUP_METALS) {
            if (path.equals("molten_" + metal) || path.equals("flowing_molten_" + metal)) {
                return true;
            }
        }
        return false;
    }

    @SubscribeEvent
    public static void hideFromCreative(BuildCreativeModeTabContentsEvent event) {
        if (!isActive()) {
            return;
        }
        var iterator = event.getEntries().iterator();
        while (iterator.hasNext()) {
            if (hiddenItems().contains(iterator.next().getKey().getItem())) {
                iterator.remove();
            }
        }
    }
}
