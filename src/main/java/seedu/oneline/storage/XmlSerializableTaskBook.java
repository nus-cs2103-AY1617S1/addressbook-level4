package seedu.oneline.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.commons.exceptions.TagNotFoundException;
import seedu.oneline.model.ReadOnlyTaskBook;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.tag.TagColor;
import seedu.oneline.model.tag.TagColorMap;
import seedu.oneline.model.tag.UniqueTagList;
import seedu.oneline.model.task.ReadOnlyTask;
import seedu.oneline.model.task.UniqueTaskList;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "taskbook")
public class XmlSerializableTaskBook implements ReadOnlyTaskBook {

    @XmlElement
    private List<XmlAdaptedTask> tasks;
    @XmlElement
    private List<XmlAdaptedTagColor> tagAndColors;

    {
        tasks = new ArrayList<>();
        tagAndColors = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableTaskBook() {}

    /**
     * Conversion
     */
    public XmlSerializableTaskBook(ReadOnlyTaskBook src) {
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        List<Tag> tagList = src.getTagList();
        TagColorMap tagColorMap = src.getTagColorMap();
        tagAndColors = new ArrayList<XmlAdaptedTagColor>(tagList.size());
        for (int i = 0; i < src.getTagList().size(); i++) {
            Tag tag = tagList.get(i);
            TagColor color = tagColorMap.getTagColor(tag);
            tagAndColors.add(new XmlAdaptedTagColor(tag, color));
        }
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        try {
            return new UniqueTagList(getTagList());
        } catch (UniqueTagList.DuplicateTagException e) {
            //TODO: better error handling
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        UniqueTaskList lists = new UniqueTaskList();
        for (XmlAdaptedTask p : tasks) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {
                //TODO: better error handling
            }
        }
        return lists;
    }

    @Override
    public TagColorMap getTagColorMap() {
        TagColorMap colorMap = new TagColorMap();
        try {
            for (int i = 0; i < tagAndColors.size(); i++) {
                XmlAdaptedTagColor tagAndColor = tagAndColors.get(i);
                colorMap.setTagColor(tagAndColor.toTag(), tagAndColor.toTagColor());
            }
        } catch (IllegalValueException e) {
            e.printStackTrace();
            //TODO: better error handling
            return null;
        }
        return colorMap;
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return tasks.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<Tag> getTagList() {
        List<Tag> tagList = new ArrayList<Tag>(tagAndColors.size());
        try {
            for (int i = 0; i < tagAndColors.size(); i++) {
                tagList.add(tagAndColors.get(i).toModelType().getKey());
            }
        } catch (IllegalValueException e) {
            e.printStackTrace();
            //TODO: better error handling
            return null;
        }
        return tagList;
    }

    @Override
    public Map<Tag, TagColor> getInternalTagColorMap() {
        return getTagColorMap().getInternalMap();
    }

}
