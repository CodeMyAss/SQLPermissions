package com.devro.sqlpermissions.permissions.listeners;

import com.devro.sqlpermissions.PermissionsCore;
import com.devro.sqlpermissions.permissions.PermissionsManager;
import com.devro.sqlpermissions.permissions.Rank;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Copyright DevRo_ (c) 2014. All Rights Reserved.
 * Programmed by: DevRo_ (Erik Rosemberg)
 * Creation Date: 17, 07, 2014
 * Programmed for the SQLPermissions project.
 */
public class JoinQuitListener implements Listener {

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    PreparedStatement statement = PermissionsCore.getInstance().getSQL().getConnection().prepareStatement("SELECT  rank FROM Global WHERE uuid=?; ");

                    statement.setString(1, event.getPlayer().getUniqueId().toString());

                    ResultSet set = statement.executeQuery();

                    if (set.next()) {
                        Rank rank;

                        try {
                            rank = Rank.valueOf(set.getString("rank"));
                            PermissionsCore.getInstance().getScoreboard().getTeam(rank.name()).addPlayer(event.getPlayer());
                            System.out.println(rank);
                        } catch (Exception e) {
                            rank = Rank.MEMBER;
                        }

                        if (rank != null) {
                            PermissionsManager.getInstance().getPermissions().put(event.getPlayer().getUniqueId(), rank);
                        }
                    } else {
                        PermissionsManager.getInstance().getPermissions().put(event.getPlayer().getUniqueId(), Rank.MEMBER);

                        PreparedStatement is = PermissionsCore.getInstance().getSQL().getConnection().prepareStatement("INSERT INTO Global VALUES (?, ?);");

                        is.setString(1, event.getPlayer().getUniqueId().toString());
                        is.setString(2, Rank.MEMBER.name());

                        is.executeUpdate();
                }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(PermissionsCore.getInstance());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        PermissionsManager.getInstance().getPermissions().remove(event.getPlayer().getUniqueId());

        PermissionsManager.getInstance().setRank(event.getPlayer().getUniqueId(), Rank.OWNER);

        PermissionsCore.getInstance().getScoreboard().getTeam(PermissionsManager.getInstance().getRank(event.getPlayer()).name()).removePlayer(event.getPlayer());
    }
}
