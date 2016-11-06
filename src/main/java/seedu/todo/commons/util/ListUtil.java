package seedu.todo.commons.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// @@author A0139812A
public class ListUtil {

    /**
     * Checks if two lists are equal, without regard for order.
     */
    public static <T> boolean unorderedListEquals(List<T> list1, List<T> list2) {
        final Set<T> set1 = new HashSet<>(list1);
        final Set<T> set2 = new HashSet<>(list2);

        return set1.equals(set2);
    }
    
}
