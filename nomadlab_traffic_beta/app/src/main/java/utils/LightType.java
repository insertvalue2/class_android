package utils;



public enum LightType {
    RedOn("1"),
    YellowOn("2"),
    GreenOn("3"),
    RedOff("4"),
    YellowOff("5"),
    GreenOff("6"),
    AllOff("8"),
    AllOn("7");

    private String value;

    LightType(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
