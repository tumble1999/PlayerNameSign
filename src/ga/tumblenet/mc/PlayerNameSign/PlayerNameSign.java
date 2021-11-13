package ga.tumblenet.mc.PlayerNameSign;

import org.bukkit.plugin.java.JavaPlugin;

public class PlayerNameSign extends JavaPlugin {
	@Override
	public void onEnable() {
		getLogger().info("PlayerNameSigns Enabled");
		new SignListener(this);
	}
	
	@Override
	public void onDisable() {
		getLogger().info("PlayerNameSigns Disabled");
	}
}