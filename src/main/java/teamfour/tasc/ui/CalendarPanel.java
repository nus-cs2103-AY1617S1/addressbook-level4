package teamfour.tasc.ui;

import java.util.logging.Logger;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jfxtras.scene.control.agenda.Agenda;
import teamfour.tasc.commons.core.LogsCenter;
import teamfour.tasc.commons.util.FxViewUtil;
import teamfour.tasc.logic.Logic;

/**
 * Panel containing a visual overview of the calendar.
 */
public class CalendarPanel extends UiPart {

    private static Logger logger = LogsCenter.getLogger(CalendarPanel.class);
   
    private Agenda agendaView;

    /**
     * Constructor is kept private as {@link #load(AnchorPane)} is the only way to create a CalendarPanel.
     */
    private CalendarPanel() {
        
    }
    
    public static CalendarPanel load(AnchorPane placeholder) {
        logger.info("Initializing calendar view");
        CalendarPanel calendarPanel = new CalendarPanel();
        calendarPanel.agendaView = new Agenda();
        FxViewUtil.applyAnchorBoundaryParameters(calendarPanel.agendaView, 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().add(calendarPanel.agendaView);
        return calendarPanel;
    }

    @Override
    public void setNode(Node node) {
        // not applicable
    }

    @Override
    public String getFxmlPath() {
        // not applicable (not using fxml for this panel)
        return null;
    }

    /**
     * Free resources used by the calendar.
     */
    public void freeResources() {
        // TODO Auto-generated method stub
        agendaView = null;
    }

}
