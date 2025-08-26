package calculator;

public class Token {
    private TokenType type;
    private Double value;

    public Token(TokenType type, Double value) {
        this.type = type;
        this.value = value;
    }

    public Token(TokenType type) {
        this(type, null);
    }

    public TokenType getType() {
        return type;
    }

    public Double getValue() {
        return value;
    }
}
