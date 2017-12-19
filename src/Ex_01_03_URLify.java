public class Ex_01_03_URLify {

    static int replaceSpaces(char[] str, int trueLength) {
        int spaceCount = 0;
        for (int i = 0; i < trueLength; ++i) {
            if (str[i] == ' ')
                ++spaceCount;
        }

        int length = trueLength + spaceCount * 2;
        if (str.length < length + 1) {
            throw new IllegalArgumentException("The size of str is too short!");
        }

        str[length] = '\0';
        int index = length;
        for (int i = trueLength - 1; 0 <= i; --i) {
            if (str[i] == ' ') {
                str[index - 1] = '0';
                str[index - 2] = '2';
                str[index - 3] = '%';
                index -= 3;
            } else {
                str[index - 1] = str[i];
                --index;
            }
        }

        return length;
    }

    public static void main(String args[]) {
        char[] str = new char[128];
        int length = 0;
        String text = "Mr John Smith";
        for (int i = 0; i < text.length(); ++i) {
            str[i] = text.charAt(i);
            ++length;
        }

        int totalLength = replaceSpaces(str, length);
        System.out.println(new String(str, 0, totalLength));
    }
}
