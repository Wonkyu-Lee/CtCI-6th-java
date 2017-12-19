public class Ex_01_04_PermutationOfPalindrome2 {

    boolean isPermutationOfPalindrome(String phrase) {
        int bits = createBitSet(phrase);
        return (bits == 0 || hasOneBitSet(bits));
    }

    int createBitSet(String phrase) {
        int bits = 0;

        int aCode = Character.getNumericValue('a');
        int zCode = Character.getNumericValue('z');

        for (int i = 0; i < phrase.length(); ++i) {
            char ch = phrase.charAt(i);
            int chCode = Character.getNumericValue(ch);
            if (aCode <= chCode && chCode  <= zCode) {
                int index = chCode - aCode;
                bits = toggleBit(bits, index);
            }
        }

        return bits;
    }

    boolean hasOneBitSet(int bits) {
        return ((bits - 1) & bits) == 0;
    }

    int toggleBit(int bits, int i) {
        int mask = 1 << i;
        if ((bits & mask) == 0) {
            bits |= mask;
        } else {
            bits &= ~mask;
        }

        return bits;
    }
}
