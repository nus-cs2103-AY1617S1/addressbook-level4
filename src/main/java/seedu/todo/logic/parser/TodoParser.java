package seedu.todo.logic.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;


/**
 * Parses a input string into the command, positional argument and named arguments. 
 * The format looks like: 
 * 
 * <pre><code>command positional [-f or --flag named]...</code></pre>
 * 
 * There will only be one command, optionally one positional argument, and 
 * optionally many named arguments. 
 */
public class TodoParser {
    private static final Pattern FLAG_REGEX = Pattern.compile("^(?:-(\\w)|--(\\w{2,}))$"); 
    
    private final String command; 
    private final String positional;
    private final Map<String, String> named = new HashMap<>();
    
    public TodoParser(String input) {
        List<String> tokens = tokenize(input);
        
        // Pull out the command, exit if there's no more things to parse
        command = tokens.remove(0).toLowerCase();
        
        // Parse out positional argument
        StringJoiner sj = new StringJoiner(" ");
        while (!tokens.isEmpty() && !isFlag(tokens.get(0))) {
            sj.add(tokens.remove(0));
        }
        positional = sj.toString();
        
        // Parse out named arguments 
        if (tokens.isEmpty()) {
            return;
        }
        
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
        return token.replace("-", "");
    }
    
    private boolean isFlag(String token) {
        return FLAG_REGEX.matcher(token).matches();
    }
    
    public String getComand() {
        return command;
    }
    
    public Optional<String> getPositionalArgument() {
        return Optional.ofNullable(positional.isEmpty() ? null : positional);
    }
    
    public Map<String, String> getNamedArguments() {
        return named;
    }
    
}
