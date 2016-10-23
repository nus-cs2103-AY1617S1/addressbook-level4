package seedu.todo.logic.parser;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.*;
import java.util.Map.Entry;


/**
 * Parses a input string into the command, positional argument and named arguments. 
 * The format looks like: 
 * 
 * <pre><code>command positional [-f or --flag named]...</code></pre>
 * 
 * There will only be one command, optionally one positional argument, and 
 * optionally many named arguments. 
 */
public class TodoParser implements Parser {
    public static String FLAG_TOKEN = "/";
    
    private class TodoResult implements ParseResult {
        private final String command;
        private final String positional;
        private final Map<String, String> named;
        
        public TodoResult(String command, String positional, Map<String, String> named) {
            this.command = command;
            this.positional = positional.length() > 0 ? positional : null;
            this.named = named;
        }

        @Override
        public String getCommand() {
            return command;
        }

        @Override
        public Optional<String> getPositionalArgument() {
            return Optional.ofNullable(positional);
        }

        @Override
        public Map<String, String> getNamedArguments() {
            return named;
        }
        
        @Override
        public String toString() {
            StringJoiner sj = new StringJoiner(" ");
            sj.add(command).add(getPositionalArgument().orElse(""));
            for (Entry<String, String> e : getNamedArguments().entrySet()) {
                sj.add(TodoParser.FLAG_TOKEN + e.getKey()).add(e.getValue());
            }
            return sj.toString();
        }
    }

    private List<String> tokenize(String input) {
        input = input.trim();
        
        return Lists.newArrayList(Splitter
            .on(" ")
            .trimResults()
            .omitEmptyStrings()
            .split(input));
    }
    
    private String parseFlag(String token) {
        return token.substring(1, token.length());
    }
    
    private boolean isFlag(String token) {
        return token.startsWith(TodoParser.FLAG_TOKEN) && token.length() > 1;
    }
    
    @Override
    public ParseResult parse(String input) {
        List<String> tokens = tokenize(input);
        Map<String, String> named = new HashMap<>();
        
        // Pull out the command, exit if there's no more things to parse
        String command = tokens.remove(0).toLowerCase();
        
        // Parse out positional argument
        StringJoiner sj = new StringJoiner(" ");
        while (!tokens.isEmpty() && !isFlag(tokens.get(0))) {
            sj.add(tokens.remove(0));
        }
        String positional = sj.toString();
        
        // If there are no more tokens return immediately
        if (tokens.isEmpty()) {
            return new TodoResult(command, positional, named);
        }
        
        // Parse out named arguments
        String flag = tokens.remove(0);
        sj = new StringJoiner(" ");
        while (!tokens.isEmpty()) {
            if (isFlag(tokens.get(0))) {
                named.put(parseFlag(flag), sj.toString());
                flag = tokens.remove(0);
                sj = new StringJoiner(" ");
            } else {
                sj.add(tokens.remove(0));
            }
        }
        named.put(parseFlag(flag), sj.toString());
        
        return new TodoResult(command, positional, named);
    }
    
}
