package nl.knijper.timechanger.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.server.SUpdateTimePacket;
import nl.knijper.timechanger.Main;
import nl.knijper.timechanger.listeners.TimeDropdownMenu;
import nl.knijper.timechanger.timeutils.TimeChanger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayNetHandler.class)
public class TimeUpdater {

    Main main = Main.getInstance();

    @Shadow private Minecraft client;

    /**
     * @author Wqsknijper
     * @reason To make sure that when the player has the Time Changer addon enabled, it doesn't change time because of a server packet.
     */
    @Overwrite
    public void handleTimeUpdate(SUpdateTimePacket packetIn) {
        if (client.getConnection() != null)
            PacketThreadUtil.checkThreadAndEnqueue(packetIn, client.getConnection(), this.client);
        main.getTimeChanger().updateLastServerTime(packetIn);
        if (this.client.world != null && TimeChanger.getTime() != TimeDropdownMenu.EnumModuleTime.DEFAULT)
            main.getTimeChanger().updateServerTime(main.getTimeChanger().getCurrentTime());
        else
            main.getTimeChanger().updateServerTime(packetIn.getWorldTime());
    }

}

