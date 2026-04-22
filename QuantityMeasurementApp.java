public class QuantityMeasurementApp {

    // Enum for supported length units (base unit = FEET)
    public enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),          // 1 inch = 1/12 foot
        YARD(3.0),                 // 1 yard = 3 feet
        CENTIMETER(0.0328084);     // 1 cm = 0.0328084 feet

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double toFeet(double value) {
            return value * conversionFactor;
        }

        public double fromFeet(double valueInFeet) {
            return valueInFeet / conversionFactor;
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
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Value must be finite");
            }
            this.value = value;
            this.unit = unit;
        }

        // Equality check
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || this.getClass() != obj.getClass()) return false;
            QuantityLength other = (QuantityLength) obj;

            double thisInFeet = this.unit.toFeet(this.value);
            double otherInFeet = other.unit.toFeet(other.value);

            return Double.compare(thisInFeet, otherInFeet) == 0;
        }

        // Conversion instance method
        public QuantityLength convertTo(LengthUnit targetUnit) {
            double valueInFeet = this.unit.toFeet(this.value);
            double convertedValue = targetUnit.fromFeet(valueInFeet);
            return new QuantityLength(convertedValue, targetUnit);
        }

        @Override
        public String toString() {
            return value + " " + unit.name();
        }
    }

    // Static conversion API
    public static double convert(double value, LengthUnit source, LengthUnit target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Units cannot be null");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be finite");
        }
        double valueInFeet = source.toFeet(value);
        return target.fromFeet(valueInFeet);
    }

    // Demo main method
    public static void main(String[] args) {
        System.out.println("1 foot to inches: " + convert(1.0, LengthUnit.FEET, LengthUnit.INCH)); // 12.0
        System.out.println("3 yards to feet: " + convert(3.0, LengthUnit.YARD, LengthUnit.FEET));   // 9.0
        System.out.println("36 inches to yards: " + convert(36.0, LengthUnit.INCH, LengthUnit.YARD)); // 1.0
        System.out.println("1 cm to inches: " + convert(1.0, LengthUnit.CENTIMETER, LengthUnit.INCH)); // ~0.393701
        System.out.println("0 feet to inches: " + convert(0.0, LengthUnit.FEET, LengthUnit.INCH)); // 0.0

        // Instance method usage
        QuantityLength lengthInFeet = new QuantityLength(2.0, LengthUnit.FEET);
        QuantityLength lengthInInches = lengthInFeet.convertTo(LengthUnit.INCH);
        System.out.println("2 feet converted to inches: " + lengthInInches);
    }
}
