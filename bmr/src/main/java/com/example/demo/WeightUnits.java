package com.example.demo;

public enum WeightUnits {
    KG(1, "kg", "kilograms"),
    LBS(2.20, "lbs", "pounds");

    private final double factor;
    private final String short_name;
    private final String detailed_name;
    WeightUnits(double factor, String short_name, String detailed_name) {
        this.factor = factor;
        this.short_name = short_name;
        this.detailed_name = detailed_name;
    }

    public String getShortName() {
        return this.short_name;
    }

    public double getFactor() {
        return this.factor;
    }

    @Override
    public String toString() {
        return "(" + this.short_name + "), " + this.detailed_name;
    }
}
