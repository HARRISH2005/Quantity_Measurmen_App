// UC8: Refactored Design with Standalone LengthUnit

// ------------------------------
// LengthUnit Enum (Standalone)
// ------------------------------
enum LengthUnit {

    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double toFeetFactor;

    LengthUnit(double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }

    // Convert to base unit (FEET)
    public double convertToBaseUnit(double value) {
        return value * toFeetFactor;
    }

    // Convert from base unit (FEET)
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / toFeetFactor;
    }

    public double getConversionFactor() {
        return toFeetFactor;
    }
}

// ------------------------------
// QuantityLength Class
// ------------------------------
final class QuantityLength {

    private static final double EPSILON = 1e-6;

    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid numeric value");
        }
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }

    // ------------------------------
    // Conversion (UC5)
    // ------------------------------
    public QuantityLength convertTo(LengthUnit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double base = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(base);

        return new QuantityLength(converted, targetUnit);
    }

    public static double convert(double value, LengthUnit from, LengthUnit to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Units cannot be null");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }

        double base = from.convertToBaseUnit(value);
        return to.convertFromBaseUnit(base);
    }

    // ------------------------------
    // Addition (UC6 & UC7)
    // ------------------------------

    // UC6 → default target = this.unit
    public QuantityLength add(QuantityLength other) {
        return add(other, this.unit);
    }

    // UC7 → explicit target unit
    public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
        if (other == null || targetUnit == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        double base1 = this.unit.convertToBaseUnit(this.value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        double sumBase = base1 + base2;
        double result = targetUnit.convertFromBaseUnit(sumBase);

        return new QuantityLength(result, targetUnit);
    }

    // ------------------------------
    // Equality (UC1–UC4)
    // ------------------------------
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof QuantityLength)) return false;

        QuantityLength other = (QuantityLength) obj;

        double base1 = this.unit.convertToBaseUnit(this.value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        return Math.abs(base1 - base2) < EPSILON;
    }

    @Override
    public String toString() {
        return String.format("Quantity(%.4f, %s)", value, unit);
    }
}

// ------------------------------
// Main Application (Demo)
// ------------------------------
public class Quantity_Measurment_App {

    public static void main(String[] args) {

        System.out.println("=== UC8 DEMO OUTPUT ===\n");

        // Equality
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        System.out.println("Equality Test:");
        System.out.println(q1 + " == " + q2 + " → " + q1.equals(q2));

        // Conversion
        System.out.println("\nConversion Test:");
        QuantityLength converted = q1.convertTo(LengthUnit.INCHES);
        System.out.println(q1 + " → " + converted);

        // Static Conversion
        System.out.println("\nStatic Conversion:");
        double result = QuantityLength.convert(3.0, LengthUnit.YARDS, LengthUnit.FEET);
        System.out.println("3 YARDS → " + result + " FEET");

        // Addition (UC6)
        System.out.println("\nAddition (Default Unit):");
        QuantityLength sum1 = q1.add(q2);
        System.out.println(q1 + " + " + q2 + " → " + sum1);

        // Addition (UC7)
        System.out.println("\nAddition (Target Unit = INCHES):");
        QuantityLength sum2 = q1.add(q2, LengthUnit.INCHES);
        System.out.println(q1 + " + " + q2 + " → " + sum2);

        // Yards Example
        System.out.println("\nYards Example:");
        QuantityLength y1 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength f1 = new QuantityLength(3.0, LengthUnit.FEET);
        System.out.println(y1 + " == " + f1 + " → " + y1.equals(f1));

        // Centimeter Example
        System.out.println("\nCentimeter Conversion:");
        QuantityLength cm = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
        System.out.println(cm + " → " + cm.convertTo(LengthUnit.INCHES));

        // Edge Cases
        System.out.println("\nEdge Case:");
        QuantityLength zero = new QuantityLength(0.0, LengthUnit.FEET);
        System.out.println(q1 + " + " + zero + " → " + q1.add(zero));
    }
}