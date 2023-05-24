package net.forexample.startcommand.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class StartMakeScaryCommand implements Command<CommandSource> {

    private static final StartMakeScaryCommand CMD = new StartMakeScaryCommand();

    public static void register(CommandDispatcher<CommandSource> dispatcher){
        LiteralArgumentBuilder<CommandSource> scaryCommand = Commands.literal("scary")
            .requires((commandSource -> commandSource.hasPermissionLevel(0)))
            .executes(CMD);
        dispatcher.register(scaryCommand);
    }

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        player.sendChatMessage("/difficulty hard");
        player.sendChatMessage("/summon witherstormmod:wither_storm ~ -120 ~ {Phase:4}");
        player.sendChatMessage("/attribute @e[type=witherstormmod:wither_storm,limit=1] witherstormmod:slow_flying_speed base set 0.16");
        player.sendChatMessage("/tp @e[type=witherstormmod:wither_storm,limit=1] ~ 140 ~-2000");
        return 0;
    }
}