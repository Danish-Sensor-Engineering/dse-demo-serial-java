package dse.cli.serial;

public enum SerialBaud {

    BAUD_38400("38400"),
    BAUD_115200("115200");

    private final String name;

    SerialBaud(String stringVal) {
        name=stringVal;
    }

    public String toString(){
        return name;
    }

    public static String getEnumByString(String code) {
        for(SerialBaud e : SerialBaud.values()) {
            if(e.name.equals(code)) return e.name();
        }
        return null;
    }
}
