package seedu.todo.ui;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

import seedu.todo.MainApp;
import seedu.todo.commons.exceptions.ParseException;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.controllers.*;

public class InputHandler {
    
    private static InputHandler instance;
    
    private static final int MAX_HISTORY_SIZE = 20;
    private static LinkedList<String> commandHistory = new LinkedList<String>();
    private static ListIterator<String> commandHistoryIterator = commandHistory.listIterator();

    private Controller handlingController = null;
    
    protected InputHandler() {
        // Prevent instantiation.
    }
    
    /**
     * Gets the current input handler instance.
     * @@author A0139812A
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
     * @@author A0139812A
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
     * @@author A0139812A
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
     * @@author A0139812A
     */
    public String getNextCommandFromHistory() {
        if (!commandHistoryIterator.hasNext()) {
            return "";
        }
        
        return commandHistoryIterator.next();
    }

    /**
     * @@author A0093907W
     * 
     * Processes the command. Returns true if the command was intercepted by a controller, false if otherwise.
     * If the command was not intercepted by a controller, it means that the command was not recognized.
     */
    public boolean processInput(String input) {
        
        Map<String, String> aliases = MainApp.getConfig().getAliases();
        String aliasedInput = StringUtil.replaceAliases(input, aliases);
        Controller selectedController = null;
        
        if (this.handlingController != null) {
            selectedController = handlingController;
        } else {
            Controller[] controllers = instantiateAllControllers();

            // Get controller which has the maximum confidence.
            Controller maxController = getMaxController(aliasedInput, controllers);

            // No controller exists with confidence > 0.
            if (maxController == null) {
                return false;
            }

            // Select best-matched controller.
            selectedController = maxController;

        }
        
        // Process using best-matched controller.
        boolean isProcessSuccess = processWithController(input, aliasedInput, selectedController);
        
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
     * Get controller which has the maximum confidence.
     * 
     * @param aliasedInput  Input with aliases replaced appropriately
     * @param controllers   Array of instantiated controllers to test
     * @return              Controller with maximum confidence, null if all non-positive.
     */
    private Controller getMaxController(String aliasedInput, Controller[] controllers) {
        // Define the controller which returns the maximum confidence.
        Controller maxController = null;
        
        float maxConfidence = Integer.MIN_VALUE;

        for (int i = 0; i < controllers.length; i++) {
            float confidence = controllers[i].inputConfidence(aliasedInput);

            // Don't consider controllers with non-positive confidence.
            if (confidence <= 0) {
                continue;
            }

            if (confidence > maxConfidence) {
                maxConfidence = confidence;
                maxController = controllers[i];
            }
        }
        return maxController;
    }
    
    private Controller[] instantiateAllControllers() {
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
