package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.events.model.ActivityManagerChangedEvent;
import seedu.address.commons.core.ComponentManager;
import seedu.address.model.activity.Activity;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.UniquePersonList.PersonNotFoundException;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ActivityManager activityManager;
    private final FilteredList<Activity> filteredActivities;

    /**
     * Initializes a ModelManager with the given ActivityManager
     * ActivityManager and its variables should not be null
     */
    public ModelManager(ActivityManager src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        activityManager = new ActivityManager(src);
        filteredActivities = new FilteredList<>(activityManager.getPersons());
    }

    public ModelManager() {
        this(new ActivityManager(), new UserPrefs());
    }

    public ModelManager(ReadOnlyActivityManager initialData, UserPrefs userPrefs) {
        activityManager = new ActivityManager(initialData);
        filteredActivities = new FilteredList<>(activityManager.getPersons());
    }

    @Override
    public void resetData(ReadOnlyActivityManager newData) {
        activityManager.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyActivityManager getAddressBook() {
        return activityManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new ActivityManagerChangedEvent(activityManager));
    }

    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
//        activityManager.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(Activity activity) {
        activityManager.addActivity(activity);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }

    //=========== Filtered Person List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<Activity> getFilteredPersonList() {
        return new UnmodifiableObservableList<>(filteredActivities);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredActivities.setPredicate(null);
    }

    @Override
    public void updateFilteredPersonList(Set<String> keywords){
        updateFilteredPersonList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredPersonList(Expression expression) {
//        filteredActivities.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyPerson person);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyPerson person) {
            return qualifier.run(person);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyPerson person);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyPerson person) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(person.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
