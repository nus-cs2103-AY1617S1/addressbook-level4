# A0142421Xunused
###### /java/guitests/guihandles/ExitHandle.java
``` java
/**
 * Provides a handle to exit the app.
 */
public class ExitHandle extends GuiHandle {
	
	private static final String EXIT_TITLE = "Exit";
	private static final String EXIT_ROOT_FIELD_ID = "#exitRoot";
	
	public ExitHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, EXIT_TITLE);
    }
}
```
###### /java/guitests/guihandles/MainGuiHandle.java
``` java
    public TagListPanelHandle getTagListPanel() {
    	return new TagListPanelHandle(guiRobot, primaryStage);
    }

}
```
###### /java/guitests/guihandles/TagCardHandle.java
``` java
/**
 * Provides a handle to a tag card in the tag list panel
 */
public class TagCardHandle extends GuiHandle {
	private static final String TAG_NAME_FIELD_ID = "#tagName";
	
	private Node node;
	
	public TagCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
		super(guiRobot, primaryStage, null);
        this.node = node;
	}
	
	protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }
	
	public String getTagName() {
		return getTextFromLabel(TAG_NAME_FIELD_ID);
	}
	
	public boolean isSameTag(Tag tag){
        return getTagName().equals(tag.getName()); 
    }

}
```
###### /java/guitests/guihandles/TagListPanelHandle.java
``` java
/**
 * Provides a handle for the panel containing tag List
 */
public class TagListPanelHandle extends GuiHandle{
	
	public static final int NOT_FOUND = -1;
	public static final String CARD_PANE_ID = "#cardPane";

    private static final String TAG_LIST_VIEW_ID = "#tagListView";

    public TagListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<Tag> getSelectedTasks() {
        ListView<Tag> tagList = getListView();
        return tagList.getSelectionModel().getSelectedItems();
    }

    public ListView<Tag> getListView() {
        return (ListView<Tag>) getNode(TAG_LIST_VIEW_ID);
    }
}
```
###### /java/seedu/todo/commons/util/DateTimeUtilTest.java
``` java
    /*
     *@Test
    public void testBeforeOther_onDate_null_true() throws IllegalValueException {
    	TaskDate byDate = new TaskDate("8-12-2000", "by");
    	assertTrue(DateTimeUtil.beforeOther(null, byDate));
    }

    @Test
    public void testBeforeOther_byDate_null_true() throws IllegalValueException {
    	TaskDate onDate = new TaskDate("8-12-2000", "on");
    	assertTrue(DateTimeUtil.beforeOther(onDate, null));
    }
    
    @Test
    public void testBeforeOther_onDate_null_byDate_null_true() throws IllegalValueException {
    	assertTrue(DateTimeUtil.beforeOther(null, null));
    }
    */
```
