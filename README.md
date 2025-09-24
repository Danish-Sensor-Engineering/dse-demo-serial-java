# DSE Serial Reader - Example  

This repository serves as example of how to read measurements from a RS232 or RS422 connected serial sensor.

The flow would typically be:

- Instantiate a *SerialDistanceSensor* (for ODS) or *SerialProfileSensor* (for O2DS / Z-Line) (with the correct serial port and baud-rate)
- Set the correct TelegramHandler (eg. 16bit or 18bit), in case of *SerialDistanceSensor*
- Subscribe to the results (by implementing a [Flow.Subscriber< Measurement >](src/main/java/dse/cli/serial/DataSubscriber.java) class)
- Use the results in your business logic
- Cancel subscription and exit


## Requirements

Java (21 or later) - [OpenJDK](https://adoptopenjdk.net/), Oracle JDK, or any other Java JDK, is required to build and run.

Our library [libdse-java](lib/README.md) uses the cross-platform [jSerialComm](https://fazecast.github.io/jSerialComm/) library for serial port communication, which must also be included in your project.


## Notes

Our product manuals describes the low-level sensor protocol, making it possible to implement the communication in other programming languages. We are ready to support you within the limits of our knowledge of the programming language you use.


## Development

To build and test the application, checkout the code from this repository and use the Gradle build tool as shown:

```shell
./gradlew clean build
```

On Windows execute ```gradlew.bat``` in a terminal, or use an IDE that support Gradle projects.



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
