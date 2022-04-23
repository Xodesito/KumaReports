package com.realxode.kumareports.utils;

import org.bukkit.ChatColor;

public class TextHandler {

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
