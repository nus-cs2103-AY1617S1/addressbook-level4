package seedu.flexitrack.testutil;

import com.google.common.io.Files;
import guitests.guihandles.TaskCardHandle;
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
import seedu.flexitrack.TestApp;
import seedu.flexitrack.commons.exceptions.IllegalValueException;
import seedu.flexitrack.commons.util.FileUtil;
import seedu.flexitrack.commons.util.XmlUtil;
import seedu.flexitrack.model.FlexiTrack;
import seedu.flexitrack.model.tag.Tag;
import seedu.flexitrack.model.tag.UniqueTagList;
import seedu.flexitrack.model.task.*;
import seedu.flexitrack.storage.XmlSerializableFlexiTrack;
import seedu.flexitrack.testutil.TestTask;

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

/**
 * A utility class for test cases.
 */
public class TestUtil {

    public static String LS = System.lineSeparator();

    public static void assertThrows(Class<? extends Throwable> expected, Runnable executable) {
        try {
            executable.run();
        } catch (Throwable actualException) {
            if (!actualException.getClass().isAssignableFrom(expected)) {
                String message = String.format("Expected thrown: %s, actual: %s", expected.getName(),
                        actualException.getClass().getName());
                throw new AssertionFailedError(message);
            } else
                return;
        }
        throw new AssertionFailedError(
                String.format("Expected %s to be thrown, but nothing was thrown.", expected.getName()));
    }

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    public static String SANDBOX_FOLDER = FileUtil.getPath("./src/test/data/sandbox/");

    public static final Task[] sampleTaskData = getSampleTaskData();

    private static Task[] getSampleTaskData() {
        try {
            return new Task[] {
                    new Task(new Name("Go shopping"), new DateTimeInfo("29 Feb 23.23"), new DateTimeInfo("13 Jan 4pm"),
                            new DateTimeInfo("13 Jan 6pm"), new UniqueTagList()),
                    new Task(new Name("Buy books"), new DateTimeInfo("11 May"), new DateTimeInfo("29 Feb 23.23"),
                            new DateTimeInfo("29 Feb 23.23"), new UniqueTagList()),
                    new Task(new Name("Go for jogging"), new DateTimeInfo("29 Feb 23.23"),
                            new DateTimeInfo("8 Apr 5.30"), new DateTimeInfo("8 Apr 6.30"), new UniqueTagList()),
                    new Task(new Name("Dinner with friends"), new DateTimeInfo("29 Feb 23.23"),
                            new DateTimeInfo("10 Dec 8.30am"), new DateTimeInfo("10 Dec 11am"), new UniqueTagList()),
                    new Task(new Name("CS2103 homework"), new DateTimeInfo("12 Jun 7.30"),
                            new DateTimeInfo("29 Feb 23.23"), new DateTimeInfo("29 Feb 23.23"), new UniqueTagList()),
                    new Task(new Name("Cs2103 exam"), new DateTimeInfo("29 Feb 23.23"), new DateTimeInfo("7 Jun 3pm"),
                            new DateTimeInfo("7 Jun 5pm"), new UniqueTagList()),
                    new Task(new Name("Ma1505 midterm"), new DateTimeInfo("29 Feb 23.23"),
                            new DateTimeInfo("9 Aug 7pm"), new DateTimeInfo("9 Aug 11pm"), new UniqueTagList()),
                    new Task(new Name("Cycling"), new DateTimeInfo("29 Feb 23.23"), new DateTimeInfo("14 Nov 10.30"),
                            new DateTimeInfo("14 Nov 12.30"), new UniqueTagList()),
                    new Task(new Name("Movie time"), new DateTimeInfo("29 Feb 23.23"), new DateTimeInfo("today 2pm"),
                            new DateTimeInfo("today 6pm"), new UniqueTagList()) };
        } catch (IllegalValueException e) {
            assert false;
            // not possible
            return null;
        }
    }

    public static final Tag[] sampleTagData = getSampleTagData();

    private static Tag[] getSampleTagData() {
        try {
            return new Tag[] { new Tag("relatives"), new Tag("friends") };
        } catch (IllegalValueException e) {
            assert false;
            return null;
            // not possible
        }
    }

