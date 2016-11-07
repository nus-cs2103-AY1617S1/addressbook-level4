package w15c2.tusk.testutil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.loadui.testfx.GuiTest;
import org.testfx.api.FxToolkit;

import com.google.common.io.Files;

import guitests.guihandles.TaskCardHandle;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import junit.framework.AssertionFailedError;
import w15c2.tusk.TestApp;
import w15c2.tusk.commons.collections.UniqueItemCollection;
import w15c2.tusk.commons.collections.UniqueItemCollection.DuplicateItemException;
import w15c2.tusk.commons.core.UnmodifiableObservableList;
import w15c2.tusk.commons.exceptions.IllegalValueException;
import w15c2.tusk.commons.util.FileUtil;
import w15c2.tusk.commons.util.XmlUtil;
import w15c2.tusk.logic.commands.taskcommands.AddTaskCommand;
import w15c2.tusk.model.Model;
import w15c2.tusk.model.ModelManager;
import w15c2.tusk.model.task.DeadlineTask;
import w15c2.tusk.model.task.EventTask;
import w15c2.tusk.model.task.FloatingTask;
import w15c2.tusk.model.task.Task;
import w15c2.tusk.storage.task.XmlSerializableTaskManager;

/**
 * A utility class for test cases.
 */
public class TestUtil {
    
    /**
    * Folder used for temp files created during testing. Ignored by Git.
    */
    public static String SANDBOX_FOLDER = FileUtil.getPath("./src/test/data/sandbox/");
    
