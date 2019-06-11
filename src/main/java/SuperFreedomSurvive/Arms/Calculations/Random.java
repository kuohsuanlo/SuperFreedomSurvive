package SuperFreedomSurvive.Arms.Calculations;

final public class Random {

    static final public String Create(int length) {

        String text = "";
        String[] RegSNContent = {
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                "_", "+", "-", ".", "~", "/", "=", "[", "]", "(", ")", "<", ">"
        };
        for (int i = 0; i < length; i++) text += RegSNContent[(int) (Math.random() * RegSNContent.length)];

        return text;
    }
}
