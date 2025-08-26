package calculator;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

    private String input;
    private int pos;
    private char currentChar;

    public double evaluate(String expression) {
        this.input = expression.replace(" ", ""); // remove spaces
        this.pos = 0;
        this.currentChar = !input.isEmpty() ? input.charAt(0) : '\0';

        List<Token> tokens = tokenize();
        return parseExpression(tokens);
    }

    private List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (currentChar != '\0') {
            if (Character.isDigit(currentChar) || currentChar == '.') {
                tokens.add(new Token(TokenType.NUMBER, readNumber()));
            } else if (currentChar == '+') {
                tokens.add(new Token(TokenType.PLUS));
                advance();
            } else if (currentChar == '-') {
                tokens.add(new Token(TokenType.MINUS));
                advance();
            } else if (currentChar == '*') {
                tokens.add(new Token(TokenType.MUL));
                advance();
            } else if (currentChar == '/') {
                tokens.add(new Token(TokenType.DIV));
                advance();
            } else if (currentChar == '(') {
                tokens.add(new Token(TokenType.LPAREN));
                advance();
            } else if (currentChar == ')') {
                tokens.add(new Token(TokenType.RPAREN));
                advance();
            } else {
                throw new RuntimeException("Недопустимый символ: " + currentChar);
            }
        }
        tokens.add(new Token(TokenType.EOF));
        return tokens;
    }

    private void advance() {
        pos++;
        if (pos >= input.length()) {
            currentChar = '\0';
        } else {
            currentChar = input.charAt(pos);
        }
    }

    private double readNumber() {
        StringBuilder number = new StringBuilder();
        while (currentChar != '\0' && (Character.isDigit(currentChar) || currentChar == '.')) {
            number.append(currentChar);
            advance();
        }
        return Double.parseDouble(number.toString());
    }


    private int indexToken;
    private List<Token> tokens;

    private Token getCurrentToken() {
        return tokens.get(indexToken);
    }

    private void eat(TokenType type) {
        if (getCurrentToken().getType() == type) {
            indexToken++;
        } else {
            throw new RuntimeException("Ожидался токен: " + type);
        }
    }

    private double parseExpression(List<Token> tokens) {
        this.tokens = tokens;
        this.indexToken = 0;
        return expr();
    }


    private double expr() {
        double result = term();

        while (getCurrentToken().getType() == TokenType.PLUS ||
                getCurrentToken().getType() == TokenType.MINUS) {
            if (getCurrentToken().getType() == TokenType.PLUS) {
                eat(TokenType.PLUS);
                result += term();
            } else {
                eat(TokenType.MINUS);
                result -= term();
            }
        }
        return result;
    }

    private double term() {
        double result = factor();

        while (getCurrentToken().getType() == TokenType.MUL ||
                getCurrentToken().getType() == TokenType.DIV) {
            if (getCurrentToken().getType() == TokenType.MUL) {
                eat(TokenType.MUL);
                result *= factor();
            } else {
                eat(TokenType.DIV);
                double divisor = factor();
                if (divisor == 0) {
                    throw new ArithmeticException("Деление на ноль");
                }
                result /= divisor;
            }
        }
        return result;
    }

    private double factor() {
        Token token = getCurrentToken();

        if (token.getType() == TokenType.NUMBER) {
            eat(TokenType.NUMBER);
            return token.getValue();
        } else if (token.getType() == TokenType.LPAREN) {
            eat(TokenType.LPAREN);
            double result = expr();
            eat(TokenType.RPAREN);
            return result;
        } else if (token.getType() == TokenType.MINUS) {
            // унарный минус
            eat(TokenType.MINUS);
            return -factor();
        } else {
            throw new RuntimeException("Неожиданный токен: " + token.getType());
        }
    }
}
