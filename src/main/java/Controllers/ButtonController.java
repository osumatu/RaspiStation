package Controllers;

import Interfaces.RaspiStationControllersInterface;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class ButtonController {
    GpioPinDigitalInput leftButton;
    GpioPinDigitalInput rightButton;
    RaspiStationControllersInterface raspiStationGame;

    public void startButton() {
        leftButton.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().equals(PinState.LOW)) raspiStationGame.leftButtonClicked();
            else raspiStationGame.leftButtonReleased();
        });
        rightButton.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().equals(PinState.LOW)) raspiStationGame.rightButtonClicked();
            else raspiStationGame.rightButtonReleased();
        });
    }

    public ButtonController(RaspiStationControllersInterface raspiStationGame) {
        GpioController gpioController = GpioFactory.getInstance();
        leftButton = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_25, PinPullResistance.PULL_DOWN);
        rightButton = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_28, PinPullResistance.PULL_DOWN);
        leftButton.setShutdownOptions(true);
        rightButton.setShutdownOptions(true);
        this.raspiStationGame = raspiStationGame;
    }

}
