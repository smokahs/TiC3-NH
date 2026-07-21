package com.tic3nh.leveling.commands;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import com.tic3nh.TiC3NH;
import com.tic3nh.leveling.LvlLogic;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;

@Mod.EventBusSubscriber(modid = TiC3NH.MOD_ID)
public final class Cmds {

    private Cmds() {}

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("leveluptool")
                .requires(source -> source.hasPermission(2))
                .executes(ctx -> levelUp(ctx, 1))
                .then(Commands.argument("levels", IntegerArgumentType.integer(1, 98))
                        .executes(ctx -> levelUp(ctx, IntegerArgumentType.getInteger(ctx, "levels")))));
        dispatcher.register(Commands.literal("toolxp")
                .requires(source -> source.hasPermission(2))
                .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                        .executes(ctx -> addXp(ctx, IntegerArgumentType.getInteger(ctx, "amount")))));
    }

    private static int levelUp(CommandContext<CommandSourceStack> ctx, int levels) {
        ToolStack tool = heldTool(ctx);
        if (tool == null) {
            return 0;
        }
        LvlLogic.addLevels(tool, ctx.getSource().getPlayer(), levels);
        ctx.getSource().sendSuccess(() -> Component.translatable("tic3nh.command.leveled", levels), true);
        return 1;
    }

    private static int addXp(CommandContext<CommandSourceStack> ctx, int amount) {
        ToolStack tool = heldTool(ctx);
        if (tool == null) {
            return 0;
        }
        LvlLogic.addXp(tool, ctx.getSource().getPlayer(), amount);
        ctx.getSource().sendSuccess(() -> Component.translatable("tic3nh.command.gained_xp", amount), true);
        return 1;
    }

    @javax.annotation.Nullable
    private static ToolStack heldTool(CommandContext<CommandSourceStack> ctx) {
        ServerPlayer player = ctx.getSource().getPlayer();
        if (player == null) {
            return null;
        }
        ItemStack stack = player.getMainHandItem();
        if (!stack.is(TinkerTags.Items.MODIFIABLE)) {
            ctx.getSource().sendFailure(Component.translatable("tic3nh.command.not_a_tool"));
            return null;
        }
        return ToolStack.from(stack);
    }
}
