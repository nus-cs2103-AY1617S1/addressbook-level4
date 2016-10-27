package seedu.todoList.testutil;

import com.google.common.io.Files;

import guitests.guihandles.DeadlineCardHandle;
import guitests.guihandles.EventCardHandle;
import guitests.guihandles.TaskCardHandle;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import junit.framework.AssertionFailedError;
import seedu.todoList.TestApp;
import seedu.todoList.commons.exceptions.IllegalValueException;
import seedu.todoList.commons.util.FileUtil;
import seedu.todoList.commons.util.XmlUtil;
import seedu.todoList.model.TaskList;

import seedu.todoList.model.task.*;
import seedu.todoList.model.task.attributes.*;
import seedu.todoList.storage.XmlSerializableTaskList;
import seedu.todoList.storage.XmlSerializableTodoList;
import seedu.todoList.storage.XmlSerializableDeadlineList;
import seedu.todoList.storage.XmlSerializableEventList;

import org.loadui.testfx.GuiTest;
import org.testfx.api.FxToolkit;

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

    public static final Task[] sampletaskData = getSampletaskData();
    public static final Event[] sampleeventData = getSampleeventData();
    public static final Deadline[] sampledeadlineData = getSampledeadlineData();

    private static Task[] getSampletaskData() {
        try {
            return new Task[]{
                    new Todo(new Name("TT 11"), new StartDate("01-11-2016"), new EndDate("02-11-2016"), new Priority("1"), "false"),
                    new Todo(new Name("Assignment 12"), new StartDate("02-11-2016"), new EndDate("03-11-2016"), new Priority("2"), "false"),
                    new Todo(new Name("Assignment 13"), new StartDate("03-11-2016"), new EndDate("03-11-2016"), new Priority("3"), "false"),
                    new Todo(new Name("Assignment 14"), new StartDate("04-11-2016"), new EndDate("05-11-2016"), new Priority("4"), "false"),
                    new Todo(new Name("Assignment 15"), new StartDate("05-11-2016"), new EndDate("06-11-2016"), new Priority("5"), "false"),
                    new Todo(new Name("Assignment 16"), new StartDate("06-11-2016"), new EndDate("07-11-2016"), new Priority("6"), "false"),
                    new Todo(new Name("Assignment 17"), new StartDate("07-11-2016"), new EndDate("08-11-2016"), new Priority("7"), "false"),
            };
        } catch (IllegalValueException e) {
            assert false;
            //not possible
            return null;
        }
    }
    //@@author A0132157M reused
    private static Event[] getSampleeventData() {
        try {
            return new Event[]{
                    new Event(new Name("EE 11"), new StartDate("01-11-2016"), new EndDate("02-11-2016"), new StartTime("1000"), new EndTime("1030"), "false"),
                    new Event(new Name("Essignment 12"), new StartDate("02-11-2016"), new EndDate("02-11-2016"),  new StartTime("1000"), new EndTime("1030"), "false"),
                    new Event(new Name("Essignment 13"), new StartDate("03-11-2016"), new EndDate("02-11-2016"),  new StartTime("1100"), new EndTime("1130"), "false"),
                    new Event(new Name("Essignment 14"), new StartDate("04-11-2016"), new EndDate("02-11-2016"),  new StartTime("1200"), new EndTime("1230"), "false"),
                    new Event(new Name("Essignment 15"), new StartDate("05-11-2016"), new EndDate("02-11-2016"),  new StartTime("1300"), new EndTime("1330"), "false"),
                    new Event(new Name("Essignment 16"), new StartDate("06-11-2016"), new EndDate("02-11-2016"),  new StartTime("1400"), new EndTime("1430"), "false"),
                    new Event(new Name("Essignment 17"), new StartDate("07-11-2016"), new EndDate("02-11-2016"),  new StartTime("1500"), new EndTime("1530"), "false"),
            };
        } catch (IllegalValueException e) {
            assert false;
            //not possible
            return null;
        }
    }
    //@@author A0132157M reused
    private static Deadline[] getSampledeadlineData() {
        try {
            return new Deadline[]{
                    new Deadline(new Name("DD 11"), new StartDate("01-11-2016"), new EndTime("1100"), "false"),
                    new Deadline(new Name("Dssignment 12"), new StartDate("02-11-2016"), new EndTime("1200"), "false"),
                    new Deadline(new Name("Dssignment 13"), new StartDate("03-11-2016"), new EndTime("1300"), "false"),
                    new Deadline(new Name("Dssignment 14"), new StartDate("04-11-2016"), new EndTime("1400"), "false"),
                    new Deadline(new Name("Dssignment 15"), new StartDate("05-11-2016"), new EndTime("1500"), "false"),
                    new Deadline(new Name("Dssignment 16"), new StartDate("06-11-2016"), new EndTime("1600"), "false"),
                    new Deadline(new Name("Dssignment 17"), new StartDate("07-11-2016"), new EndTime("1700"), "false"),
            };
        } catch (IllegalValueException e) {
            assert false;
            //not possible
            return null;
        }
    }

    public static List<Task> generateSampletaskData() {
        return Arrays.asList(sampletaskData);
    }
    //@@author A0132157M reused   
    public static List<Event> generateSampleeventData() {
        return Arrays.asList(sampleeventData);
    }
    //@@author A0132157M reused
    public static List<Deadline> generateSampledeadlineData() {
        return Arrays.asList(sampledeadlineData);
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
        createDataFileWithData(generateSampleStorageTodoList(), filePath);
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


    public static TaskList generateEmptyTodoList() {
        return new TaskList(new UniqueTaskList());
    }
    //@@author A0132157M reused
    public static TaskList generateEmptyEventList() {
        return new TaskList(new UniqueTaskList());
    }
    //@@author A0132157M reused
    public static TaskList generateEmptyDeadlineList() {
        return new TaskList(new UniqueTaskList());
    }

    public static XmlSerializableTodoList generateSampleStorageTodoList() {
        return new XmlSerializableTodoList(generateEmptyTodoList());
    }   
    //@@author A0132157M reused
    public static XmlSerializableEventList generateSampleStorageEventList() {
        return new XmlSerializableEventList(generateEmptyEventList());
    }
    //@@author A0132157M reused
    public static XmlSerializableDeadlineList generateSampleStorageDeadlineList() {
        return new XmlSerializableDeadlineList(generateEmptyDeadlineList());
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

    /**
     * Removes a subset from the list of tasks.
     * @param tasks The list of tasks
     * @param tasksToRemove The subset of tasks.
     * @return The modified tasks after removal of the subset from tasks.
     */
    public static TestTask[] removetasksFromList(final TestTask[] tasks, TestTask... tasksToRemove) {
        List<TestTask> listOftasks = asList(tasks);
        listOftasks.removeAll(asList(tasksToRemove));
        return listOftasks.toArray(new TestTask[listOftasks.size()]);
    }
    
    public static TestEvent[] removeEventsFromList(final TestEvent[] events, TestEvent... eventsToRemove) {
        List<TestEvent> listOfEvents = asList(events);
        listOfEvents.removeAll(asList(eventsToRemove));
        return listOfEvents.toArray(new TestEvent[listOfEvents.size()]);
    }
    
    public static TestDeadline[] removeDeadlinesFromList(final TestDeadline[] events, TestDeadline... deadlineToRemove) {
        List<TestDeadline> listOfdd = asList(events);
        listOfdd.removeAll(asList(deadlineToRemove));
        return listOfdd.toArray(new TestDeadline[listOfdd.size()]);
    }


    /**
     * Returns a copy of the list with the task at specified index removed.
     * @param list original list to copy from
     * @param targetIndexInOneIndexedFormat e.g. if the first element to be removed, 1 should be given as index.
     */
    public static TestTask[] removetaskFromList(final TestTask[] list, int targetIndexInOneIndexedFormat) {
        return removetasksFromList(list, list[targetIndexInOneIndexedFormat-1]);
    }
    public static TestEvent[] removeEventFromList(final TestEvent[] list, int targetIndexInOneIndexedFormat) {
        return removeEventsFromList(list, list[targetIndexInOneIndexedFormat-1]);
    }
    public static TestDeadline[] removeDeadlineFromList(final TestDeadline[] list, int targetIndexInOneIndexedFormat) {
        return removeDeadlinesFromList(list, list[targetIndexInOneIndexedFormat-1]);
    }

    /**
     * Replaces tasks[i] with a task.
     * @param tasks The array of tasks.
     * @param task The replacement task
     * @param index The index of the task to be replaced.
     * @return
     */
    public static TestTask[] replacetaskFromList(TestTask[] tasks, TestTask task, int index) {
        tasks[index] = task;
        return tasks;
    }
    
    public static TestEvent[] replaceEventFromList(TestEvent[] events, TestEvent event, int index) {
        events[index] = event;
        return events;
    }
    public static TestDeadline[] replaceDeadlineFromList(TestDeadline[] dds, TestDeadline dd, int index) {
        dds[index] = dd;
        return dds;
    }

    /**
     * Appends tasks to the array of tasks.
     * @param tasks A array of tasks.
     * @param tasksToAdd The tasks that are to be appended behind the original array.
     * @return The modified array of tasks.
     */
    public static TestTask[] addTasksToList(final TestTask[] tasks, TestTask... tasksToAdd) {
        List<TestTask> listOftasks = asList(tasks);
        listOftasks.addAll(asList(tasksToAdd));
        return listOftasks.toArray(new TestTask[listOftasks.size()]);
    }
    
    public static TestEvent[] addEventsToList(final TestEvent[] events, TestEvent... eventsToAdd) {
        List<TestEvent> listOfEvents = asList(events);
        listOfEvents.addAll(asList(eventsToAdd));
        return listOfEvents.toArray(new TestEvent[listOfEvents.size()]);
    }
    
    public static TestDeadline[] addDeadlinesToList(final TestDeadline[] events, TestDeadline... eventsToAdd) {
        List<TestDeadline> listOfEvents = asList(events);
        listOfEvents.addAll(asList(eventsToAdd));
        return listOfEvents.toArray(new TestDeadline[listOfEvents.size()]);
    }

    private static <T> List<T> asList(T[] objs) {
        List<T> list = new ArrayList<>();
        for(T obj : objs) {
            list.add(obj);
        }
        return list;
    }

    public static boolean compareCardAndTask(TaskCardHandle card, ReadOnlyTask task) {
        return card.isSametask(task); //something wrong. Always return false
    }
    
    public static boolean compareCardAndEvent(EventCardHandle card, ReadOnlyTask event) {
        return card.isSameEvent(event); //something wrong. Always return false
    }
    
    public static boolean compareCardAndDeadline(DeadlineCardHandle card, ReadOnlyTask tasks) {
        return card.isSameDeadline(tasks); //something wrong. Always return false
    }

}
