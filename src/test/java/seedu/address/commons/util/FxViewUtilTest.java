package seedu.address.commons.util;

import static org.junit.Assert.*;

import org.junit.Test;

import javafx.scene.layout.AnchorPane;
import seedu.address.testutil.TestUtil;

//@@author A0139708W
/**
 * Tests FxViewUtil's applying of boundaries
 */
public class FxViewUtilTest {
    private double EPSILON = 0.000001;
    
    
    @Test
    public void applyAnchorBoundaryParameters_validParams() {
        AnchorPane anchor = TestUtil.generateAnchorPane();
        FxViewUtil.applyAnchorBoundaryParameters(anchor, 0.0,0.0,0.0,0.0);
        assertEquals(AnchorPane.getTopAnchor(anchor),0.0,EPSILON);
        assertEquals(AnchorPane.getBottomAnchor(anchor),0.0,EPSILON);
        assertEquals(AnchorPane.getLeftAnchor(anchor),0.0,EPSILON);
        assertEquals(AnchorPane.getRightAnchor(anchor),0.0,EPSILON);
    }
    
    @Test
    public void applyAnchorBoundaryParameters_noParams() {
        AnchorPane anchor = TestUtil.generateAnchorPane();
        assertEquals(AnchorPane.getTopAnchor(anchor),null);
        assertEquals(AnchorPane.getBottomAnchor(anchor),null);
        assertEquals(AnchorPane.getLeftAnchor(anchor),null);
        assertEquals(AnchorPane.getRightAnchor(anchor),null);
    }
    

    
    

}
