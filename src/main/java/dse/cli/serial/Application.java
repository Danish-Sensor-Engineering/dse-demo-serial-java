package dse.cli.serial;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.Serial;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.concurrent.Callable;

@Command(name = "dse-cli-serial", mixinStandardHelpOptions = true, versionProvider = VersionProvider.class)
public class Application implements Callable<Integer> {


    @CommandLine.Parameters(index = "0")
    String port;

    @CommandLine.Parameters(index = "1", defaultValue = "38400", description = "Valid values: ${COMPLETION-CANDIDATES}")
    SerialBaud baudrate;

    @CommandLine.Parameters(index = "2", defaultValue = "16bit", description = "Valid values: ${COMPLETION-CANDIDATES}")
    SerialType type;

    @CommandLine.Parameters(hidden = true)  // "hidden": don't show this parameter in usage help message
    ArrayList<String> allParameters; // no "index" attribute: captures _all_ arguments


    public static void main(String... args) {
        int exitCode = new CommandLine(new Application()).execute(args);
        System.exit(exitCode);
    }


    @Override
    public Integer call() {

        return 0;
    }

}
