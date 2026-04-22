public class QuantityMeasurementApp {

    // Inner class representing Feet measurement
    public static class Feet {
        private final double value;

        // Constructor
        public Feet(double value) {
            this.value = value;
        }

        // Override equals method
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true; // same reference
            if (obj == null || this.getClass() != obj.getClass()) return false; // null/type check
            Feet other = (Feet) obj;
            return Double.compare(this.value, other.value) == 0; // value comparison
        }
    }

    // Main method for quick verification
    public static void main(String[] args) {
        Feet feet1 = new Feet(1.0);
        Feet feet2 = new Feet(1.0);

        System.out.println("Are they equal? " + feet1.equals(feet2));
    }
}
