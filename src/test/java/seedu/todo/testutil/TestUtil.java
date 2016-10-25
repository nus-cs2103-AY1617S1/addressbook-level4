package seedu.todo.testutil;

import com.google.common.io.Files;
import guitests.guihandles.PersonCardHandle;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import junit.framework.AssertionFailedError;
import org.loadui.testfx.GuiTest;
import org.testfx.api.FxToolkit;
import seedu.todo.TestApp;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.util.FileUtil;
import seedu.todo.commons.util.XmlUtil;
import seedu.todo.model.AddressBook;
import seedu.todo.model.TodoList;
import seedu.todo.model.person.*;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.tag.UniqueTagList;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.storage.TodoListStorage;
import seedu.todo.storage.XmlSerializableAddressBook;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

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

    public static final Tag[] sampleTagData = getSampleTagData();

    private static Tag[] getSampleTagData() {
        try {
            return new Tag[]{
                    new Tag("relatives"),
                    new Tag("friends")
            };
        } catch (IllegalValueException e) {
            assert false;
            return null;
            //not possible
        }
    }

    /**
     * Appends the file name to the sandbox folder path.
     * Creates the sandbox folder if it doesn't exist.
     * @param fileName
     * @return
     */
    public static String getFilePathInSandboxFolder(String fileName) {
        try {
            FileUtil.createDirs(new File(SANDBOX_FOLDER));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER + fileName;
    }

    public static void createDataFileWithSampleData(String filePath) {
        createDataFileWithData(generateSampleStorageAddressBook(), filePath);
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

    public static void main(String... s) {
        createDataFileWithSampleData(TestApp.SAVE_LOCATION_FOR_TESTING);
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

    @Deprecated
    public static AddressBook generateEmptyAddressBook() {
        return new AddressBook(new UniquePersonList(), new UniqueTagList());
    }

    @Deprecated
    public static XmlSerializableAddressBook generateSampleStorageAddressBook() {
        return new XmlSerializableAddressBook(generateEmptyAddressBook());
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

    public static boolean isHeadlessEnvironment() {
        String headlessProperty = System.getProperty("testfx.headless");
        return headlessProperty != null && headlessProperty.equals("true");
    }

    public static void captureScreenShot(String fileName) {
        File file = GuiTest.captureScreenshot();
        try {
            Files.copy(file, new File(fileName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String descOnFail(Object... comparedObjects) {
        return "Comparison failed \n"
                + Arrays.asList(comparedObjects).stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }

    public static void setFinalStatic(Field field, Object newValue) throws NoSuchFieldException, IllegalAccessException{
        field.setAccessible(true);
        // remove final modifier from field
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        // ~Modifier.FINAL is used to remove the final modifier from field so that its value is no longer
        // final and can be changed
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }

    public static void initRuntime() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.hideStage();
    }

    public static void tearDownRuntime() throws Exception {
        FxToolkit.cleanupStages();
    }

    /**
     * Gets private method of a class
     * Invoke the method using method.invoke(objectInstance, params...)
     *
     * Caveat: only find method declared in the current Class, not inherited from supertypes
     */
    public static Method getPrivateMethod(Class objectClass, String methodName) throws NoSuchMethodException {
        Method method = objectClass.getDeclaredMethod(methodName);
        method.setAccessible(true);
        return method;
    }

    public static void renameFile(File file, String newFileName) {
        try {
            Files.copy(file, new File(newFileName));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Gets mid point of a node relative to the screen.
     * @param node
     * @return
     */
    public static Point2D getScreenMidPoint(Node node) {
        double x = getScreenPos(node).getMinX() + node.getLayoutBounds().getWidth() / 2;
        double y = getScreenPos(node).getMinY() + node.getLayoutBounds().getHeight() / 2;
        return new Point2D(x,y);
    }

    /**
     * Gets mid point of a node relative to its scene.
     * @param node
     * @return
     */
    public static Point2D getSceneMidPoint(Node node) {
        double x = getScenePos(node).getMinX() + node.getLayoutBounds().getWidth() / 2;
        double y = getScenePos(node).getMinY() + node.getLayoutBounds().getHeight() / 2;
        return new Point2D(x,y);
    }

    /**
     * Gets the bound of the node relative to the parent scene.
     * @param node
     * @return
     */
    public static Bounds getScenePos(Node node) {
        return node.localToScene(node.getBoundsInLocal());
    }

    public static Bounds getScreenPos(Node node) {
        return node.localToScreen(node.getBoundsInLocal());
    }

    public static double getSceneMaxX(Scene scene) {
        return scene.getX() + scene.getWidth();
    }

    public static double getSceneMaxY(Scene scene) {
        return scene.getX() + scene.getHeight();
    }

    public static Object getLastElement(List<?> list) {
        return list.get(list.size() - 1);
    }


    @Deprecated
    public static boolean compareCardAndPerson(PersonCardHandle card, ReadOnlyPerson person) {
        return card.isSamePerson(person);
    }

    public static Tag[] getTagList(String tags) {

        if (tags.equals("")) {
            return new Tag[]{};
        }

        final String[] split = tags.split(", ");

        final List<Tag> collect = Arrays.asList(split).stream().map(e -> {
            try {
                return new Tag(e.replaceFirst("Tag: ", ""));
            } catch (IllegalValueException e1) {
                //not possible
                assert false;
                return null;
            }
        }).collect(Collectors.toList());

        return collect.toArray(new Tag[split.length]);
    }

}
