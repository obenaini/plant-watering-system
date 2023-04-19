import java.util.TimerTask;
import org.firmata4j.Pin;
import org.firmata4j.ssd1306.SSD1306;
import java.io.IOException;

public class Main extends TimerTask{
    private final Pin sensor;
    private final SSD1306 theOledObject;
    private final Pin myPump;
    private final int sensorValue = 600;
    private final int off = 0;
    private final int on = 1;

    Main(Pin sensor, Pin myPump, SSD1306 theOledObject){
        this.sensor = sensor;
        this.myPump = myPump;
        this.theOledObject = theOledObject;
    }
    @Override
    public void run() {
        String value = String.valueOf(sensor.getValue());
        theOledObject.getCanvas().clear();
        theOledObject.getCanvas().drawString(0,0,value);
        theOledObject.display();
        try {
            if(sensor.getValue() > sensorValue){
                myPump.setValue(on);
                System.out.println("Watering the plant!");
            }
            else{
                myPump.setValue(off);
                System.out.println("Plant has been watered!");
            }
        } catch (Exception ex) {
            System.out.println("Oops, something went wrong!");
        }

    }
}