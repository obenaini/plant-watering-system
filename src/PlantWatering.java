import org.firmata4j.firmata.FirmataDevice;
import org.firmata4j.Pin;
import org.firmata4j.IODevice;
import org.firmata4j.I2CDevice;
import org.firmata4j.ssd1306.SSD1306;
import java.io.IOException;
import java.util.Timer;
public class PlantWatering {
    public static void main(String[] args) throws IOException, InterruptedException {
        var myPort = "COM3";
        IODevice myBoard = new FirmataDevice(myPort);
        myBoard.start();
        myBoard.ensureInitializationIsDone();
        Pin sensor = myBoard.getPin(15);
        Pin myPump = myBoard.getPin(2);
        sensor.setMode(Pin.Mode.ANALOG);
        myPump.setMode(Pin.Mode.OUTPUT);

        I2CDevice i2cObject = myBoard.getI2CDevice((byte) 0x3C);
        SSD1306 theOledObject = new SSD1306(i2cObject, SSD1306.Size.SSD1306_128_64);
        theOledObject.init();

        var timerTask = new Main(sensor, myPump, theOledObject);
        Timer timerObject = new Timer();
        timerObject.schedule(timerTask ,0, 1000);
    }
}