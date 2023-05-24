package net.forexample.startcommand.events;

import com.mojang.brigadier.CommandDispatcher;
import net.forexample.startcommand.commands.StartMakeScaryCommand;
import net.minecraft.command.CommandSource;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModCommands {

    @SubscribeEvent
    public static void onRegisterCommandEvent(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> commandDispatcher = event.getDispatcher();

        StartMakeScaryCommand.register(commandDispatcher);
    }
}
