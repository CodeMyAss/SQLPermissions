package com.devro.sqlpermissions.permissions;

import com.devro.sqlpermissions.PermissionsCore;
import com.devro.sqlpermissions.permissions.listeners.ChatListener;
import com.devro.sqlpermissions.permissions.listeners.JoinQuitListener;
import com.devro.sqlpermissions.utils.UUIDUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

/**
 * Copyright DevRo_ (c) 2014. All Rights Reserved.
 * Programmed by: DevRo_ (Erik Rosemberg)
 * Creation Date: 17, 07, 2014
 * Programmed for the SQLPermissions project.
 */
public class PermissionsManager {
    private static PermissionsManager instance;

    private HashMap<UUID, Rank> permissions = new HashMap<UUID, Rank>();

    public static PermissionsManager getInstance() {
        if (instance == null) {
            instance = new PermissionsManager();
        }

        return instance;
    }

    public PermissionsManager() {
        PermissionsCore.getInstance().getServer().getPluginManager().registerEvents(new JoinQuitListener(), PermissionsCore.getInstance());
        PermissionsCore.getInstance().getServer().getPluginManager().registerEvents(new ChatListener(), PermissionsCore.getInstance());
    }

    public Rank getRank(Player player) {
        if (!(permissions.containsKey(player.getUniqueId()))) {
            permissions.put(player.getUniqueId(), Rank.MEMBER);

            return getRank(player);
        }

        return permissions.get(player.getUniqueId());
    }

    public Rank getRankOfflinePlayer(String name) {
        UUID uuid = UUIDUtil.getUUIDOf(name);

        try {
            PreparedStatement statement = PermissionsCore.getInstance().getSQL().getConnection().prepareStatement("SELECT rank FROM Global WHERE uuid=?;");

            statement.setString(1, uuid.toString());

            ResultSet set = statement.executeQuery();

            if (set.next()) {
                Rank rank = Rank.valueOf(set.getString("rank"));

                if (rank != null) {
                    set.close();
                    return rank;
                }
            }

            set.close();
        } catch (Exception e) {
            e.printStackTrace();

            return Rank.MEMBER;
        }

        return Rank.MEMBER;
    }

    public void setRank(final UUID uuid, final Rank rank) {
        Player player = Bukkit.getPlayer(uuid);

        if (player != null) {

            permissions.put(uuid, rank);

        }

        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    PreparedStatement statement = PermissionsCore.getInstance().getSQL().getConnection().prepareStatement("UPDATE Global SET rank=? WHERE uuid=?;");

                    statement.setString(1, rank.name());
                    statement.setString(2, uuid.toString());

                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(PermissionsCore.getInstance());
    }

    public HashMap<UUID, Rank> getPermissions() {
        return permissions;
    }
}
