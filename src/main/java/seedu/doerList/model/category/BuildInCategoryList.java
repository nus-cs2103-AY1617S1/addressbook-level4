package seedu.doerList.model.category;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.doerList.commons.core.UnmodifiableObservableList;
import seedu.doerList.commons.util.CollectionUtil;

import java.util.*;

import org.joda.time.DateTime;

/**
 * A list of categories that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Category#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class BuildInCategoryList implements Iterable<Category> {
    
    public static final BuildInCategory ALL;
    public static final BuildInCategory TODAY;
    public static final BuildInCategory NEXT;
    public static final BuildInCategory INBOX;
    public static final BuildInCategory COMPLETE;
       
    static {
        try {
            ALL = new BuildInCategory("All", (task) -> {return true;});     
            INBOX = new BuildInCategory("Inbox", (task) -> {
                return task.isFloatingTask();
            });
            COMPLETE = new BuildInCategory("Complete", (task) -> {
                return task.getBuildInCategories().contains(BuildInCategoryList.COMPLETE);
            });
            TODAY = new BuildInCategory("Today", (task) -> {
                DateTime todayBegin = new DateTime().withTimeAtStartOfDay();
                DateTime todayEnd = todayBegin.plusDays(1);  
                if (!INBOX.getPredicate().test(task)) {
                    if (task.hasStartTime() && !task.hasEndTime()) {
                        return task.getStartTime().value.isBefore(todayEnd);
                    } else if (task.hasEndTime() && !task.hasStartTime()) {
                        return task.getEndTime().value.isAfter(todayBegin) &&
                                task.getEndTime().value.isBefore(todayEnd);
                    } else {
                        if (task.getEndTime().value.isAfter(todayBegin) 
                                && task.getStartTime().value.isBefore(todayEnd)) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            });
            NEXT = new BuildInCategory("Next", (task) -> {
                DateTime todayEnd = new DateTime().withTimeAtStartOfDay().plusDays(1);     
                return (!task.hasStartTime() || task.getStartTime().value.isAfter(todayEnd)) &&
                        task.hasEndTime() && task.getEndTime().value.isAfter(todayEnd);
            });
        } catch (Exception e) {
            e.printStackTrace();
            // impossible
            throw new RuntimeException("Could not init class.", e);
        }
    }
    
    public static void resetBuildInCategoryPredicate() {
        ALL.setToDeafultPredicate();
        TODAY.setToDeafultPredicate();
        NEXT.setToDeafultPredicate();
        INBOX.setToDeafultPredicate();
        COMPLETE.setToDeafultPredicate();
    }
    
    private final ObservableList<Category> internalList = FXCollections.observableArrayList();

    public void addAllBuildInCategories() {
        internalList.addAll(ALL, TODAY, NEXT, INBOX, COMPLETE);
    }
       
    /**
     * Constructs empty CategoryList.
     */
    public BuildInCategoryList() {}
    
    public BuildInCategoryList(Collection<Category> stroedList) {
        BuildInCategory[] buildInCategories = {ALL, TODAY, NEXT, INBOX, COMPLETE};
        for(Category c : stroedList) {
            for(BuildInCategory bc : buildInCategories) {
                if (c.categoryName.equals(bc.categoryName)) {
                    internalList.add(bc);
                }
            }
        }
    }
    
    /**
     * Add a BuildInCategory form list
     * Restrict that can only BuildInCategory can be added
     * 
     * @param category
     */
    public void add(BuildInCategory category) {
        if (!contains(category)) {
            internalList.add(category);
        }
    }
    
    /**
     * Remove a BuildInCategory form list
     * 
     * @param category
     */
    public void remove(BuildInCategory category) {
        if (!contains(category)) {
            internalList.remove(category);
        }
    }

    /**
     * Returns true if the list contains an equivalent Category as the given argument.
     */
    public boolean contains(BuildInCategory toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    @Override
    public Iterator<Category> iterator() {
        return internalList.iterator();
    }

    public UnmodifiableObservableList<Category> getInternalList() {
        return new UnmodifiableObservableList<Category>(internalList);
    }

    public void replaceWith(BuildInCategoryList buildInCategories) {
        this.internalList.clear();
        this.internalList.addAll(buildInCategories.internalList);
    }
}