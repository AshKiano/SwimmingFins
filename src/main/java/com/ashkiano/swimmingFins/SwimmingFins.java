package com.ashkiano.swimmingFins;

import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SwimmingFins extends JavaPlugin {

    private String swimmingFinsUsePermission;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.swimmingFinsUsePermission = this.getConfig().getString("permissions.use", "swimmingFins.use");

        getCommand("swimmingFins").setExecutor(new SwimmingFinsCommand(swimmingFinsUsePermission, this));
        getServer().getPluginManager().registerEvents(new SwimmingFinsListener(this), this);

        this.getLogger().info("Thank you for using the swimmingFins plugin! If you enjoy using this plugin, please consider making a donation to support the development. You can donate at: https://donate.ashkiano.com");

        Metrics metrics = new Metrics(this, 19409);

        checkForUpdates();
    }

    private void checkForUpdates() {
        try {
            String pluginName = this.getDescription().getName();
            URL url = new URL("https://plugins.ashkiano.com/version_check.php?plugin=" + pluginName);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String jsonResponse = response.toString();
                JSONObject jsonObject = new JSONObject(jsonResponse);
                if (jsonObject.has("error")) {
                    this.getLogger().warning("Error when checking for updates: " + jsonObject.getString("error"));
                } else {
                    String latestVersion = jsonObject.getString("latest_version");

                    String currentVersion = this.getDescription().getVersion();
                    if (currentVersion.equals(latestVersion)) {
                        this.getLogger().info("This plugin is up to date!");
                    } else {
                        this.getLogger().warning("There is a newer version (" + latestVersion + ") available! Please update!");
                    }
                }
            } else {
                this.getLogger().warning("Failed to check for updates. Response code: " + responseCode);
            }
        } catch (Exception e) {
            this.getLogger().warning("Failed to check for updates. Error: " + e.getMessage());
        }
    }
}