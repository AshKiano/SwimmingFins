package com.ashkiano.swimmingFins;

import org.bukkit.plugin.java.JavaPlugin;

public class SwimmingFins extends JavaPlugin {

    private String swimmingFinsUsePermission;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.swimmingFinsUsePermission = this.getConfig().getString("permissions.use", "swimmingFins.use");

        getCommand("swimmingFins").setExecutor(new SwimmingFinsCommand(swimmingFinsUsePermission, this));
        getServer().getPluginManager().registerEvents(new SwimmingFinsListener(this), this);

        this.getLogger().info("Thank you for using the swimmingFins plugin! If you enjoy using this plugin, please consider making a donation to support the development. You can donate at: https://paypal.me/josefvyskocil");

        Metrics metrics = new Metrics(this, 19409);
    }
}