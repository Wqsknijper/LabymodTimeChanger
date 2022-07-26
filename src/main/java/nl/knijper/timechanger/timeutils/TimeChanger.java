package nl.knijper.timechanger.timeutils;

import net.labymod.api.event.events.client.TickEvent;
import net.labymod.main.LabyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SUpdateTimePacket;
import nl.knijper.timechanger.Main;
import nl.knijper.timechanger.listeners.TimeDropdownMenu;

public class TimeChanger {
    
    Main main;
    Minecraft mc;
    public TimeChanger(Main main) {
        this.main = main;
        this.mc = main.getMinecraft();

        currentTime = TimeDropdownMenu.EnumModuleTime.SERVER_TIME;
    }
    
    static TimeDropdownMenu.EnumModuleTime currentTime;
    SUpdateTimePacket lastServerPacket;
    Long customSliderTime;

    public static TimeDropdownMenu.EnumModuleTime getTime() {
        return currentTime;
    }

    public void changeCurrentTime(TimeDropdownMenu.EnumModuleTime newTime) {
        currentTime = newTime;
        if (currentTime == TimeDropdownMenu.EnumModuleTime.CUSTOM) {
            currentTime.time = customSliderTime;
            updateServerTime(customSliderTime);
        }
        else if (currentTime == TimeDropdownMenu.EnumModuleTime.SERVER_TIME && lastServerPacket != null)
            updateServerTime(lastServerPacket.getWorldTime());
        else
            updateServerTime(currentTime.time);
    }

    public void updateLastServerTime(SUpdateTimePacket lastServerTime) {
        this.lastServerPacket = lastServerTime;
    }

    public void updateServerTime(Long dayTime) {
        if (mc.world != null) {
            mc.world.setDayTime(dayTime);
            mc.world.func_239134_a_(lastServerPacket.getTotalWorldTime());
        }
    }

    public Long getCurrentTime() {
        return currentTime.time;
    }

    public void updateSliderTime(Long time) {
        this.customSliderTime = time;
    }

    public SUpdateTimePacket getLastServerPacket() {
        return this.lastServerPacket;
    }

}
