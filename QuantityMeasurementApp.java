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

        // Addition (UC6): result in unit of first operand
        public QuantityLength add(QuantityLength other) {
            return add(other, this.unit);
        }

        // Addition (UC7): result in explicitly specified target unit
        public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
            if (other == null) {
                throw new IllegalArgumentException("Other quantity cannot be null");
            }
            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double thisInFeet = this.unit.toFeet(this.value);
            double otherInFeet = other.unit.toFeet(other.value);

            double sumInFeet = thisInFeet + otherInFeet;
            double sumInTargetUnit = targetUnit.fromFeet(sumInFeet);

            return new QuantityLength(sumInTargetUnit, targetUnit);
        }

        @Override
        public String toString() {
            return value + " " + unit.name();
        }
    }

    // Demo main method
    public static void main(String[] args) {
        QuantityLength feet1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength inch12 = new QuantityLength(12.0, LengthUnit.INCH);

        System.out.println("1 foot + 12 inches (FEET): " + feet1.add(inch12, LengthUnit.FEET)); // 2 FEET
        System.out.println("1 foot + 12 inches (INCHES): " + feet1.add(inch12, LengthUnit.INCH)); // 24 INCH
        System.out.println("1 foot + 12 inches (YARDS): " + feet1.add(inch12, LengthUnit.YARD)); // ~0.667 YARD

        QuantityLength yard1 = new QuantityLength(1.0, LengthUnit.YARD);
        QuantityLength feet3 = new QuantityLength(3.0, LengthUnit.FEET);
        System.out.println("1 yard + 3 feet (YARDS): " + yard1.add(feet3, LengthUnit.YARD)); // 2 YARD

        QuantityLength cm2_54 = new QuantityLength(2.54, LengthUnit.CENTIMETER);
        QuantityLength inch1 = new QuantityLength(1.0, LengthUnit.INCH);
        System.out.println("2.54 cm + 1 inch (CENTIMETERS): " + cm2_54.add(inch1, LengthUnit.CENTIMETER)); // ~5.08 CM

        QuantityLength feet5 = new QuantityLength(5.0, LengthUnit.FEET);
        QuantityLength feetMinus2 = new QuantityLength(-2.0, LengthUnit.FEET);
        System.out.println("5 feet + (-2 feet) (INCHES): " + feet5.add(feetMinus2, LengthUnit.INCH)); // 36 INCH
    }
}
