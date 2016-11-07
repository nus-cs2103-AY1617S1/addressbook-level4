# A0125534L
###### /java/seedu/task/commons/events/storage/StorageLocationChangedEvent.java
``` java
 * */

import seedu.task.commons.core.Config;
import seedu.task.commons.events.BaseEvent;

public class StorageLocationChangedEvent extends BaseEvent {
    
    private Config config;
    
    public StorageLocationChangedEvent(Config config) {
        this.config = config;
    }
    
    public Config getConfig() {
        return config;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### /java/seedu/task/logic/commands/HelpCommand.java
``` java
 */
public class HelpCommand extends Command {

	public final String commandWord;
	public boolean isPopUp;
	public static final String COMMAND_WORD = "help";

	public static final String MESSAGE_USAGE = COMMAND_WORD + "\n"
			+ "Shows program commands usage instructions.\n" 
			+ "Example: " + COMMAND_WORD + "\n\n"  
			+ "List of available commands for help\n" + COMMAND_WORD + " add\n" + COMMAND_WORD + " delete\n"
			+ COMMAND_WORD + " find\n" + COMMAND_WORD + " list\n" + COMMAND_WORD + " select\n" + COMMAND_WORD
			+ " mark\n" + COMMAND_WORD + " undo\n" + COMMAND_WORD + " show\n" + COMMAND_WORD + " save\n" + COMMAND_WORD + " clear\n" + COMMAND_WORD + " exit\n"
			+ "Parameters: help [KEY_WORD]\n"
			+ "Example: "+ COMMAND_WORD + " add\n\n";
	
	public HelpCommand(String commandWord, boolean helpWindowPopUp) { //values passed from help parser
		this.commandWord = commandWord;
		this.isPopUp = helpWindowPopUp;
	}

	@Override
	public CommandResult execute() {

		if (isPopUp == true) { //check if there is a need to have the help popup window
			EventsCenter.getInstance().post(new ShowHelpEvent());
			return new CommandResult(commandWord);
		} else {
			return new CommandResult(commandWord);
		}
	
	}
}
```
###### /java/seedu/task/logic/commands/SaveCommand.java
``` java
 * */

public class SaveCommand extends Command {
    
    private Logger logger = LogsCenter.getLogger(SaveCommand.class.getName());
    
    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n" 
    		+ "Saves data file to new location specified. "
            + "New folders with the file can be auto-created as long as given directory is valid.\n"
    		+ "Main Directory will the dafault save location for any valid but unspecifed file path\n"
            + "Example: " + COMMAND_WORD + " C: /Users/Computin/Desktop/CS2103" + "Take note: No spacing after :\n"
            + "Parameters: FILEPATH (must be valid)\n"
            + "Example: " + COMMAND_WORD + " C:/Users/Computing/Desktop/CS2103";
    
    private static final String MESSAGE_SUCCESS = "Data successfully saved to new location.";
    private static final String MESSAGE_INVALID_PATH = "Filepath given is invalid. Filepath will be reset to old path." + "\n\n" + MESSAGE_USAGE;
    
    private static Config config;
    private String newStorageFilePath, oldStorageFilePath;
    private ReadOnlyTaskBook taskBookManager;
    private static JsonConfigStorage jsonConfigStorage;
    private static Storage storage;
    
    public SaveCommand(String newStorageFilePath) {
        this.oldStorageFilePath = config.getTaskBookFilePath();
        logger.info("Old file path: " + oldStorageFilePath);
        
        this.newStorageFilePath = newStorageFilePath.trim().replace("\\", "/") + "/dowat.xml";
        logger.info("New file path: " + this.newStorageFilePath);
        jsonConfigStorage = new JsonConfigStorage(Config.DEFAULT_CONFIG_FILE);
    }
    
    public static void setConfig(Config c) {
        config = c;
    }
    
    public static void setStorage(Storage s) {
        storage = s;
    }

