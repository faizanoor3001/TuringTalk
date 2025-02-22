package org.faiza.turingTalk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TuringTalk {
    static boolean hadError = false;
    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else
            runPrompt();
    }

    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        if(hadError) System.exit(65);
    }
    private static void runPrompt() throws IOException{
        // byte stream to character stream
        InputStreamReader input = new InputStreamReader(System.in);
        // for efficient reading
        BufferedReader reader = new BufferedReader(input);
        for(;;)
        {
            System.out.println("> ");
            String line = reader.readLine();
            if(line == null)
                break;
            run(line);
            hadError = false;
        }
    }

    private static void run(String source){
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        for(Token token: tokens)
        {
            System.out.println(token);
        }
    }

    // never forget error handling, because we are humans, but even AI does mistakes
    static void error(int line, String message)
    {
        report(line, "", message);
    }
    private static void report(int line, String where, String message)
    {
        System.err.println("[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }
}
