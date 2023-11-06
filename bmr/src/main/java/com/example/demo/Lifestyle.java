package com.example.demo;

public enum Lifestyle {
    SEDENTARY(1.2, "Sedentary"),
    LOW_ACTIVE(1.375, "Low active"),
    ACTIVE(1.55, "Active"),
    HIGH_ACTIVE(1.725, "Very active"),
    ULTRA_ACTIVE(1.9, "Extremely active");

    private final double factor;
    private final String name;
    Lifestyle(double factor, String name) {
        this.factor = factor;
        this.name = name;
    }

    public double getFactor() {
        return this.factor;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
