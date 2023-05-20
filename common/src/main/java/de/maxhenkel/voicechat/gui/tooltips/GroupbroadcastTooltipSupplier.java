package de.maxhenkel.voicechat.gui.tooltips;

import de.maxhenkel.voicechat.gui.widgets.ImageButton;
import de.maxhenkel.voicechat.voice.client.ClientPlayerStateManager;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.ArrayList;
import java.util.List;

public class GroupbroadcastTooltipSupplier implements ImageButton.TooltipSupplier {

    private GuiScreen screen;
    private ClientPlayerStateManager stateManager;

    public GroupbroadcastTooltipSupplier(GuiScreen screen, ClientPlayerStateManager stateManager) {
        this.screen = screen;
        this.stateManager = stateManager;
    }

    @Override
    public void onTooltip(ImageButton button, int mouseX, int mouseY) {
        List<String> tooltip = new ArrayList<>();

        if (stateManager.isBroadcastingToGroups()) {
            tooltip.add(new TextComponentTranslation("message.voicechat.groupbroadcast.enabled").getUnformattedComponentText());
        } else {
            tooltip.add(new TextComponentTranslation("message.voicechat.groupbroadcast.disabled").getUnformattedComponentText());
        }

        screen.drawHoveringText(tooltip, mouseX, mouseY);
    }
}
