public class QuantityMeasurementApp {

    // Enum for supported length units
    public enum LengthUnit {
        FEET(1.0),                // base unit
        INCH(1.0 / 12.0),         // 1 inch = 1/12 foot
        YARD(3.0),                // 1 yard = 3 feet
        CENTIMETER(0.0328084);    // 1 cm = 0.0328084 feet (since 1 cm = 0.393701 inch)

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double toFeet(double value) {
            return value * conversionFactor;
        }
    }

    // Generic QuantityLength class
    public static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || this.getClass() != obj.getClass()) return false;
            QuantityLength other = (QuantityLength) obj;

            double thisInFeet = this.unit.toFeet(this.value);
            double otherInFeet = other.unit.toFeet(other.value);

            return Double.compare(thisInFeet, otherInFeet) == 0;
        }
    }

    // Demo main method
    public static void main(String[] args) {
        QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARD);
        QuantityLength feet = new QuantityLength(3.0, LengthUnit.FEET);
        QuantityLength inches = new QuantityLength(36.0, LengthUnit.INCH);
        QuantityLength cm = new QuantityLength(1.0, LengthUnit.CENTIMETER);
        QuantityLength inchEquivalent = new QuantityLength(0.393701, LengthUnit.INCH);

        System.out.println("1 yard vs 3 feet: " + yard.equals(feet));          // true
        System.out.println("1 yard vs 36 inches: " + yard.equals(inches));     // true
        System.out.println("2 yards vs 2 yards: " +
            new QuantityLength(2.0, LengthUnit.YARD).equals(new QuantityLength(2.0, LengthUnit.YARD))); // true
        System.out.println("2 cm vs 2 cm: " +
            new QuantityLength(2.0, LengthUnit.CENTIMETER).equals(new QuantityLength(2.0, LengthUnit.CENTIMETER))); // true
        System.out.println("1 cm vs 0.393701 inches: " + cm.equals(inchEquivalent)); // true
        System.out.println("1 yard vs 2 feet: " + yard.equals(new QuantityLength(2.0, LengthUnit.FEET))); // false
    }
}
