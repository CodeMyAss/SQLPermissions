package com.devro.sqlpermissions;

import com.devro.sqlpermissions.permissions.PermissionsManager;
import com.devro.sqlpermissions.permissions.Rank;
import com.devro.sqlpermissions.sql.SQL;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.sql.SQLException;

/**
 * Copyright DevRo_ (c) 2014. All Rights Reserved.
 * Programmed by: DevRo_ (Erik Rosemberg)
 * Creation Date: 17, 07, 2014
 * Programmed for the SQLPermissions project.
 */
public class PermissionsCore extends JavaPlugin {
    private static PermissionsCore instance;

    private Scoreboard scoreboard;
    private SQL sql;

    @Override
    public void onEnable() {
        setInstance(this);

        saveDefaultConfig();

        sql = new SQL(getConfig().getString("SQL.Address"), getConfig().getString("SQL.Port"), getConfig().getString("SQL.Database"), getConfig().getString("SQL.Username"), getConfig().getString("SQL.Password"));

        try {
            sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS  Global(uuid LONGTEXT, rank MEDIUMTEXT);").executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        scoreboard = getServer().getScoreboardManager().getMainScoreboard();

        for (Rank rank : Rank.values()) {
            Team team = scoreboard.registerNewTeam(rank.name());
            if (rank == Rank.MEMBER) {
                team.setPrefix("");
            } else {
                team.setPrefix(ChatColor.DARK_GRAY + "[" + rank.getPrefixColor() + rank.getRankName() + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY);
            }
            team.setDisplayName(rank.getRankName());
        }

        PermissionsManager.getInstance();
    }

    @Override
    public void onDisable() {
        getSQL().endConnection();

        for (Team t : scoreboard.getTeams()) {
            t.unregister();
        }

        setInstance(null);
    }

    private static void setInstance(PermissionsCore instance) {
        PermissionsCore.instance = instance;
    }

    public static PermissionsCore getInstance() {
        return instance;
    }

    public SQL getSQL() {
        return sql;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
