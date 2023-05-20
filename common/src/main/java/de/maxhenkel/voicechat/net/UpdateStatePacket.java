package de.maxhenkel.voicechat.net;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class UpdateStatePacket implements Packet<UpdateStatePacket> {

    public static final ResourceLocation PLAYER_STATE = new ResourceLocation(NetManager.CHANNEL, "update_state");

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
    public ResourceLocation getIdentifier() {
        return PLAYER_STATE;
    }

    @Override
    public UpdateStatePacket fromBytes(PacketBuffer buf) {
        disabled = buf.readBoolean();
        listeningToGroups = buf.readBoolean();
        broadcastingToGroups = buf.readBoolean();
        return this;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeBoolean(disabled);
        buf.writeBoolean(listeningToGroups);
        buf.writeBoolean(broadcastingToGroups);
    }

}
