package pl.norbit.exampleplugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Logger;

public final class ExamplePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        FileConfiguration config = this.getConfig();

        String host = "http://localhost:8080/api/v1/license/";
        String accessToken = "admin-secret-token";
        String licenseKey = config.getString("license-key");

        LicenseHandler licenseHandler = new LicenseHandler(licenseKey, host, accessToken);

        if(!licenseHandler.isValid()){
            disablePlugin("Invalid license key");
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if(licenseHandler.isValidServerKey()) return;

                disablePlugin("The plugin was launched on another server!");
            }
        }.runTaskTimerAsynchronously(this, 0, 20 * 60L);
    }

    public void disablePlugin(String message){
        Logger logger = getServer().getLogger();
        logger.warning(message);
        this.getServer().getPluginManager().disablePlugin(this);
    }
}
