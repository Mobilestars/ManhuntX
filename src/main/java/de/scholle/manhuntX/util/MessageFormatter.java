package de.scholle.manhuntX.util;

import de.scholle.manhuntX.ManhuntX;
import org.bukkit.ChatColor;

public class MessageFormatter {

    public static String formatMessage(String message, String color) {
        String prefix = ManhuntX.getInstance().getConfigManager().getPrefix();
        ChatColor chatColor = getChatColor(color);
        return chatColor + "[" + prefix + "] " + message;
    }

    public static String formatTitle(String title, String subtitle, String color) {
        ChatColor chatColor = getChatColor(color);
        return chatColor + title + (subtitle != null ? "\n" + ChatColor.WHITE + subtitle : "");
    }

    private static ChatColor getChatColor(String color) {
        switch (color.toLowerCase()) {
            case "red": return ChatColor.RED;
            case "green": return ChatColor.GREEN;
            case "yellow": return ChatColor.YELLOW;
            case "blue": return ChatColor.BLUE;
            case "aqua": return ChatColor.AQUA;
            case "purple": return ChatColor.LIGHT_PURPLE;
            case "gold": return ChatColor.GOLD;
            case "white": return ChatColor.WHITE;
            case "gray": return ChatColor.GRAY;
            default: return ChatColor.WHITE;
        }
    }
}