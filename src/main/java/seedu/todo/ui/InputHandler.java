package seedu.todo.ui;

import seedu.todo.controllers.*;

public class InputHandler {
    
    Controller handlingController = null;
    
    public void processInput(String input) {
        if (this.handlingController != null)
            handlingController.process(input);
        else {
            Controller[] controllers = instantiateAllControllers();
            float[] confidences = new float[controllers.length];
            
            // Java sucks. No map/max...
            float maxConfidence = controllers[0].inputConfidence(input);
            Controller maxController = controllers[0];
            for (int i = 0; i < controllers.length; i++) {
                float confidence = controllers[i].inputConfidence(input);
                if (confidence > maxConfidence) {
                    maxConfidence = confidence;
                    maxController = controllers[i];
                }
            }
            
            // Process using best-matched controller.
            maxController.process(input);
        }
    }
    
    private Controller[] instantiateAllControllers() {
        return new Controller[] { new AddController(),
                                  new ListController(),
                                  new DestroyController(),
                                  new UpdateController() };
    }

}
