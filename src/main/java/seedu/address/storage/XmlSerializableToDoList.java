package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyToDoList;
import seedu.address.model.todo.ReadOnlyToDo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable ToDoList that is serializable to XML format
 */
@XmlRootElement(name = "todolist")
public class XmlSerializableToDoList implements ReadOnlyToDoList {

    @XmlElement
    private List<XmlAdaptedToDo> toDos;
    {
        toDos = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableToDoList() {}

    /**
     * Conversion
     */
    public XmlSerializableToDoList(ReadOnlyToDoList src) {
        toDos.addAll(src.getToDoList().stream().map(XmlAdaptedToDo::new).collect(Collectors.toList()));
    }

    @Override
    public List<ReadOnlyToDo> getToDoList() {
        return toDos.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }
}
