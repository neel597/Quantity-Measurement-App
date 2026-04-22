public class QuantityMeasurementApp {

    // Enum for supported length units
    public enum LengthUnit {
        FEET(1.0),          // base unit
        INCH(1.0 / 12.0);   // 1 inch = 1/12 foot

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
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);
        QuantityLength q3 = new QuantityLength(1.0, LengthUnit.INCH);

        System.out.println("1 foot vs 12 inches: " + q1.equals(q2)); // true
        System.out.println("1 inch vs 1 inch: " + q3.equals(new QuantityLength(1.0, LengthUnit.INCH))); // true
        System.out.println("1 foot vs 2 feet: " + q1.equals(new QuantityLength(2.0, LengthUnit.FEET))); // false
    }
}
