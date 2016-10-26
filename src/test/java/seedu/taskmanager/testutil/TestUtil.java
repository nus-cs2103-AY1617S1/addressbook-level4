package seedu.taskmanager.testutil;

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

import seedu.taskmanager.TestApp;
import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.commons.util.FileUtil;
import seedu.taskmanager.commons.util.XmlUtil;
import seedu.taskmanager.model.TaskManager;
import seedu.taskmanager.model.item.ItemDate;
import seedu.taskmanager.model.item.Item;
import seedu.taskmanager.model.item.ItemType;
import seedu.taskmanager.model.item.Name;
import seedu.taskmanager.model.item.ReadOnlyItem;
import seedu.taskmanager.model.item.ItemTime;
import seedu.taskmanager.model.item.UniqueItemList;
import seedu.taskmanager.model.tag.Tag;
import seedu.taskmanager.model.tag.UniqueTagList;
import seedu.taskmanager.storage.XmlSerializableTaskManager;

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

    public static final Item[] sampleItemData = getSampleItemData();

    //@@author A0140060A-reused
    private static Item[] getSampleItemData() {
        try {
            return new Item[]{
                    new Item(new ItemType("event"), new Name("Game of Life"), new ItemDate("07-07"), new ItemTime("00:00"), new ItemDate("08-07"), new ItemTime("12:00"), new UniqueTagList()),
                    new Item(new ItemType("deadline"), new Name("This is a deadline"), new ItemDate(""), new ItemTime(""), new ItemDate("2017-05-05"), new ItemTime("23:59"), new UniqueTagList()),
                    new Item(new ItemType("task"), new Name("Win at Life"), new ItemDate(""), new ItemTime(""), new ItemDate(""), new ItemTime(""), new UniqueTagList()),
                    new Item(new ItemType("event"), new Name("This is an event"), new ItemDate("2018-05-05"), new ItemTime("23:59"), new ItemDate("2019-01-01"), new ItemTime("02:30"), new UniqueTagList()),
                    new Item(new ItemType("deadline"), new Name("Pay my bills"), new ItemDate(""), new ItemTime(""), new ItemDate("2020-12-30"), new ItemTime("04:49"), new UniqueTagList()),
                    new Item(new ItemType("task"), new Name("This is a task"), new ItemDate(""), new ItemTime(""), new ItemDate(""), new ItemTime(""), new UniqueTagList()),
                    new Item(new ItemType("event"), new Name("2103 exam"), new ItemDate("2018-05-05"), new ItemTime("21:59"), new ItemDate("2022-01-01"), new ItemTime("19:21"), new UniqueTagList()),
                    new Item(new ItemType("deadline"), new Name("Submit report"), new ItemDate(""), new ItemTime(""), new ItemDate("2023-03-03"), new ItemTime("14:21"), new UniqueTagList()),
                    new Item(new ItemType("task"), new Name("Buy a dozen cartons of milk"), new ItemDate(""), new ItemTime(""), new ItemDate("2016-11-21"), new ItemTime("13:10"), new UniqueTagList()),
                    new Item(new ItemType("event"), new Name("Java Workshop"), new ItemDate("2017-01-02"), new ItemTime("08:00"), new ItemDate("2017-01-02"), new ItemTime("12:00"), new UniqueTagList()),
                    new Item(new ItemType("deadline"), new Name("Submit essay assignment"), new ItemDate(""), new ItemTime(""), new ItemDate("2016-11-28"), new ItemTime("21:29"), new UniqueTagList()),
                    new Item(new ItemType("task"), new Name("Call for the electrician"), new ItemDate(""), new ItemTime(""), new ItemDate(""), new ItemTime(""), new UniqueTagList())
            };
        } catch (IllegalValueException e) {
            assert false;
            //not possible
            return null;
        }
    }
    //@@author 
    
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

    public static List<Item> generateSampleItemData() {
        return Arrays.asList(sampleItemData);
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
    
    //@@author A0143641M
    public static boolean changedFilePathInSandboxFolder(String oldFilePath, String newFilePath) { 
        return !oldFilePath.equals(newFilePath);
    }
    //@@author

    public static void createDataFileWithSampleData(String filePath) {
        createDataFileWithData(generateSampleStorageTaskManager(), filePath);
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

    public static TaskManager generateEmptyTaskManager() {
        return new TaskManager(new UniqueItemList(), new UniqueTagList());
    }

    public static XmlSerializableTaskManager generateSampleStorageTaskManager() {
        return new XmlSerializableTaskManager(generateEmptyTaskManager());
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

    //@@author A0140060A-reused
    /**
     * Removes a subset from the list of items.
     * @param items The list of items
     * @param itemsToRemove The subset of items.
     * @return The modified items after removal of the subset from items.
     */
    public static TestItem[] removeItemsFromList(final TestItem[] items, TestItem... itemsToRemove) {
        List<TestItem> listOfItems = asList(items);
        listOfItems.removeAll(asList(itemsToRemove));
        return listOfItems.toArray(new TestItem[listOfItems.size()]);
    }


    /**
     * Returns a copy of the list with the item at specified index removed.
     * @param list original list to copy from
     * @param targetIndexInOneIndexedFormat e.g. if the first element to be removed, 1 should be given as index.
     */
    public static TestItem[] removeItemFromList(final TestItem[] list, int targetIndexInOneIndexedFormat) {
        return removeItemsFromList(list, list[targetIndexInOneIndexedFormat-1]);
    }
    
    /**
     * Returns a copy of the list with items at the specified multiple indexes removed.
     * @param list original list to copy from
     * @param targetIndexes the integer array of indexes of the items to be removed
     */
    public static TestItem[] removeItemsFromList(final TestItem[] list, int[] targetIndexes) {
        TestItem[] itemsToRemove = new TestItem[targetIndexes.length];
        int numToRemove = 0;
        for (int targetIndex : targetIndexes) {
            itemsToRemove[numToRemove] = list[targetIndex-1];
            numToRemove += 1;
        }

        return removeItemsFromList(list, itemsToRemove);
    }

    /**
     * Replaces items[i] with an item.
     * @param items The array of items.
     * @param item The replacement item
     * @param index The index of the item to be replaced.
     * @return
     */
    public static TestItem[] replaceItemFromList(TestItem[] items, TestItem item, int index) {
        items[index] = item;
        return items;
    }

    /**
     * Appends items to the array of items.
     * @param items A array of items.
     * @param itemsToAdd The items that are to be appended behind the original array.
     * @return The modified array of items.
     */
    public static TestItem[] addItemsToList(final TestItem[] items, TestItem... itemsToAdd) {
        List<TestItem> listOfItems = asList(items);
        listOfItems.addAll(asList(itemsToAdd));
        return listOfItems.toArray(new TestItem[listOfItems.size()]);
    }

    private static <T> List<T> asList(T[] objs) {
        List<T> list = new ArrayList<>();
        for(T obj : objs) {
            list.add(obj);
        }
        return list;
    }

    public static boolean compareCardAndItem(TaskCardHandle card, ReadOnlyItem item) {
        return card.isSameItem(item);
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
