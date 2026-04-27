public class Quantity_Measurment_App {

    // ---------------- ENUM ----------------
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.0328084);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }

        public double fromFeet(double feetValue) {
            return feetValue / toFeetFactor;
        }
    }

    // ---------------- QUANTITY CLASS ----------------
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Value must be finite");
            }
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        // Instance conversion → returns new Quantity
        public Quantity convertTo(LengthUnit targetUnit) {
            double converted = convert(this.value, this.unit, targetUnit);
            return new Quantity(converted, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;
            return Math.abs(this.toFeet() - other.toFeet()) < 1e-6;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(toFeet());
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // ---------------- STATIC API ----------------
    public static double convert(double value, LengthUnit source, LengthUnit target) {

        // Validation
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid numeric value");
        }
        if (source == null || target == null) {
            throw new IllegalArgumentException("Units cannot be null");
        }

        // Same unit shortcut
        if (source == target) {
            return value;
        }

        // Normalize to base (feet)
        double valueInFeet = source.toFeet(value);

        // Convert to target
        return target.fromFeet(valueInFeet);
    }

    // ---------------- DEMO METHODS (Overloading) ----------------

    // Method 1: raw values
    public static void demonstrateLengthConversion(double value, LengthUnit from, LengthUnit to) {
        double result = convert(value, from, to);
        System.out.println("convert(" + value + ", " + from + ", " + to + ") → " + result);
    }

    // Method 2: using Quantity object
    public static void demonstrateLengthConversion(Quantity quantity, LengthUnit to) {
        Quantity result = quantity.convertTo(to);
        System.out.println(quantity + " → " + result);
    }

    public static void demonstrateLengthEquality(Quantity q1, Quantity q2) {
        System.out.println(q1 + " == " + q2 + " → " + q1.equals(q2));
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {

        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCH);
        demonstrateLengthConversion(3.0, LengthUnit.YARD, LengthUnit.FEET);
        demonstrateLengthConversion(36.0, LengthUnit.INCH, LengthUnit.YARD);
        demonstrateLengthConversion(1.0, LengthUnit.CENTIMETER, LengthUnit.INCH);

        demonstrateLengthConversion(new Quantity(3.0, LengthUnit.YARD), LengthUnit.INCH);

        demonstrateLengthEquality(
                new Quantity(1.0, LengthUnit.FEET),
                new Quantity(12.0, LengthUnit.INCH)
        );
    }
}