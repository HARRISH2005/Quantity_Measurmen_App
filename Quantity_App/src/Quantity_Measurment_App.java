public class Quantity_Measurment_App {

    // ---------------- FEET CLASS ----------------
    static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Feet other = (Feet) obj;
            return Double.compare(this.value, other.value) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(value);
        }
    }

    // ---------------- INCHES CLASS ----------------
    static class Inches {
        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Inches other = (Inches) obj;
            return Double.compare(this.value, other.value) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(value);
        }
    }

    // ---------------- STATIC METHODS ----------------

    // Feet equality
    public static boolean areFeetEqual(double v1, double v2) {
        Feet f1 = new Feet(v1);
        Feet f2 = new Feet(v2);
        return f1.equals(f2);
    }

    // Inches equality
    public static boolean areInchesEqual(double v1, double v2) {
        Inches i1 = new Inches(v1);
        Inches i2 = new Inches(v2);
        return i1.equals(i2);
    }

    // Cross comparison (Feet ↔ Inches)
    public static boolean areFeetAndInchesEqual(double feet, double inches) {
        double feetToInches = feet * 12;
        return Double.compare(feetToInches, inches) == 0;
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {

        System.out.println("Input: 1.0 ft and 1.0 ft");
        System.out.println("Output: Equal (" + areFeetEqual(1.0, 1.0) + ")");

        System.out.println("Input: 1.0 inch and 1.0 inch");
        System.out.println("Output: Equal (" + areInchesEqual(1.0, 1.0) + ")");

        System.out.println("Input: 1.0 ft and 12.0 inch");
        System.out.println("Output: Equal (" + areFeetAndInchesEqual(1.0, 12.0) + ")");
    }
}