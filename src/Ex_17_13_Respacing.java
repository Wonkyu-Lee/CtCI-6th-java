import java.util.Arrays;
import java.util.HashSet;

public class Ex_17_13_Respacing {

    class Result {
        int invalid = Integer.MAX_VALUE;
        String parsed;
        Result(int invalid, String parsed) {
            this.invalid = invalid;
            this.parsed = parsed;
        }
    }

    String bestSplit(HashSet<String> dictionary, String sentence) {
        return bestSplit(dictionary, sentence, 0).parsed;
    }

    Result bestSplit(HashSet<String> dictionary, String sentence, int start) {
        if (start >= sentence.length()) {
            return new Result(0, "");
        }

        int bestInvalid = Integer.MAX_VALUE;
        String bestSentence = "";
        String partial = "";
        for (int i = start; i < sentence.length(); ++i) {
            partial += sentence.charAt(i);
            int leftInvalid = dictionary.contains(partial) ? 0 : partial.length();
            if (leftInvalid >= bestInvalid)
                continue;

            Result right = bestSplit(dictionary, sentence, i + 1);
            int invalid = leftInvalid + right.invalid;
            if (invalid < bestInvalid) {
                bestInvalid = invalid;
                bestSentence = partial + " " + right.parsed;
                if (bestInvalid == 0) {
                    break;
                }
            }
        }
        return new Result(bestInvalid, bestSentence);
    }

    String bestSplitMemo(HashSet<String> dictionary, String sentence) {
        Result[] memo = new Result[sentence.length()];
        return bestSplitMemo(dictionary, sentence, 0, memo).parsed;
    }

    Result bestSplitMemo(HashSet<String> dictionary, String sentence, int start, Result[] memo) {
        if (start >= sentence.length()) {
            return new Result(0, "");
        }

        if (memo[start] != null) {
            return memo[start];
        }

        int bestInvalid = Integer.MAX_VALUE;
        String bestSentence = "";
        String partial = "";
        for (int i = start; i < sentence.length(); ++i) {
            partial += sentence.charAt(i);
            int leftInvalid = dictionary.contains(partial) ? 0 : partial.length();
            if (leftInvalid >= bestInvalid)
                continue;

            Result right = bestSplitMemo(dictionary, sentence, i + 1, memo);
            int invalid = leftInvalid + right.invalid;
            if (invalid < bestInvalid) {
                bestInvalid = invalid;
                bestSentence = partial + " " + right.parsed;
                if (bestInvalid == 0) {
                    break;
                }
            }
        }

        return memo[start] = new Result(bestInvalid, bestSentence);
    }

    Ex_17_13_Respacing() {
        String[] words = {
                "look", "looked", "just", "like", "he", "her", "brother"
        };
        HashSet<String> dictionary = new HashSet<>();
        dictionary.addAll(Arrays.asList(words));
        String sentence = "jesslookedjustliketimherbrother";

        System.out.println(bestSplitMemo(dictionary, sentence));
    }

    public static void main(String[] args) {
        new Ex_17_13_Respacing();
    }
}