    @Override
    public CommandResult execute() {
        assert config != null;
        assert jsonConfigStorage != null;

        taskBookManager = model.getTaskBook();
        
        config.setTaskBookFilePath(newStorageFilePath);
        indicateStorageLocationChanged();
        try {
            storage.saveTaskBook(taskBookManager, newStorageFilePath);
        } catch (IOException e) {
            handleInvalidFilePathException();
            return new CommandResult(MESSAGE_INVALID_PATH);
        }
        
        saveToConfigJson();
        model.updateFilteredTaskListToShowAll();
        model.updateFilteredEventListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    private void indicateStorageLocationChanged() {
        assert config != null;
        EventsCenter.getInstance().post(new StorageLocationChangedEvent(config));
    }
    
    private void handleInvalidFilePathException() { 
        logger.info("Error writing to filepath. Handling data save exception.");
        assert config != null;
        
        config.setTaskBookFilePath(oldStorageFilePath);  //set back to old filepath
        indicateStorageLocationChanged(); 
        indicateAttemptToExecuteIncorrectCommand();
        
        try {
            storage.saveTaskBook(taskBookManager, newStorageFilePath);
        } catch (IOException e) {
            logger.severe("Error saving task manager");
        }
        
        saveToConfigJson();
    }
    
    private void saveToConfigJson() {
        try {
            jsonConfigStorage.saveConfigFile(config);
        } catch (IOException e) {
            logger.severe("save to config json error");
        }
        
       
    }

}
```
###### /java/seedu/task/logic/commands/SelectCommand.java
``` java
 */

public abstract class SelectCommand extends Command {

	public int targetIndex;


	public static final String COMMAND_WORD = "select";
	

	public static final String MESSAGE_USAGE = COMMAND_WORD + "\n"
			+ "Selects an existing task/event from the TaskBook.\n\n"
			+ "Selects a task at the specified INDEX in the most recent task listing.\n"
			+ "Parameters: SELECT_TYPE + INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " /t"
			+ " 1\n\n" + "Selects a event at the specified INDEX in the most recent event listing.\n"
			+ "Parameters: SELECT_TYPE + INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " /e"
			+ " 1";
	
    @Override
    public abstract CommandResult execute();
}
		

```
###### /java/seedu/task/logic/commands/SelectEventCommand.java
``` java
 */

public class SelectEventCommand extends SelectCommand {

	public static final String MESSAGE_SELECT_EVENT_SUCCESS = "Selected Event: %1$s";

	public SelectEventCommand(int targetIndex) {
		this.targetIndex = targetIndex;
	}

	@Override
	public CommandResult execute() {

		UnmodifiableObservableList<ReadOnlyEvent> lastShownEventList = model.getFilteredEventList();

		//validation for input index greater than list size
		if (lastShownEventList.size() < targetIndex) { 
			indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
		}
		
		ReadOnlyEvent targetEvent = model.getFilteredEventList().get(targetIndex-1);
		
		EventsCenter.getInstance().post(new JumpToEventListRequestEvent(targetEvent, targetIndex - 1));
		return new CommandResult(String.format(MESSAGE_SELECT_EVENT_SUCCESS, targetIndex));

	}

}
```
###### /java/seedu/task/logic/commands/SelectTaskCommand.java
``` java
 */

public class SelectTaskCommand extends SelectCommand {

	public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";

	public SelectTaskCommand(int targetIndex) {
		this.targetIndex = targetIndex;
	}

	@Override
	public CommandResult execute() {

		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
		
		//validation for input index greater than list size
		if (lastShownList.size() < targetIndex) { 
			indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		}
		ReadOnlyTask targetTask = model.getFilteredTaskList().get(targetIndex-1);
		EventsCenter.getInstance().post(new JumpToTaskListRequestEvent(targetTask, targetIndex - 1));
		return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, targetIndex));

	}
}
```
###### /java/seedu/task/logic/parser/HelpParser.java
``` java
 */

public class HelpParser implements Parser {

	public HelpParser() {
	}

	private static final Pattern HELP_ARGS_FORMAT = Pattern.compile("(?<arguments>.*)");

	/**
	 * Parses arguments in the context of the help command.
	 *
	 * @param args
	 *            full command args string
	 * @return the prepared command
	 */

	public Command prepare(String args) {
		final Matcher matcher = HELP_ARGS_FORMAT.matcher(args.trim());
		if (!matcher.matches()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
		}

		switch (matcher.group("arguments")) {

		case AddCommand.COMMAND_WORD:
			return new HelpCommand(AddCommand.MESSAGE_USAGE, false);
		case EditCommand.COMMAND_WORD:
            return new HelpCommand(EditCommand.MESSAGE_USAGE, false);
		case DeleteCommand.COMMAND_WORD:
			return new HelpCommand(DeleteCommand.MESSAGE_USAGE, false);
		case FindCommand.COMMAND_WORD:
			return new HelpCommand(FindCommand.MESSAGE_USAGE, false);
		case ListCommand.COMMAND_WORD:
			return new HelpCommand(ListCommand.MESSAGE_USAGE, false);
		case SelectCommand.COMMAND_WORD:
			return new HelpCommand(SelectCommand.MESSAGE_USAGE, false);
		case MarkCommand.COMMAND_WORD:
			return new HelpCommand(MarkCommand.MESSAGE_USAGE, false);
		case UndoCommand.COMMAND_WORD:
			return new HelpCommand(UndoCommand.MESSAGE_USAGE, false);
		case CalendarCommand.COMMAND_WORD:
			return new HelpCommand(CalendarCommand.MESSAGE_USAGE, false);
		case SaveCommand.COMMAND_WORD:
			return new HelpCommand(SaveCommand.MESSAGE_USAGE, false);
		case ClearCommand.COMMAND_WORD:
			return new HelpCommand(ClearCommand.MESSAGE_USAGE, false);
		case ExitCommand.COMMAND_WORD:
			return new HelpCommand(ExitCommand.MESSAGE_USAGE, false);
		default:
			return new HelpCommand(HelpCommand.MESSAGE_USAGE, true);
			
			
		}
	}
}
```
###### /java/seedu/task/logic/parser/SaveParser.java
``` java
 */
