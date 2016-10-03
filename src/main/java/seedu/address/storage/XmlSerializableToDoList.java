package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
//import seedu.address.model.tag.Tag;
//import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.ReadOnlyToDoList;
import seedu.address.model.person.ReadOnlyToDo;
import seedu.address.model.person.UniqueToDoList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable ToDoList that is serializable to XML format
 */
@XmlRootElement(name = "todolist")
public class XmlSerializableToDoList implements ReadOnlyToDoList {

    @XmlElement
    private List<XmlAdaptedToDo> toDos;
//    @XmlElement
//    private List<Tag> tags;

    {
        toDos = new ArrayList<>();
//        tags = new ArrayList<>();
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
//        tags = src.getTagList();
    }

//    @Override
//    public UniqueTagList getUniqueTagList() {
//        try {
//            return new UniqueTagList(tags);
//        } catch (UniqueTagList.DuplicateTagException e) {
//            //TODO: better error handling
//            e.printStackTrace();
//            return null;
//        }
//    }

    @Override
    public UniqueToDoList getUniqueToDoList() {
        UniqueToDoList lists = new UniqueToDoList();
        for (XmlAdaptedToDo p : toDos) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {
                //TODO: better error handling
            }
        }
        return lists;
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

//    @Override
//    public List<Tag> getTagList() {
//        return Collections.unmodifiableList(tags);
//    }

}
