package seedu.todo.ui;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

import seedu.todo.MainApp;
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

    public boolean processInput(String input) {
        
        Map<String, String> aliases = MainApp.getConfig().getAliases();
        String aliasedInput = StringUtil.replaceAliases(input, aliases);
        
        if (this.handlingController != null) {
            handlingController.process(aliasedInput);
        } else {
            Controller[] controllers = instantiateAllControllers();

            // Define the controller which returns the maximum confidence.
            Controller maxController = null;

            // Get controller which has the maximum confidence.
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

            // No controller exists with confidence > 0.
            if (maxController == null) {
                return false;
            }

            // Process using best-matched controller.
            maxController.process(aliasedInput);

        }
        
        // Since command is not invalid, we push it to history
        pushCommand(aliasedInput);

        return true;
    }
    
    private Controller[] instantiateAllControllers() {
        return new Controller[] { new AliasController(),
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
                                  new ExitController() };
    }

}
