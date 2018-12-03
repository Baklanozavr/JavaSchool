package ru.academits.baklanov.tasks;

final class TemperatureConverter {
    public enum Scale {
        CELSIUS("шкала Цельсия", 1, 0),
        KELVIN("шкала Кельвина", 1, 273.15),
        FAHRENHEIT("шкала Фаренгейта", 1.8, 32);

        private final String name;
        private final double fromCelsiusMultiplier;
        private final double fromCelsiusDelta;

        Scale(String name, double fromCelsiusMultiplier, double fromCelsiusDelta) {
            this.name = name;
            this.fromCelsiusMultiplier = fromCelsiusMultiplier;
            this.fromCelsiusDelta = fromCelsiusDelta;
        }

        public String getNameString() {
            return name;
        }

        public double convertToCelsius(double temperature) {
            return (temperature - fromCelsiusDelta) / fromCelsiusMultiplier;
        }

        public double convertFromCelsius(double temperature) {
            return temperature * fromCelsiusMultiplier + fromCelsiusDelta;
        }
    }

    static double convert(double degrees, Scale from, Scale to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Не выбраны шкалы для перевода!");
        }

        if (from == to) {
            return degrees;
        }

        double temperatureCelsius = from.convertToCelsius(degrees);

        if (temperatureCelsius < -Scale.KELVIN.fromCelsiusDelta) {
            throw new IllegalArgumentException("Такой температуры не бывает!");
        }

        return to.convertFromCelsius(temperatureCelsius);
    }
}