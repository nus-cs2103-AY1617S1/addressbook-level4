package seedu.todo.ui.controller;

import seedu.todo.commons.util.StringUtil;
import seedu.todo.logic.Logic;
import seedu.todo.logic.commands.CommandResult;
import seedu.todo.model.ErrorBag;
import seedu.todo.ui.view.CommandErrorView;
import seedu.todo.ui.view.CommandFeedbackView;
import seedu.todo.ui.view.CommandInputView;

//@@author A0135805H
/**
 * Processes the input command from {@link CommandInputView}, pass it to {@link seedu.todo.logic.Logic}
 * and hands the {@link seedu.todo.logic.commands.CommandResult}
 * to {@link CommandFeedbackView} and {@link CommandErrorView}
 */
public class CommandController {

    private Logic logic;
    private CommandInputView inputView;
    private CommandFeedbackView feedbackView;
    private CommandErrorView errorView;

    /**
     * Defines a default constructor
     */
    private CommandController() {}

    /**
     * Constructs a link between the classes defined in the parameters.
     */
    public static CommandController constructLink(Logic logic, CommandInputView inputView,
                                                  CommandFeedbackView feedbackView, CommandErrorView errorView) {
        CommandController controller = new CommandController();
        controller.logic = logic;
        controller.inputView = inputView;
        controller.feedbackView = feedbackView;
        controller.errorView = errorView;
        controller.start();
        return controller;
    }

    /**
     * Asks {@link #inputView} to start listening for a new command.
     * Once the callback returns a command, {@link #submitCommand(String)} will process this command.
     */
    private void start() {
        inputView.listenToCommandExecution(this::submitCommand);
    }

    /**
     * Submits a command to logic, and once logic is completed with a {@link CommandResult}, it will be processed
     * by {@link #handleCommandResult(CommandResult)}
     * @param commandText command submitted by user
     */
    private void submitCommand(String commandText) {
        //Note: Do not execute an empty command. TODO: This check should be done in the parser class.
        if (!StringUtil.isEmpty(commandText)) {
            CommandResult result = logic.execute(commandText);
            handleCommandResult(result);
        }
    }

    /**
     * Handles a CommandResult object, and updates the user interface to reflect the result.
     * @param result produced by {@link Logic}
     */
    private void handleCommandResult(CommandResult result) {
        displayMessage(result.getFeedback());
        if (result.isSuccessful()) {
            viewDisplaySuccess();
        } else {
            viewDisplayError(result.getErrors());
        }
    }

    /**
     * Displays error in the respective UI elements
     * @param errorBag group of errors to display
     */
    private void viewDisplayError(ErrorBag errorBag) {
        inputView.flagError();
        feedbackView.flagError();
        errorView.displayErrors(errorBag);
    }

    /**
     * Displays success behaviour in the respective UI elements
     */
    private void viewDisplaySuccess() {
        inputView.resetViewState();
        feedbackView.unFlagError();
        errorView.hideCommandErrorView();
    }

    /**
     * Displays a message with regards to the user's input
     * @param message to be displayed to user
     */
    private void displayMessage(String message) {
        feedbackView.displayMessage(message);
    }
}