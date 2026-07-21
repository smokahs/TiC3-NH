package com.tic3nh.mixin;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;

import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.definition.module.build.ToolTraitHook;
import slimeknights.tconstruct.library.tools.helper.ModifierBuilder;
import slimeknights.tconstruct.library.tools.nbt.MaterialNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import com.tic3nh.setup.reg;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ToolStack.class)
public class TSMixin {

    @Shadow @Final private Item item;

    @Redirect(
            method = "rebuildStats",
            at = @At(value = "INVOKE",
                    target = "Lslimeknights/tconstruct/library/tools/definition/module/build/ToolTraitHook;"
                           + "addTraits("
                           + "Lslimeknights/tconstruct/library/tools/definition/ToolDefinition;"
                           + "Lslimeknights/tconstruct/library/tools/nbt/MaterialNBT;"
                           + "Lslimeknights/tconstruct/library/tools/helper/ModifierBuilder;)V"))
    private void tic3nh$addLevelingTrait(ToolTraitHook hook, ToolDefinition definition,
                                         MaterialNBT materials, ModifierBuilder builder) {
        hook.addTraits(definition, materials, builder);
        builder.add(reg.REPAIR_TWEAKS.getId(), 1);
        if (!(this.item instanceof ArmorItem)) {
            builder.add(reg.LEVELING.getId(), 1);
            com.tic3nh.leveling.LvlLogic.noteMixinActive();
        }
    }
}
