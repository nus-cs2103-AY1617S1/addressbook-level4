package harmony.mastermind.ui;

import static org.junit.Assert.*;
import harmony.mastermind.ui.HelpPopup;
import org.junit.Test;

public class HelpPopupTest {
    
    @Test
    public void getContent_success() {
        HelpPopup popup = new HelpPopup();
        popup.setContent("testing");
        assertEquals(popup.getContent(), "testing");
    }
    
    @Test
    public void setContent_success() {
        HelpPopup popup = new HelpPopup();
        popup.setContent("test");
        assertEquals(popup.getContent(), "test");
    }
    
    /**
     * Stub class to help test getContent
     */
    class HelpPopupStub extends HelpPopup {
        public HelpPopupStub() {
            HelpPopup popup = new HelpPopup();
            popup.setContent("testing");
        } 
    }
}
