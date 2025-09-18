package dse.cli.serial;

public enum SerialType {

    B16("16bit"),
    B18("18bit");

    private final String name;

    SerialType(String stringVal) {
        name=stringVal;
    }

    public String toString(){
        return name;
    }

    public static String getEnumByString(String code) {
        for(SerialType e : SerialType.values()) {
            if(e.name.equals(code)) return e.name();
        }
        return null;
    }
}
