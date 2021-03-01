package com.github.laszlozh.lexer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Lexer {

    private final BufferedReader br;
    private final List<Token> tokens = new ArrayList<>();

    public Lexer(BufferedReader br){
        this.br = br;
    }

    public List<Token> scan() {
        String line;
        int nLine = 1;
        while ((line = readLine()) != null){
            for (int i = 0; i < line.length(); i++){
                char c = line.charAt(i);
                if (Character.isWhitespace(c)) continue;
                if (Character.isLetter(c)) {
                    i = scanIdentifier(line, i, nLine);
                    continue;
                }
                TokenType tokenType = switch (c) {
                    case '(' -> TokenType.OP_BRACE;
                    case ')' -> TokenType.CL_BRACE;
                    case '+' -> TokenType.PLUS;
                    case '-' -> TokenType.MINUS;
                    case '/' -> TokenType.SLASH;
                    case '*' -> TokenType.STAR;
                    case '^' -> TokenType.POW;
                    default -> null;
                };
                if (tokenType == null) {
                    throw new IllegalStateException(String.format("Unexpected char '%s' at line %d", c, nLine));
                }
                tokens.add(new Token(c, nLine, tokenType));
            }
            nLine++;
            tokens.add(new Token(null, nLine, TokenType.EOL));
        }
        tokens.add(new Token(null, nLine, TokenType.EOF));
        return tokens;
    }

    private int scanIdentifier(String line, int i, int nLine){
        return scanSequence(line, i, nLine, Character::isLetter, TokenType.IDENTIFIER);
    }

    private int scanNumber(String line, int i, int nLine) {
        return scanSequence(line, i, nLine, Character::isDigit, TokenType.NUMBER);
    }

    private int scanSequence(String line, int i, int nLine, Predicate<Character> charPredicate, TokenType tokenType) {
        StringBuilder sb = new StringBuilder();
        while (i < line.length()){
            char c = line.charAt(i);
            if (!charPredicate.test(c)) break;
            sb.append(c);
            i++;
        }
        tokens.add(new Token(sb.toString(), nLine, tokenType));
        return i - 1;
    }

    private String readLine() {
        try {
            return br.readLine();
        } catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }
}
