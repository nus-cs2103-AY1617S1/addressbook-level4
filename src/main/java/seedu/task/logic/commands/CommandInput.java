package seedu.task.logic.commands;

import java.util.HashMap;
import java.util.Optional;

public class CommandInput {
    private String command;
    private String mainInput;
    private HashMap<String, String> keywordArguments;
    
    public CommandInput(String command, String mainInput, HashMap<String, String> keywordArguments) {
        this.command = command;
        this.mainInput = mainInput;
        this.keywordArguments = new HashMap<String, String>(keywordArguments);
    }
    
    public String getCommand() {
        return command;
    }
    
    public String getMainInput() {
        return mainInput;
    }
    
    public Optional<String> getKeywordArgument(String keyword) {
        if (keywordArguments.containsKey(keyword)) {
            return Optional.of(keywordArguments.get(keyword));
        } else {
            return Optional.empty();
        }
    }
}