    public static List<Task> generateSampleTaskData() {
        return Arrays.asList(sampleTaskData);
    }

    /**
     * Appends the file name to the sandbox folder path. Creates the sandbox
     * folder if it doesn't exist.
     * 
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
        createDataFileWithData(generateSampleStorageFlexiTrack(), filePath);
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

    public static FlexiTrack generateEmptyFlexiTrack() {
        return new FlexiTrack(new UniqueTaskList(), new UniqueTagList());
    }

    public static XmlSerializableFlexiTrack generateSampleStorageFlexiTrack() {
        return new XmlSerializableFlexiTrack(generateEmptyFlexiTrack());
    }

    /**
     * Tweaks the {@code keyCodeCombination} to resolve the
     * {@code KeyCode.SHORTCUT} to their respective platform-specific keycodes
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
        return keys.toArray(new KeyCode[] {});
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
                + Arrays.asList(comparedObjects).stream().map(Object::toString).collect(Collectors.joining("\n"));
    }

    public static void setFinalStatic(Field field, Object newValue)
            throws NoSuchFieldException, IllegalAccessException {
        field.setAccessible(true);
        // remove final modifier from field
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        // ~Modifier.FINAL is used to remove the final modifier from field so
        // that its value is no longer
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
     * Gets private method of a class Invoke the method using
     * method.invoke(objectInstance, params...)
     *
     * Caveat: only find method declared in the current Class, not inherited
     * from supertypes
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
     * 
     * @param node
     * @return
     */
    public static Point2D getScreenMidPoint(Node node) {
        double x = getScreenPos(node).getMinX() + node.getLayoutBounds().getWidth() / 2;
        double y = getScreenPos(node).getMinY() + node.getLayoutBounds().getHeight() / 2;
        return new Point2D(x, y);
    }

    /**
     * Gets mid point of a node relative to its scene.
     * 
     * @param node
     * @return
     */
    public static Point2D getSceneMidPoint(Node node) {
        double x = getScenePos(node).getMinX() + node.getLayoutBounds().getWidth() / 2;
        double y = getScenePos(node).getMinY() + node.getLayoutBounds().getHeight() / 2;
        return new Point2D(x, y);
    }

    /**
     * Gets the bound of the node relative to the parent scene.
     * 
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

    /**
     * Removes a subset from the list of tasks.
     * 
     * @param tasks
     *            The list of tasks
     * @param tasksToRemove
     *            The subset of tasks.
     * @return The modified tasks after removal of the subset from tasks.
     */
    public static TestTask[] removeTasksFromList(final TestTask[] tasks, TestTask... tasksToRemove) {
        List<TestTask> listOfTasks = asList(tasks);
        listOfTasks.removeAll(asList(tasksToRemove));
        return listOfTasks.toArray(new TestTask[listOfTasks.size()]);
    }

    /**
     * Returns a copy of the list with the task at specified index removed.
     * 
     * @param list
     *            original list to copy from
     * @param targetIndexInOneIndexedFormat
     *            e.g. if the first element to be removed, 1 should be given as
     *            index.
     */
    public static TestTask[] removeTaskFromList(final TestTask[] list, int targetIndexInOneIndexedFormat) {
        return removeTasksFromList(list, list[targetIndexInOneIndexedFormat - 1]);
    }

    /**
     * Replaces tasks[i] with a task.
     * 
     * @param tasks
     *            The array of tasks.
     * @param task
     *            The replacement task
     * @param index
     *            The index of the task to be replaced.
     * @return
     */
    public static TestTask[] replaceTaskFromList(TestTask[] tasks, TestTask task, int index) {
        tasks[index] = task;
        return tasks;
    }

    /**
     * Appends tasks to the array of tasks.
     * 
     * @param tasks
     *            A array of tasks.
     * @param tasksToAdd
     *            The tasks that are to be appended behind the original array.
     * @return The modified array of tasks.
     */
    public static TestTask[] addTasksToList(final TestTask[] tasks, TestTask... tasksToAdd) {
        List<TestTask> listOfTasks = asList(tasks);
        listOfTasks.addAll(asList(tasksToAdd));
        return listOfTasks.toArray(new TestTask[listOfTasks.size()]);
    }

