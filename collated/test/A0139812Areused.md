# A0139812Areused
###### \java\seedu\todo\guitests\guihandles\GuiHandle.java
``` java
/**
 * Base class for all GUI Handles used in testing.
 */
public class GuiHandle {
    protected final GuiRobot guiRobot;
    protected final Stage primaryStage;
    /**
     * An optional stage that exists in the App other than the primaryStage, could be a alert dialog, popup window, etc.
     */
    protected Optional<Stage> intermediateStage = Optional.empty();
    protected final String stageTitle;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    public GuiHandle(GuiRobot guiRobot, Stage primaryStage, String stageTitle) {
        this.guiRobot = guiRobot;
        this.primaryStage = primaryStage;
        this.stageTitle = stageTitle;
        focusOnSelf();
    }

    public void focusOnWindow(String stageTitle) {
        logger.info("Focusing " + stageTitle);
        Optional<Window> window = guiRobot.listTargetWindows()
                .stream()
                .filter(w -> w instanceof Stage && ((Stage) w).getTitle().equals(stageTitle)).findAny();

        if (!window.isPresent()) {
            logger.warning("Can't find stage " + stageTitle + ", Therefore, aborting focusing");
            return;
        }
        intermediateStage = Optional.ofNullable((Stage) window.get());
        guiRobot.targetWindow(window.get());
        guiRobot.interact(() -> window.get().requestFocus());
        logger.info("Finishing focus " + stageTitle);
    }

    protected Node getNode(String query) {
        return guiRobot.lookup(query).tryQuery().get();
    }

    protected String getTextFieldText(String filedName) {
        return ((TextField) getNode(filedName)).getText();
    }
    
    protected String getTextAreaText(String filedName) {
        return ((TextArea) getNode(filedName)).getText();
    }

    protected void setTextField(String textFieldId, String newText) {
        guiRobot.clickOn(textFieldId);
        ((TextField) guiRobot.lookup(textFieldId).tryQuery().get()).setText(newText);
        guiRobot.sleep(10); // so that the texts stays visible on the GUI for a short period
    }

    public void pressEnter() {
        guiRobot.type(KeyCode.ENTER);
    }

    protected String getStringFromText(String fieldId, Node parentNode) {
        com.google.common.base.Optional<Node> text = guiRobot.from(parentNode).lookup(fieldId).tryQuery();
        if (text.isPresent()) {
            return ((Text) text.get()).getText();
        } else {
            return "";
        }
    }

    protected String getStringFromText(String fieldId) {
        return ((Text) guiRobot.lookup(fieldId).tryQuery().get()).getText();
    }

    public void focusOnSelf() {
        if (stageTitle != null) {
            focusOnWindow(stageTitle);
        }
    }

    public void focusOnMainApp() {
        this.focusOnWindow(TestApp.APP_TITLE);
    }

    public void closeWindow() {
        Optional<Window> window = guiRobot.listTargetWindows()
                .stream()
                .filter(w -> w instanceof Stage && ((Stage) w).getTitle().equals(stageTitle)).findAny();

        if (!window.isPresent()) {
            return;
        }

        guiRobot.targetWindow(window.get());
        guiRobot.interact(() -> ((Stage) window.get()).close());
        focusOnMainApp();
    }
}
```
###### \java\seedu\todo\testutil\TestUtil.java
``` java
public class TestUtil {

    public static final String LS = System.lineSeparator();

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    public static final String SANDBOX_FOLDER = FileUtil.getPath("./src/test/data/sandbox/");

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

}
```
