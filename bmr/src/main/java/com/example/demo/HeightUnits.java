package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public enum HeightUnits {
    CM(1, "cm", "centimeters"),
    M(0.01, "m", "meters"),;

    private final double factor;
    private final String short_name;
    private final String detailed_name;

    HeightUnits(double factor, String short_name, String detailed_name) {
        this.factor = factor;
        this.short_name = short_name;
        this.detailed_name = detailed_name;
    }

    public String getShortName() {
        return this.short_name;
    }

    public String getDetailedName() {
        return this.detailed_name;
    }

    public double getFactor() {
        return this.factor;
    }

    @Override
    public String toString() {
        return "(" + this.short_name + "), " + this.detailed_name;
    }
}
