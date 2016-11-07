# A0147986Hunused
###### \java\seedu\unburden\commons\util\StringUtil.java
``` java
    
    public static String getTaskDetails(ArrayList<ReadOnlyTask> taskList){
    	StringBuilder resultedList=new StringBuilder();
    	for(ReadOnlyTask p:taskList){
    		resultedList.append(p.toString());
    		resultedList.append("\n\n");
    	}
    	return resultedList.toString();
    }
}
```
###### \java\seedu\unburden\logic\commands\SelectCommand.java
``` java
/**
 * Selects a task identified using it's last displayed index from the address book.
 * It will also show the details of the task selected, especially
 * task descriptions that may not be able to show on the taskList panel
 * This is unused because I did not discuss with my teammates in advanced so they decided 
 * not to include this method
 */
public class SelectCommand extends Command {

    public final int taskIndex;

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer that does not exceed the maximum number of tasks)\n"
            + "Format: select INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";

    public SelectCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    
    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < taskIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(taskIndex - 1));
        return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, lastShownList.get(taskIndex-1)));
    }
}
```
###### \java\seedu\unburden\logic\commands\UnwantedDeleteCommand.java
``` java
/**
 * Deletes a task or a set of tasks identified 
 * using it's last displayed index from the address book.
 * This is unused because I did not discuss with my teammates in advanced so they decided 
 * not to include this method
 */
public class UnwantedDeleteCommand extends Command {

	public static final String COMMAND_WORD = "multipledelete";

	public static final String MESSAGE_USAGE = COMMAND_WORD
			+ ": Deletes the task identified by the index or a range of indexes\n"
			+ "(must be positive integer)used in the last task listing.\n"
			+ "Format1: multipledelete index1-index2\n"
			+ "Example: " + COMMAND_WORD + " 1-3\n"
			+ "Format2: multipledelete index1 index2 index3 index4 index5\n"
			+ "Example: " + COMMAND_WORD + " 1 2 3";

	public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task:\n%1$s";

	public final ArrayList<Integer> targetIndexes;

	public UnwantedDeleteCommand(ArrayList<Integer> targetIndexes) {
		this.targetIndexes = targetIndexes;
	}

	@Override
	public CommandResult execute() throws DuplicateTagException, IllegalValueException {

		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
		ArrayList<ReadOnlyTask> deletedTaskList=new ArrayList<ReadOnlyTask>();

		if (lastShownList.size() < targetIndexes.get(targetIndexes.size()-1)) {
			indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		}

		model.saveToPrevLists();
		
		for(int i = 0; i < targetIndexes.size(); i++){

			ReadOnlyTask taskToDelete = lastShownList.get(targetIndexes.get(i) - i - 1);
			deletedTaskList.add(taskToDelete);

			try {
				model.deleteTask(taskToDelete);
			} catch (TaskNotFoundException pnfe) {
				assert false : "The target task cannot be missing";
			}
		}
		return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTaskList)));         
	}    
	
	
}

```
###### \java\seedu\unburden\logic\parser\Parser.java
``` java
	private static final Pattern INDEX_PHASE_FORMAT = Pattern.compile("(?<targetIndex>\\d+-\\d+)");
			
	private static final Pattern INDEX_LIST_FORMAT = Pattern.compile("(?<targetIndex>\\d+(\\s+\\d+)*)");

```
###### \java\seedu\unburden\logic\parser\Parser.java
``` java
	/**
	 * Parses arguments in the context of the delete person command.
	 *
	 * @param args
	 *            full command args string
	 * @return the prepared command
	 * This is unused because I did not discuss with my teammates in advanced so they decided 
     * not to include this method
	 */
	/*
	private Command prepareUnwantedDelete(String args) throws ParseException {
		final Matcher matcherList = INDEX_LIST_FORMAT.matcher(args.trim());
		final Matcher matcherPhase = INDEX_PHASE_FORMAT.matcher(args.trim());  

		if(!matcherList.matches()&&!matcherPhase.matches()){
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnwantedDeleteCommand.MESSAGE_USAGE));		
		}

		if(matcherPhase.matches()){
			String indexes_phase = matcherPhase.group("targetIndex");
			String[] SeperateIndexes_phase = indexes_phase.trim().split("-");
			ArrayList<Integer> sortList = new ArrayList<> ();
			ArrayList<Integer> indexesInt_phase = new ArrayList<> ();
			Optional<Integer> index_list = parseIndex(SeperateIndexes_phase[0]);

			if (!index_list.isPresent()) {
				return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnwantedDeleteCommand.MESSAGE_USAGE));
			}			
			
			Optional<Integer> index_list2 = parseIndex(SeperateIndexes_phase[1]);
			if (!index_list2.isPresent()) {
				return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnwantedDeleteCommand.MESSAGE_USAGE));
			}

			sortList.add(Integer.parseInt(SeperateIndexes_phase[0]));
			sortList.add(Integer.parseInt(SeperateIndexes_phase[1]));

			Collections.sort(sortList);

			for(int i= sortList.get(0); i<=sortList.get(1); i++){   
				indexesInt_phase.add(i);
			}
			Collections.sort(indexesInt_phase);
			return new UnwantedDeleteCommand(indexesInt_phase);
		}

		else if(matcherList.matches()){
			String indexes_list = matcherList.group("targetIndex");     
			String[] SeperateIndexes_list = indexes_list.split(" ");
			ArrayList<Integer> indexesInt_list = new ArrayList<> ();
			
			for(int i=0; i<(SeperateIndexes_list.length); i++){
				Optional<Integer> index_list = parseIndex(SeperateIndexes_list[i]);
				if (!index_list.isPresent()) {
					return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnwantedDeleteCommand.MESSAGE_USAGE));
				}
				indexesInt_list.add(Integer.parseInt(SeperateIndexes_list[i]));
			}	
			indexesInt_list = (ArrayList<Integer>) indexesInt_list.stream().distinct().collect(Collectors.toList());
			Collections.sort(indexesInt_list);
			return new UnwantedDeleteCommand(indexesInt_list); 
		}

		else
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnwantedDeleteCommand.MESSAGE_USAGE));	

	}
	*/
	
```
###### \java\seedu\unburden\model\tag\UniqueTagList.java
``` java
	/**check whether the internalList is empty
	 */
	public boolean isEmpty(){
		return internalList.isEmpty();
	}
}
```
###### \java\seedu\unburden\model\task\ReadOnlyTask.java
``` java
	/**
	 * Formats the task as text, showing all contact details.
	 *  This is unused because I did not discuss with my teammates in advanced so they decided 
     * not to include this method
	 */
	default String getAsText() {
		final StringBuilder builder = new StringBuilder();

		// Floating task
		if (getTaskDescription().getFullTaskDescription() == "" && getDate().getFullDate() == ""
				&& getStartTime().getFullTime() == "" && getEndTime().getFullTime() == "") {
			builder.append(getName());
			if (!getTags().isEmpty()) {
				builder.append("\n");
				builder.append("Tags : ");
				builder.append("\n");
				getTags().forEach(builder::append);
			}
		}

		// Floating task with task description
		else if (getTaskDescription().getFullTaskDescription() != "" && getDate().getFullDate() == ""
				&& getStartTime().getFullTime() == "" && getEndTime().getFullTime() == "") {
			builder.append(getName());
			builder.append("\n");
			builder.append("Task Description : ");
			builder.append(getTaskDescription());
			builder.append("\n");
			if (!getTags().isEmpty()) {
				builder.append("\n");
				builder.append("Tags : ");
				builder.append("\n");
				getTags().forEach(builder::append);
			}

		}

		// Task with deadline
		else if (getTaskDescription().getFullTaskDescription() == "" && getDate().getFullDate() != ""
				&& getStartTime().getFullTime() == "" && getEndTime().getFullTime() == "") {
			builder.append(getName());
			builder.append("\n");
			builder.append("Deadline : ");
			builder.append(getDate());
			if (!getTags().isEmpty()) {
				builder.append("\n");
				builder.append("Tags : ");
				builder.append("\n");
				getTags().forEach(builder::append);
			}
		}

		// Task with deadline and task description
		else if (getTaskDescription().getFullTaskDescription() != "" && getDate().getFullDate() != ""
				&& getStartTime().getFullTime() == "" && getEndTime().getFullTime() == "") {
			builder.append(getName());
			builder.append("\n");
			builder.append("Task Description : ");
			builder.append(getTaskDescription());
			builder.append("\n");
			builder.append("Deadline : ");
			builder.append(getDate());
			if (!getTags().isEmpty()) {
				builder.append("\n");
				builder.append("Tags : ");
				builder.append("\n");
				getTags().forEach(builder::append);
			}
		}

		// Task with deadline and end date
		else if (getTaskDescription().getFullTaskDescription() == "" && getDate().getFullDate() != ""
				&& getStartTime().getFullTime() == "" && getEndTime().getFullTime() != "") {
			builder.append(getName());
			builder.append("\n");
			builder.append("Deadline : ");
			builder.append(getDate());
			builder.append("\n");
			builder.append("End time : ");
			builder.append(getEndTime() + "   ");
			if (!getTags().isEmpty()) {
				builder.append("\n");
				builder.append("Tags : ");
				builder.append("\n");
				getTags().forEach(builder::append);
			}
		}

		// Task with deadline and end date and task description
		else if (getTaskDescription().getFullTaskDescription() != "" && getDate().getFullDate() != ""
				&& getStartTime().getFullTime() == "" && getEndTime().getFullTime() != "") {
			builder.append(getName());
			builder.append("\n");
			builder.append("Task Description : ");
			builder.append(getTaskDescription());
			builder.append("\n");
			builder.append("Deadline : ");
			builder.append(getDate());
			builder.append("\n");
			builder.append("End time : ");
			builder.append(getEndTime() + "   ");
			if (!getTags().isEmpty()) {
				builder.append("\n");
				builder.append("Tags : ");
				builder.append("\n");
				getTags().forEach(builder::append);
			}
		}

		// Task with deadline, start time and end time
		else if (getTaskDescription().getFullTaskDescription() == "" && getDate().getFullDate() != ""
				&& getStartTime().getFullTime() != "" && getEndTime().getFullTime() != "") {
			builder.append(getName());
			builder.append("\n");
			builder.append("Deadline : ");
			builder.append(getDate());
			builder.append("\n");
			builder.append("Start Time - End time : ");
			builder.append(getStartTime() + " - ");
			builder.append(getEndTime() + "   ");
			if (!getTags().isEmpty()) {
				builder.append("\n");
				builder.append("Tags : ");
				builder.append("\n");
				getTags().forEach(builder::append);
			}
		}

		// Task with deadline, task description, start time and end time
		else {
			builder.append(getName());
			builder.append("\n");
			builder.append("Task Description : ");
			builder.append(getTaskDescription());
			builder.append("\n");
			builder.append("Deadline : ");
			builder.append(getDate());
			builder.append("\n");
			builder.append("Start Time - End time : ");
			builder.append(getStartTime() + " - ");
			builder.append(getEndTime() + "   ");
			if (!getTags().isEmpty()) {
				builder.append("\n");
				builder.append("Tags : ");
				builder.append("\n");
				getTags().forEach(builder::append);
			}
		}

		return builder.toString();
	}

	/**
	 * Returns a string representation of this Task's tags
	 */
	default String tagsString() {
		final StringBuffer buffer = new StringBuffer();
		final String separator = ", ";
		getTags().forEach(tag -> buffer.append(tag).append(separator));
		if (buffer.length() == 0) {
			return "";
		} else {
			return buffer.substring(0, buffer.length() - separator.length());
		}
	}

}
```