    private static UniqueItemCollection<Task> initialTasks = new UniqueItemCollection<Task>();
    static {
        
        try {
            initialTasks.add(new FloatingTask("Initial Test Task 1"));
            initialTasks.add(new FloatingTask("Initial Test Task 2"));
            initialTasks.add(new FloatingTask("Initial Test Task 3"));
            initialTasks.add(new FloatingTask("Initial Test Task 4"));
            initialTasks.add(new FloatingTask("Initial Test Task 5"));
            initialTasks.add(new FloatingTask("Initial Test Task 6"));
            initialTasks.add(new FloatingTask("Initial Test Task 7"));
            initialTasks.add(new FloatingTask("Initial Test Task 8"));
            initialTasks.add(new FloatingTask("Initial Test Task 9"));
            initialTasks.add(new FloatingTask("Initial Test Task 10"));
            initialTasks.add(new FloatingTask("Initial Test Task 11"));
            initialTasks.add(new FloatingTask("Initial Test Task 12"));
        } catch (DuplicateItemException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    public static Model setupEmptyTaskList() {
        return new ModelManager();
    }
    
    public static UniqueItemCollection<Task> getInitialTasks() {
        return initialTasks.copyCollection();
    }
    
    public static UniqueItemCollection<Task> getDefaultTasks() {
        try {
            return setupSomeTasksInTaskList(10).getTasks();
        } catch (IllegalValueException ex) {
            assert false;
            return null;
        }
        
    }

    // Setting up tasks in the TaskList in order to find them in the tests
    public static Model setupSomeTasksInTaskList(int n) throws IllegalValueException {
        Model newTaskList = new ModelManager();
        // Add 3 tasks into the task manager
        for (int i = 0; i < n; i++) {
            AddTaskCommand command = new AddTaskCommand(String.format("Task %d", i));
            command.setData(newTaskList);
            command.execute();
        }
        return newTaskList;
    }
	
    //@@author A0139817U
	/**
	 * Setting up Floating tasks in the TaskList in order to find them in the tests
	 * 
	 * @param n 	Number of floating tasks to setup in the model.
	 * @return		Model that contains the floating tasks.
	 * @throws IllegalValueException 	If Floating task constructor failed.
	 */
	public static Model setupFloatingTasks(int n) throws IllegalValueException {
		Model newTaskList = new ModelManager();
		// Add n tasks into the task manager
		for (int i = 0; i < n; i++) {
			newTaskList.addTask(new FloatingTask(String.format("Task %d", i)));
		}
		return newTaskList;
	}
	
	//@@author A0139817U
	/**
	 * Setting up tasks with more varied names
	 * 
	 * @param n 	Number of floating tasks with varied names to setup in the model.
	 * @return		Model that contains the floating tasks with varied names.
	 * @throws IllegalValueException 	If Floating task constructor failed.
	 */
	public static Model setupTasksWithVariedNames(int n) throws IllegalValueException {
		Model newTaskList = new ModelManager();
		// Add n tasks into the task manager
		for (int i = 0; i < n; i++) {
			if (i % 3 == 0) {
				newTaskList.addTask(new FloatingTask(String.format("Apple %d", i)));
			} else if (i % 3 == 1) {
				newTaskList.addTask(new FloatingTask(String.format("Banana %d", i)));
			} else {
				newTaskList.addTask(new FloatingTask(String.format("Carrot %d", i)));
			}
		}
		return newTaskList;
	}
	
	// Setting up completed tasks in the TaskList in order to find them in the tests
	public static Model setupSomeCompletedTasksInTaskList(int n) throws IllegalValueException {
		Model newTaskList = new ModelManager();
		// Add 3 tasks into the task manager
		for (int i = 0; i < n; i++) {
			AddTaskCommand command = new AddTaskCommand(String.format("Task %d", i));
			command.setData(newTaskList);
			command.execute();
		}
		UnmodifiableObservableList<Task> list= newTaskList.getCurrentFilteredTasks();
		for (int i = 0; i < n; i++) {
			list.get(i).setAsComplete();
		}
		return newTaskList;
	}
		
	// Setting up pinned tasks in the TaskList in order to find them in the tests
	public static Model setupSomePinnedTasksInTaskList(int n) throws IllegalValueException {
		Model newTaskList = new ModelManager();
		// Add 3 tasks into the task manager
		for (int i = 0; i < n; i++) {
			AddTaskCommand command = new AddTaskCommand(String.format("Task %d", i));
			command.setData(newTaskList);
			command.execute();
		}
		UnmodifiableObservableList<Task> list= newTaskList.getCurrentFilteredTasks();
		for (int i = 0; i < n; i++) {
			list.get(i).setAsPin();
		}
		return newTaskList;
	}
	
	//@@author A0139817U
	/**
	 * Setting up interleaved Floating, Deadline and Event tasks.
	 * Dates for Deadline tasks are set to 1 January 2016.
	 * Date range for Event tasks are set from 1 January 2016 to 2 January 2016.
	 * 
	 * NOTE: Do not change the descriptions and dates because other tests depend on it.
	 * 
	 * @param n 	Number of mixed tasks to setup in the model.
	 * @return		Model that contains the mixed tasks.
	 * @throws IllegalValueException 	If task constructor failed.
	 */
	public static Model setupMixedTasks(int n) throws IllegalValueException {
		Model newTaskList = new ModelManager();
		int counter = 0;
		for (int i = 0; i < n; i++) {
			int k = counter % 3;
			if (k == 0) {
				// Add Floating task
				String description = String.format("Task %d", i);
				newTaskList.addTask(new FloatingTask(description));
				
			} else if (k == 1) {
				// Add Deadline task
				String description = String.format("Task %d", i);
				Date deadline = new GregorianCalendar(2016, Calendar.JANUARY, 1).getTime();
				newTaskList.addTask(new DeadlineTask(description, deadline));
				
			} else {
				// Add Event task
				String description = String.format("Task %d", i);
				Date startDate = new GregorianCalendar(2016, Calendar.JANUARY, 1).getTime();
				Date endDate = new GregorianCalendar(2016, Calendar.JANUARY, 2).getTime();
				newTaskList.addTask(new EventTask(description, startDate, endDate));
			}
			counter++;
		}
		return newTaskList;
	}
	
	

	 public static XmlSerializableTaskManager generateSampleStorageTaskManager() {
	     return new XmlSerializableTaskManager(generateEmptyTaskManager());
	 }
	 public static UniqueItemCollection<Task> generateEmptyTaskManager() {
	     return new UniqueItemCollection<Task>();
	 }
	 
	 public static AnchorPane generateAnchorPane() {
	     return new AnchorPane();
	 }

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
    
    public static boolean compareCardAndTask(TaskCardHandle card, Task task) {
        return card.isSameTask(task);
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
    
    public static void main(String... s) {
        createDataFileWithSampleData(TestApp.SAVE_LOCATION_FOR_TESTING);
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
}
