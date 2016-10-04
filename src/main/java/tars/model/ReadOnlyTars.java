package tars.model;


import tars.model.task.ReadOnlyPerson;
import tars.model.task.UniquePersonList;
import tars.model.tag.Tag;
import tars.model.tag.UniqueTagList;

import java.util.List;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTars {

    UniqueTagList getUniqueTagList();

    UniquePersonList getUniquePersonList();

    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ReadOnlyPerson> getPersonList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
