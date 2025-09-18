package dse.cli.serial;

import dse.lib.*;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.ArrayList;
import java.util.concurrent.Callable;

@Command(name = "dse-cli-serial", mixinStandardHelpOptions = true, versionProvider = VersionProvider.class)
public class Application implements Callable<Integer> {


    @CommandLine.Parameters(index = "0", arity = "0..1", defaultValue = "ttyUSB0")
    String serialPort;

    @CommandLine.Parameters(index = "1", defaultValue = "38400", description = "Valid values: 38400, 115200")
    Integer serialBaudRate;

    @CommandLine.Parameters(index = "2", defaultValue = "ODS-16bit", description = "Valid values: ODS-16bit, ODS-18bit, O2DS")
    SensorType serialTelegramType;

    @CommandLine.Parameters(hidden = true)  // "hidden": don't show this parameter in usage help message
    ArrayList<String> allParameters; // no "index" attribute: captures _all_ arguments


    public static void main(String... args) {
        int exitCode = new CommandLine(new Application()).execute(args);
        System.exit(exitCode);
    }


    @Override
    public Integer call() {

        SerialSensor serialSensor = null;
        switch (serialTelegramType) {
            case ODS_B16 -> {
                serialSensor = new SerialDistanceSensor();
                serialSensor.setTelegramHandler(new TelegramHandler16Bit());
            }
            case ODS_B18 -> {
                serialSensor = new SerialDistanceSensor();
                serialSensor.setTelegramHandler(new TelegramHandler18Bit());
            }
            case O2DS -> serialSensor = new SerialProfileSensor();
        }


        if(serialSensor != null && !serialSensor.openPort(serialPort, serialBaudRate)) {
            System.err.println("Error opening serial port: " + serialPort);
            SerialSensor.printSerialPorts();
            return -1;
        }


        System.out.printf("Port: %s, Baud: %s, Type: %s %n", serialPort, serialBaudRate, serialTelegramType);




        return 0;
    }

}
