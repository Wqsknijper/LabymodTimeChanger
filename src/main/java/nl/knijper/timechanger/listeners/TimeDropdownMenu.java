package nl.knijper.timechanger.listeners;

import net.labymod.gui.elements.DropDownMenu;
import net.labymod.settings.elements.DropDownElement;
import net.labymod.utils.Consumer;
import nl.knijper.timechanger.Main;

public class TimeDropdownMenu {

    Main main;
    public TimeDropdownMenu(Main main) {
        this.main = main;
    }

    private final DropDownMenu<EnumModuleTime> timeDropDownMenu = new DropDownMenu<EnumModuleTime>("Time", 0, 0, 0, 0)
            .fill(EnumModuleTime.values());
    private final DropDownElement<EnumModuleTime> timeDropDown = new DropDownElement<>("Select Time", timeDropDownMenu);

    public Consumer<EnumModuleTime> changeListener() {
        return enumModuleTime -> {
            main.getTimeChanger().changeCurrentTime(enumModuleTime);
            main.getConfig().addProperty("timeChoice", enumModuleTime.toString());
        };
    }

    public DropDownElement<EnumModuleTime> create(EnumModuleTime elementChoice) {
        timeDropDownMenu.setSelected(elementChoice);
        timeDropDown.setChangeListener(changeListener());

        main.getTimeChanger().changeCurrentTime(elementChoice);

        return timeDropDown;
    }

    public enum EnumModuleTime {
        DEFAULT(null),
        DAY(1000L),
        SUNSET(13000L),
        NIGHT(18000L),
        CUSTOM(null);

        public Long time;
        EnumModuleTime(Long time) {
            this.time = time;
        }
    }
}
