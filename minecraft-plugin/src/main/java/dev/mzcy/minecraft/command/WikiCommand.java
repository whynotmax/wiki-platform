package dev.mzcy.minecraft.command;

import dev.mzcy.minecraft.client.WikiApiClient;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class WikiCommand extends Command {

    private final WikiApiClient wikiApiClient;

    public WikiCommand(@NotNull WikiApiClient wikiApiClient, @NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases, @Nullable String permission) {
        super(name, description, usageMessage, aliases);
        if (permission != null) {
            this.setPermission(permission);
        }
        this.wikiApiClient = wikiApiClient;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage("Usage: /wiki <entryId>");
            return true;
        }

        String entryId = args[0];

        try {
            var entry = wikiApiClient.getWikiEntryById(entryId);

            if (entry == null) {
                player.sendMessage("Wiki entry not found.");
            } else {
                player.sendMessage("§6Wiki Entry: §r" + entry.getId());
                player.sendMessage("§6Title: §r" + entry.getTitle());
                player.sendMessage("§7Content: §r" + entry.getContent());
            }

        } catch (Exception e) {
            player.sendMessage("Error fetching wiki entry.");
            e.printStackTrace();
        }

        return true;
    }
}
