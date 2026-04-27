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

        // -------- UC6: ADDITION (Instance Method) --------
        public Quantity add(Quantity other) {
            if (other == null) {
                throw new IllegalArgumentException("Other quantity cannot be null");
            }

            double sumInFeet = this.toFeet() + other.toFeet();
            double resultValue = unit.fromFeet(sumInFeet);

            return new Quantity(resultValue, this.unit);
        }

        // -------- UC6: ADDITION (Static Overload) --------
        public static Quantity add(Quantity q1, Quantity q2) {
            return q1.add(q2);
        }

        // -------- Optional overload --------
        public static Quantity add(double v1, LengthUnit u1, double v2, LengthUnit u2) {
            return new Quantity(v1, u1).add(new Quantity(v2, u2));
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

    // ---------------- MAIN (Demo) ----------------
    public static void main(String[] args) {

        System.out.println(Quantity.add(
                new Quantity(1.0, LengthUnit.FEET),
                new Quantity(2.0, LengthUnit.FEET)));

        System.out.println(Quantity.add(
                new Quantity(1.0, LengthUnit.FEET),
                new Quantity(12.0, LengthUnit.INCH)));

        System.out.println(Quantity.add(
                new Quantity(12.0, LengthUnit.INCH),
                new Quantity(1.0, LengthUnit.FEET)));

        System.out.println(Quantity.add(
                new Quantity(1.0, LengthUnit.YARD),
                new Quantity(3.0, LengthUnit.FEET)));

        System.out.println(Quantity.add(
                new Quantity(2.54, LengthUnit.CENTIMETER),
                new Quantity(1.0, LengthUnit.INCH)));
    }
}