package de.maxhenkel.voicechat.net;

import de.maxhenkel.voicechat.Voicechat;
import de.maxhenkel.voicechat.util.FriendlyByteBuf;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

public class UpdateStatePacket implements Packet<UpdateStatePacket> {

    public static final NamespacedKey PLAYER_STATE = Voicechat.compatibility.createNamespacedKey("update_state");

    private boolean disabled;
    private boolean listeningToGroups;
    private boolean broadcastingToGroups;

    public UpdateStatePacket() {

    }

    public UpdateStatePacket(boolean disabled, boolean listeningToGroups, boolean broadcastingToGroups) {
        this.disabled = disabled;
        this.listeningToGroups = listeningToGroups;
        this.broadcastingToGroups = broadcastingToGroups;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public boolean shouldListenToGroups() {
    	return listeningToGroups;
    }

    public boolean shouldBroadcastToGroups() {
    	return broadcastingToGroups;
    }

    @Override
    public NamespacedKey getID() {
        return PLAYER_STATE;
    }

    @Override
    public void onPacket(Player player) {
        Voicechat.SERVER.getServer().getPlayerStateManager().onUpdateStatePacket(player, this);
    }

    @Override
    public UpdateStatePacket fromBytes(FriendlyByteBuf buf) {
        disabled = buf.readBoolean();
        listeningToGroups = buf.readBoolean();
        broadcastingToGroups = buf.readBoolean();
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(disabled);
        buf.writeBoolean(listeningToGroups);
        buf.writeBoolean(broadcastingToGroups);
    }

}
