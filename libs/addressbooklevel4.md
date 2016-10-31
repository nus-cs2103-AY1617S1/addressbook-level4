# addressbooklevel4
###### /src/main/java/seedu/gtd/logic/commands/FindCommand.java
``` java
    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";
    
```
###### /src/main/java/seedu/gtd/logic/parser/Parser.java
``` java
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    private static final Pattern TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + " (?<isDueDatePrivate>p?)d/(?<dueDate>[^/]+)"
                    + " (?<isAddressPrivate>p?)a/(?<address>[^/]+)"
                    + " (?<isPriorityPrivate>p?)p/(?<priority>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags
    
    private static final Pattern EDIT_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
    		Pattern.compile("(?<targetIndex>\\S+)" 
                    + " (?<newDetail>.*)");

    public Parser() {}

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);
            
        case EditCommand.COMMAND_WORD:
        	return prepareEdit(arguments);

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
        	return prepareHelp(arguments);

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

	/**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args){
        final Matcher matcher = TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        
        String dueDate = parseDueDate(matcher.group("dueDate"));
        
        try {
            return new AddCommand(
                    matcher.group("name"),
                    dueDate,
                    matcher.group("address"),
                    matcher.group("priority"),
                    getTagsFromArgs(matcher.group("tagArguments"))
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
```
###### /src/main/java/seedu/gtd/logic/parser/Parser.java
``` java
    /**
     * Extracts the new task's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    private static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
        // no tags
        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" t/", "").split(" t/"));
        return new HashSet<>(tagStrings);
    }
    
```
###### /src/main/java/seedu/gtd/logic/parser/Parser.java
``` java
    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(index.get());
    }

    /**
     * Parses arguments in the context of the select task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareSelect(String args) {
        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        return new SelectCommand(index.get());
    }

    /**
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as the index.
     *   Returns an {@code Optional.empty()} otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        System.out.println(index);
        if(!StringUtil.isUnsignedInteger(index)){
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Parses arguments in the context of the find task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] splitKeywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(splitKeywords));
        
        final String keywords = matcher.group("keywords");
        return new FindCommand(keywords, keywordSet);
    }

    /**
     * Parses arguments in the context of the help command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareHelp(String args) {
    	//if no argument
    	if (args.equals("")) {
    		args="help";
    	}
    	
    	final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        return new HelpCommand(commandWord);
    }
}
```
###### /src/main/java/seedu/gtd/MainApp.java
``` java
public class MainApp extends Application {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public static final Version VERSION = new Version(1, 0, 0, true);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;

    public MainApp() {}

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing TaskList ]===========================");
        super.init();

        config = initConfig(getApplicationParameter("config"));
        storage = new StorageManager(config.getAddressBookFilePath(), config.getUserPrefsFilePath());

        userPrefs = initPrefs(config);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

    private String getApplicationParameter(String parameterName){
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
    }

    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialData;
        try {
            addressBookOptional = storage.readAddressBook();
            if(!addressBookOptional.isPresent()){
                logger.info("Data file not found. Will be starting with an empty TaskList");
            }
            initialData = addressBookOptional.orElse(new AddressBook());
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty TaskList");
            initialData = new AddressBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. . Will be starting with an empty TaskList");
            initialData = new AddressBook();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    protected Config initConfig(String configFilePath) {
        Config initializedConfig;
        String configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if(configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. " +
                    "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    protected UserPrefs initPrefs(Config config) {
        assert config != null;

        String prefsFilePath = config.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. " +
                    "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. . Will be starting with an empty TaskList");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    private void initEventsCenter() {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting TaskList " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Task List ] =============================");
        ui.stop();
        try {
            storage.saveUserPrefs(userPrefs);
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
        Platform.exit();
        System.exit(0);
    }

    @Subscribe
    public void handleExitAppRequestEvent(ExitAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```
