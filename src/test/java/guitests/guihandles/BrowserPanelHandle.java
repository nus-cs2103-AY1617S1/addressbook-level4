package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import jfxtras.scene.control.agenda.Agenda.AppointmentImplLocal;
import seedu.address.TestApp;
import seedu.address.testutil.TestUtil;
import seedu.address.ui.MyAgenda;

//@@author A0147967J
/**
 * Provides a handle for the panel containing the task list.
 */
public class BrowserPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;

    private static final String AGENDA_ID = "#agenda";

    public BrowserPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public MyAgenda getMyAgenda() {
        return (MyAgenda) getNode(AGENDA_ID);
    }
    
    public boolean isContained(AppointmentImplLocal target){
    	for(Appointment a: getMyAgenda().appointments())
    		if(TestUtil.isSameAppointment(a, target)) return true;
    	return false;
    }

}
