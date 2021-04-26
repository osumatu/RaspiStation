package Controllers;

import Interfaces.RaspiStationControllersInterface;
import com.pi4j.io.spi.SpiChannel;
import com.pi4j.io.spi.SpiDevice;
import com.pi4j.io.spi.SpiFactory;

import java.io.IOException;

public class JoystickController {
    private static SpiDevice spiDevice = null;
    public int xAxisValue = 0;
    public int yAxisValue = 0;
    public boolean isJoystickLeft = false;
    public boolean isJoystickRight = false;
    public boolean isJoystickUp = false;
    public boolean isJoystickDown = false;
    private final RaspiStationControllersInterface raspiStationGame;

    public JoystickController(RaspiStationControllersInterface raspiStationGame) throws IOException {
        // Initialize SPI Device.
        spiDevice = SpiFactory.getInstance(SpiChannel.CS0,
                SpiDevice.DEFAULT_SPI_SPEED, // default spi speed 1 MHz
                SpiDevice.DEFAULT_SPI_MODE); // default spi mode 0k
        this.raspiStationGame = raspiStationGame;
    }

    public void startJoystick() {
        JoystickThread joystickThread = new JoystickThread();
        joystickThread.start();
    }

    private class JoystickThread extends Thread {
        public void run() {
            while (true) {
                try {
                    read(1); // Read channel 1 (X-Axis)
                    read(2); // Read channel 2 (Y-Axis)
                    setPositions();
                    Thread.sleep(100);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void read(int channel) throws IOException {
            // 10-bit ADC MCP3008
            byte[] packet = new byte[3];
            packet[0] = 0x01;  // INIT_CMD;  // address byte
            packet[1] = (byte) ((0x08 + channel) << 4);  // singleEnded + channel

            byte[] result = spiDevice.write(packet);
            if (channel == 1)
                xAxisValue = ((result[1] & 0x03) << 8) | (result[2] & 0xff);
            else
                yAxisValue = ((result[1] & 0x03) << 8) | (result[2] & 0xff);
        }

        private void setPositions() {
            // Keep old values.
            boolean _isJoystickLeft = isJoystickLeft;
            boolean _isJoystickRight = isJoystickRight;
            boolean _isJoystickUp = isJoystickUp;
            boolean _isJoystickDown = isJoystickDown;

            // Set new values.
            isJoystickLeft = xAxisValue < 200;
            isJoystickRight = xAxisValue > 800;
            isJoystickDown = yAxisValue > 800;
            isJoystickUp = yAxisValue < 200;

            if (isJoystickLeft && !_isJoystickLeft)
                raspiStationGame.joystickWentLeft();
            if (isJoystickRight && !_isJoystickRight)
                raspiStationGame.joystickWentRight();
            if (isJoystickUp && !_isJoystickUp)
                raspiStationGame.joystickWentUp();
            if (isJoystickDown && !_isJoystickDown)
                raspiStationGame.joystickWentDown();
        }
    }
}

