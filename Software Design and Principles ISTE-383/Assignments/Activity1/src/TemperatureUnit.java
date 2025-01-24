public enum TemperatureUnit {
    KELVIN(0),
    CELSIUS(-27315); // declare them
    
    private int conversionFactor;

    TemperatureUnit (int conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double get(int reading) {
        return (reading + conversionFactor) / 100.00;
    }
    
}
