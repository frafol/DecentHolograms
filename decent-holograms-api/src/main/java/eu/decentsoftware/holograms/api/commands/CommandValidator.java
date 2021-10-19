package eu.decentsoftware.holograms.api.commands;

import eu.decentsoftware.holograms.api.Lang;
import lombok.experimental.UtilityClass;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@UtilityClass
public class CommandValidator {

	/**
	 * Get the Player from CommandSender if it is a Player.
	 * <p>
	 *     If CommandSender is not a Player, message will be sent to him.
	 * </p>
	 *
	 * @param sender The CommandSender.
	 * @return The Player or null if CommandSender is not Player.
	 */
	public static Player getPlayer(CommandSender sender) {
		if (!(sender instanceof Player)) {
			Lang.ONLY_PLAYER.send(sender);
			return null;
		}
		return (Player) sender;
	}

	/**
	 * Check if String is a valid identifier of Command.
	 *
	 * @param identifier The String to check.
	 * @param commandBase The Command.
	 * @return Boolean whether the String is a valid identifier of the CommandBase.
	 */
	public static boolean isIdentifier(String identifier, CommandBase commandBase) {
		if (identifier.equalsIgnoreCase(commandBase.getName())) {
			return true;
		}

		for (String alias : commandBase.getAliases()) {
			if (identifier.equalsIgnoreCase(alias)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if CommandSender is allowed to execute Command.
	 * <p>
	 *     If the CommandSender isn't allowed to execute the Command, message
	 *     will be sent to him.
	 * </p>
	 *
	 * @param sender The CommandSender
	 * @param commandBase The Command being executed
	 * @return Boolean whether the Command may be executed by the CommandSender
	 */
	public static boolean canExecute(CommandSender sender, CommandBase commandBase, boolean sendMessage) {
		if (commandBase.isPlayerOnly() && !(sender instanceof Player)) {
			if (sendMessage) Lang.ONLY_PLAYER.send(sender);
			return false;
		}

		if (commandBase.getPermission() != null && !commandBase.getPermission().isEmpty() && !sender.hasPermission(commandBase.getPermission())) {
			if (sendMessage) Lang.NO_PERM.send(sender);
			return false;
		}
		return true;
	}

}