###### /src/main/java/seedu/gtd/model/AddressBook.java
``` java
    private final UniqueTaskList tasks;
    private final UniqueTagList tags;

    {
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public AddressBook() {}

    /**
     * Tasks and Tags are copied into this addressbook
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Tasks and Tags are copied into this addressbook
     */
    public AddressBook(UniqueTaskList tasks, UniqueTagList tags) {
        resetData(tasks.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyAddressBook getEmptyAddressBook() {
        return new AddressBook();
    }

//// list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<Tag> newTags) {
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyAddressBook newData) {
        resetData(newData.getTaskList(), newData.getTagList());
    }

//// task-level operations

    /**
     * Adds a task to the address book.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task t) throws UniqueTaskList.DuplicateTaskException {
        syncTagsWithMasterList(t);
        tasks.add(t);
    }
    
```
###### /src/main/java/seedu/gtd/model/AddressBook.java
``` java
    /**
     * Ensures that every tag in this task:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Task t) {
        final UniqueTagList taskTags = t.getTags();
        tags.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        for (Tag tag : tags) {
            masterTagObjects.put(tag, tag);
        }

        // Rebuild the list of task tags using references from the master list
        final Set<Tag> commonTagReferences = new HashSet<>();
        for (Tag tag : taskTags) {
            commonTagReferences.add(masterTagObjects.get(tag));
        }
        t.setTags(new UniqueTagList(commonTagReferences));
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.tasks.equals(((AddressBook) other).tasks)
                && this.tags.equals(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }
}
```
###### /src/main/java/seedu/gtd/model/ModelManager.java
``` java
	
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given AddressBook
     * AddressBook and its variables should not be null
     */
    public ModelManager(AddressBook src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        addressBook = new AddressBook(src);
        filteredTasks = new FilteredList<>(addressBook.getTasks());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    public ModelManager(ReadOnlyAddressBook initialData, UserPrefs userPrefs) {
        addressBook = new AddressBook(initialData);
        filteredTasks = new FilteredList<>(addressBook.getTasks());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        addressBook.removeTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        addressBook.addTask(task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }
    
```
###### /src/main/java/seedu/gtd/model/ModelManager.java
``` java
    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(String keywords, Set<String> keywordSet){
        updateFilteredTaskList(new PredicateExpression(new orderedNameQualifier(keywords, keywordSet)));
    }
    
    @Override
    public void updateFilteredTaskList(Set<String> keywordSet) {
    	updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywordSet)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);
        String toString();
    }
    
    private class NameQualifier implements Qualifier {
        protected Set<String> keywordSet;

        NameQualifier(Set<String> keywordSet) {
            this.keywordSet = keywordSet;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return keywordSet.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", keywordSet);
        }
    }
    
    private class orderedNameQualifier extends NameQualifier implements Qualifier {
		private String nameKeyWords;

        orderedNameQualifier(String keywords, Set<String> keywordSet) {
        	super(keywordSet);
            this.nameKeyWords = keywords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
        	String taskFullNameLowerCase = task.getName().fullName.toLowerCase();
        	boolean orderMatch = taskFullNameLowerCase.contains(nameKeyWords.toLowerCase());
        	
        	boolean eachWordMatch = keywordSet.stream()
            .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName, keyword))
            .findAny()
            .isPresent();
        	
            return eachWordMatch && orderMatch;
        }
    }
}
```
###### /src/main/java/seedu/gtd/model/task/UniqueTaskList.java
``` java
    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTaskException extends DuplicateDataException {
        protected DuplicateTaskException() {
            super("Operation would result in duplicate tasks");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching task in the list.
     */
    public static class TaskNotFoundException extends Exception {}

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TaskList.
     */
    public UniqueTaskList() {}

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }
    
```
###### /src/main/java/seedu/gtd/model/task/UniqueTaskList.java
``` java
    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    public ObservableList<Task> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### /src/test/java/seedu/gtd/logic/LogicManagerTest.java
``` java

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyAddressBook latestSavedAddressBook;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(AddressBookChangedEvent abce) {
        latestSavedAddressBook = new AddressBook(abce.data);
    }

    @Subscribe
    private void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
        helpShown = true;
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent je) {
        targetedJumpIndex = je.targetIndex;
    }

    @Before
    public void setup() {
        model = new ModelManager();
        String tempAddressBookFile = saveFolder.getRoot().getPath() + "TempAddressBook.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempAddressBookFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedAddressBook = new AddressBook(model.getAddressBook()); // last saved assumed to be up to date before.
        helpShown = false;
        targetedJumpIndex = -1; // non yet
    }

    @After
    public void teardown() {
        EventsCenter.clearSubscribers();
    }

    @Test
    public void execute_invalid() throws Exception {
        String invalidCommand = "       ";
        assertCommandBehavior(invalidCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'address book' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, ReadOnlyAddressBook, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new AddressBook(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedAddressBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedAddressBook} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyAddressBook expectedAddressBook,
                                       List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);
        System.out.println(inputCommand);

        //Confirm the ui display elements should contain the right data
        System.out.println(result.feedbackToUser);
        System.out.println(expectedMessage);
        assertEquals(expectedMessage, result.feedbackToUser);
        System.out.println("correct message");
        assertEquals(expectedShownList, model.getFilteredTaskList());
        System.out.println("correct data in UI");

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedAddressBook, model.getAddressBook());
        assertEquals(expectedAddressBook, latestSavedAddressBook);
    }


    @Test
    public void execute_unknownCommandWord() throws Exception {
        String unknownCommand = "uicfhmowqewca";
        assertCommandBehavior(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }
    
```
###### /src/test/java/seedu/gtd/logic/LogicManagerTest.java
``` java
    @Test
    public void execute_exit() throws Exception {
        assertCommandBehavior("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

    @Test
    public void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addTask(helper.generateTask(1));
        model.addTask(helper.generateTask(2));
        model.addTask(helper.generateTask(3));

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new AddressBook(), Collections.emptyList());
    }


    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add wrong args wrong args", expectedMessage);
        assertCommandBehavior(
                "add Valid Name 12345 d/valid@dueDate.butNoDueDatePrefix a/valid, address", expectedMessage);
        assertCommandBehavior(
                "add Valid Name d/12345 valid@address.butNoPrefix p/valid, priority", expectedMessage);
        assertCommandBehavior(
                "add Valid Name d/12345 a/valid@email.butNoAddressPrefix valid, priority", expectedMessage);
    }

    @Test
    public void execute_add_invalidTaskData() throws Exception {
        assertCommandBehavior(
                "add []\\[;] d/12345 a/valid, address p/1", Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name d/not_numbers a/valid, address p/2", DueDate.MESSAGE_DUEDATE_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name d/12345 a/valid, address p/not_priority_numbers", Priority.MESSAGE_PRIORITY_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name d/12345 a/valid, address p/5 t/invalid_-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS);

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        Task dateChanged = helper.adamChanged();
        AddressBook expectedAB = new AddressBook();
        expectedAB.addTask(dateChanged);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, dateChanged),
                expectedAB,
                expectedAB.getTaskList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        Task changedDate = helper.adamChanged();
        AddressBook expectedAB = new AddressBook();
        expectedAB.addTask(changedDate);

        // setup starting state
        model.addTask(changedDate); // task already in internal address book

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_TASK,
                expectedAB,
                expectedAB.getTaskList());

    }
    
    /*
    @Test
    public void execute_edit_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeEdited = helper.adam();
        AddressBook expectedAB = helper.generateAddressBook(2);
        expectedAB.editTask(1, toBeEdited);

        // execute command and verify result
        assertCommandBehavior(helper.generateEditCommand(toBeEdited),
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getTaskList());
    }
    */


    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        AddressBook expectedAB = helper.generateAddressBook(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare address book state
        helper.addToModel(model, 2);

        assertCommandBehavior("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList);
    }


    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage) throws Exception {
        assertCommandBehavior(commandWord , expectedMessage); //index missing
        assertCommandBehavior(commandWord + " +1", expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " -1", expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " 0", expectedMessage); //index cannot be 0
        assertCommandBehavior(commandWord + " not_a_number", expectedMessage);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set AB state to 2 tasks
        model.resetData(new AddressBook());
        for (Task p : taskList) {
            model.addTask(p);
        }

        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getAddressBook(), taskList);
    }

    @Test
    public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
    }

    @Test
    public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("select");
    }

    @Test
    public void execute_select_jumpsToCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        AddressBook expectedAB = helper.generateAddressBook(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2),
                expectedAB,
                expectedAB.getTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
    }


    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
    }

    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete");
    }

    @Test
    public void execute_delete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        AddressBook expectedAB = helper.generateAddressBook(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }


    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p1 = helper.generateTaskWithName("KE Y");
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        AddressBook expectedAB = helper.generateAddressBook(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithName("key key");
        Task p4 = helper.generateTaskWithName("KEy sduauo");

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        AddressBook expectedAB = helper.generateAddressBook(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("key key");
        Task p1 = helper.generateTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        AddressBook expectedAB = helper.generateAddressBook(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourTasks);
        
        String keywords = "key rAnDoM";
        TestFindHelper findhelper = new TestFindHelper();
    	
        assertCommandBehavior("find " + keywords,
        		String.format(findhelper.generateCorrectResultIfExactPhraseNotFound(keywords, expectedList.size())),
                expectedAB,
                expectedList);
    }
    
    class TestFindHelper{
    	
    	String generateCorrectResultIfExactPhraseNotFound(String keywords, int expectedListSize) {	
            String task_tasks = (expectedListSize == 1) ? "task" : "tasks";
        	String MESSAGE_IF_EXACT_PHRASE_NOT_FOUND = "The exact phrase '" + keywords + "' was not found. Listing " + expectedListSize + " " + task_tasks + " containing the keywords entered instead.";
        	return MESSAGE_IF_EXACT_PHRASE_NOT_FOUND;
    	}
    }


    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        Task adam() throws Exception {
            Name name = new Name("Adam Brown");
            DueDate privateDueDate = new DueDate("noon");
            Address address = new Address("111, alpha street");
            Priority privatePriority = new Priority("1");
            Tag tag1 = new Tag("tag1");
            UniqueTagList tags = new UniqueTagList(tag1);
            return new Task(name, privateDueDate, address, privatePriority, tags);
        }
        Task adamChanged() throws Exception {
        	NaturalLanguageProcessor nlpTest = new DateNaturalLanguageProcessor();
        	String formattedDate = nlpTest.formatString("noon");
        	Name name = new Name("Adam Brown");
            DueDate privateDueDate = new DueDate(formattedDate);
            Address address = new Address("111, alpha street");
            Priority privatePriority = new Priority("1");
            Tag tag1 = new Tag("tag1");
            UniqueTagList tags = new UniqueTagList(tag1);
            return new Task(name, privateDueDate, address, privatePriority, tags);
        }

        /**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values guarantees the returned task will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the task data field values
         */
        Task generateTask(int seed) throws Exception {
            return new Task(
                    new Name("Task " + seed),
                    new DueDate("" + Math.abs(seed)),
                    new Address(seed + ", -address"),
                    new Priority("1 " + seed),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
            );
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getName().toString());
            cmd.append(" d/").append(p.getDueDate());
            cmd.append(" a/").append(p.getAddress());
            cmd.append(" p/").append(p.getPriority());

            UniqueTagList tags = p.getTags();
            for(Tag t: tags){
                cmd.append(" t/").append(t.tagName);
            }

            return cmd.toString();
        }
        
        /** Generates the correct add command based on the task given */
        String generateEditCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("edit ");
            cmd.append(" d/").append(p.getDueDate());

            return cmd.toString();
        }

        /**
         * Generates an AddressBook with auto-generated tasks.
         */
        AddressBook generateAddressBook(int numGenerated) throws Exception{
            AddressBook addressBook = new AddressBook();
            addToAddressBook(addressBook, numGenerated);
            return addressBook;
        }

        /**
         * Generates an AddressBook based on the list of Tasks given.
         */
        AddressBook generateAddressBook(List<Task> tasks) throws Exception{
            AddressBook addressBook = new AddressBook();
            addToAddressBook(addressBook, tasks);
            return addressBook;
        }

        /**
         * Adds auto-generated Task objects to the given AddressBook
         * @param addressBook The AddressBook to which the Tasks will be added
         */
        void addToAddressBook(AddressBook addressBook, int numGenerated) throws Exception{
            addToAddressBook(addressBook, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given AddressBook
         */
        void addToAddressBook(AddressBook addressBook, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                addressBook.addTask(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Tasks will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception{
            addToModel(model, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        void addToModel(Model model, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                model.addTask(p);
            }
        }

        /**
         * Generates a list of Tasks based on the flags.
         */
        List<Task> generateTaskList(int numGenerated) throws Exception{
            List<Task> tasks = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                tasks.add(generateTask(i));
            }
            return tasks;
        }

        List<Task> generateTaskList(Task... tasks) {
            return Arrays.asList(tasks);
        }

        /**
         * Generates a Task object with given name. Other fields will have some dummy values.
         */
        Task generateTaskWithName(String name) throws Exception {
            return new Task(
                    new Name(name),
                    new DueDate("1"),
                    new Address("House of 1"),
                    new Priority("1"),
                    new UniqueTagList(new Tag("tag"))
            );
        }
    }
}
```
