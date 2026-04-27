// ==============================
// LENGTH SECTION (UC8)
// ==============================

enum LengthUnit {
    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double factor;

    LengthUnit(double factor) {
        this.factor = factor;
    }

    public double convertToBaseUnit(double value) {
        return value * factor;
    }

    public double convertFromBaseUnit(double base) {
        return base / factor;
    }
}

final class QuantityLength {

    private static final double EPSILON = 1e-6;

    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        if (unit == null || !Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid input");
        }
        this.value = value;
        this.unit = unit;
    }

    public QuantityLength convertTo(LengthUnit target) {
        double base = unit.convertToBaseUnit(value);
        double result = target.convertFromBaseUnit(base);
        return new QuantityLength(result, target);
    }

    public QuantityLength add(QuantityLength other, LengthUnit target) {
        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);
        double sum = base1 + base2;
        return new QuantityLength(target.convertFromBaseUnit(sum), target);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof QuantityLength)) return false;
        QuantityLength other = (QuantityLength) obj;
        double b1 = unit.convertToBaseUnit(value);
        double b2 = other.unit.convertToBaseUnit(other.value);
        return Math.abs(b1 - b2) < EPSILON;
    }
}

// ==============================
// WEIGHT SECTION (UC9)
// ==============================

enum WeightUnit {

    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double factor;

    WeightUnit(double factor) {
        this.factor = factor;
    }

    public double convertToBaseUnit(double value) {
        return value * factor;
    }

    public double convertFromBaseUnit(double base) {
        return base / factor;
    }

    public double getConversionFactor() {
        return factor;
    }
}

final class QuantityWeight {

    private static final double EPSILON = 1e-6;

    private final double value;
    private final WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public WeightUnit getUnit() {
        return unit;
    }

    // --------------------
    // Conversion
    // --------------------
    public QuantityWeight convertTo(WeightUnit target) {
        if (target == null) {
            throw new IllegalArgumentException("Target unit null");
        }

        double base = unit.convertToBaseUnit(value);
        double result = target.convertFromBaseUnit(base);

        return new QuantityWeight(result, target);
    }

    // --------------------
    // Addition (default unit)
    // --------------------
    public QuantityWeight add(QuantityWeight other) {
        return add(other, this.unit);
    }

    // --------------------
    // Addition (target unit)
    // --------------------
    public QuantityWeight add(QuantityWeight other, WeightUnit target) {
        if (other == null || target == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        double base1 = this.unit.convertToBaseUnit(this.value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        double sum = base1 + base2;
        double result = target.convertFromBaseUnit(sum);

        return new QuantityWeight(result, target);
    }

    // --------------------
    // Equality
    // --------------------
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof QuantityWeight)) return false;

        QuantityWeight other = (QuantityWeight) obj;

        double b1 = unit.convertToBaseUnit(value);
        double b2 = other.unit.convertToBaseUnit(other.value);

        return Math.abs(b1 - b2) < EPSILON;
    }

    @Override
    public int hashCode() {
        long base = Double.doubleToLongBits(unit.convertToBaseUnit(value));
        return (int) (base ^ (base >>> 32));
    }

    @Override
    public String toString() {
        return String.format("Quantity(%.4f, %s)", value, unit);
    }
}

// ==============================
// MAIN APP
// ==============================

public class Quantity_Measurment_App {

    public static void main(String[] args) {

        System.out.println("=== UC9 OUTPUT ===\n");

        // Equality
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);

        System.out.println("Equality:");
        System.out.println(w1 + " == " + w2 + " → " + w1.equals(w2));

        // Conversion
        System.out.println("\nConversion:");
        System.out.println(w1 + " → " + w1.convertTo(WeightUnit.POUND));

        // Addition
        System.out.println("\nAddition:");
        System.out.println(w1 + " + " + w2 + " → " + w1.add(w2));

        // Cross-unit addition
        QuantityWeight w3 = new QuantityWeight(2.0, WeightUnit.POUND);
        System.out.println("\nCross Unit:");
        System.out.println(w3 + " + " + w1 + " → " + w3.add(w1, WeightUnit.POUND));
    }
}