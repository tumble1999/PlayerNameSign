package ga.tumblenet.mc.PlayerNameSign;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class SignListener implements Listener {
	
	final int SIGN_LENGTH = 16;

	public SignListener(PlayerNameSign plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		if (e.getLine(0).equalsIgnoreCase("[Player]")) {
			String username = e.getLine(1);
			Player placer = e.getPlayer();
			
			PermissionUser user = PermissionsEx.getUser(username);
			//Player player = user.getPlayer();
			//Player player = Bukkit.getServer().getPlayer(username);
			if (user == null) {
				Block block = e.getBlock();
				block.breakNaturally();
				placer.sendMessage("§cPlayer " + username + " does not exist");
				return;
			}
			
			//placer.sendMessage("Name: " + player.getName());
			//placer.sendMessage("Custom Name: " + player.getCustomName());
			//placer.sendMessage("Display Name" + player.getDisplayName());
			//placer.sendMessage("List Name" + player.getPlayerListName());
			
			String nameRaw = user.getName();
			String prefixRaw = getGroup(user).getPrefix();
			
			String name = colorize(nameRaw);
			String prefix = colorize(prefixRaw);
			String full = colorize(prefixRaw + nameRaw);
			
			e.setLine(0, "");
			e.setLine(1, full);
			if (!fitsOnSign(full)) {
				e.setLine(1, prefix);
				e.setLine(2, ChatColor.getLastColors(prefix) + name);
			}
			
		}
	}
	
	public PermissionGroup getGroup(PermissionUser user) {
		return user.getParents().get(0);
	}
	
	public String colorize(String s) {
		return ChatColor.translateAlternateColorCodes('&',s);
	}
	public boolean fitsOnSign(String s) {
		String rawString = ChatColor.stripColor(s);
		return rawString.length() < SIGN_LENGTH;
	}
}
