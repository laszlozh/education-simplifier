package com.github.laszlozh.lexer;

import java.io.BufferedReader;
import java.util.ArrayList;

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

            }
        }
    }
}
