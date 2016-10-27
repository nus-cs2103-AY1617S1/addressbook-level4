/**
 * 
 */
package tars.model.task;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Arrays;

import tars.commons.exceptions.IllegalValueException;
import tars.model.tag.UniqueTagList;
import tars.model.task.DateTime.IllegalDateException;

/**
 * @@author A0124333U
 *
 */
public class TaskQuery extends Task {

    private String tagQuery = "";
    private String statusString = "";
    public final static String MESSAGE_BOTH_STATUS_SEARCHED_ERROR = "Both '-do (Done)' and '-ud (Undone)' flags "
            + "have been detected.\n"
            + "Please search for either '-do (Done)' or '-ud (Undone)' status";

    public TaskQuery() {
    }

    public TaskQuery(Name name, DateTime dateTime, Priority priority, Status status, UniqueTagList tags) {
        super(name, dateTime, priority, status, tags);
    }

    /* --------------- SETTER METHODS -------------------- */

    public void createNameQuery(String nameQueryString) throws IllegalValueException {
        name = new Name(nameQueryString);
    }

    public void createDateTimeQuery(String[] dateTimeQueryString) throws DateTimeException, IllegalDateException {
        dateTime = new DateTime(dateTimeQueryString[0], dateTimeQueryString[1]);
    }

    public void createPriorityQuery(String priorityString) throws IllegalValueException {

        /*
         * To convert long versions of priority strings (i.e. high, medium, low)
         * into characters (i.e. h, m, l)
         */
        switch (priorityString) {
        case PRIORITY_HIGH:
            priorityString = PRIORITY_H;
            break;

        case PRIORITY_MEDIUM:
            priorityString = PRIORITY_M;
            break;

        case PRIORITY_LOW:
            priorityString = PRIORITY_L;
            break;
        }

        priority = new Priority(priorityString);
    }

    public void createStatusQuery(Boolean statusQuery) {
        status = new Status();
        status.status = statusQuery;
    }

    public void createTagsQuery(String tagQueryString) {
        tagQuery = tagQueryString;
    }

    /* --------------- GETTER METHODS -------------------- */

    public ArrayList<String> getNameKeywordsAsList() {
        return new ArrayList<String>(Arrays.asList(getName().taskName.split(" ")));
    }

    public DateTime getDateTimeQueryRange() {
        if (getDateTime().getEndDate() != null) {
            return getDateTime();
        } else {
            return null;
        }
    }

    public ArrayList<String> getPriorityKeywordsAsList() {
        return new ArrayList<String>(Arrays.asList(priorityString()));
    }

    public String getStatusQuery() {
        if (status != null) {
            statusString = status.toString();
        }
        return statusString;
    }

    public ArrayList<String> getTagKeywordsAsList() {
        return new ArrayList<String>(Arrays.asList(tagQuery.split(" ")));
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Filter Search Keywords: ");

        if (!getName().toString().equals("")) {
            builder.append("[Task Name: ").append(getName()).append("] ");
        }
        if (getDateTime().getEndDate() != null) {
            builder.append("[DateTime: ").append(getDateTime()).append("] ");
        }
        if (!priorityString().equals("")) {
            builder.append("[Priority: ").append(priorityString()).append("] ");
        }
        if (!statusString.equals("")) {
            builder.append("[Status: ").append(statusString).append("] ");
        }
        if (!tagQuery.equals("")) {
            builder.append("[Tags: ").append(tagQuery).append("]");
        }

        return builder.toString();
    }

}
