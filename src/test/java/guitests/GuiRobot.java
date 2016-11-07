package guitests;

import org.testfx.api.FxRobot;

import javafx.scene.input.KeyCodeCombination;
import w15c2.tusk.testutil.TestUtil;

//@@authour A0138978E-reused
/**
 * Robot used to simulate user actions on the GUI.
 * Extends {@link FxRobot} by adding some customized functionality and workarounds.
 */
public class GuiRobot extends FxRobot {

    public GuiRobot push(KeyCodeCombination keyCodeCombination){
        return (GuiRobot) super.push(TestUtil.scrub(keyCodeCombination));
    }

}
