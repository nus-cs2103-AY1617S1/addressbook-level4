package seedu.whatnow.storage;

//@@author A0126240W-reused
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.whatnow.commons.core.LogsCenter;
import seedu.whatnow.commons.exceptions.IllegalValueException;
import seedu.whatnow.commons.util.CollectionUtil;
import seedu.whatnow.model.ReadOnlyWhatNow;
import seedu.whatnow.model.tag.Tag;
import seedu.whatnow.model.tag.UniqueTagList;
import seedu.whatnow.model.tag.UniqueTagList.DuplicateTagException;
import seedu.whatnow.model.task.ReadOnlyTask;
import seedu.whatnow.model.task.UniqueTaskList;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * An Immutable WhatNow that is serializable to XML format
 */
@XmlRootElement(name = "whatnow")
public class XmlSerializableWhatNow implements ReadOnlyWhatNow {

    private static final Logger logger = LogsCenter.getLogger(XmlSerializableWhatNow.class);
    
    @XmlElement
    private List<XmlAdaptedTask> tasks = new ArrayList<>();
    @XmlElement
    private List<Tag> tags = new ArrayList<>();

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableWhatNow() {
    }

    /**
     * Conversion
     */
    public XmlSerializableWhatNow(ReadOnlyWhatNow src) {
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        tags = src.getTagList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public UniqueTagList getUniqueTagList() {
        try {
            return new UniqueTagList(tags);
        } catch (DuplicateTagException e) {
            logger.info("Duplicated tags will be removed from the data file.");
            return new UniqueTagList((Set<Tag>)CollectionUtil.getUniqueElements(tags));
        }
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        UniqueTaskList lists = new UniqueTaskList();
        for (XmlAdaptedTask p : tasks) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {
                // TODO: better error handling
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
                logger.warning("At XmlSerializableWhatNow, getTaskList: \n" + e.getMessage());
                return null;
            } catch (ParseException e) {
                logger.warning("At XmlSerializableWhatNow, getTaskList: \n" + e.getMessage());
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags);
    }

}
