# A0135817Breused
###### \java\seedu\todo\commons\util\XmlUtilTest.java
``` java
public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validTodoList.xml");
    private static final File TEMP_FILE = new File(TestUtil.getFilePathInSandboxFolder("tempTodoList.xml"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(null, XmlSerializableTodoList.class);
    }

    @Test
    public void getDataFromFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, XmlSerializableTodoList.class);
    }

    @Test
    public void getDataFromFile_emptyFile_DataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, XmlSerializableTodoList.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        XmlSerializableTodoList dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableTodoList.class);
        assertEquals(6, dataFromFile.getTasks().size());
    }

    @Test
    public void saveDataToFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(null, new XmlSerializableTodoList());
    }

    @Test
    public void saveDataToFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new XmlSerializableTodoList());
    }
  
    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        MovableStorage<ImmutableTodoList> storage = new TodoListStorage(TEMP_FILE.getAbsolutePath());
        XmlSerializableTodoList dataToWrite = new XmlSerializableTodoList(new TodoList(storage));
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableTodoList dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableTodoList.class);

        assertTrue(isShallowEqual(dataFromFile.getTasks(), dataToWrite.getTasks()));

        TodoList todoList = new TodoList(storage);
        todoList.add("test");
        dataToWrite = new XmlSerializableTodoList(todoList);
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);

        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableTodoList.class);
        assertTrue(isShallowEqual(dataFromFile.getTasks(), dataToWrite.getTasks()));
    }
}
```
###### \java\seedu\todo\model\UnmodifiableObservableListTest.java
``` java
public class UnmodifiableObservableListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private List<Integer> backing;
    private UnmodifiableObservableList<Integer> list;

    @Before
    public void setup() {
        backing = new ArrayList<>();
        backing.add(10);
        list = new UnmodifiableObservableList<>(FXCollections.observableList(backing));
    }

    @Test
    public void transformationListGenerators_correctBackingList() {
        assertSame(list.sorted().getSource(), list);
        assertSame(list.filtered(i -> true).getSource(), list);
    }

    @Test
    public void mutatingMethods_disabled() {

        final Class<UnsupportedOperationException> ex = UnsupportedOperationException.class;

        assertThrows(ex, () -> list.add(0, 2));
        assertThrows(ex, () -> list.add(3));

        assertThrows(ex, () -> list.addAll(2, 1));
        assertThrows(ex, () -> list.addAll(backing));
        assertThrows(ex, () -> list.addAll(0, backing));

        assertThrows(ex, () -> list.set(0, 2));

        assertThrows(ex, () -> list.setAll(new ArrayList<Number>()));
        assertThrows(ex, () -> list.setAll(1, 2));

        assertThrows(ex, () -> list.remove(0, 1));
        assertThrows(ex, () -> list.remove(null));
        assertThrows(ex, () -> list.remove(0));

        assertThrows(ex, () -> list.removeAll(backing));
        assertThrows(ex, () -> list.removeAll(1, 2));

        assertThrows(ex, () -> list.retainAll(backing));
        assertThrows(ex, () -> list.retainAll(1, 2));

        assertThrows(ex, () -> list.replaceAll(i -> 1));

        assertThrows(ex, () -> list.sort(Comparator.naturalOrder()));

        assertThrows(ex, () -> list.clear());

        final Iterator<Integer> iter = list.iterator();
        iter.next();
        assertThrows(ex, iter::remove);

        final ListIterator<Integer> liter = list.listIterator();
        liter.next();
        assertThrows(ex, liter::remove);
        assertThrows(ex, () -> liter.add(5));
        assertThrows(ex, () -> liter.set(3));
        assertThrows(ex, () -> list.removeIf(i -> true));
    }
}
```
###### \java\seedu\todo\storage\TodoListStorageTest.java
``` java
public class TodoListStorageTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Rule 
    public MockitoRule mockito = MockitoJUnit.rule();

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/TodoListStorageTest/");
    private static final String TEST_DATA_FILE = "TestTodoList.xml";

    @Mock private ImmutableTodoList original;
    private String filePath;
    private TodoListStorage storage;

    private String getFilePath(String filename) {
        return filename != null ? TEST_DATA_FOLDER + filename : null;
    }

    private ImmutableTodoList readTodoList(String filePath) throws Exception {
        return new TodoListStorage(filePath).read(getFilePath(filePath));
    }
    
    @Before
    public void setUp() {
        filePath = testFolder.getRoot().getPath() + TEST_DATA_FILE;
        storage = new TodoListStorage(filePath);
        when(original.getTasks()).thenReturn(Collections.emptyList());
    }

    @Test
    public void testReadNullTodoListTestFile() throws Exception {
        thrown.expect(AssertionError.class);
        readTodoList(null);
    }

    @Test
    public void testMissingFile() throws Exception {
        thrown.expect(FileNotFoundException.class);
        readTodoList("NonExistentFile.xml");
    }

    @Test
    public void testReadNonXmlFormatFile() throws Exception {
        thrown.expect(DataConversionException.class);
        readTodoList("NotXmlFormatTodoList.xml");
    }
    
    @Test
    public void testLocationNotChangedAfterError() throws Exception {
        String before = storage.getLocation();
        try {
            readTodoList("NotXmlFormatTodoList.xml");
        } catch (DataConversionException e) {
            assertEquals(before, storage.getLocation());
        }
    }

    @Test
    public void testEmptySave() throws Exception {
        storage.save(original);
        ImmutableTodoList readBack = storage.read(filePath);
        assertTrue(isShallowEqual(original.getTasks(), readBack.getTasks()));
    }

    @Test
    public void testReadAndSave() throws Exception {
        when(original.getTasks()).thenReturn(TaskFactory.list());
        // Save in new file and read back
        storage.save(original);
        ImmutableTodoList readBack = storage.read(filePath);
        assertTrue(isShallowEqual(original.getTasks(), readBack.getTasks()));
    }

    @Test
    public void testUpdateAndSave() throws Exception {
        // Save in new file
        storage.save(original);
        
        // Modify data, overwrite exiting file, and read back
        when(original.getTasks()).thenReturn(TaskFactory.list());
        storage.save(original);

        ImmutableTodoList readBack = storage.read(filePath);
        
        assertTrue(isShallowEqual(original.getTasks(), readBack.getTasks()));
    }

    @Test
    public void testReadAndSaveWithoutPath() throws Exception {
        // Save in new file
        storage.save(original);

        ImmutableTodoList readBack = storage.read();
        assertTrue(isShallowEqual(original.getTasks(), readBack.getTasks()));
    }

    @Test
    public void testSaveNullTodoList() throws IOException {
        thrown.expect(AssertionError.class);
        storage.save(null);
    }

    @Test
    public void testSaveNullFilePath() throws IOException {
        thrown.expect(AssertionError.class);
        storage.save(original, null);
    }
}
```
###### \java\seedu\todo\storage\UserPrefsStorageTest.java
``` java
public class UserPrefsStorageTest {

    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/UserPrefsStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readUserPrefs_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readUserPrefs(null);
    }

    private UserPrefs readUserPrefs(String userPrefsFile) throws Exception {
        return new UserPrefsStorage(getPrefsPath(userPrefsFile)).read();
    }

    @Test
    public void readUserPrefs_missingFile_emptyResult() throws Exception {
        assertEquals(new UserPrefs(), readUserPrefs("NonExistentFile.json"));
    }

    @Test
    public void readUserPrefs_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readUserPrefs("NotJsonFormatUserPrefs.json");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    private String getPrefsPath(String filename) {
        return filename != null ? TEST_DATA_FOLDER + filename : null;
    }

    @Test
    public void readUserPrefs_fileInOrder_successfullyRead() throws Exception {
        UserPrefs expected = new UserPrefs();
        expected.setGuiSettings(1000, 500, 300, 100);
        UserPrefs actual = readUserPrefs("TypicalUserPref.json");
        assertEquals(expected, actual);
    }

    @Test
    public void readUserPrefs_valuesMissingFromFile_defaultValuesUsed() throws Exception {
        UserPrefs actual = readUserPrefs("EmptyUserPrefs.json");
        assertEquals(new UserPrefs(), actual);
    }

    @Test
    public void readUserPrefs_extraValuesInFile_extraValuesIgnored() throws Exception {
        UserPrefs expected = new UserPrefs();
        expected.setGuiSettings(1000, 500, 300, 100);
        UserPrefs actual = readUserPrefs("ExtraValuesUserPref.json");

        assertEquals(expected, actual);
    }

    @Test
    public void savePrefs_nullPrefs_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveUserPrefs(null, "SomeFile.json");
    }

    @Test
    public void saveUserPrefs_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveUserPrefs(new UserPrefs(), null);
    }

    private void saveUserPrefs(UserPrefs userPrefs, String userPrefsFile) throws IOException {
        new UserPrefsStorage(getPrefsPath(userPrefsFile)).save(userPrefs);
    }

    @Test
    public void saveUserPrefs_allInOrder_success() throws Exception {

        UserPrefs original = new UserPrefs();
        original.setGuiSettings(1200, 200, 0, 2);

        String pefsFilePath = testFolder.getRoot() + File.separator + "TempPrefs.json";
        UserPrefsStorage userPrefsStorage = new UserPrefsStorage(pefsFilePath);

        //Try writing when the file doesn't exist
        userPrefsStorage.save(original);
        UserPrefs readBack = userPrefsStorage.read();
        assertEquals(original, readBack);

        //Try saving when the file exists
        original.setGuiSettings(5, 5, 5, 5);
        userPrefsStorage.save(original);
        readBack = userPrefsStorage.read();
        assertEquals(original, readBack);
    }

}
```
###### \java\seedu\todo\testutil\EventsCollector.java
``` java
/**
 * A class that collects events raised by other classes.
 */
public class EventsCollector{
    private List<BaseEvent> events = new ArrayList<>();

    public EventsCollector(){
        EventsCenter.getInstance().registerHandler(this);
    }

    /**
     * Collects any event raised by any class
     */
    @Subscribe
    public void collectEvent(BaseEvent event){
        events.add(event);
    }

    /**
     * Removes collected events from the collected list
     */
    public void reset(){
        events.clear();
    }

    /**
     * Returns the event at the specified index
     */
    public BaseEvent get(int index){
        return events.get(index);
    }
    
    public BaseEvent last() {
        return events.get(events.size() - 1);
    }
    
    public int size(){
        return events.size();
    }
}
```
###### \java\seedu\todo\testutil\SerializableTestClass.java
``` java
/**
 * A class used to test serialization and deserialization
 */
public class SerializableTestClass {
    public static final String JSON_STRING_REPRESENTATION = String.format("{%n" +
            "  \"name\" : \"This is a test class\",%n" +
            "  \"listOfLocalDateTimes\" : " +
            "[ \"-999999999-01-01T00:00:00\", \"+999999999-12-31T23:59:59.999999999\", \"0001-01-01T01:01:00\" ],%n" +
            "  \"mapOfIntegerToString\" : {%n" +
            "    \"1\" : \"One\",%n" +
            "    \"2\" : \"Two\",%n" +
            "    \"3\" : \"Three\"%n" +
            "  }%n" +
            "}");

    private static final String NAME_TEST_VALUE = "This is a test class";

    private String name;

    private List<LocalDateTime> listOfLocalDateTimes;
    private HashMap<Integer, String> mapOfIntegerToString;

    public SerializableTestClass() {}

    public static String getNameTestValue() {
        return NAME_TEST_VALUE;
    }

    public static List<LocalDateTime> getListTestValues() {
        List<LocalDateTime> listOfLocalDateTimes = new ArrayList<>();

        listOfLocalDateTimes.add(LocalDateTime.MIN);
        listOfLocalDateTimes.add(LocalDateTime.MAX);
        listOfLocalDateTimes.add(LocalDateTime.of(1, 1, 1, 1, 1));

        return listOfLocalDateTimes;
    }

    public static HashMap<Integer, String> getHashMapTestValues() {
        HashMap<Integer, String> mapOfIntegerToString = new HashMap<>();

        mapOfIntegerToString.put(1, "One");
        mapOfIntegerToString.put(2, "Two");
        mapOfIntegerToString.put(3, "Three");

        return mapOfIntegerToString;
    }

    public void setTestValues() {
        name = getNameTestValue();
        listOfLocalDateTimes = getListTestValues();
        mapOfIntegerToString = getHashMapTestValues();
    }

    public String getName() {
        return name;
    }

    public List<LocalDateTime> getListOfLocalDateTimes() {
        return listOfLocalDateTimes;
    }

    public HashMap<Integer, String> getMapOfIntegerToString() {
        return mapOfIntegerToString;
    }
}
```
###### \java\seedu\todo\testutil\TestUtil.java
``` java
/**
 * A utility class for test cases.
 */
public class TestUtil {

    public static void assertThrows(Class<? extends Throwable> expected, Runnable executable) {
        try {
            executable.run();
        }
        catch (Throwable actualException) {
            if (!actualException.getClass().isAssignableFrom(expected)) {
                String message = String.format("Expected thrown: %s, actual: %s", expected.getName(),
                        actualException.getClass().getName());
                throw new AssertionFailedError(message);
            } else return;
        }
        throw new AssertionFailedError(
                String.format("Expected %s to be thrown, but nothing was thrown.", expected.getName()));
    }

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    public static String SANDBOX_FOLDER = FileUtil.getPath("./src/test/data/sandbox/");

    /**
     * Appends the file name to the sandbox folder path.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static String getFilePathInSandboxFolder(String fileName) {
        try {
            FileUtil.createDirs(new File(SANDBOX_FOLDER));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER + fileName;
    }

    public static <T> void createDataFileWithData(T data, String filePath) {
        try {
            File saveFileForTesting = new File(filePath);
            FileUtil.createIfMissing(saveFileForTesting);
            XmlUtil.saveDataToFile(saveFileForTesting, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a new {@link TodoList} given the path of the file, {@code filePath}.
     *
     * @param filePath Location where the file should be saved.
     * @return Returns a new instance of {@link TodoList}.
     */
    public static TodoList generateEmptyTodoList(String filePath) {
        TodoListStorage storage = new TodoListStorage(filePath);
        return new TodoList(storage);
    }

    /**
     * Loads the {@link TodoList} with sample data stored in {@code tasks}.
     *
     * @param todoList The to-do list to inject the data from.
     * @param tasks The list of {@link ImmutableTask} that should be stored inside the {@code todoList}.
     */
    public static void loadTodoListWithData(TodoList todoList, List<ImmutableTask> tasks) {
        todoList.setTasks(tasks);
    }

    public static void assertAllPropertiesEqual(ImmutableTask a, ImmutableTask b) {
        assertEquals(a.getTitle(), b.getTitle());
        assertEquals(a.getDescription(), b.getDescription());
        assertEquals(a.getLocation(), b.getLocation());
        assertEquals(a.getStartTime(), b.getStartTime());
        assertEquals(a.getEndTime(), b.getEndTime());
        assertEquals(a.isPinned(), b.isPinned());
        assertEquals(a.isCompleted(), b.isCompleted());
        assertEquals(a.getTags(), b.getTags());
        assertEquals(a.getUUID(), b.getUUID());
    }

    /**
     * Do a shallow equality test based on the titles in the two list of tasks
     * 
     * @param list the first list of tasks to compare
     * @param otherList the second list of tasks to compare
     * @return whether the two contains tasks with the same titles
     */
    public static boolean isShallowEqual(List<ImmutableTask> list, List<ImmutableTask> otherList) {
        if (list.size() != otherList.size()) {
            return false;
        }

        for (int i = 0; i < list.size(); i++) {
            ImmutableTask task = list.get(i);
            ImmutableTask otherTask = otherList.get(i);
            
            boolean isSame = task.getTitle().equals(otherTask.getTitle()) &&
                    task.isCompleted() == otherTask.isCompleted() &&
                    task.isPinned() == otherTask.isPinned() &&
                    task.getDescription().equals(otherTask.getDescription()) &&
                    task.getLocation().equals(otherTask.getLocation()) &&
                    task.getStartTime().equals(otherTask.getStartTime()) &&
                    task.getEndTime().equals(otherTask.getEndTime()) &&
                    task.getCreatedAt().equals(otherTask.getCreatedAt()) &&
                    task.getTags().containsAll(otherTask.getTags());
            if (!isSame) {
                return false;
            }
        }

        return true;
    }

    /**
     * Compares whether 2 tasks contain the same vital information, such as:
     *      title, details, start and end time, location, is pinned, is completed
     * @return True when two tasks are the same.
     */
    public static boolean isShallowEqual(ImmutableTask task1, ImmutableTask task2) {
        boolean hasSameTitle = task1.getTitle().equals(task2.getTitle());
        boolean hasSameDescription = task1.getDescription().equals(task2.getDescription());
        boolean hasSameStartTime = task1.getStartTime().equals(task2.getStartTime());
        boolean hasSameEndTime = task1.getEndTime().equals(task2.getEndTime());
        boolean hasSameLocation = task1.getLocation().equals(task2.getLocation());
        boolean hasSamePinFlag = task1.isPinned() == task2.isPinned();
        boolean hasSameCompletedFlag = task1.isCompleted() == task2.isCompleted();
        boolean hasSameTags = task1.getTags().equals(task2.getTags());

        return hasSameTitle && hasSameDescription && hasSameStartTime
                && hasSameEndTime && hasSameLocation && hasSamePinFlag
                && hasSameCompletedFlag && hasSameTags;
    }

    /**
     * Compares two list that are almost similar, except that longer list has 1 item more than shorter list.
     * So, return the index of that 1 item.
     * @return Index of the item from longerList that is missing in shorterList.
     *         If the two list are identical, returns -1.
     */
    public static int compareAndGetIndex(List<ImmutableTask> shorterList, List<ImmutableTask> longerList) {
        for (int listIndex = 0; listIndex < longerList.size(); listIndex ++) {
            ImmutableTask taskInLongerList = longerList.get(listIndex);

            //Note, that ImmutableTask does not support *shallow* hashing and equals.
            java.util.Optional<ImmutableTask> foundItem = shorterList.stream()
                    .filter(taskInShorterList -> isShallowEqual(taskInLongerList, taskInShorterList))
                    .findFirst();
            if (foundItem.isPresent()) {
                shorterList.remove(foundItem.get());
            } else {
                return listIndex;
            }
        }
        return -2048;
    }

    /**
     * Tweaks the {@code keyCodeCombination} to resolve the {@code KeyCode.SHORTCUT} to their
     * respective platform-specific keycodes
     */
    public static KeyCode[] scrub(KeyCodeCombination keyCodeCombination) {
        List<KeyCode> keys = new ArrayList<>();
        if (keyCodeCombination.getAlt() == KeyCombination.ModifierValue.DOWN) {
            keys.add(KeyCode.ALT);
        }
        if (keyCodeCombination.getShift() == KeyCombination.ModifierValue.DOWN) {
            keys.add(KeyCode.SHIFT);
        }
        if (keyCodeCombination.getMeta() == KeyCombination.ModifierValue.DOWN) {
            keys.add(KeyCode.META);
        }
        if (keyCodeCombination.getControl() == KeyCombination.ModifierValue.DOWN) {
            keys.add(KeyCode.CONTROL);
        }
        keys.add(keyCodeCombination.getCode());
        return keys.toArray(new KeyCode[]{});
    }

    /**
     * Gets mid point of a node relative to the screen.
     */
    public static Point2D getScreenMidPoint(Node node) {
        double x = getScreenPos(node).getMinX() + node.getLayoutBounds().getWidth() / 2;
        double y = getScreenPos(node).getMinY() + node.getLayoutBounds().getHeight() / 2;
        return new Point2D(x,y);
    }

    public static Bounds getScreenPos(Node node) {
        return node.localToScreen(node.getBoundsInLocal());
    }
}
```
