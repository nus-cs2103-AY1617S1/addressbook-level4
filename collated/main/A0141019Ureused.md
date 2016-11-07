# A0141019Ureused
###### \java\seedu\address\commons\util\StringUtil.java
``` java
	/**
     * Case-insensitive substring method
     * @return true if query is contained in source
     */
	public static boolean containsIgnoreCase(String source, String query) {
        return source.toLowerCase().contains(query.toLowerCase());
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t){
        assert t != null;
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if s represents an unsigned integer e.g. 1, 2, 3, ... <br>
     *   Will return false for null, empty string, "-1", "0", "+1", and " 2 " (untrimmed) "3 0" (contains whitespace).
     * @param s Should be trimmed.
     */
    public static boolean isUnsignedInteger(String s){
        return s != null && s.matches("^0*[1-9]\\d*$");
    }
    
```
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
/**
 * Deletes a task identified using its displayed index in the last task listing .
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "del";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX [MORE_INDICES] ... \n"
            + "Example: " + COMMAND_WORD + " 1 3";
 
    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted tasks: %1$s";

    private final int[] targetIndices;

    
    public DeleteCommand(int[] targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute() {
    	doExecutionSetup();

        try {
        	List<ReadOnlyTask> tasksToDelete = getTasksToDelete();
            deleteTasks(tasksToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, tasksToDelete));
        } 
        catch (IllegalValueException e) {
        	indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(e.getMessage());
        }
        catch (TaskNotFoundException pnfe) {
        	undoModelSaveState();
        	return new CommandResult(Messages.MESSAGE_INDEX_NOT_IN_LIST);
        }
    }
    
    private void doExecutionSetup() {
    	EventsCenter.getInstance().post(new DisplayTaskListEvent(model.getFilteredTaskList()));

    	model.checkForOverdueTasks();
    	model.saveState();
    }
    
    
    private List<ReadOnlyTask> getTasksToDelete() throws IllegalValueException {
    	UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        List<ReadOnlyTask> tasksToDelete = new ArrayList<>();
        
        for (int i=0; i<targetIndices.length; i++) {
        	if (lastShownList.size() < targetIndices[i]) {
        		throw new IllegalValueException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
            }

            tasksToDelete.add(lastShownList.get(targetIndices[i] - 1));
        }
        
        return tasksToDelete;
    }
    
    
    private void deleteTasks(List<ReadOnlyTask> tasksToDelete) throws TaskNotFoundException {
    	model.deleteTasks(tasksToDelete);
    }
    
    private void undoModelSaveState() {
    	model.undoSaveState();
    }
}
```
###### \java\seedu\address\logic\parser\Parser.java
``` java
	public Command parseCommand(String userInput) {
		String replacedInput;
		try {
			replacedInput = replaceAliases(userInput);
		} catch (IllegalArgumentException e) {
			return new IncorrectCommand(e.getMessage());
		}
		
		System.out.println("original: " + userInput);
		System.out.println("replaced: " + replacedInput);
		
		final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(replacedInput.trim());
		if (!matcher.matches()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
		}

		final String commandWord = matcher.group("commandWord").trim();
		final String arguments = matcher.group("arguments").trim();

		switch (commandWord) {
		case AddCommand.COMMAND_WORD:
			return prepareAdd(arguments);
			
		case FindCommand.COMMAND_WORD:
			return prepareFind(arguments);

		case ListCommand.COMMAND_WORD:
			return prepareList(arguments);

		case DeleteCommand.COMMAND_WORD:
			return prepareDelete(arguments);

		case EditCommand.COMMAND_WORD:
			return prepareEdit(arguments);
			
		case SetStorageCommand.COMMAND_WORD:
			return prepareSetStorage(arguments);	
			
		case ChangeStatusCommand.COMMAND_WORD_DONE:
			return prepareChangeStatus(arguments, "done");
			
		case ChangeStatusCommand.COMMAND_WORD_PENDING:
			return prepareChangeStatus(arguments, "pending");
		
		case ClearCommand.COMMAND_WORD:
			return new ClearCommand();

		case HelpCommand.COMMAND_WORD:
			return new HelpCommand();

		case ExitCommand.COMMAND_WORD:
			return new ExitCommand();

		case UndoCommand.COMMAND_WORD:
			return new UndoCommand();

		case RedoCommand.COMMAND_WORD:
			return new RedoCommand();
		
		case AddAliasCommand.COMMAND_WORD:
			return prepareAddAlias(arguments);
			
		case ListAliasCommand.COMMAND_WORD:
			return new ListAliasCommand();
			
		case DeleteAliasCommand.COMMAND_WORD:
			return prepareDeleteAlias(arguments);
			
		case TabCommand.COMMAND_WORD:
			return prepareTabCommand(arguments);

		default:
			return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
		}
	}
	
	
```
