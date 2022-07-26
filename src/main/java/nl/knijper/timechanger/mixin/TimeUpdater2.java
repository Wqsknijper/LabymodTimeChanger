package nl.knijper.timechanger.mixin;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.profiler.IProfiler;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.DimensionType;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.storage.ISpawnWorldInfo;
import net.minecraft.world.storage.IWorldInfo;
import nl.knijper.timechanger.Main;
import nl.knijper.timechanger.listeners.TimeDropdownMenu;
import nl.knijper.timechanger.timeutils.TimeChanger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Supplier;

@Mixin(ClientWorld.class)
public abstract class TimeUpdater2 {

    @Shadow public abstract void func_239134_a_(long p_239134_1_);
    @Shadow public abstract void setDayTime(long time);

    @Shadow public abstract ClientWorld.ClientWorldInfo getWorldInfo();

    /**
     * @author Wqsknijper
     * @reason Without this overwrite, the client would still update the daytime with +1, for the time changer addon itself to then get it back to normal.
     */

    @Overwrite
    private void func_239141_x_() {
        this.func_239134_a_(getWorldInfo().getGameTime() + 1L);
        if (getWorldInfo().getGameRulesInstance().getBoolean(GameRules.DO_DAYLIGHT_CYCLE) && TimeChanger.getTime() == TimeDropdownMenu.EnumModuleTime.DEFAULT) {
            this.setDayTime(getWorldInfo().getDayTime() + 1L);
        }
    }

}
