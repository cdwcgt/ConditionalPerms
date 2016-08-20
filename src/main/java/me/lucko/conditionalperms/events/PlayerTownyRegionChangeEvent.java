package me.lucko.conditionalperms.events;

import lombok.Getter;
import me.lucko.conditionalperms.utils.TownyRegion;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerTownyRegionChangeEvent extends PlayerEvent {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    @Getter
    private final TownyRegion from;

    @Getter
    private final TownyRegion to;

    public PlayerTownyRegionChangeEvent(Player who, TownyRegion from, TownyRegion to) {
        super(who);
        this.from = from;
        this.to = to;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
