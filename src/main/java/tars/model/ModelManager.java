package tars.model;

import javafx.collections.transformation.FilteredList;
import tars.commons.core.ComponentManager;
import tars.commons.core.LogsCenter;
import tars.commons.core.UnmodifiableObservableList;
import tars.commons.events.model.TarsChangedEvent;
import tars.commons.util.StringUtil;
import tars.model.task.Person;
import tars.model.task.ReadOnlyPerson;
import tars.model.task.UniquePersonList;
import tars.model.task.UniquePersonList.PersonNotFoundException;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of tars data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Tars tars;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given Tars
     * Tars and its variables should not be null
     */
    public ModelManager(Tars src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        tars = new Tars(src);
        filteredPersons = new FilteredList<>(tars.getPersons());
    }

    public ModelManager() {
        this(new Tars(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTars initialData, UserPrefs userPrefs) {
        tars = new Tars(initialData);
        filteredPersons = new FilteredList<>(tars.getPersons());
    }

    @Override
    public void resetData(ReadOnlyTars newData) {
        tars.resetData(newData);
        indicateTarsChanged();
    }

    @Override
    public ReadOnlyTars getTars() {
        return tars;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTarsChanged() {
        raise(new TarsChangedEvent(tars));
    }

    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        tars.removePerson(target);
        indicateTarsChanged();
    }

    @Override
    public synchronized void addPerson(Person task) throws UniquePersonList.DuplicatePersonException {
        tars.addPerson(task);
        updateFilteredListToShowAll();
        indicateTarsChanged();
    }

    //=========== Filtered Person List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return new UnmodifiableObservableList<>(filteredPersons);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredPersons.setPredicate(null);
    }

    @Override
    public void updateFilteredPersonList(Set<String> keywords){
        updateFilteredPersonList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredPersonList(Expression expression) {
        filteredPersons.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyPerson task);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyPerson task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyPerson task);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyPerson task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
