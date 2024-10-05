package io.github.OminousOne.pvp.test.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;

public class Commands {
	public static void init() {
		CommandManager commandManager = MinecraftServer.getCommandManager();
		
		commandManager.register(new GameModeCommand());
		//commandManager.register(new NickCommand());
		commandManager.register(new SkinCommand());
		commandManager.register(new DamageCommand());
		commandManager.register(new ClearCommand());
	}
}
