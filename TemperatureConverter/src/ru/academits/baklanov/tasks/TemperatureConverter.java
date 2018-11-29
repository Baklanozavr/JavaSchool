package ru.academits.baklanov.tasks;

public final class TemperatureConverter {
    public enum Scale {
        CELSIUS, KELVIN, FAHRENHEIT
    }

    public static double convert(double degrees, Scale from, Scale to) {
        final double CELSIUS_TO_KELVIN_DELTA = 273.15;
        final double CELSIUS_TO_FAHRENHEIT_DELTA = 32;
        final double CELSIUS_TO_FAHRENHEIT_MULTIPLIER = 1.8;

        double temperatureCelsius;

        if (from == Scale.KELVIN) {
            temperatureCelsius = degrees - CELSIUS_TO_KELVIN_DELTA;

            if (to == Scale.FAHRENHEIT) {
                return temperatureCelsius * CELSIUS_TO_FAHRENHEIT_MULTIPLIER + CELSIUS_TO_FAHRENHEIT_DELTA;
            }
            return temperatureCelsius;
        }

        if (from == Scale.FAHRENHEIT) {
            temperatureCelsius = (degrees - CELSIUS_TO_FAHRENHEIT_DELTA) / CELSIUS_TO_FAHRENHEIT_MULTIPLIER;

            if (to == Scale.KELVIN) {
                return (degrees - CELSIUS_TO_FAHRENHEIT_DELTA) / CELSIUS_TO_FAHRENHEIT_MULTIPLIER + CELSIUS_TO_KELVIN_DELTA;
            }
            return temperatureCelsius;
        }

        if (to == Scale.FAHRENHEIT) {
            return degrees * CELSIUS_TO_FAHRENHEIT_MULTIPLIER + CELSIUS_TO_FAHRENHEIT_DELTA;
        }

        return degrees + CELSIUS_TO_KELVIN_DELTA;
    }
}