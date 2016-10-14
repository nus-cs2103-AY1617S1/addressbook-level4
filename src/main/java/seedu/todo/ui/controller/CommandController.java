package seedu.todo.ui.controller;

import seedu.todo.logic.Logic;
import seedu.todo.ui.view.CommandErrorView;
import seedu.todo.ui.view.CommandFeedbackView;
import seedu.todo.ui.view.CommandInputView;

/**
 * Processes the input command from {@link CommandInputView}, pass it to {@link seedu.todo.logic.Logic}
 * and hands the {@link seedu.todo.logic.commands.CommandResult}
 * to {@link CommandFeedbackView} and {@link CommandErrorView}
 *
 * @author Wang Xien Dong
 */
public class CommandController {

    private Logic logic;
    private CommandInputView inputView;
    private CommandFeedbackView feedbackView;
    private CommandErrorView errorView;

    public static CommandController constructLink(Logic logic, CommandInputView inputView, CommandFeedbackView feedbackView,
                                                  CommandErrorView errorView) {
        CommandController controller = new CommandController();
        controller.logic = logic;
        controller.inputView = inputView;
        controller.feedbackView = feedbackView;
        controller.errorView = errorView;
        return controller;
    }



}
