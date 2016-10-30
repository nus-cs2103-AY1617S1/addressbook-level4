# A0139128Areused
###### \java\seedu\whatnow\commons\exceptions\IllegalValueException.java
``` java
package seedu.whatnow.commons.exceptions;

/**
 * Signals that some given data does not fulfill some constraints.
 */
public class IllegalValueException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public IllegalValueException(String message) {
        super(message);
    }
}
```
###### \java\seedu\whatnow\model\ModelManager.java
``` java
    @Override
    public ReadOnlyWhatNow getWhatNow() {
        return whatNow;
    }
```
###### \java\seedu\whatnow\model\ModelManager.java
``` java
    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        whatNow.removeTask(target);
        indicateWhatNowChanged();
    }
```
