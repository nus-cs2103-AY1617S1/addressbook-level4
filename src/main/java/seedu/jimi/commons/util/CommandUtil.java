package seedu.jimi.commons.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.jimi.logic.commands.AddCommand;
import seedu.jimi.logic.commands.ClearCommand;
import seedu.jimi.logic.commands.Command;
import seedu.jimi.logic.commands.CompleteCommand;
import seedu.jimi.logic.commands.DeleteCommand;
import seedu.jimi.logic.commands.EditCommand;
import seedu.jimi.logic.commands.ExitCommand;
import seedu.jimi.logic.commands.FindCommand;
import seedu.jimi.logic.commands.HelpCommand;
import seedu.jimi.logic.commands.RedoCommand;
import seedu.jimi.logic.commands.SaveAsCommand;
import seedu.jimi.logic.commands.ShowCommand;
import seedu.jimi.logic.commands.UndoCommand;

// @@author A0140133B
/**
 * Represents a singleton manager for utility methods related to commands.
 */
public class CommandUtil {
    
    private static CommandUtil instance;
    
    private List<Command> cmdStubList;
    
    private List<String> allCmdWords;
    
    private HashMap<String, Set<String>> cmdWordNearMatches;
    
    private CommandUtil() {
        populateCommandStubList();
        populateCommandWords();
        populateCommandWordsNearMatches();
    }
    
    /** Returns a string of all command words in {@code cmdStubList} joined by a comma. */
    public String commandsToString() {
        return allCmdWords.stream()
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.joining(", "));
    }
    
    public List<Command> getCommandStubList() {
        return cmdStubList;
    }
    
    /** Returns the only instance in the singleton manager. */
    public static CommandUtil getInstance() {
        if (instance == null) {
            instance = new CommandUtil();
        }
        return instance;
    }
    
    /** Returns a list of command words that nearly matches {@code target} */
    public List<String> getCommandWordMatches(String target) {
        return cmdStubList.stream()
                .filter(c -> c.isValidCommandWord(target) 
                        || StringUtil.isValidSubstrings(c.getCommandWord(), target)
                        || (cmdWordNearMatches.containsKey(target)
                                && cmdWordNearMatches.get(target).contains(c.getCommandWord())))
                .map(c -> c.getCommandWord())
                .collect(Collectors.toList());
    }
    
    /*
     * ==========================================================
     *                  Initialization Methods
     * ==========================================================
     */
    
    private void populateCommandWordsNearMatches() {
        assert cmdStubList != null && allCmdWords != null;
        cmdWordNearMatches = new HashMap<String, Set<String>>();
        allCmdWords.forEach(cmdWord -> {
            Set<String> oneEditDistanceDict = Dictionary.generateOneEditDistanceDict(cmdWord);
            
            Set<String> twoEditDistanceDict = new HashSet<String>();
            oneEditDistanceDict.forEach(w -> twoEditDistanceDict.addAll(Dictionary.generateOneEditDistanceDict(w)));
            
            for (String twoEditDistanceWord : twoEditDistanceDict) {
                Set<String> toPut = cmdWordNearMatches.containsKey(twoEditDistanceWord)
                        ? cmdWordNearMatches.get(twoEditDistanceWord) : new HashSet<String>();
                toPut.add(cmdWord);
                cmdWordNearMatches.put(twoEditDistanceWord, toPut);
            }
        });
    }
    
    /** Creating a list of commands available in Jimi. */
    private void populateCommandStubList() {
        cmdStubList = Arrays.asList(
                new AddCommand(), 
                new EditCommand(), 
                new CompleteCommand(), 
                new ShowCommand(), 
                new DeleteCommand(),
                new ClearCommand(), 
                new FindCommand(), 
                new UndoCommand(),
                new RedoCommand(),
                new ExitCommand(), 
                new HelpCommand(), 
                new SaveAsCommand()
        );
    }

    /** Creating a list of commands words from commands in {@code cmdStubList} */
    private void populateCommandWords() {
        assert cmdStubList != null;
        allCmdWords = cmdStubList.stream()
                .map(c -> c.getCommandWord())
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.toList());
    }

}
