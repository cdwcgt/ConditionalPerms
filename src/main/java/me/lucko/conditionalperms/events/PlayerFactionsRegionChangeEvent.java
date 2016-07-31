package me.lucko.conditionalperms.events;

import lombok.Getter;
import me.lucko.conditionalperms.utils.FactionsRegion;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerFactionsRegionChangeEvent extends PlayerEvent {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    @Getter
    private final FactionsRegion from;

    @Getter
    private final FactionsRegion to;

    public PlayerFactionsRegionChangeEvent(Player who, FactionsRegion from, FactionsRegion to) {
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