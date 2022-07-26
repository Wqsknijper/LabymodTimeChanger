package nl.knijper.timechanger;

import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.DropDownElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.settings.elements.SliderElement;
import net.minecraft.client.Minecraft;
import nl.knijper.timechanger.listeners.CustomTimeSlider;
import nl.knijper.timechanger.listeners.TimeDropdownMenu;
import nl.knijper.timechanger.timeutils.TimeChanger;

import java.util.List;

public class Main extends LabyModAddon {

  private TimeChanger timeChanger;
  private Minecraft minecraft;
  private static Main instance;

  private TimeDropdownMenu.EnumModuleTime timeChoice = TimeDropdownMenu.EnumModuleTime.DEFAULT;
  private Long sliderChoice = 0L;

  @Override
  public void onEnable() {
      instance = this;
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft != null)
          this.minecraft = minecraft;

      timeChanger = new TimeChanger(this);
  }

  @Override
  public void loadConfig() {
      this.timeChoice = TimeDropdownMenu.EnumModuleTime.valueOf(getConfig().has("timeChoice" ) ? getConfig().get("timeChoice" ).getAsString() : "DEFAULT"); // <- default value 'true'
      getConfig().addProperty("timeChoice", this.timeChoice.toString());
      this.sliderChoice = getConfig().has( "sliderChoice" ) ? getConfig().get("sliderChoice").getAsLong() : 0L; // <- default value '5'
      getConfig().addProperty("sliderChoice", this.sliderChoice);
  }

  @Override
  protected void fillSettings(List<SettingsElement> list) {
      list.add(new TimeDropdownMenu(this).create(timeChoice));
      list.add(new CustomTimeSlider(this).create(sliderChoice));
  }

  public TimeChanger getTimeChanger() {
    return timeChanger;
  }

  public Minecraft getMinecraft() {
      return this.minecraft;
  }

  public static Main getInstance() {
      return instance;
  }
}
