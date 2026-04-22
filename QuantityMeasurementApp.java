public class QuantityMeasurementApp {

    // ---------------- LENGTH UNITS ----------------
    public enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),          // 1 inch = 1/12 foot
        YARD(3.0),                 // 1 yard = 3 feet
        CENTIMETER(0.0328084);     // 1 cm = 0.0328084 feet

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double convertToBaseUnit(double value) {
            return value * conversionFactor; // to feet
        }

        public double convertFromBaseUnit(double baseValue) {
            return baseValue / conversionFactor; // from feet
        }
    }

    public static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite");
            this.value = value;
            this.unit = unit;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || this.getClass() != obj.getClass()) return false;
            QuantityLength other = (QuantityLength) obj;
            double thisInFeet = unit.convertToBaseUnit(this.value);
            double otherInFeet = other.unit.convertToBaseUnit(other.value);
            return Double.compare(thisInFeet, otherInFeet) == 0;
        }

        public QuantityLength convertTo(LengthUnit targetUnit) {
            double valueInFeet = unit.convertToBaseUnit(this.value);
            double convertedValue = targetUnit.convertFromBaseUnit(valueInFeet);
            return new QuantityLength(convertedValue, targetUnit);
        }

        public QuantityLength add(QuantityLength other) {
            return add(other, this.unit);
        }

        public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
            if (other == null) throw new IllegalArgumentException("Other quantity cannot be null");
            if (targetUnit == null) throw new IllegalArgumentException("Target unit cannot be null");
            double sumInFeet = unit.convertToBaseUnit(this.value) + other.unit.convertToBaseUnit(other.value);
            double sumInTargetUnit = targetUnit.convertFromBaseUnit(sumInFeet);
            return new QuantityLength(sumInTargetUnit, targetUnit);
        }

        @Override
        public String toString() {
            return value + " " + unit.name();
        }
    }

    // ---------------- WEIGHT UNITS ----------------
    public enum WeightUnit {
        KILOGRAM(1.0),
        GRAM(0.001),                // 1 g = 0.001 kg
        POUND(0.453592);            // 1 lb ≈ 0.453592 kg

        private final double conversionFactor;

        WeightUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double convertToBaseUnit(double value) {
            return value * conversionFactor; // to kilograms
        }

        public double convertFromBaseUnit(double baseValue) {
            return baseValue / conversionFactor; // from kilograms
        }
    }

    public static class QuantityWeight {
        private final double value;
        private final WeightUnit unit;

        public QuantityWeight(double value, WeightUnit unit) {
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite");
            this.value = value;
            this.unit = unit;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || this.getClass() != obj.getClass()) return false;
            QuantityWeight other = (QuantityWeight) obj;
            double thisInKg = unit.convertToBaseUnit(this.value);
            double otherInKg = other.unit.convertToBaseUnit(other.value);
            return Double.compare(thisInKg, otherInKg) == 0;
        }

        public QuantityWeight convertTo(WeightUnit targetUnit) {
            double valueInKg = unit.convertToBaseUnit(this.value);
            double convertedValue = targetUnit.convertFromBaseUnit(valueInKg);
            return new QuantityWeight(convertedValue, targetUnit);
        }

        public QuantityWeight add(QuantityWeight other) {
            return add(other, this.unit);
        }

        public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {
            if (other == null) throw new IllegalArgumentException("Other quantity cannot be null");
            if (targetUnit == null) throw new IllegalArgumentException("Target unit cannot be null");
            double sumInKg = unit.convertToBaseUnit(this.value) + other.unit.convertToBaseUnit(other.value);
            double sumInTargetUnit = targetUnit.convertFromBaseUnit(sumInKg);
            return new QuantityWeight(sumInTargetUnit, targetUnit);
        }

        @Override
        public String toString() {
            return value + " " + unit.name();
        }
    }

    // ---------------- DEMO MAIN ----------------
    public static void main(String[] args) {
        // Length examples
        QuantityLength foot = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength inch12 = new QuantityLength(12.0, LengthUnit.INCH);
        System.out.println("1 foot to inches: " + foot.convertTo(LengthUnit.INCH));
        System.out.println("1 foot + 12 inches (FEET): " + foot.add(inch12, LengthUnit.FEET));
        System.out.println("36 inches == 1 yard? " + new QuantityLength(36.0, LengthUnit.INCH).equals(new QuantityLength(1.0, LengthUnit.YARD)));

        // Weight examples
        QuantityWeight kg1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight g1000 = new QuantityWeight(1000.0, WeightUnit.GRAM);
        QuantityWeight lb2 = new QuantityWeight(2.0, WeightUnit.POUND);

        System.out.println("1 kg == 1000 g? " + kg1.equals(g1000));
        System.out.println("1 kg to pounds: " + kg1.convertTo(WeightUnit.POUND));
        System.out.println("2 pounds to kg: " + lb2.convertTo(WeightUnit.KILOGRAM));
        System.out.println("1 kg + 1000 g (GRAM): " + kg1.add(g1000, WeightUnit.GRAM));
        System.out.println("2 pounds + 1 kg (KILOGRAM): " + lb2.add(kg1, WeightUnit.KILOGRAM));
    }
}
