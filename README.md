# DSE Serial Reader - Example  

This repository serves as example of how to open a serial port and read measurements from a sensor.
We use the DSE Java library for taking care of low-level details, such as parsing the binary telegrams.

The flow would typically be:

- Create a SerialDistanceSensor (for ODS sensors) or SerialProfileSensor (for O2DS / Z-Line sensors)
- Open the serial port with correct baud-rate (depending on sensor and settings)
- Subscribe to the results (by implementing a [Flow.Subscriber< Measurement >](src/main/java/dse/cli/serial/DataSubscriber.java) class)
- Use the results in your business logic
- Cancel subscription and exit


## Requirements

- Java (21 or later) - [OpenJDK](https://adoptopenjdk.net/), Oracle JDK, or any other Java JDK, is required to build and run.
- The DSE Java library and the cross-platform [jSerialComm](https://fazecast.github.io/jSerialComm/) library for the serial port communication.


## Development

To build and test the application, checkout the code from this repository and use the Gradle build tool as shown:

```shell
./gradlew clean build
```

On Windows execute ```gradlew.bat clean build``` in a terminal, or use an IDE that support Gradle projects.



## Error Codes

| Code | Description                                            |
|------|--------------------------------------------------------| 
| 99   | An unknown error occurred                              |
| 6    | Too little light returned or there is no target at all |
| 5    | Too much light returned/blinding or false light        |
| 4    | False light or an undefined spot recorded              |
| 2    | A target is observed but outside the measuring range   |
| 1    | A target is observed but outside the measuring range   |
| 0    | A target is observed but outside the measuring range   |
