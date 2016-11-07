# A0143756Yreused
###### \java\seedu\address\commons\events\model\AliasManagerChangedEvent.java
``` java
/** Indicates that the AliasManager in the model has changed*/
public class AliasManagerChangedEvent extends BaseEvent {

    public final ReadOnlyAliasManager data;

    public AliasManagerChangedEvent(ReadOnlyAliasManager data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "Number of tasks: " + data.getAliasList().size();
    }
}
```
###### \java\seedu\address\logic\parser\Parser.java
``` java
	/**
     * Parses arguments in the context of the set alias task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAddAlias(String arguments) {
        final Matcher matcher = ADD_ALIAS_COMMAND_FORMAT.matcher(arguments.trim());
    	
    	if (!matcher.matches()){	
        	return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAliasCommand.MESSAGE_USAGE));
        }
    	
    	final String alias = matcher.group("alias").trim();
    	final String originalPhrase = matcher.group("originalPhrase").trim();
        
        return new AddAliasCommand(alias, originalPhrase);
    }
    
	/**
	 * @param a valid argument is one or more integers separated by spaces, corresponding to aliases
	 * displayed on the screen.
	 * @return a DeleteAliasCommand if the argument string is valid, IncorrectCommand otherwise.
	 */
	private Command prepareDeleteAlias(String arguments) {
		int[] indices;
		try {
			indices = parseIndices(arguments);
		} catch (IllegalArgumentException e) {
			return new IncorrectCommand(e.getMessage());
		}
		return new DeleteAliasCommand(indices);
	}
```
###### \java\seedu\address\model\alias\Alias.java
``` java
public class Alias implements ReadOnlyAlias, Comparable<ReadOnlyAlias> {
	
	private String alias;
	private String originalPhrase;
	
	/**
	 * Constructor for Alias class given alias and originalPhrase Strings.
	 */
	
	public Alias(String alias, String originalPhrase) {
		assert alias != null;
		assert !alias.isEmpty();
		assert originalPhrase != null;
		assert !originalPhrase.isEmpty();
		
		this.alias = alias;
		this.originalPhrase = originalPhrase;
	}
	
	
	public Alias(ReadOnlyAlias readOnlyAlias){
		this(readOnlyAlias.getAlias(), readOnlyAlias.getOriginalPhrase());
	}
	
	/**
	 * Constructor for Alias class given readOnlyAlias.
	 */

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
    
    public String getOriginalPhrase() {
        return originalPhrase;
    }
    
    public void setOriginalPhrase(String originalPhrase) {
        this.originalPhrase = originalPhrase;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // Short circuit if same object
                || (other instanceof ReadOnlyAlias // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyAlias) other));
    }

    @Override
    public int hashCode() {
        // Use this method for custom fields hashing instead of implementing your own
        return Objects.hash(alias, originalPhrase);
    }

    @Override
    public String toString() {
        return getAsText();
    }

	@Override
	public int compareTo(ReadOnlyAlias other) {
		int statusCompare = this.getOriginalPhrase().compareTo(other.getOriginalPhrase());
		if (statusCompare != 0) {
			return statusCompare;
		}
		else {
			return this.getAlias().compareTo(other.getAlias());	
		}
	}
}
```
###### \java\seedu\address\model\alias\ReadOnlyAlias.java
``` java
/**
 * A read-only immutable interface for an Alias in the alias book.
 * Implementations should guarantee that 
 *
 */
public interface ReadOnlyAlias extends Comparable<ReadOnlyAlias> {

	public String getAlias();
	public String getOriginalPhrase();
	
    /**
     * Returns true if both have the same state. (Note that interfaces cannot override equals() method in Object class)
     */
    default boolean isSameStateAs(ReadOnlyAlias other) {
        return other == this // Short circuit if same object
                || (other != null // To avoid NullPointerException below
                && other.getAlias().equals(this.getAlias()) // State checks here onwards
                && other.getOriginalPhrase().equals(this.getOriginalPhrase()));
    }
    
    /**
     * Formats the alias as text, printing alias and originalPhrase.
     */
    default String getAsText() {
    	final StringBuilder builder = new StringBuilder();
    	
    	builder.append("Alias: " + getAlias() + ", ");
    	builder.append("Original phrase: " + getOriginalPhrase() + ".");

        return builder.toString();
    }
    
    public int compareTo(ReadOnlyAlias other);
}
```
###### \java\seedu\address\model\alias\UniqueAliasList.java
``` java
/**
 * A list of aliases that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Alias#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueAliasList implements Iterable<Alias> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateAliasException extends DuplicateDataException {
        protected DuplicateAliasException() {
            super("Operation would result in duplicate aliases.");
        }
    }

    /**
     * Signals that an operation targeting a specified alias in the list would fail because
     * there is no such matching task in the list.
     */
    public static class AliasNotFoundException extends Exception {}

    private final ObservableList<Alias> internalList = FXCollections.observableArrayList();


    /**
     * Constructs empty AliasList.
     */
    public UniqueAliasList() {}

    /**
     * Returns true if the list contains an equivalent alias as the given argument.
     */
    public boolean contains(ReadOnlyAlias toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateAliasException if the alias to add is a duplicate of an existing task in the list.
     */
    public void add(Alias toAdd) throws DuplicateAliasException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateAliasException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyAlias toRemove) throws AliasNotFoundException {
        assert toRemove != null;
        final boolean aliasFoundAndDeleted = internalList.remove(toRemove);
        if (!aliasFoundAndDeleted) {
            throw new AliasNotFoundException();
        }
        return aliasFoundAndDeleted;
    }
    
    /**
     * set the equivalent task to the specified index of the list
     * @throws TaskNotFoundException if no such task could be found in the list.
     */ 			
    public boolean set(int key, Alias toSet) throws AliasNotFoundException {
        assert toSet != null;
        boolean isFound = false;
        if (internalList.size() < key) {
            throw new AliasNotFoundException();
        } else {
        	internalList.set(key, toSet);
        	isFound = true;
        }
        return isFound;
    }

    public ObservableList<Alias> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<Alias> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueAliasList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueAliasList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
```
###### \java\seedu\address\model\AliasManager.java
``` java
/**
 * Wraps all data at the alias manager level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AliasManager implements ReadOnlyAliasManager{

    private final UniqueAliasList aliases;
    
    {
    	aliases = new UniqueAliasList();
    }
   
    public AliasManager() {}

    /**
     * Aliases are copied into this alias manager.
     */
    public AliasManager(ReadOnlyAliasManager toBeCopied) {
        this(toBeCopied.getUniqueAliasList());
    }

    /**
     * Tasks and Tags are copied into this task manager
     */
    public AliasManager(UniqueAliasList aliases) {
        resetData(aliases.getInternalList());
    }

    public static ReadOnlyAliasManager getEmptyAliasManager() {
        return new AliasManager();
    }
    
    //========== Alias List Overwrite Operations ======================================

    public ObservableList<Alias> getFilteredAliases() {
        return aliases.getInternalList();
    }

    public void setAliases(List<Alias> aliases) {
        this.aliases.getInternalList().setAll(aliases);
    }

    public void resetData(Collection<? extends ReadOnlyAlias> newAliases) {
        setAliases(newAliases.stream().map(Alias::new).collect(Collectors.toList()));
    }

    public void resetData(ReadOnlyAliasManager newData) {
        resetData(newData.getAliasList());
    }

    //========== Alias-Level Operations ===============================================
    
    /**
     * Adds an alias to the alias manager.
     *
     * @throws UniqueAliasList.DuplicateTaskException if an equivalent alias already exists.
     */
    public void addAlias(Alias aliasToAdd) throws UniqueAliasList.DuplicateAliasException {
    	aliases.add(aliasToAdd);
    	sortAliases();
    }
    
    public boolean removeAlias(ReadOnlyAlias aliasToRemove) throws UniqueAliasList.AliasNotFoundException {
    	if (aliases.remove(aliasToRemove)) {
    		return true;
    	} 
    	
    	else {
    		throw new UniqueAliasList.AliasNotFoundException();
    	}		
    }
    
    private void sortAliases() {
    	Collections.sort(aliases.getInternalList());
    }

    //========== Util Methods =========================================================
    
    @Override
    public String toString() {
    	StringBuilder stringBuilder = new StringBuilder();
    	stringBuilder.append(aliases.getInternalList().size() + " aliases.\n");
    	
    	ObservableList<Alias> internalList = aliases.getInternalList();
    	for(Alias currentAlias: internalList){
    		stringBuilder.append(currentAlias.toString());
    	}
    	
    	return stringBuilder.toString();
    }
    

    @Override
    public List<ReadOnlyAlias> getAliasList() {
        return Collections.unmodifiableList(aliases.getInternalList());
    }
    
    @Override
    public UniqueAliasList getUniqueAliasList() {
        return this.aliases;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // Short circuit if same object
                || (other instanceof AliasManager // instanceof handles nulls
                && this.aliases.equals(((AliasManager) other).aliases));
    }

    @Override
    public int hashCode() {
        // Use this method for custom fields hashing instead of implementing your own
        return Objects.hash(aliases);
    }
}
```
###### \java\seedu\address\model\Model.java
``` java
    /** Saves alias to XML file, "aliasbook.xml" in ./data folder. */
    void addAlias(Alias aliasToAdd) throws UniqueAliasList.DuplicateAliasException;
    
    /** Deletes the given aliases. */
    void deleteAliases(ArrayList<ReadOnlyAlias> targets) throws UniqueAliasList.AliasNotFoundException;
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /** Raise an event to indicate that the aliasManager in model has changed */
    private void indicateAliasManagerChanged() {
    	raise(new AliasManagerChangedEvent(aliasManager));
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void addAlias(Alias aliasToAdd) throws UniqueAliasList.DuplicateAliasException {
    	assert aliasToAdd != null;
    	
    	aliasManager.addAlias(aliasToAdd);
    	indicateAliasManagerChanged();
    }
    
    @Override
    public synchronized void deleteAliases(ArrayList<ReadOnlyAlias> targets) throws AliasNotFoundException {
        for(ReadOnlyAlias target : targets) {
        	aliasManager.removeAlias(target);
        	indicateAliasManagerChanged();
        }
    }
    
```
###### \java\seedu\address\model\ReadOnlyAliasManager.java
``` java
import java.util.List;

import seedu.address.model.alias.ReadOnlyAlias;
import seedu.address.model.alias.UniqueAliasList;

/**
 * Unmodifiable view of an alias manager.
 */
public interface ReadOnlyAliasManager {

    UniqueAliasList getUniqueAliasList();

    /**
     * Returns an unmodifiable view of alias list
     */
    List<ReadOnlyAlias> getAliasList();
}
```
###### \java\seedu\address\storage\AliasManagerStorage.java
``` java
/**
 * Represents a storage for {@link seedu.address.model.AliasManager}.
 */
public interface AliasManagerStorage {

    /**
     * Returns the file path of the data file.
     */
    String getAliasManagerFilePath();

    /**
     * Returns AliasManager data as a {@link ReadOnlyAliasManager}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAliasManager> readAliasManager() throws DataConversionException, IOException;

    /**
     * @see #getAliasManagerFilePath()
     */
    Optional<ReadOnlyAliasManager> readAliasManager(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAliasManager} to the storage.
     * @param aliasManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAliasManager(ReadOnlyAliasManager alias) throws IOException;

    /**
     * @see #saveAliasManager(ReadOnlyAliasManager)
     */
    void saveAliasManager(ReadOnlyAliasManager alias, String filePath) throws IOException;

}
```
###### \java\seedu\address\storage\XmlAdaptedAlias.java
``` java
/**
 * JAXB-friendly version of the Alias.
 */
public class XmlAdaptedAlias {

    @XmlElement(required = true)
    private String alias;
    @XmlElement(required = true)
    private String originalPhrase;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedAlias() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedAlias
     */
    public XmlAdaptedAlias(ReadOnlyAlias source) {
        alias = source.getAlias();
        originalPhrase = source.getOriginalPhrase();
    }

    /**
     * Converts this jaxb-friendly adapted alias object into the model's Alias object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted alias
     */
    public Alias toModelType() throws IllegalValueException {
        final String alias = new String(this.alias);
        final String originalPhrase = new String(this.originalPhrase);
        
        return new Alias(alias, originalPhrase);
    }
}
```
###### \java\seedu\address\storage\XmlAliasManagerStorage.java
``` java
/**
 * A class to access AliasManager data stored as an xml file on the hard disk.
 */
public class XmlAliasManagerStorage implements AliasManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlAliasManagerStorage.class);

    private String filePath;

    public XmlAliasManagerStorage(String filePath){
        this.filePath = filePath;
    }

    public String getAliasManagerFilePath(){
        return filePath;
    }

    /**
     * Similar to {@link #readAliasManager()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAliasManager> readAliasManager(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File aliasFile = new File(filePath);

        if (!aliasFile.exists()) {
            logger.info("AliasManager file "  + aliasFile + " not found");
            return Optional.empty();
        }

        ReadOnlyAliasManager aliasOptional = XmlFileStorage.loadDataFromSaveAliasFile(new File(filePath));

        return Optional.of(aliasOptional);
    }

    /**
     * Similar to {@link #saveAliasManager(ReadOnlyAliasManager)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveAliasManager(ReadOnlyAliasManager alias, String filePath) throws IOException {
        assert alias != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableAliasManager(alias));
    }

    @Override
    public Optional<ReadOnlyAliasManager> readAliasManager() throws DataConversionException, IOException {
        return readAliasManager(filePath);
    }

    @Override
    public void saveAliasManager(ReadOnlyAliasManager alias) throws IOException {
        saveAliasManager(alias, filePath);
    }

}
```
###### \java\seedu\address\storage\XmlSerializableAliasManager.java
``` java
/**
 * An immutable AliasManager that is serializable to XML format
 */

@XmlRootElement(name = "aliasmanager")
public class XmlSerializableAliasManager implements ReadOnlyAliasManager {

    @XmlElement
    private List<XmlAdaptedAlias> aliases;

    {
        aliases = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableAliasManager() {}

    /**
     * Conversion
     */
    public XmlSerializableAliasManager(ReadOnlyAliasManager src) {
        aliases.addAll(src.getAliasList().stream().map(XmlAdaptedAlias::new).collect(Collectors.toList()));
    }

    @Override
    public UniqueAliasList getUniqueAliasList() {
        UniqueAliasList lists = new UniqueAliasList();
        for (XmlAdaptedAlias p : aliases) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {
            }
        }
        return lists;
    }

    @Override
    public List<ReadOnlyAlias> getAliasList() {
        return aliases.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }
}
```
