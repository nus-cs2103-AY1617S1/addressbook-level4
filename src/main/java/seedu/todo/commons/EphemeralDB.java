package seedu.todo.commons;

import java.util.List;

import seedu.todo.models.CalendarItem;

/**
 * A bit like Redis, essentially a data store for things that should not be
 * persisted to disk, but should be shared between all modules.
 * 
 * All variables should be public. In-place modifications of variables are
 * encouraged.
 * 
 * @author louietyj
 */
public class EphemeralDB {

    private static EphemeralDB instance = null;

    // Stores
    public List<CalendarItem> displayedCalendarItems;

    
    protected EphemeralDB() {
        // Prevent instantiation.
    }

    public static EphemeralDB getInstance() {
        if (instance == null) {
            instance = new EphemeralDB();
        }
        return instance;
    }
    
    
    /** ======== DISPLAYED CALENDAR ITEMS ======== **/

    /**
     * Returns a CalendarItem from all displayedCalendarItems according to their displayed ID.
     * Note that displayedCalendarItems stores the indexes of the last displayed list of CalendarItems.
     * Their displayed ID is simply their index in the ArrayList + 1 (due to 0-indexing of ArrayLists).
     * 
     * @param id   Display ID of task. Bounded between 1 and the size of the ArrayList.
     * @return     Returns the Task at the specified display index.
     */
    public CalendarItem getCalendarItemsByDisplayedId(int id) {
        if (id <= 0 || id > displayedCalendarItems.size()) {
            return null;
        } else {
            return displayedCalendarItems.get(id - 1);
        }
    }

    public int addToDisplayedCalendarItems(CalendarItem item) {
        displayedCalendarItems.add(item);
        return displayedCalendarItems.size();
    }

}
