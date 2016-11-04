package guitests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import javafx.scene.input.KeyCode;


//@@author A0146107M
public class KeyboardScrollTest extends TaskListGuiTest {
	
	@Before
	public void initialize(){
		assertCurrentlySelectedIndex(-1);
		commandBox.enterCommand("");
	}
	
	@Test
	public void keyboardScrollUp_noSelection_selectFirst() {
		//test scrolling up from unselected
		scrollUp();
		assertCurrentlySelectedIndex(0);
	}
	
	@Test
	public void keyboardScrollDown_noSelection_selectFirst() {
		//test scrolling down from unselected
		scrollDown();
		assertCurrentlySelectedIndex(0);
	}
	
	@Test
	public void keyboardScrollUp_topBound_nothingHappens() {
		selectIndexInListPanel(0);
		scrollUp();
		assertCurrentlySelectedIndex(0);
	}
	
	@Test
	public void keyboardScrollDown_lowerBound_nothingHappens() {
		selectIndexInListPanel(getListViewSize()-1);
		scrollDown();
		assertCurrentlySelectedIndex(getListViewSize()-1);
	}
	
	@Test
	public void keyboardScrollDown_success_indexIncreases() {
		selectIndexInListPanel(0);
		scrollDown();
		assertCurrentlySelectedIndex(getListViewSize()-1);
	}
	
	@Test
	public void keyboardScrollUp_success_indexIncreases() {
		selectIndexInListPanel(getListViewSize()-1);
		scrollUp();
		assertCurrentlySelectedIndex(0);
	}
	
	private void scrollUp(){
		commandBox.pressKeyCombi(KeyCode.CONTROL, KeyCode.UP);
	}
	
	private void scrollDown(){
		commandBox.pressKeyCombi(KeyCode.CONTROL, KeyCode.DOWN);
	}
	
	private void assertCurrentlySelectedIndex(int expected){
		assertEquals(expected, currentSelectedItem());
	}
	
	private void selectIndexInListPanel(int index){
		mainGui.getPersonListPanel().getListView().getSelectionModel().select(index);
	}
	
	private int getListViewSize(){
		return mainGui.getPersonListPanel().getNumberOfTasks();
	}
	
	private int currentSelectedItem(){
		return taskListPanel.getListView().getSelectionModel().getSelectedIndex();
	}
}
