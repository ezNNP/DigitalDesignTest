public class StringTranslator {

    private static final StringTranslator instance = new StringTranslator();

    private StringTranslator() {}

    public static StringTranslator getInstance() {
        return instance;
    }

    private enum TokenType {
        CHAR,
        BR_OPEN,
        BR_CLOSE,
        DIGIT
    }

    public boolean validateInput(String input) {
        TokenType lastTokenType = TokenType.CHAR;
        int nesting = 0;
        char[] tokens = input.toCharArray();
        for (int i = 0; i < input.length(); i++) {
            char token = tokens[i];
            if (token >= '0' && token <= '9') {
                lastTokenType = TokenType.DIGIT;
            } else if (token == '[') {
                if (lastTokenType != TokenType.DIGIT) {
                    return false; // before opened bracket always must be digit
                }
                lastTokenType = TokenType.BR_OPEN;
                nesting++;
            } else if (token == ']') {
                if (lastTokenType == TokenType.DIGIT) {
                    return false; // can't be digit before close bracket
                }
                lastTokenType = TokenType.BR_CLOSE;
                nesting--;
                if (nesting < 0) {
                    return false; // can't be close token before open example: 2]abc[
                }
            } else if (token >= 'a' && token <= 'z' || token >= 'A' && token <= 'Z') {
                if (lastTokenType == TokenType.DIGIT) {
                    return false;
                }
                lastTokenType = TokenType.CHAR;
            } else {
                return false; // not correct symbol
            }
        }

        return nesting == 0 && // nesting in the end should always be zero
               lastTokenType != TokenType.DIGIT; // last token must be close bracket or char
    }

    public String translate(String input) {

        assert(input != null);
        if (!validateInput(input)) {
            throw new IllegalArgumentException("Not valid string was given");
        }

        StringBuilder output = new StringBuilder();
        int repeat = -1;
        int nesting = 0;
        int subStringBeginIdx = -1;
        char[] tokens = input.toCharArray();

        for (int i = 0; i < input.length(); i++) {
            char token = tokens[i];
            if (token >= '0' && token <= '9') {
                if (nesting == 0) {
                    repeat = repeat == -1 ? token - '0' : repeat * 10 + (token - '0');
                }
            } else if (token == '[') {
                nesting++;
                if (subStringBeginIdx == -1) {
                    subStringBeginIdx = i;
                }
            } else if (token == ']') {
                nesting--;
                if (nesting == 0) {
                    String innerTranslated = translate(input.substring(subStringBeginIdx + 1, i));
                    for (int j = 0; j < repeat; j++) {
                        output.append(innerTranslated);
                    }
                    repeat = -1;
                    subStringBeginIdx = -1;
                }
            } else if (nesting == 0) {
                output.append(token);
            }
        }
        return output.toString();
    }
}
