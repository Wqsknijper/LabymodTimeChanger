package nl.knijper.timechanger.listeners;

import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SliderElement;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import nl.knijper.timechanger.Main;
import nl.knijper.timechanger.timeutils.TimeChanger;

public class CustomTimeSlider {

    Main main;
    public CustomTimeSlider(Main main) {
        this.main = main;
    }

    public SliderElement create(Long elementChoice) {
        SliderElement scalingSliderElement = new SliderElement("Custom Time", new ControlElement.IconData(Material.CLOCK), elementChoice.intValue());

        scalingSliderElement.setRange(0, 24000);
        scalingSliderElement.setSteps(1000);

        main.getTimeChanger().updateSliderTime(elementChoice);
        main.getTimeChanger().changeCurrentTime(TimeChanger.getTime());

        scalingSliderElement.addCallback(accepted -> {
            main.getTimeChanger().updateSliderTime((long) accepted);
            main.getTimeChanger().changeCurrentTime(TimeChanger.getTime());
            main.getConfig().addProperty("sliderChoice", accepted);
        });

        return scalingSliderElement;
    }
}