    public static TestTask[] editTasksToList(final TestTask[] tasks, int index, TestTask EditedTask) {
        List<TestTask> listOfTasks = asList(tasks);
        listOfTasks.set(index, EditedTask);
        return listOfTasks.toArray(new TestTask[listOfTasks.size()]);
    }

    private static <T> List<T> asList(T[] objs) {
        List<T> list = new ArrayList<>();
        for (T obj : objs) {
            list.add(obj);
        }
        return list;
    }

    public static boolean compareCardAndTask(TaskCardHandle card, ReadOnlyTask task) {
        return card.isSameTask(task);
    }

    public static Tag[] getTagList(String tags) {

        if (tags.equals("")) {
            return new Tag[] {};
        }

        final String[] split = tags.split(", ");

        final List<Tag> collect = Arrays.asList(split).stream().map(e -> {
            try {
                return new Tag(e.replaceFirst("Tag: ", ""));
            } catch (IllegalValueException e1) {
                // not possible
                assert false;
                return null;
            }
        }).collect(Collectors.toList());

        return collect.toArray(new Tag[split.length]);
    }

    /**
     * Mark a task as done.
     * 
     * @param tasks
     *            A array of tasks.
     * @param taskToMark
     *            The tasks that are to be appended behind the original array.
     * @return The modified array of tasks.
     */
    public static TestTask[] markTasksToList(final TestTask[] tasks, int taskToMark) {
        taskToMark = taskToMark - 1;
        List<TestTask> listOfTasks = asList(tasks);
        if (taskToMark > listOfTasks.size()) {
            throw new IllegalArgumentException("The task index provided is invalid");
        }
        if (!listOfTasks.get(taskToMark).getIsDone()) {
            try {
                listOfTasks.get(taskToMark).setName(new Name("(Done)" + listOfTasks.get(taskToMark).getName()));
                listOfTasks.get(taskToMark).setIsDone(true);
            } catch (IllegalValueException e) {
                throw new IllegalArgumentException("The task index provided is invalid");
            }
        }
        return listOfTasks.toArray(new TestTask[listOfTasks.size()]);
    }

    /**
     * Mark a task as undone.
     * 
     * @param tasks
     *            A array of tasks.
     * @param taskToMark
     *            The tasks that are to be appended behind the original array.
     * @return The modified array of tasks.
     */
    public static TestTask[] unMarkTasksToList(TestTask[] currentList, int taskToUnMark) {
        taskToUnMark = taskToUnMark - 1;
        List<TestTask> listOfTasks = asList(currentList);
        if (taskToUnMark > listOfTasks.size()) {
            throw new IllegalArgumentException("The task index provided is invalid");
        }
        if (!listOfTasks.get(taskToUnMark).getIsDone()) {
            try {
                listOfTasks.get(taskToUnMark)
                        .setName(new Name(listOfTasks.get(taskToUnMark).getName().toString().replace("(Done)", "")));
                listOfTasks.get(taskToUnMark).setIsDone(false);
            } catch (IllegalValueException e) {
                throw new IllegalArgumentException("The task index provided is invalid");
            }
        }
        return listOfTasks.toArray(new TestTask[listOfTasks.size()]);
    }

    public static TestTask[] listTasksAccordingToCommand(TestTask[] currentList, String listCommand) {
        TypicalTestTasks tt = new TypicalTestTasks();
        switch (listCommand) { 
        case "list":
            return tt.getTypicalTasks(); 
        case "list future":
            return tt.getExpectedTypicalFutureTasks(); 
        case "list past": 
            return tt.getExpectedTypicalPastTasks();
        case "list mark": 
            return tt.getExpectedTypicalMarkTasks();
        case "list unmark": 
            return tt.getExpectedTypicalUnMarkTasks();
        case "list future mark":
            return tt.getExpectedTypicalFutureMarkTasks();
        default: 
            return null;
        }
    }

}
