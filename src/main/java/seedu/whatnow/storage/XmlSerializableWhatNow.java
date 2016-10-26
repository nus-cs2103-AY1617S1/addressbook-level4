package seedu.whatnow.storage;
//@@author A0126240W-reused
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.whatnow.commons.exceptions.IllegalValueException;
import seedu.whatnow.model.ReadOnlyWhatNow;
import seedu.whatnow.model.tag.Tag;
import seedu.whatnow.model.tag.UniqueTagList;
import seedu.whatnow.model.task.ReadOnlyTask;
import seedu.whatnow.model.task.UniqueTaskList;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable WhatNow that is serializable to XML format
 */
@XmlRootElement(name = "whatnow")
public class XmlSerializableWhatNow implements ReadOnlyWhatNow {

    @XmlElement
    private List<XmlAdaptedTask> tasks;
    @XmlElement
    private List<Tag> tags;

    {
        tasks = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableWhatNow() {}

    /**
     * Conversion
     */
    public XmlSerializableWhatNow(ReadOnlyWhatNow src) {
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        tags = src.getTagList();
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        try {
            return new UniqueTagList(tags);
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
            } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return lists;
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
            } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags);
    }

}
