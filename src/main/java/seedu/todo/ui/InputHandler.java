package seedu.todo.ui;

import java.util.LinkedList;
import java.util.ListIterator;

import seedu.todo.controllers.*;

public class InputHandler {
    
    private static InputHandler instance;
    
    private static final int MAX_HISTORY_SIZE = 20;
    private static LinkedList<String> commandHistory = new LinkedList<String>();
    private static ListIterator<String> commandHistoryIterator;

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
     * Pushes a command to the start of a LinkedList.
     * @param command   Command string
     */
    private void pushCommand(String command) {
        // Adds to the start of the LinkedList.
        commandHistory.addFirst(command);
        
        // Truncates the list when it gets too big.
        if (commandHistory.size() > MAX_HISTORY_SIZE) {
            commandHistory.removeLast();
        }
        
        // Create a new iterator
        commandHistoryIterator = commandHistory.listIterator();
    }
    
    /**
     * Gets the previous command from the command history. Successive calls will return commands earlier in history.
     * 
     * @return  The input command earlier than what was previously retrieved
     */
    public String getPreviousCommandFromHistory() {
        if (!commandHistoryIterator.hasNext()) {
            return "";
        }
        
        return commandHistoryIterator.next();
    }
    
    /**
     * Gets the next command from the command history. Successive calls will return commands later in history.
     * 
     * @return  The input command later than what was previously retrieved
     */
    public String getNextCommandFromHistory() {
        if (!commandHistoryIterator.hasPrevious()) {
            return "";
        }
        
        return commandHistoryIterator.previous();
    }

    public boolean processInput(String input) {
        // Push to history
        pushCommand(input);
        
        if (this.handlingController != null) {
            handlingController.process(input);
        } else {
            Controller[] controllers = instantiateAllControllers();

            // Define the controller which returns the maximum confidence.
            Controller maxController = null;

            // Get controller which has the maximum confidence.
            float maxConfidence = Integer.MIN_VALUE;

            for (int i = 0; i < controllers.length; i++) {
                float confidence = controllers[i].inputConfidence(input);

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
            maxController.process(input);

        }

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
