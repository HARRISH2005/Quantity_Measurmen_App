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
        public final double value;
        public final LengthUnit unit;

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

        // -------- UC6 METHOD --------
        public Quantity add(Quantity other) {
            if (other == null) {
                throw new IllegalArgumentException("Other cannot be null");
            }

            double sumFeet = this.toFeet() + other.toFeet();
            double result = this.unit.fromFeet(sumFeet);

            return new Quantity(result, this.unit);
        }

        // -------- UC7 METHOD (TARGET UNIT) --------
        public Quantity add(Quantity other, LengthUnit targetUnit) {
            if (other == null) {
                throw new IllegalArgumentException("Other cannot be null");
            }
            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double sumFeet = this.toFeet() + other.toFeet();
            double result = targetUnit.fromFeet(sumFeet);

            return new Quantity(result, targetUnit);
        }

        // Static helper
        public static Quantity add(Quantity q1, Quantity q2, LengthUnit targetUnit) {
            return q1.add(q2, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;
            return Math.abs(this.toFeet() - other.toFeet()) < 1e-6;
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // ---------------- MAIN METHOD ----------------
    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);

        System.out.println(q1.add(q2, LengthUnit.FEET));      // 2.0 FEET
        System.out.println(q1.add(q2, LengthUnit.INCH));      // 24.0 INCH
        System.out.println(q1.add(q2, LengthUnit.YARD));      // ~0.667 YARD
        System.out.println(q1.add(q2, LengthUnit.CENTIMETER));// ~60.96 CM
    }
}