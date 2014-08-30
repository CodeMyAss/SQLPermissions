package com.devro.sqlpermissions.permissions;

import org.bukkit.ChatColor;

/**
 * Copyright DevRo_ (c) 2014. All Rights Reserved.
 * Programmed by: DevRo_ (Erik Rosemberg)
 * Creation Date: 17, 07, 2014
 * Programmed for the SQLPermissions project.
 */
public enum  Rank {
    MEMBER("Member", ChatColor.GRAY),
    OWNER("Owner", ChatColor.DARK_RED);

    private String rankName;
    private ChatColor prefixColor;

    Rank(String rankName, ChatColor prefixColor) {
        this.rankName = rankName;
        this.prefixColor = prefixColor;
    }

    public String getRankName() {
        return rankName;
    }

    public ChatColor getPrefixColor() {
        return prefixColor;
    }

    public boolean has(Rank rank) {
        return compareTo(rank) <= 0;
    }
}
