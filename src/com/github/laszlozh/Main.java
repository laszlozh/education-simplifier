package com.github.laszlozh;

import com.github.laszlozh.lexer.Lexer;
import com.github.laszlozh.lexer.Token;
import com.github.laszlozh.parser.Parser;
import com.github.laszlozh.parser.model.Expression;
import com.github.laszlozh.simplifier.Simplifier;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String s = """
                -(-(x - y))
                -(-(-(-x)))
                -3 * 4 + (-7) - -12 + 5 * x
                """;
        BufferedReader br = new BufferedReader(new StringReader(s));
        Lexer lexer = new Lexer(br);
        List<Token> tokens = lexer.scan();
        Parser parser = new Parser(tokens);
        List<Expression> expressions = parser.parse();
        expressions = Simplifier.simplify(expressions);
        expressions.forEach(ExpressionPrinter::print);
    }
}
