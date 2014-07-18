package com.devro.sqlpermissions.permissions.listeners;

import com.devro.sqlpermissions.permissions.PermissionsManager;
import com.devro.sqlpermissions.permissions.Rank;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Copyright DevRo_ (c) 2014. All Rights Reserved.
 * Programmed by: DevRo_ (Erik Rosemberg)
 * Creation Date: 18, 07, 2014
 * Programmed for the SQLPermissions project.
 */
public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setFormat(getFormat(PermissionsManager.getInstance().getRank(event.getPlayer())));
    }

    private String getFormat(Rank rank) {
        if (rank == Rank.MEMBER) {
            return rank.getPrefixColor() + "%s" + ChatColor.BLUE + ">> " + ChatColor.WHITE + "%s";
        }  else {
            return ChatColor.DARK_GRAY + "[" +  rank.getPrefixColor() + rank.getRankName() + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY  + "%s" + ChatColor.BLUE + ">> " + ChatColor.WHITE + "%s";
        }
    }
}
