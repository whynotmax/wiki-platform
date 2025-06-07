package dev.mzcy.minecraft;


import dev.mzcy.minecraft.client.WikiApiClient;
import dev.mzcy.minecraft.command.WikiCommand;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.List;

public class WikiPlugin extends JavaPlugin {

    private WikiApiClient apiClient;
    private CommandMap commandMap;

    @Override
    public void onEnable() {
        // config.yml laden oder defaults speichern, falls nicht vorhanden
        saveDefaultConfig();
        try {
            apiClient = getApiClient();
        } catch (Exception e) {
            getLogger().severe("Failed to initialize Wiki API client: " + e.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        try {
            commandMap = getCommandMap();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            getLogger().severe("Failed to access command map: " + e.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        boolean enableCommand = getConfig().getBoolean("plugin.enable-command", true);

        if (enableCommand) {
            String commandName = getConfig().getString("plugin.command.name", "wiki");
            String description = getConfig().getString("plugin.command.description", "Get a wiki entry by ID");
            String usage = getConfig().getString("plugin.command.usage", "/wiki <id>");
            List<String> aliases = getConfig().getStringList("plugin.command.aliases");
            @Nullable String permission = getConfig().getString("plugin.command.permission");

            Command command = new WikiCommand(apiClient, commandName, description, usage, aliases, permission);
            commandMap.register("wiki", command);

            getLogger().info("Command /" + commandName + " enabled.");
        } else {
            getLogger().info("Wiki command disabled by config.");
        }

        getLogger().info("Plugin enabled. Made by mzcy. Version: " + getDescription().getVersion());
    }

    private WikiApiClient getApiClient() {
        if (apiClient == null) {
            String baseUrl = getConfig().getString("plugin.api.base-url", "https://api.example.com/wiki");
            int timeout = getConfig().getInt("plugin.api.timeout", 5000);
            apiClient = new WikiApiClient(baseUrl, timeout);
        }
        return apiClient;
    }

    private CommandMap getCommandMap() throws NoSuchFieldException, IllegalAccessException {
        if (commandMap == null) {
            final Class<? extends Server> serverClass = Bukkit.getServer().getClass();
            final Field commandMapField = serverClass.getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
        }
        return commandMap;
    }
}
