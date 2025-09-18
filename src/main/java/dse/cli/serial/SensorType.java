package dse.cli.serial;

public enum SensorType {

    ODS_B16("ODS-16bit"),
    ODS_B18("ODS-18bit"),
    O2DS("O2DS");

    private final String name;

    SensorType(String stringVal) {
        name=stringVal;
    }

    public String toString(){
        return name;
    }

    public static String getEnumByString(String code) {
        for(SensorType e : SensorType.values()) {
            if(e.name.equals(code)) return e.name();
        }
        return null;
    }
}
