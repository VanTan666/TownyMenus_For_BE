package xaviermc.top.townymenus_for_be;

import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.ChatColor;

import xaviermc.top.townymenus_for_be.gui.*;

public final class TownyMenus_For_BE extends JavaPlugin {

    @Override
    public void onEnable() {
        // Загрузка конфига
        saveDefaultConfig();
        getLogger().info(ChatColor.GREEN + "Конфигурация загружена");

        // Регистрация команд
        getLogger().info(ChatColor.GREEN + "Команды регистрируются");
        loadCommands();
    }

    private void loadCommands() {
        // Регистрация команды begui
        PluginCommand begui = getCommand("begui");
        if (begui == null) {
            getLogger().severe("Ошибка при загрузке команды begui. Проверьте plugin.yml");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        begui.setExecutor(this);

        // Регистрация команды ping
        PluginCommand ping = getCommand("ping");
        if (ping == null) {
            getLogger().severe("Ошибка при загрузке команды ping. Проверьте plugin.yml");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        ping.setExecutor(this);

        getLogger().info(ChatColor.GREEN + "Команды успешно зарегистрированы");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("begui")) {
            if (sender instanceof Player) {
                MainForm.sendMainForm((Player) sender);
            } else {
                sender.sendMessage("§cТолько игроки могут использовать эту команду");
            }
            return true;
        }

        if (command.getName().equalsIgnoreCase("ping")) {
            // Проверка разрешения
            String permission = getConfig().getString("ping.permission", "");
            if (!permission.isEmpty() && !sender.hasPermission(permission)) {
                sender.sendMessage(ChatColor.RED + "У вас нет разрешения для этой команды!");
                return true;
            }

            // Получение префикса и сообщения из конфига
            String prefix = getConfig().getString("ping.prefix", "");
            String pingMessage = getConfig().getString("ping.message", "pong");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + pingMessage));
            return true;
        }

        return false;
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.GREEN + "Плагин успешно отключен");
    }
}