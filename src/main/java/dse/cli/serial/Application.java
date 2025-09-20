package dse.cli.serial;

import dse.lib.*;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import java.util.concurrent.Callable;


@Command(name = "dse-cli-serial", mixinStandardHelpOptions = true, versionProvider = VersionProvider.class)
public class Application implements Callable<Integer> {


    @CommandLine.Parameters(index = "0", arity = "0..1", defaultValue = "ttyUSB0")
    String serialPort;

    @CommandLine.Parameters(index = "1", defaultValue = "38400", description = "Valid values: 38400, 115200")
    Integer serialBaudRate;

    @CommandLine.Parameters(index = "2", defaultValue = "ODS-16bit", description = "Valid values: ODS-16bit, ODS-18bit, O2DS")
    SensorType serialTelegramType;


    public static void main(String... args) {
        int exitCode = new CommandLine(new Application()).execute(args);
        System.exit(exitCode);
    }


    @Override
    public Integer call() throws InterruptedException {

        // Setup the sensor based on the type
        SerialSensor serialSensor = getSerialSensor();
        if(serialSensor == null) {
            System.err.println("Error creating serial sensor");
            return -1;
        }

        // Try to open the serial port or print the available ports and exit
        if(!serialSensor.openPort(serialPort, serialBaudRate)) {
            System.err.println("Error opening serial port: " + serialPort);
            SerialSensor.printSerialPorts();
            return -1;
        }

        System.out.printf("Port: %s, Baud: %s, Type: %s %n", serialPort, serialBaudRate, serialTelegramType);

        // Setup our data subscriber and processor
        DataSubscriber dataSubscriber = new DataSubscriber();

        // Start reading from the serial port
        serialSensor.start();

        // Subscribe to the data read from the serial port
        serialSensor.subscribe(dataSubscriber);

        // Sleep some time to get some measurements
        Thread.sleep(5000);

        // Unsubscribe to measurement data
        dataSubscriber.cancel();

        return 0;
    }


    // Based in the arguments, create a SerialDistanceSensor or SerialProfileSensor
    private SerialSensor getSerialSensor() {
        SerialSensor serialSensor = null;
        switch (serialTelegramType) {
            case ODS_B16 -> {
                // 16Bit telegram handler for 16Bit ODS
                serialSensor = new SerialDistanceSensor();
                serialSensor.setTelegramHandler(new TelegramHandler16Bit());
                break;
            }
            case ODS_B18 -> {
                // 18Bit telegram handler for 18Bit ODS
                serialSensor = new SerialDistanceSensor();
                serialSensor.setTelegramHandler(new TelegramHandler18Bit());
                break;
            }
            case O2DS -> serialSensor = new SerialProfileSensor();
        }
        return serialSensor;
    }

}
