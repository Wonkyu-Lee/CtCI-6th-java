import java.util.ArrayList;
import java.util.HashMap;

public class Ex_00_AddingPolynomials {
    static class Term {
        final double coefficient;
        final int exponent;

        Term(double coefficient, int exponent) {
            this.coefficient = coefficient;
            this.exponent = exponent;
        }
    }

    static ArrayList<Term> add(ArrayList<Term> polynomial1, ArrayList<Term> polynomial2) {
        HashMap<Integer, Double> sum = new HashMap<>();
        for (Term each : polynomial1) {
            sum.put(each.exponent, each.coefficient);
        }

        for (Term each : polynomial2) {
            if (!sum.containsKey(each.exponent)) {
                sum.put(each.exponent, each.coefficient);
            } else {
                double newCoefficient = sum.get(each.exponent) + each.coefficient;
                sum.put(each.exponent, newCoefficient);
            }
        }

        ArrayList<Term> result = new ArrayList<>();
        for (int key : sum.keySet()) {
            result.add(new Term(sum.get(key), key));
        }

        return result;
    }

    // TODO: Test
}
