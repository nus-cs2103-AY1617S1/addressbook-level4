package seedu.malitio.testutil;

import com.google.common.io.Files;

import guitests.guihandles.DeadlineCardHandle;
import guitests.guihandles.EventCardHandle;
import guitests.guihandles.FloatingTaskCardHandle;
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
import seedu.malitio.TestApp;
import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.commons.util.FileUtil;
import seedu.malitio.commons.util.XmlUtil;
import seedu.malitio.model.Malitio;
import seedu.malitio.model.tag.Tag;
import seedu.malitio.model.tag.UniqueTagList;
import seedu.malitio.model.task.*;
import seedu.malitio.storage.XmlSerializableMalitio;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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

    public static final FloatingTask[] sampleFloatingTaskData = getSampleTaskData();
    public static final Deadline[] sampleDeadlineData = getSampleDeadlineData();
    public static final Event[] sampleEventData = getSampleEventData();
    
    private static FloatingTask[] getSampleTaskData() {
        try {
            return new FloatingTask[]{
                    new FloatingTask(new Name("adjust meter"), new UniqueTagList()),
                    new FloatingTask(new Name("bring along notes"), new UniqueTagList()),
                    new FloatingTask(new Name("copy answer"), new UniqueTagList()),
                    new FloatingTask(new Name("do some sit-up"), new UniqueTagList()),
                    new FloatingTask(new Name("eat with mom"), new UniqueTagList()),
                    new FloatingTask(new Name("forgive and forget"), new UniqueTagList()),
                    new FloatingTask(new Name("go shopping"), new UniqueTagList()),
                    new FloatingTask(new Name("hopping"), new UniqueTagList()),
                    new FloatingTask(new Name("Ida Mueller"), new UniqueTagList())
            };
        } catch (IllegalValueException e) {
            assert false;
            //not possible
            return null;
        }
    }

    private static Event[] getSampleEventData() {
        try {
            return new Event[]{
                    new Event(new Name("Zen Birthday Celebration"), new DateTime("10-20 1100"), new DateTime("10-20 1200"), new UniqueTagList()),
                    new Event(new Name("JuMin Speech"), new DateTime("11-20 1100"), new DateTime("11-20 1200"), new UniqueTagList()),
                    new Event(new Name("STxxxx Lecture"), new DateTime("10-03 0000"), new DateTime("10-03 1000"), new UniqueTagList()),
                    new Event(new Name("My Birthday"), new DateTime("03-20 0000"), new DateTime("03-20 2359"), new UniqueTagList()),
                    new Event(new Name("Dinner Date with YooSung"), new DateTime("02-18-2017 1100"), new DateTime("02-19-2017 1200"), new UniqueTagList()),
                    new Event(new Name("Play Dota with Friends"), new DateTime("12-02-2016 0000"), new DateTime("12-02-2016 2359"), new UniqueTagList()),
                    new Event(new Name("Get Rich Seminar"), new DateTime("11-17 0000"), new DateTime("11-17 0210"), new UniqueTagList()),
                    new Event(new Name("Badminton"), new DateTime("01-03 1400"), new DateTime("12-02 1700"), new UniqueTagList()),
            };
        } catch (IllegalValueException e) {
            assert false;
            //not possible
            return null;
        }
    }

    private static Deadline[] getSampleDeadlineData() {
        try {
            return new Deadline[]{
                    new Deadline(new Name("Complete ST4231 Homework"), new DateTime ("10-20 2300"), new UniqueTagList()),
                    new Deadline(new Name("Feed Elizabeth the 3rd"), new DateTime ("08-08 0808"), new UniqueTagList()),
                    new Deadline(new Name("Buy materials for christmas party!"), new DateTime ("12-24 1200"), new UniqueTagList()),
                    new Deadline(new Name("Make new year resolutions"), new DateTime ("12-31 2359"), new UniqueTagList()),
                    new Deadline(new Name("Study for Test"), new DateTime ("11-18 1300"), new UniqueTagList()),
                    new Deadline(new Name("Buy food for Zen"), new DateTime ("01-01-2017 0000"), new UniqueTagList()),
                    new Deadline(new Name("Buy present for girlfriend"), new DateTime ("03-05-2017 0500"), new UniqueTagList()),
                    new Deadline(new Name("Complete Hearthstone missions"), new DateTime ("06-10 1940"), new UniqueTagList()),
                    new Deadline(new Name("Reply lecturer emails"), new DateTime ("10-30-2016 2359"), new UniqueTagList())
            };
        } catch (IllegalValueException e) {
            assert false;
            //not possible
            return null;
        }
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

    public static List<FloatingTask> generateSampleTaskData() {
        return Arrays.asList(sampleFloatingTaskData);
    }
    
    public static List<Deadline> generateSampleDeadlineData() {
        return Arrays.asList(sampleDeadlineData);
    }
    
    public static List<Event> generateSampleEventData() {
        return Arrays.asList(sampleEventData);
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
        createDataFileWithData(generateSampleStoragemalitio(), filePath);
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

    public static Malitio generateEmptymalitio() {
        return new Malitio(new UniqueFloatingTaskList(), new UniqueDeadlineList(),
               new UniqueEventList(), new UniqueTagList());
    }

    public static XmlSerializableMalitio generateSampleStoragemalitio() {
        return new XmlSerializableMalitio(generateEmptymalitio());
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
    public static TestFloatingTask[] removeTasksFromList(final TestFloatingTask[] tasks, TestFloatingTask... tasksToRemove) {
        List<TestFloatingTask> listOfTasks = asList(tasks);
        listOfTasks.removeAll(asList(tasksToRemove));
        return listOfTasks.toArray(new TestFloatingTask[listOfTasks.size()]);
    }


    /**
     * Returns a copy of the list with the task at specified index removed.
     * @param list original list to copy from
     * @param targetIndexInOneIndexedFormat e.g. if the first element to be removed, 1 should be given as index.
     */
    public static TestFloatingTask[] removeTaskFromList(final TestFloatingTask[] list, int targetIndexInOneIndexedFormat) {
        return removeTasksFromList(list, list[targetIndexInOneIndexedFormat-1]);
    }

    /**
     * Replaces tasks[i] with a task.
     * @param tasks The array of tasks.
     * @param task The replacement task
     * @param index The index of the task to be replaced.
     * @return
     */
    public static TestFloatingTask[] replaceTaskFromList(TestFloatingTask[] tasks, TestFloatingTask task, int index) {
        tasks[index] = task;
        return tasks;
    }

    /**
     * Appends tasks to the array of tasks.
     * @param tasks A array of tasks.
     * @param tasksToAdd The tasks that are to be appended behind the original array.
     * @return The modified array of tasks.
     */
    public static TestFloatingTask[] addTasksToList(final TestFloatingTask[] tasks, TestFloatingTask... tasksToAdd) {
        List<TestFloatingTask> listOfTasks = asList(tasks);
        listOfTasks.addAll(asList(tasksToAdd));
        return listOfTasks.toArray(new TestFloatingTask[listOfTasks.size()]);
    }
    
    public static TestDeadline[] addTasksToList(final TestDeadline[] deadlines, TestDeadline... deadlinesToAdd) {
        List<TestDeadline> listOfDeadlines = asList(deadlines);
        listOfDeadlines.addAll(asList(deadlinesToAdd));
        Collections.sort(listOfDeadlines, new Comparator<TestDeadline>() {
            public int compare(TestDeadline d1, TestDeadline d2) {
                if (d1.getDue() == null || d2.getDue() == null)
                  return 0;
                return d1.getDue().compareTo(d2.getDue());
            }
          });
        return listOfDeadlines.toArray(new TestDeadline[listOfDeadlines.size()]);
    }
    
    public static TestEvent[] addTasksToList(final TestEvent[] events, TestEvent... eventsToAdd) {
        List<TestEvent> listOfEvents = asList(events);
        listOfEvents.addAll(asList(eventsToAdd));
        Collections.sort(listOfEvents, new Comparator<TestEvent>() {
            public int compare(TestEvent e1, TestEvent e2) {
                if (e1.getStart() == null || e2.getStart() == null)
                  return 0;
                return e1.getStart().compareTo(e2.getStart());
            }
          });
        return listOfEvents.toArray(new TestEvent[listOfEvents.size()]);
    }

    private static <T> List<T> asList(T[] objs) {
        List<T> list = new ArrayList<>();
        for(T obj : objs) {
            list.add(obj);
        }
        return list;
    }

    public static boolean compareCardAndTask(FloatingTaskCardHandle card, ReadOnlyFloatingTask task) {
        return card.isSameTask(task);
    }
    
    public static boolean compareCardAndTask(DeadlineCardHandle card, ReadOnlyDeadline task) {
        return card.isSameDeadline(task);
    }
    
    public static boolean compareCardAndTask(EventCardHandle card, ReadOnlyEvent task) {
        return card.isSameTask(task);
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
