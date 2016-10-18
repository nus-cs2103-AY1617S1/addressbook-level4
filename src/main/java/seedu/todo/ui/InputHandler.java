package seedu.todo.ui;

import seedu.todo.controllers.*;

public class InputHandler {

    Controller handlingController = null;

    public boolean processInput(String input) {
        if (this.handlingController != null) {
            handlingController.process(input);
        } else {
            Controller[] controllers = instantiateAllControllers();

            // Define the controller which returns the maximum confidence.
            Controller maxController = null;

            // Get controller which has the maximum confidence.
            float maxConfidence = Integer.MIN_VALUE;

            for (int i = 0; i < controllers.length; i++) {
                float confidence = controllers[i].inputConfidence(input);

                // Don't consider controllers with non-positive confidence.
                if (confidence <= 0) {
                    continue;
                }

                if (confidence > maxConfidence) {
                    maxConfidence = confidence;
                    maxController = controllers[i];
                }
            }

            // No controller exists with confidence > 0.
            if (maxController == null) {
                return false;
            }

            // Process using best-matched controller.
            maxController.process(input);

        }

        return true;
    }
    
    private Controller[] instantiateAllControllers() {
        return new Controller[] { new HelpController(),
                                  new AddController(),
                                  new ListController(),
                                  new DestroyController(),
                                  new CompleteTaskController(),
                                  new UncompleteTaskController(),
                                  new UpdateController(),
                                  new UndoController(),
                                  new RedoController(),
                                  new ConfigController(),
                                  new ClearController(),
                                  new ExitController() };
    }

}
