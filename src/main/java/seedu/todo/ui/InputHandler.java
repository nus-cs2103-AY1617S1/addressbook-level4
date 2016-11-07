package seedu.todo.ui;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

import seedu.todo.commons.core.ConfigCenter;
import seedu.todo.commons.exceptions.ParseException;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.controllers.*;

// @@author A0139812A
/**
 * InputHandler is the bridge between the UI, or more 
 * specifically the Console, and the Controllers. 
 * 
 * It will match the correct Controller to handle the command
 * based on the command words.
 * 
 * It will also maintain a command history (similar to that of a
 * terminal console) where the user can interact with using
 * up and down arrow keys.
 */
public class InputHandler {
    
    private static InputHandler instance;
    
    private static final int MAX_HISTORY_SIZE = 20;
    private static LinkedList<String> commandHistory = new LinkedList<String>();
    private static ListIterator<String> commandHistoryIterator = commandHistory.listIterator();

    private static final String CHAR_SPACE = " ";
    
    protected InputHandler() {
        // Prevent instantiation.
    }
    
    /**
     * Gets the current input handler instance.
     */
    public static InputHandler getInstance() {
        if (instance == null) {
            instance = new InputHandler();
        }
        
        return instance;
    }
    
    /**
     * Pushes a command to the end of a LinkedList.
     * Commands are stored like a queue, where the oldest items 
     * are at the start of the List and will be popped off first.
     * 
     * @param command   Command string
     */
    private void pushCommand(String command) {
        // Adds to the end of the LinkedList.
        commandHistory.addLast(command);
        
        // Truncates the list when it gets too big.
        if (commandHistory.size() > MAX_HISTORY_SIZE) {
            commandHistory.removeFirst();
        }
        
        // Create a new iterator, initialize position to point right at the end.
        commandHistoryIterator = commandHistory.listIterator(commandHistory.size());
    }
    
    /**
     * Gets the previous command from the command history. Successive calls will return commands earlier in history.
     * 
     * @return  The input command earlier than what was previously retrieved
     */
    public String getPreviousCommandFromHistory() {
        if (!commandHistoryIterator.hasPrevious()) {
            return "";
        }
        
        return commandHistoryIterator.previous();
    }
    
    /**
     * Gets the next command from the command history. Successive calls will return commands later in history.
     * 
     * @return  The input command later than what was previously retrieved
     */
    public String getNextCommandFromHistory() {
        if (!commandHistoryIterator.hasNext()) {
            return "";
        }
        
        return commandHistoryIterator.next();
    }

    /**
     * Processes the command. Returns true if the command was intercepted by a controller, false if otherwise.
     * If the command was not intercepted by a controller, it means that the command was not recognized.
     */
    public boolean processInput(String input) {
        
        Map<String, String> aliases = ConfigCenter.getInstance().getConfig().getAliases();
        String aliasedInput = StringUtil.replaceAliases(input, aliases);
        
        Controller[] controllers = instantiateAllControllers();
        
        // Extract keyword.
        String keyword = extractKeyword(aliasedInput);

        // Get controller which has the maximum confidence.
        Controller matchingController = getMatchingController(keyword, controllers);

        // If command keyword did not match any controllers, console will show invalid command.
        if (matchingController == null) {
            return false;
        }
        
        // Patch input commands.
        input = patchCommandKeyword(input);
        aliasedInput = patchCommandKeyword(aliasedInput);
        
        // Process using matched controller.
        boolean isProcessSuccess = processWithController(input, aliasedInput, matchingController);
        
        // Catch commands which throw errors here.
        if (!isProcessSuccess) {
            return false;
        }
        
        // Since command is not invalid, we push it to history
        pushCommand(aliasedInput);

        return true;
    }

    /**
     * Process an input/aliasedInput with a selected controller.
     * 
     * Note that for proper functioning, <code>alias</code> and
     * <code>unalias</code> will receive the <code>input</code> instead of
     * <code>aliasedInput</code> for proper functioning.
     * 
     * @param input                 Raw user input
     * @param aliasedInput          Input with aliases replaced
     * @param selectedController    Controller to process input
     * @return                      true if processing was successful, false otherwise
     */
    private boolean processWithController(String input, String aliasedInput, Controller selectedController) {
        try {
            // Alias and unalias should not receive an aliasedInput for proper functioning.
            if (selectedController.getClass() == AliasController.class ||
                    selectedController.getClass() == UnaliasController.class) {
                selectedController.process(input);
            } else {
                selectedController.process(aliasedInput);
            }
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Get controller which matches the command keyword.
     * 
     * @param aliasedCommandKeyword  Input with aliases replaced appropriately
     * @param controllers   Array of instantiated controllers to test
     * @return              Matching controller.
     */
    private Controller getMatchingController(String aliasedCommandKeyword, Controller[] controllers) {
        for (Controller controller : controllers) {
            if (controller.matchCommandKeyword(aliasedCommandKeyword)) {
                return controller;
            }
        }
        
        return null;
    }
    
    /**
     * Extracts the command keyword from the input command.
     */
    private String extractKeyword(String inputCommand) {
        String[] commandWords = inputCommand.trim().split(CHAR_SPACE);
        
        if (commandWords.length < 1) {
            return "";
        }
        
        return commandWords[0].toLowerCase();
    }
    
    /**
     * Replaces the first word in a command input string to prepare it for parsing and/or disambiguating.
     */
    private String patchCommandKeyword(String inputCommand) {
        String[] commandWords = inputCommand.trim().split(CHAR_SPACE);
        
        if (commandWords.length < 1) {
            return "";
        } else {
            commandWords[0] = extractKeyword(inputCommand);
        }
        
        return String.join(CHAR_SPACE, commandWords);
    }
    
    private static Controller[] instantiateAllControllers() {
        return new Controller[] { new AliasController(),
                                  new UnaliasController(),
                                  new HelpController(),
                                  new AddController(),
                                  new ListController(),
                                  new DestroyController(),
                                  new CompleteTaskController(),
                                  new UncompleteTaskController(),
                                  new UpdateController(),
                                  new UndoController(),
                                  new RedoController(),
                                  new ConfigController(),
                                  new ClearController(),
                                  new FindController(),
                                  new TagController(),
                                  new UntagController(),
                                  new ExitController() };
    }

}
