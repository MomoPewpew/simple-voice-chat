package de.maxhenkel.voicechat.voice.client;

import de.maxhenkel.voicechat.VoicechatClient;
import de.maxhenkel.voicechat.gui.VoiceChatScreen;
import de.maxhenkel.voicechat.gui.VoiceChatSettingsScreen;
import de.maxhenkel.voicechat.gui.group.GroupScreen;
import de.maxhenkel.voicechat.gui.group.JoinGroupScreen;
import de.maxhenkel.voicechat.gui.volume.AdjustVolumesScreen;
import de.maxhenkel.voicechat.intercompatibility.ClientCompatibilityManager;
import de.maxhenkel.voicechat.voice.common.ClientGroup;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TextComponentTranslation;
import org.lwjgl.input.Keyboard;

public class KeyEvents {

    private final Minecraft minecraft;

    public static KeyBinding KEY_PTT;
    public static KeyBinding KEY_WHISPER;
    public static KeyBinding KEY_MUTE;
    public static KeyBinding KEY_DISABLE;
    public static KeyBinding KEY_HIDE_ICONS;
    public static KeyBinding KEY_VOICE_CHAT;
    public static KeyBinding KEY_VOICE_CHAT_SETTINGS;
    public static KeyBinding KEY_GROUP;
    public static KeyBinding KEY_TOGGLE_RECORDING;
    public static KeyBinding KEY_ADJUST_VOLUMES;

    public KeyEvents() {
        minecraft = Minecraft.getMinecraft();
        ClientCompatibilityManager.INSTANCE.onHandleKeyBinds(this::handleKeybinds);

        KEY_PTT = ClientCompatibilityManager.INSTANCE.registerKeyBinding(new KeyBinding("key.push_to_talk", Keyboard.KEY_CAPITAL, "key.categories.voicechat"));
        KEY_WHISPER = ClientCompatibilityManager.INSTANCE.registerKeyBinding(new KeyBinding("key.whisper", Keyboard.KEY_NONE, "key.categories.voicechat"));
        KEY_MUTE = ClientCompatibilityManager.INSTANCE.registerKeyBinding(new KeyBinding("key.mute_microphone", Keyboard.KEY_M, "key.categories.voicechat"));
        KEY_DISABLE = ClientCompatibilityManager.INSTANCE.registerKeyBinding(new KeyBinding("key.disable_voice_chat", Keyboard.KEY_N, "key.categories.voicechat"));
        KEY_HIDE_ICONS = ClientCompatibilityManager.INSTANCE.registerKeyBinding(new KeyBinding("key.hide_icons", Keyboard.KEY_H, "key.categories.voicechat"));
        KEY_VOICE_CHAT = ClientCompatibilityManager.INSTANCE.registerKeyBinding(new KeyBinding("key.voice_chat", Keyboard.KEY_V, "key.categories.voicechat"));
        KEY_VOICE_CHAT_SETTINGS = ClientCompatibilityManager.INSTANCE.registerKeyBinding(new KeyBinding("key.voice_chat_settings", Keyboard.KEY_NONE, "key.categories.voicechat"));
        KEY_GROUP = ClientCompatibilityManager.INSTANCE.registerKeyBinding(new KeyBinding("key.voice_chat_group", Keyboard.KEY_G, "key.categories.voicechat"));
        KEY_TOGGLE_RECORDING = ClientCompatibilityManager.INSTANCE.registerKeyBinding(new KeyBinding("key.voice_chat_toggle_recording", Keyboard.KEY_NONE, "key.categories.voicechat"));
        KEY_ADJUST_VOLUMES = ClientCompatibilityManager.INSTANCE.registerKeyBinding(new KeyBinding("key.voice_chat_adjust_volumes", Keyboard.KEY_NONE, "key.categories.voicechat"));
    }

    private void handleKeybinds() {
        ClientVoicechat client = ClientManager.getClient();
        ClientPlayerStateManager playerStateManager = ClientManager.getPlayerStateManager();
        if (KEY_VOICE_CHAT.isPressed()) {
            minecraft.displayGuiScreen(new VoiceChatScreen());
        }

        if (KEY_GROUP.isPressed()) {
            if (client != null && client.getConnection() != null && client.getConnection().getData().groupsEnabled()) {
                ClientGroup group = playerStateManager.getGroup();
                if (group != null) {
                    minecraft.displayGuiScreen(new GroupScreen(group));
                } else {
                    minecraft.displayGuiScreen(new JoinGroupScreen());
                }
            } else {
                minecraft.player.sendStatusMessage(new TextComponentTranslation("message.voicechat.groups_disabled"), true);
            }
        }

        if (KEY_VOICE_CHAT_SETTINGS.isPressed()) {
            minecraft.displayGuiScreen(new VoiceChatSettingsScreen());
        }

        if (KEY_ADJUST_VOLUMES.isPressed()) {
            minecraft.displayGuiScreen(new AdjustVolumesScreen());
        }

        if (KEY_PTT.isPressed()) {
            checkConnected();
        }

        if (KEY_WHISPER.isPressed()) {
            checkConnected();
        }

        if (KEY_MUTE.isPressed()) {
            playerStateManager.setMuted(!playerStateManager.isMuted());
        }

        if (KEY_DISABLE.isPressed()) {
            playerStateManager.setDisabled(!playerStateManager.isDisabled());
        }

        if (KEY_TOGGLE_RECORDING.isPressed() && client != null) {
            ClientManager.getClient().toggleRecording();
        }

        if (KEY_HIDE_ICONS.isPressed()) {
            boolean hidden = !VoicechatClient.CLIENT_CONFIG.hideIcons.get();
            VoicechatClient.CLIENT_CONFIG.hideIcons.set(hidden).save();

            if (hidden) {
                minecraft.player.sendStatusMessage(new TextComponentTranslation("message.voicechat.icons_hidden"), true);
            } else {
                minecraft.player.sendStatusMessage(new TextComponentTranslation("message.voicechat.icons_visible"), true);
            }
        }
    }

    private boolean checkConnected() {
        if (ClientManager.getClient() == null || ClientManager.getClient().getConnection() == null || !ClientManager.getClient().getConnection().isInitialized()) {
            sendUnavailableMessage();
            return false;
        }
        return true;
    }

    private void sendUnavailableMessage() {
        minecraft.player.sendStatusMessage(new TextComponentTranslation("message.voicechat.voice_chat_not_connected"), true);
    }

}
