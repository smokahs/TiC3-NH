package com.tic3nh.compat.emi;

import java.util.function.Consumer;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

import slimeknights.mantle.client.screen.MultiModuleScreen;

import com.tic3nh.compat.gtceu.GtDedupe;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.Bounds;

@EmiEntrypoint
public class Emi implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        registry.addGenericExclusionArea(Emi::exclude);

        if (GtDedupe.isActive()) {
            registry.removeEmiStacks(Emi::isDuplicateMetal);
        }
    }

    private static boolean isDuplicateMetal(EmiStack stack) {
        ItemStack item = stack.getItemStack();
        if (!item.isEmpty() && GtDedupe.isHiddenItem(item.getItem())) {
            return true;
        }
        Fluid fluid = stack.getKeyOfType(Fluid.class);
        return fluid != null && GtDedupe.isHiddenFluid(fluid);
    }

    private static void exclude(Screen screen, Consumer<Bounds> consumer) {
        if (screen instanceof MultiModuleScreen<?> multi) {
            for (Rect2i area : multi.getModuleAreas()) {
                consumer.accept(new Bounds(area.getX(), area.getY(), area.getWidth(), area.getHeight()));
            }
        }
    }
}
