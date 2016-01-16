package com.thomas15v.gppchannel;

import mineverse.Aust1n46.chat.api.MineverseChatAPI;
import net.kaikk.mc.gpp.Claim;
import net.kaikk.mc.gpp.GriefPreventionPlus;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GppChannelPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this,this);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        if (MineverseChatAPI.getMineverseChatPlayer(event.getPlayer()).getCurrentChannel().getName().equalsIgnoreCase("trusted")) {
            event.getRecipients().clear();
            event.getRecipients().addAll(getTrusted(event.getPlayer().getUniqueId()));
            event.getRecipients().add(event.getPlayer());
        }
    }

    public List<Player> getTrusted(UUID uuid){
        List<Player> friends = new ArrayList<Player>();
        for (Claim claim : GriefPreventionPlus.getInstance().getDataStore().getPlayerData(uuid).getClaims()){
           for (UUID friend : claim.getPermissionMapPlayers().keySet()){
               System.out.println(friend + " " + claim.getPermissionMapPlayers().get(friend));
               Player player = Bukkit.getPlayer(friend);
               if (player != null){
                   friends.add(player);
               }
           }
        }
        return friends;
    }
}
