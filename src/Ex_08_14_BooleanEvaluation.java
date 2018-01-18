public class Ex_08_14_BooleanEvaluation {

    int countWays(String expr, boolean result) {
        if (expr == null || expr.isEmpty()) return 0;
        if (expr.length() == 1) return stringToBoolean(expr) == result ? 1 : 0;

        int count = 0;
        for (int i = 1; i < expr.length(); i += 2) {
            char operator = expr.charAt(i);

            String lExpr = expr.substring(0, i);
            String rExpr = expr.substring(i + 1);

            int lTrue = countWays(lExpr, true);
            int lFalse = countWays(lExpr, false);
            int rTrue = countWays(rExpr, true);
            int rFalse = countWays(rExpr, false);

            int total = (lTrue + lFalse) * (rTrue + rFalse);

            int totalTrue = 0;
            switch (operator) {
                case '&': {
                    totalTrue = lTrue * rTrue;
                    break;
                }
                case '|': {
                    totalTrue = lTrue * rTrue + lTrue * rFalse + lFalse * rTrue;
                    break;
                }
                case '^': {
                    totalTrue = lTrue * rFalse + lFalse * rTrue;
                    break;
                }
            }

            int subCount = result ? totalTrue : total - totalTrue;
            count += subCount;
        }

        return count;
    }

    boolean stringToBoolean(String expr) {
        return expr.equals("0") ? false : true;
    }

    Ex_08_14_BooleanEvaluation() {
        String expr = "1|0^1&1|0^0";
        boolean result = true;
        System.out.printf("%s = %s --> %d\n", expr, String.valueOf(result), countWays(expr, result));
    }

    public static void main(String[] args) {
        new Ex_08_14_BooleanEvaluation();
    }

    // TODO: 괄호 넣어서 출력해볼까?
}
