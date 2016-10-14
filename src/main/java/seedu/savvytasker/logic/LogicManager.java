package seedu.savvytasker.logic;

import javafx.collections.ObservableList;
import seedu.savvytasker.commons.core.ComponentManager;
import seedu.savvytasker.commons.core.LogsCenter;
import seedu.savvytasker.logic.commands.Command;
import seedu.savvytasker.logic.commands.CommandResult;
import seedu.savvytasker.logic.parser.*;
import seedu.savvytasker.model.Model;
import seedu.savvytasker.model.person.ReadOnlyTask;
import seedu.savvytasker.storage.Storage;

import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final MasterParser parser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new MasterParser();
        
        registerAllDefaultCommandParsers();

    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parse(commandText);
        command.setData(model);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
    
    private void registerAllDefaultCommandParsers() {
        parser.registerCommandParser(new AddCommandParser());
        parser.registerCommandParser(new DeleteCommandParser());
        parser.registerCommandParser(new FindCommandParser());
        parser.registerCommandParser(new ListCommandParser());
        parser.registerCommandParser(new ModifyCommandParser());
        parser.registerCommandParser(new HelpCommandParser());
        parser.registerCommandParser(new ClearCommandParser());
        parser.registerCommandParser(new ExitCommandParser());
        parser.registerCommandParser(new MarkCommandParser());
        parser.registerCommandParser(new UnmarkCommandParser());
        parser.registerCommandParser(new UndoCommandParser());
        parser.registerCommandParser(new RedoCommandParser());
    }
}
