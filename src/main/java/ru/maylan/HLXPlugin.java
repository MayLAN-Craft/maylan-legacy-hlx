package ru.maylan;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class HLXPlugin extends JavaPlugin implements Listener {
    private String trigger;
    private Component hl3Message;
    private boolean broadcast;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadLocalConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getSLF4JLogger().info("HL3Confirm enabled.");
    }

    private void loadLocalConfig() {
        FileConfiguration cfg = getConfig();
        this.trigger = cfg.getString("pattern", "3");
        this.broadcast = cfg.getBoolean("broadcast", true);
        String msg = cfg.getString("message", "λ Half-Life 3 confirmed!");
        this.hl3Message = Component.text(msg)
                .color(NamedTextColor.GOLD)
                .decorate(TextDecoration.BOLD);
    }

    @EventHandler(ignoreCancelled = true)
    public void onChat(AsyncChatEvent event) {
        // Преобразуем сообщение игрока в plain‑текст
        String plain = PlainTextComponentSerializer.plainText().serialize(event.message());
        if (plain.contains(trigger)) {
            if (broadcast) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(hl3Message);
                }
            } else {
                event.getPlayer().sendMessage(hl3Message);
            }
        }
    }
}