public class SaveParser implements Parser {

	private static final Pattern SAVE_ARGS_FORMAT = Pattern.compile("(?<arguments>.*)");
	public SaveParser() {}

	public Command prepare(String args) {
		final Matcher matcher = SAVE_ARGS_FORMAT.matcher(args.trim());
			if (!matcher.matches()) {
			return new IncorrectCommand(
					String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
		}
			
		return new SaveCommand(args);
	}
}
```
###### /java/seedu/task/logic/parser/SelectParser.java
``` java
 */

public class SelectParser implements Parser {

	public SelectParser() {
	}

	// '/' forward slashes are reserved for delimiter prefixes
	private static final Pattern SELECT_TASK_DATA_FORMAT = Pattern.compile("(?:/t)\\s(?<index>\\d*)");

	private static final Pattern SELECT_EVENT_DATA_FORMAT = Pattern.compile("(?:/e)\\s(?<index>\\d*)");

	@Override
	public Command prepare(String args) {
		final Matcher taskMatcher = SELECT_TASK_DATA_FORMAT.matcher(args.trim());
		final Matcher eventMatcher = SELECT_EVENT_DATA_FORMAT.matcher(args.trim());
		if (taskMatcher.matches()) {
			int index = Integer.parseInt(taskMatcher.group("index"));
			// validation if index equals to zero
			if (index == 0) {
				return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
			} else { //index > 0
				return new SelectTaskCommand(index);
			}
			
		} else if (eventMatcher.matches()) {
			int index = Integer.parseInt(eventMatcher.group("index"));
			// validation if index equals to zero
			if (index==0){
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
            }else{ //index > 0
                return new SelectEventCommand(index);
            }
		} else {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
		}
	}

}
```
###### /java/seedu/task/storage/ConfigStorage.java
``` java
 */

public interface ConfigStorage {
    /**
     * Returns Config data from storage.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<Config> readConfigFile() throws DataConversionException, IOException;

    /**
     * Saves the given {@link seedu.taskell.commons.core.Config} to the storage.
     * @param config cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveConfigFile(Config config) throws IOException;
}
```
###### /java/seedu/task/storage/JsonConfigStorage.java
``` java
 */


public class JsonConfigStorage implements ConfigStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonConfigStorage.class);
    
    private String filePath;
    
    public JsonConfigStorage(String filePath) {
        this.filePath = filePath;
    }
    // read from project main directory 
    @Override
    public Optional<Config> readConfigFile() throws DataConversionException, IOException {
        return readConfig(filePath);
    }
    // save to project main directory
    @Override
    public void saveConfigFile(Config config) throws IOException {
        saveConfig(config, filePath);
    }
    
    /**
     * Similar to {@link #readConfigFile()}
     * @param configFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    
    //read from specified saved file path
    public Optional<Config> readConfig(String configFilePath) throws DataConversionException {
        assert configFilePath != null;

        File configFile = new File(configFilePath);

        if (!configFile.exists()) {
            logger.info("Config file "  + configFile + " not found");
            return Optional.empty();
        }

        Config config;

        try {
            config = FileUtil.deserializeObjectFromJsonFile(configFile, Config.class);
        } catch (IOException e) {
            logger.warning("Error reading from config file " + configFile + ": " + e);
            throw new DataConversionException(e);
        }

        return Optional.of(config);
    }

    /**
     * Similar to {@link #saveConfigFile(Config)}
     * @param configFilePath location of the data. Cannot be null.
     */
    // save to specified file path
    private void saveConfig(Config config, String configFilePath) throws IOException {
        assert config != null;
        assert configFilePath != null;
        assert !configFilePath.isEmpty();

        FileUtil.serializeObjectToJsonFile(new File(configFilePath), config);
    }

}
```
