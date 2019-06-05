/******************************************************************************
 * Multiverse 2 Copyright (c) the Multiverse Team 2011.                       *
 * Multiverse 2 is licensed under the BSD License.                            *
 * For more information please check the README.md file included              *
 * with this project.                                                         *
 ******************************************************************************/

package com.onarandombox.MultiverseCore.commands;

import com.dumptruckman.minecraft.util.Logging;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.event.MVVersionEvent;
import com.onarandombox.MultiverseCore.utils.webpaste.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dumps version info to the console.
 */
public class VersionCommand extends MultiverseCommand {
    private static final URLShortener SHORTENER = new BitlyURLShortener();

    public VersionCommand(MultiverseCore plugin) {
        super(plugin);
        this.setName("Multiverse Version");
        this.setCommandUsage("/mv version " + ChatColor.GOLD + "-[bh]");
        this.setArgRange(0, 1);
        this.addKey("mv version");
        this.addKey("mvv");
        this.addKey("mvversion");
        this.setPermission("multiverse.core.version",
                "Dumps version info to the console, optionally to pastie.org with -p or pastebin.com with a -b.", PermissionDefault.TRUE);
    }

    /**
     * Send the current contents of this.pasteBinBuffer to a web service.
     *
     * @param type       Service type to send paste data to.
     * @param isPrivate  Should the paste be marked as private.
     * @param pasteData  Legacy string only data to post to a service.
     * @param pasteFiles Map of filenames/contents of debug info.
     * @return URL of visible paste
     */
    private static String postToService(PasteServiceType type, boolean isPrivate, String pasteData,
                                        Map<String, String> pasteFiles) {
        PasteService ps = PasteServiceFactory.getService(type, isPrivate);
        try {
            String result;
            if (ps.supportsMultiFile()) {
                result = ps.postData(ps.encodeData(pasteFiles), ps.getPostURL());
            } else {
                result = ps.postData(ps.encodeData(pasteData), ps.getPostURL());
            }
            return SHORTENER.shorten(result);
        } catch (PasteFailedException e) {
            System.out.print(e.toString());
            return "Error posting to service";
        }
    }

    private String getLegacyString() {
        String legacyFile = "[Multiverse-Core] Multiverse-Core Version: " + this.plugin.getDescription().getVersion() + '\n' +
                "[Multiverse-Core] Bukkit Version: " + this.plugin.getServer().getVersion() + '\n' +
                "[Multiverse-Core] Loaded Worlds: " + this.plugin.getMVWorldManager().getMVWorlds() + '\n' +
                "[Multiverse-Core] Multiverse Plugins Loaded: " + this.plugin.getPluginCount() + '\n' +
                "[Multiverse-Core] Economy being used: " + plugin.getEconomist().getEconomyName() + '\n' +
                "[Multiverse-Core] Permissions Plugin: " + this.plugin.getMVPerms().getType() + '\n' +
                "[Multiverse-Core] Dumping Config Values: (version " +
                this.plugin.getMVConfig().getVersion() + ")" + '\n' +
                "[Multiverse-Core]  messagecooldown: " + plugin.getMessaging().getCooldown() + '\n' +
                "[Multiverse-Core]  teleportcooldown: " + plugin.getMVConfig().getTeleportCooldown() + '\n' +
                "[Multiverse-Core]  worldnameprefix: " + plugin.getMVConfig().getPrefixChat() + '\n' +
                "[Multiverse-Core]  worldnameprefixFormat: " + plugin.getMVConfig().getPrefixChatFormat() + '\n' +
                "[Multiverse-Core]  enforceaccess: " + plugin.getMVConfig().getEnforceAccess() + '\n' +
                "[Multiverse-Core]  displaypermerrors: " + plugin.getMVConfig().getDisplayPermErrors() + '\n' +
                "[Multiverse-Core]  teleportintercept: " + plugin.getMVConfig().getTeleportIntercept() + '\n' +
                "[Multiverse-Core]  firstspawnoverride: " + plugin.getMVConfig().getFirstSpawnOverride() + '\n' +
                "[Multiverse-Core]  firstspawnworld: " + plugin.getMVConfig().getFirstSpawnWorld() + '\n' +
                "[Multiverse-Core]  debug: " + plugin.getMVConfig().getGlobalDebug() + '\n' +
                "[Multiverse-Core] Special Code: FRN002" + '\n';
        return legacyFile;
    }

