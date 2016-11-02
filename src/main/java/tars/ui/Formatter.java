package tars.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import tars.commons.util.StringUtil;
import tars.model.tag.ReadOnlyTag;
import tars.model.task.DateTime;
import tars.model.task.ReadOnlyTask;
import tars.model.task.rsv.RsvTask;

/**
 * Container for formatting
 * 
 * @@author A0139924W
 */
public class Formatter {
    /** Format of indexed list item */
    private static final String MESSAGE_INDEXED_LIST_ITEM = "%1$d.\t%2$s";

    public static final String EMPTY_LIST_MESSAGE = "0 %1$s listed.";

    /** Offset required to convert between 1-indexing and 0-indexing. */
    public static final int DISPLAYED_INDEX_OFFSET = 1;

    public String formatTags(List<? extends ReadOnlyTag> tags) {
        final List<String> formattedTags = new ArrayList<>();

        if (tags.size() == 0) {
            return String.format(EMPTY_LIST_MESSAGE, "tags");
        }

        for (ReadOnlyTag tag : tags) {
            formattedTags.add(tag.getAsText());
        }
        return asIndexedList(formattedTags);
    }

    public String formatTaskList(List<? extends ReadOnlyTask> taskList) {
        final List<String> formattedTasks = new ArrayList<>();

        if (taskList.size() == 0) {
            return String.format(EMPTY_LIST_MESSAGE, "tasks");
        }

        for (ReadOnlyTask task : taskList) {
            formattedTasks.add(task.getAsText());
        }
        return asIndexedList(formattedTasks);
    }

    public String formatRsvTaskList(List<? extends RsvTask> rsvTaskList) {
        final List<String> formattedTasks = new ArrayList<>();

        if (rsvTaskList.size() == 0) {
            return String.format(EMPTY_LIST_MESSAGE, "tasks");
        }

        for (RsvTask task : rsvTaskList) {
            formattedTasks.add(task.toString());
        }
        return asIndexedList(formattedTasks);
    }

    /** Formats a list of strings as an indexed list. */
    private static String asIndexedList(List<String> listItems) {
        final StringBuilder formatted = new StringBuilder();
        int displayIndex = DISPLAYED_INDEX_OFFSET;
        for (String listItem : listItems) {
            formatted.append(getIndexedListItem(displayIndex++, listItem))
                    .append(StringUtil.STRING_NEWLINE);
        }
        return formatted.toString();
    }

    /**
     * Formats a string as an indexed list item.
     *
     * @param visibleIndex index for this listing
     */
    private static String getIndexedListItem(int visibleIndex,
            String listItem) {
        return String.format(MESSAGE_INDEXED_LIST_ITEM, visibleIndex, listItem);
    }

    /**
     * Formats a given RsvTask to display reserved dateTimes as a list
     * 
     * @@author A0121533W
     */
    public static String formatDateTimeList(RsvTask rsvTask) {
        String formatted = StringUtil.EMPTY_STRING;
        ArrayList<DateTime> dateTimeArrayList = rsvTask.getDateTimeList();
        int count = 1;
        for (DateTime dt : dateTimeArrayList) {
            formatted += StringUtil.STRING_SQUARE_BRACKET_OPEN + count
                    + StringUtil.STRING_SQUARE_BRACKET_CLOSE
                    + StringUtil.STRING_WHITESPACE + dt.toString()
                    + StringUtil.STRING_NEWLINE + StringUtil.STRING_NEWLINE;
            count++;
        }
        return formatted;
    }

    /**
     * Formats a given tasks list to display on This Week Panel
     * 
     * @@author A0121533W
     */
    public static String formatThisWeekPanelTasksList(
            ObservableList<ReadOnlyTask> tasksList) {
        String formatted = StringUtil.EMPTY_STRING;
        for (ReadOnlyTask t : tasksList) {
            String taskName = t.getName().toString();
            if (!formatted.contains(taskName)) {
                formatted += StringUtil.STRING_SQUARE_BRACKET_OPEN
                        + t.getName().toString()
                        + StringUtil.STRING_SQUARE_BRACKET_CLOSE
                        + StringUtil.STRING_WHITESPACE;
            }
        }
        return formatted.trim();
    }
}
