package seedu.todo.controllers;

public class UpdateController implements Controller {

    @Override
    public float inputConfidence(String input) {
        // TODO
        return (input.startsWith("update")) ? 1 : 0;
    }

    @Override
    public void process(String input) {
        // TODO Auto-generated method stub
        
    }
}
