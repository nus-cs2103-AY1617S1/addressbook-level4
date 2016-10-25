package tars.ui;

import java.util.ArrayList;
import java.util.List;

import tars.model.tag.ReadOnlyTag;
import tars.model.task.DateTime;
import tars.model.task.rsv.RsvTask;

public class Formatter {
    /** Format of indexed list item */
    private static final String MESSAGE_INDEXED_LIST_ITEM = "%1$d. %2$s";

    /** A decorative prefix added to the beginning of lines printed by TARS */
    private static final String LINE_PREFIX = " ";

    /** A platform independent line separator. */
    private static final String LS = System.lineSeparator();

    /** Offset required to convert between 1-indexing and 0-indexing. */
    private static final int DISPLAYED_INDEX_OFFSET = 1;

    public String formatTags(List<? extends ReadOnlyTag> tags) {
        final List<String> formattedTags = new ArrayList<>();
        for (ReadOnlyTag tag : tags) {
            formattedTags.add(tag.getAsText());
        }
        return format(asIndexedList(formattedTags));
    }

    /** Formats the given strings for displaying to the user. */
    public String format(String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String m : messages) {
            sb.append(LINE_PREFIX + m.replace("\n", LS + LINE_PREFIX) + LS);
        }
        return sb.toString();
    }

    /** Formats a list of strings as an indexed list. */
    private static String asIndexedList(List<String> listItems) {
        final StringBuilder formatted = new StringBuilder();
        int displayIndex = 0 + DISPLAYED_INDEX_OFFSET;
        for (String listItem : listItems) {
            formatted.append(getIndexedListItem(displayIndex, listItem)).append("\n");
            displayIndex++;
        }
        return formatted.toString();
    }

    /**
     * Formats a string as an indexed list item.
     *
     * @param visibleIndex index for this listing
     */
    private static String getIndexedListItem(int visibleIndex, String listItem) {
        return String.format(MESSAGE_INDEXED_LIST_ITEM, visibleIndex, listItem);
    }

    public static String formatDateTimeList(RsvTask rsvTask) {
        String formatted = "";
        ArrayList<DateTime> dateTimeArrayList = rsvTask.getDateTimeList();
        int count = 1;
        for (DateTime dt : dateTimeArrayList) {
            String topSeparator = "-------- " + String.valueOf(count) + " --------" + "\n";
            String bottomSeparator = new String(new char[topSeparator.length()-2]).replace("\0", "-");
            formatted += topSeparator
                    + dt.toString() + "\n"
                    + bottomSeparator + "\n";
            count++;
        }
        return formatted;
    }
}
