package de.maxhenkel.voicechat.voice.common;

import de.maxhenkel.voicechat.util.FriendlyByteBuf;

import javax.annotation.Nullable;
import java.util.UUID;

public class PlayerState {

    private UUID uuid;
    private String name;
    private boolean disabled;
    private boolean disconnected;
    private boolean broadcastingToGroup = true;
    private boolean listeningToGroup = true;
    @Nullable
    private UUID group;

    public PlayerState(UUID uuid, String name, boolean disabled, boolean disconnected) {
        this.uuid = uuid;
        this.name = name;
        this.disabled = disabled;
        this.disconnected = disconnected;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDisconnected() {
        return disconnected;
    }

    public void setDisconnected(boolean disconnected) {
        this.disconnected = disconnected;
    }

    @Nullable
    public UUID getGroup() {
        return group;
    }

    public void setGroup(@Nullable UUID group) {
        this.group = group;
    }

    public boolean hasGroup() {
        return group != null;
    }

	public boolean isBroadcastingToGroup() {
		return broadcastingToGroup;
	}

	public void setBroadcastingToGroup(boolean broadcastingToGroup) {
		this.broadcastingToGroup = broadcastingToGroup;
	}

	public boolean isListeningToGroup() {
		return listeningToGroup;
	}

	public void setListeningToGroup(boolean listeningToGroup) {
		this.listeningToGroup = listeningToGroup;
	}

    @Override
    public String toString() {
        return "{" +
                "disabled=" + disabled +
                ", disconnected=" + disconnected +
                ", uuid=" + uuid +
                ", name=" + name +
                ", group=" + group +
                ", broadcastingToGroup=" + broadcastingToGroup +
                ", listeningToGroup=" + listeningToGroup +
                '}';
    }

    public static PlayerState fromBytes(FriendlyByteBuf buf) {
        boolean disabled = buf.readBoolean();
        boolean disconnected = buf.readBoolean();
        UUID uuid = buf.readUUID();
        String name = buf.readUtf(32767);

        PlayerState state = new PlayerState(uuid, name, disabled, disconnected);

        state.setBroadcastingToGroup(buf.readBoolean());
        state.setListeningToGroup(buf.readBoolean());

        if (buf.readBoolean()) {
            state.setGroup(buf.readUUID());
        }

        return state;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(disabled);
        buf.writeBoolean(disconnected);
        buf.writeUUID(uuid);
        buf.writeUtf(name);

        buf.writeBoolean(broadcastingToGroup);
        buf.writeBoolean(listeningToGroup);

        buf.writeBoolean(hasGroup());
        if (hasGroup()) {
            buf.writeUUID(group);
        }
    }

}