    private String getMarkdownString() {
        String markdownString = "# Multiverse-Core\n" +
                "## Overview\n" +
                "| Name | Value |\n" +
                "| --- | --- |\n" +
                "| Multiverse-Core Version | `" + this.plugin.getDescription().getVersion() + "` |\n" +
                "| Bukkit Version | `" + this.plugin.getServer().getVersion() + "` |\n" +
                //markdownString.append("| Loaded Worlds | `").append(this.plugin.getMVWorldManager().getMVWorlds()).append("` |\n");
                "| Multiverse Plugins Loaded | `" + this.plugin.getPluginCount() + "` |\n" +
                "| Economy being used | `" + plugin.getEconomist().getEconomyName() + "` |\n" +
                "| Permissions Plugin | `" + this.plugin.getMVPerms().getType() + "` |\n" +
                "## Parsed Config\n" +
                "These are what Multiverse thought the in-memory values of the config were.\n\n" +
                "| Config Key  | Value |\n" +
                "| --- | --- |\n" +
                "| version | `" + this.plugin.getMVConfig().getVersion() + "` |\n" +
                "| messagecooldown | `" + plugin.getMessaging().getCooldown() + "` |\n" +
                "| teleportcooldown | `" + plugin.getMVConfig().getTeleportCooldown() + "` |\n" +
                "| worldnameprefix | `" + plugin.getMVConfig().getPrefixChat() + "` |\n" +
                "| worldnameprefixFormat | `" + plugin.getMVConfig().getPrefixChatFormat() + "` |\n" +
                "| enforceaccess | `" + plugin.getMVConfig().getEnforceAccess() + "` |\n" +
                "| displaypermerrors | `" + plugin.getMVConfig().getDisplayPermErrors() + "` |\n" +
                "| teleportintercept | `" + plugin.getMVConfig().getTeleportIntercept() + "` |\n" +
                "| firstspawnoverride | `" + plugin.getMVConfig().getFirstSpawnOverride() + "` |\n" +
                "| firstspawnworld | `" + plugin.getMVConfig().getFirstSpawnWorld() + "` |\n" +
                "| debug | `" + plugin.getMVConfig().getGlobalDebug() + "` |\n";
        return markdownString;
    }

    private String readFile(final String filename) {
        StringBuilder result;
        try {
            FileReader reader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            result = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line).append('\n');
            }
        } catch (FileNotFoundException e) {
            Logging.severe("Unable to find %s. Here's the traceback: %s", filename, e.getMessage());
            e.printStackTrace();
            result = new StringBuilder(String.format("ERROR: Could not load: %s", filename));
        } catch (IOException e) {
            Logging.severe("Something bad happend when reading %s. Here's the traceback: %s", filename, e.getMessage());
            e.printStackTrace();
            result = new StringBuilder(String.format("ERROR: Could not load: %s", filename));
        }
        return result.toString();
    }

    private Map<String, String> getVersionFiles() {
        Map<String, String> files = new HashMap<>();

        // Add the legacy file, but as markdown so it's readable
        files.put("version.md", this.getMarkdownString());

        // Add the config.yml
        File configFile = new File(this.plugin.getDataFolder(), "config.yml");
        files.put(configFile.getName(), this.readFile(configFile.getAbsolutePath()));

        // Add the config.yml
        File worldConfig = new File(this.plugin.getDataFolder(), "worlds.yml");
        files.put(worldConfig.getName(), this.readFile(worldConfig.getAbsolutePath()));
        return files;
    }

    @Override
    public void runCommand(final CommandSender sender, final List<String> args) {
        // Check if the command was sent from a Player.
        if (sender instanceof Player) {
            sender.sendMessage("Version info dumped to console. Please check your server logs.");
        }

        MVVersionEvent versionEvent = new MVVersionEvent(this.getLegacyString(), this.getVersionFiles());
        final Map<String, String> files = this.getVersionFiles();
        this.plugin.getServer().getPluginManager().callEvent(versionEvent);

        // log to console
        final String data = versionEvent.getVersionInfo();
        String[] lines = data.split("\n");
        for (String line : lines) {
            Logging.info(line);
        }

        BukkitRunnable logPoster = new BukkitRunnable() {
            @Override
            public void run() {
                if (args.size() == 1) {
                    String pasteUrl;
                    if (args.get(0).equalsIgnoreCase("-b")) {
                        // private post to pastebin
                        pasteUrl = postToService(PasteServiceType.PASTEBIN, true, data, files);
                    } else if (args.get(0).equalsIgnoreCase("-h")) {
                        // private post to pastebin
                        pasteUrl = postToService(PasteServiceType.HASTEBIN, true, data, files);
                    } else {
                        return;
                    }

                    if (!(sender instanceof ConsoleCommandSender)) {
                        sender.sendMessage("Version info dumped here: " + ChatColor.GREEN + pasteUrl);
                    }
                    Logging.info("Version info dumped here: %s", pasteUrl);
                }
            }
        };

        // Run the log posting operation asynchronously, since we don't know how long it will take.
        logPoster.runTaskAsynchronously(this.plugin);
    }
}
