package seedu.savvytasker.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.savvytasker.commons.core.LogsCenter;
import seedu.savvytasker.commons.exceptions.IllegalValueException;
import seedu.savvytasker.model.ReadOnlySavvyTasker;
import seedu.savvytasker.model.alias.AliasSymbol;
import seedu.savvytasker.model.alias.AliasSymbolList;
import seedu.savvytasker.model.task.ReadOnlyTask;
import seedu.savvytasker.model.task.TaskList;

/**
 * An Immutable SavvyTasker that is serializable to XML format
 */
@XmlRootElement(name = "savvytasker")
public class XmlSerializableSavvyTasker implements ReadOnlySavvyTasker {
    private static final Logger logger = LogsCenter.getLogger(XmlSerializableSavvyTasker.class);

    @XmlElement
    private List<XmlAdaptedTask> tasks;
    @XmlElement
    private List<XmlAdaptedAliasSymbol> symbols;

    {
        tasks = new ArrayList<>();
        symbols = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableSavvyTasker() {}

    /**
     * Conversion
     */
    public XmlSerializableSavvyTasker(ReadOnlySavvyTasker src) {
        tasks.addAll(src.getReadOnlyListOfTasks().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        symbols.addAll(src.getReadOnlyListOfAliasSymbols().stream().map(XmlAdaptedAliasSymbol::new).collect(Collectors.toList()));
    }

    @Override
    public TaskList getTaskList() {
        TaskList lists = new TaskList();
        for (XmlAdaptedTask t : tasks) {
            try {
                lists.add(t.toModelType());
            } catch (IllegalValueException e) {
                logger.warning("Failed to convert XmlAdaptedTask to Task or add it to TaskList: " +
                    e.getMessage());
            }
        }
        return lists;
    }

    @Override
    public List<ReadOnlyTask> getReadOnlyListOfTasks() {
        return tasks.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                logger.warning("Failed to convert XmlAdaptedTask to Task: " + e.getMessage());
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public AliasSymbolList getAliasSymbolList() {
        AliasSymbolList lists = new AliasSymbolList();
        for (XmlAdaptedAliasSymbol s : symbols) {
            try {
                lists.addAliasSymbol(s.toModelType());
            } catch (IllegalValueException e) {
                logger.warning("Failed to convert XmlAliasSymbol to AliasSymbol "
                        + "or add it to AliasSymbolList: " + e.getMessage() );
            }
        }
        return lists;
        
    }

    @Override
    public List<AliasSymbol> getReadOnlyListOfAliasSymbols() {
        return symbols.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                logger.warning("Failed to convert XmlAliasSymbol to AliasSymbol: " + e.getMessage());
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }
}
