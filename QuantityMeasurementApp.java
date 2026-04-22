public class QuantityMeasurementApp {

    // Standalone LengthUnit enum with conversion responsibility
    public enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),          // 1 inch = 1/12 foot
        YARD(3.0),                 // 1 yard = 3 feet
        CENTIMETER(0.0328084);     // 1 cm = 0.0328084 feet

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        // Convert value in this unit to base unit (feet)
        public double convertToBaseUnit(double value) {
            return value * conversionFactor;
        }

        // Convert value from base unit (feet) to this unit
        public double convertFromBaseUnit(double baseValue) {
            return baseValue / conversionFactor;
        }
    }

    // QuantityLength class simplified to delegate conversion logic
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

        // UC6: Addition default (unit of first operand)
        public QuantityLength add(QuantityLength other) {
            return add(other, this.unit);
        }

        // UC7: Addition with explicit target unit
        public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
            if (other == null) {
                throw new IllegalArgumentException("Other quantity cannot be null");
            }
            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double thisInFeet = unit.convertToBaseUnit(this.value);
            double otherInFeet = other.unit.convertToBaseUnit(other.value);

            double sumInFeet = thisInFeet + otherInFeet;
            double sumInTargetUnit = targetUnit.convertFromBaseUnit(sumInFeet);

            return new QuantityLength(sumInTargetUnit, targetUnit);
        }

        @Override
        public String toString() {
            return value + " " + unit.name();
        }
    }

    // Demo main method
    public static void main(String[] args) {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

        System.out.println("Convert 1 foot to inches: " + q1.convertTo(LengthUnit.INCH));
        System.out.println("Add 1 foot + 12 inches (FEET): " + q1.add(q2, LengthUnit.FEET));
        System.out.println("Add 1 foot + 12 inches (YARDS): " + q1.add(q2, LengthUnit.YARD));
        System.out.println("Equality check: 36 inches == 1 yard? " +
            new QuantityLength(36.0, LengthUnit.INCH).equals(new QuantityLength(1.0, LengthUnit.YARD)));
        System.out.println("2.54 cm to inches: " +
            new QuantityLength(2.54, LengthUnit.CENTIMETER).convertTo(LengthUnit.INCH));
        System.out.println("5 feet + (-2 feet) in INCHES: " +
            new QuantityLength(5.0, LengthUnit.FEET).add(new QuantityLength(-2.0, LengthUnit.FEET), LengthUnit.INCH));
    }
}
