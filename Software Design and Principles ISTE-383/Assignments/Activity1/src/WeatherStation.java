
/**
 * Class for a simple computer based weather station that reports the current
 * temperature (in Celsius) every second. The station is attached to a sensor
 * that reports the temperature as a 16-bit number (0 to 65535) representing the
 * Kelvin temperature to the nearest 1/100th of a degree.
 *
 * This class is implements Runnable so that it can be embedded in a Thread
 * which runs the periodic sensing.
 *
 * @author Karlo Siric
 * @author Kristina Marasovic
 * @version 1
 */
public class WeatherStation implements Runnable {

    private int reading; // actual sensor reading.

    private final TemperatureSensor sensor; // Temperature sensor.

    private static final long PERIOD = 1000;      // 1 sec = 1000 ms.

    /*
     * When a WeatherStation object is created, it in turn creates the sensor
     * object it will use.
     */
    public WeatherStation() {
        sensor = new TemperatureSensor();
    }
    
    
    /*
     * The "run" method called by the enclosing Thread object when started.
     * Repeatedly sleeps a second, acquires the current temperature from
     * its sensor, and reports this as a formatted output string.
     */
    public void run() {
        // int reading;           // actual sensor reading. - this is now a class variable so that we can use it in the while loop
        double celsius = 0;        // sensor reading transformed to celsius
        double kelvin = 0;
        // final int KTOC = -27315;   // Convert raw Kelvin reading to Celsius, not point in using it
        // it is not being used by anyone so.
        
        while (true) {
            
            reading = sensor.read();
            try { Thread.sleep(PERIOD); } catch (Exception e) {
            }    // ignore exceptions
            
            // celsius = (reading + KTOC) / 100.0;
            //kelvin = reading / 100.0;
            // this is because a weather station is violating the principle of single responsibility principle SRP "A class should have only one reason to change" that means it should have a single purpose
            // and that is to report the temperature and not to convert it to celsius and kelvin. So we should move this to the TemperatureSensor class.

            // we can have a get method:

            // celsius = TemperatureUnit.CELSIUS.get(reading); // can put these inside of a println that can improve the code.
            // kelvin = TemperatureUnit.KELVIN.get(reading);

            for (TemperatureUnit temps : TemperatureUnit.values()) {
                double temp = temps.get(reading);
                if (temps == TemperatureUnit.CELSIUS) {
                    celsius = temp;
                } else if (temps == TemperatureUnit.KELVIN) {
                    kelvin = temp;
                }
            }    
            /*
             * System.out.printf prints formatted data on the output screen.
             *
             * Most characters print as themselves.
             *
             * % introduces a format command that usually applies to the
             * next argument of printf:
             *   *  %6.2f formats the "celsius" (2nd) argument in a field
             *      at least 6 characters wide with 2 fractional digits.
             *   *  The %n at the end of the string forces a new line
             *      of output.
             *   *  %% represents a literal percent character.
             *
             * See docs.oracle.com/javase/tutorial/java/data/numberformat.html
             * for more information on formatting output.
             */
            System.out.printf("Reading is %6.2f degrees C and %6.2f degrees K%n", celsius, kelvin);
        }
    }
    /*
     * Initial main method.
     *      Create the WeatherStation (Runnable).
     *      Embed the WeatherStation in a Thread.
     *      Start the Thread.
     */
    public static void main(String[] args) {
        WeatherStation ws = new WeatherStation();
        Thread thread = new Thread(ws);
        thread.start();
    }
}
