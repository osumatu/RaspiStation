package Interfaces;

import java.io.IOException;

public interface RaspiStationControllersInterface {

    public void initializeControls() throws IOException;

    public void leftButtonClicked();

    public void leftButtonReleased();

    public void rightButtonClicked();

    public void rightButtonReleased();

    public void joystickWentRight();

    public void joystickWentLeft();

    public void joystickWentUp();

    public void joystickWentDown();
}
