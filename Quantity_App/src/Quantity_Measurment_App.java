public class Quantity_Measurment_App {

    // ---------------- ENUM (extended) ----------------
    enum LengthUnit {
        FEET(1.0),                 // base
        INCH(1.0 / 12.0),          // 1 in = 1/12 ft
        YARD(3.0),                 // 1 yd = 3 ft
        CENTIMETER(0.0328084);     // 1 cm = 0.0328084 ft  (derived: 1 cm = 0.393701 in)

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    // ---------------- GENERIC CLASS ----------------
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            // Use tolerance for floating-point safety
            return Math.abs(this.toFeet() - other.toFeet()) < 1e-6;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(toFeet());
        }
    }

    // ---------------- MAIN (UC4 demo) ----------------
    public static void main(String[] args) {

        System.out.println("Input: Quantity(1.0, YARD) and Quantity(3.0, FEET)");
        System.out.println("Output: Equal (" +
                new Quantity(1.0, LengthUnit.YARD)
                        .equals(new Quantity(3.0, LengthUnit.FEET)) + ")");

        System.out.println("Input: Quantity(1.0, YARD) and Quantity(36.0, INCH)");
        System.out.println("Output: Equal (" +
                new Quantity(1.0, LengthUnit.YARD)
                        .equals(new Quantity(36.0, LengthUnit.INCH)) + ")");

        System.out.println("Input: Quantity(2.0, CENTIMETER) and Quantity(2.0, CENTIMETER)");
        System.out.println("Output: Equal (" +
                new Quantity(2.0, LengthUnit.CENTIMETER)
                        .equals(new Quantity(2.0, LengthUnit.CENTIMETER)) + ")");

        System.out.println("Input: Quantity(1.0, CENTIMETER) and Quantity(0.393701, INCH)");
        System.out.println("Output: Equal (" +
                new Quantity(1.0, LengthUnit.CENTIMETER)
                        .equals(new Quantity(0.393701, LengthUnit.INCH)) + ")");
    }
}