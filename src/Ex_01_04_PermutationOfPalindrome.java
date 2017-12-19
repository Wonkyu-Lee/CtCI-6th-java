public class Ex_01_04_PermutationOfPalindrome {

    static boolean isPermutationOfPalindrome(String phrase) {
        int[] table = buildCharFrequencyTable(phrase);
        return checkMaxOneOdd(table);
    }

    static int[] buildCharFrequencyTable(String phrase) {
        int aCode = Character.getNumericValue('a');
        int zCode = Character.getNumericValue('z');
        int[] table = new int[zCode - aCode + 1];
        for (int i = 0; i < phrase.length(); ++i) {
            char ch = phrase.charAt(i);
            int code = Character.getNumericValue(ch);
            if (aCode <= code && code <= zCode) {
                table[code - aCode]++;
            }
        }
        return table;
    }

    static boolean checkMaxOneOdd(int[] table) {
        boolean foundOdd = false;
        for (int each : table) {
            if (each % 2 == 1) {
                if (foundOdd)
                    return false;
                foundOdd = true;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String phrase = "tact coa";
        boolean r = isPermutationOfPalindrome(phrase);
        System.out.printf("'%s' is %sa permutation of a palindrome!", phrase, (r ? "" : "not "));
    }
}
