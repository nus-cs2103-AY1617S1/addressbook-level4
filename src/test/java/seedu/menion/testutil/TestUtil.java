package seedu.menion.testutil;

import com.google.common.io.Files;

import guitests.guihandles.EventCardHandle;
import guitests.guihandles.FloatingTaskCardHandle;
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

import seedu.menion.TestApp;
import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.commons.util.FileUtil;
import seedu.menion.commons.util.XmlUtil;
import seedu.menion.model.ActivityManager;
import seedu.menion.model.activity.*;
import seedu.menion.storage.XmlSerializableActivityManager;

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

    public static final Activity[] sampleActivityData = getSamplePersonData();

    private static Activity[] getSamplePersonData() {
        try {
            return new Activity[]{
                    // Floating Task
                   // new Activity(Activity.FLOATING_TASK_TYPE, new ActivityName("Pay JS for spotify!"), new Note(" It costs $15")),
                    // Task
                    new Activity(Activity.TASK_TYPE, new ActivityName("Do CS2103T testing"), new Note("it is so hard!"), new ActivityDate("10-08-2016"), new ActivityTime("1900"), new Completed(Completed.UNCOMPLETED_ACTIVITY)),
                    // Event
                   // new Activity(Activity.EVENT_TYPE, new ActivityName("CS2103T tutorial"), new Note("Don't Sleep"), new ActivityDate("10-08-2016"), new ActivityTime("1500"), new ActivityDate("10-08-2016"), new ActivityTime("1800"))               
            };
        } catch (IllegalValueException e) {
            assert false;
            //not possible
            return null;
        }
    }

    public static List<Activity> generateSamplePersonData() {
        return Arrays.asList(sampleActivityData);
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

    public static ActivityManager generateEmptyAddressBook() {
        return new ActivityManager(new UniqueActivityList(), new UniqueActivityList(), new UniqueActivityList());
    }

    public static XmlSerializableActivityManager generateSampleStorageAddressBook() {
        return new XmlSerializableActivityManager(generateEmptyAddressBook());
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
     * Removes a subset from the list of activities.
     * @param activities The list of activities
     * @param activitiesToRemove The subset of activities.
     * @return The modified activities after removal of the subset from activities.
     */
    public static TestActivity[] removeActivitiesFromList(final TestActivity[] activities, TestActivity... activitiesToRemove) {
        List<TestActivity> listOfActivities = asList(activities);
        listOfActivities.removeAll(asList(activitiesToRemove));
        return listOfActivities.toArray(new TestActivity[listOfActivities.size()]);
    }


    /**
     * Returns a copy of the list with the activity at specified index removed.
     * @param list original list to copy from
     * @param targetIndexInOneIndexedFormat e.g. if the first element to be removed, 1 should be given as index.
     */
    public static TestActivity[] removeActivityFromList(final TestActivity[] list, int targetIndexInOneIndexedFormat) {
        return removeActivitiesFromList(list, list[targetIndexInOneIndexedFormat-1]);
    }

    /**
     * Replaces activities[i] with a activity.
     * @param activities The array of activities.
     * @param activity The replacement activity
     * @param index The index of the activity to be replaced.
     * @return
     */
    public static TestActivity[] replacePersonFromList(TestActivity[] activities, TestActivity activity, int index) {
        activities[index] = activity;
        return activities;
    }

    /**
     * Appends activities to the array of activities.
     * @param activities A array of activities.
     * @param activitiesToAdd The activities that are to be appended behind the original array.
     * @return The modified array of activities.
     */
    public static TestActivity[] addActivitiesToList(final TestActivity[] activities, TestActivity... activitiesToAdd) {
        List<TestActivity> listOfActivities = asList(activities);
        listOfActivities.addAll(asList(activitiesToAdd));
        return listOfActivities.toArray(new TestActivity[listOfActivities.size()]);
    }

    private static <T> List<T> asList(T[] objs) {
        List<T> list = new ArrayList<>();
        for(T obj : objs) {
            list.add(obj);
        }
        return list;
    }

    public static boolean compareCardAndTask(TaskCardHandle card, ReadOnlyActivity task) {
        return card.isSameActivity(task);
    }
    
    public static boolean compareCardAndFloatingTask(FloatingTaskCardHandle card, ReadOnlyActivity floatingTask) {
        return card.isSameActivity(floatingTask);
    }

    public static boolean compareCardAndEvent(EventCardHandle card, ReadOnlyActivity event) {
        return card.isSameActivity(event);
    }

}
