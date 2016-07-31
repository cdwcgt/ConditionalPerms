package me.lucko.conditionalperms.hooks.impl;

import me.lucko.conditionalperms.events.PlayerFactionsRegionChangeEvent;
import me.lucko.conditionalperms.hooks.AbstractHook;
import me.lucko.conditionalperms.utils.FactionsRegion;
import me.markeh.factionsframework.FactionsFramework;
import me.markeh.factionsframework.entities.FPlayer;
import me.markeh.factionsframework.entities.FPlayers;
import me.markeh.factionsframework.entities.Faction;
import me.markeh.factionsframework.entities.Factions;
import me.markeh.factionsframework.enums.Rel;
import me.markeh.factionsframework.layer.EventsLayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FactionsHook extends AbstractHook {
    private Map<UUID, FactionsRegion> regions = new HashMap<>();

    FactionsHook(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void init() {
        FactionsFramework.load(getPlugin());
        // We don't need events
        HandlerList.unregisterAll(EventsLayer.get());
        FactionsFramework.get();
    }

    @Override
    public void shutdown() {
        FactionsFramework.get().stop();
    }

    public FactionsRegion getRegion(Player player) {
        final FPlayer p = FPlayers.getBySender(player);
        if (p == null) return null;

        final Faction factionAt = p.getFactionAt();
        if (factionAt == null) {
            return FactionsRegion.NONE;
        }

        if (factionAt.isNone()) {
            return FactionsRegion.NONE;
        }

        if (factionAt.getId().equals(p.getFaction().getId())) {
            return FactionsRegion.OWN;
        }

        if (factionAt.getId().equals(Factions.getWarZone(player.getWorld()).getId())) {
            return FactionsRegion.WARZONE;
        }

        if (factionAt.getId().equals(Factions.getSafeZone(player.getWorld()).getId())) {
            return FactionsRegion.SAFEZONE;
        }

        final Rel rel = factionAt.getRelationTo(p.getFaction());

        if (rel == Rel.ALLY) {
            return FactionsRegion.ALLY;
        }

        if (rel == Rel.ENEMY) {
            return FactionsRegion.ENEMY;
        }

        if (rel == Rel.TRUCE) {
            return FactionsRegion.TRUCE;
        }

        if (rel == Rel.NEUTRAL) {
            return FactionsRegion.NEUTRAL;
        }

        return null;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        regions.put(e.getPlayer().getUniqueId(), getRegion(e.getPlayer()));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        regions.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (e.getFrom().getChunk().getX() == e.getTo().getChunk().getX() &&
                e.getFrom().getChunk().getZ() == e.getTo().getChunk().getZ()) {
            return;
        }

        final FactionsRegion from = regions.get(e.getPlayer().getUniqueId());
        final FactionsRegion to = getRegion(e.getPlayer());

        if (from.equals(to)) {
            return;
        }

        getPlugin().getServer().getPluginManager().callEvent(new PlayerFactionsRegionChangeEvent(e.getPlayer(), from, to));
        regions.put(e.getPlayer().getUniqueId(), to);
    }

}