package harmony.mastermind.commons.exceptions;


/**
 * @author A0139194X
 * Signals that the input file path does not exist
 */
public class FolderDoesNotExistException extends Exception {
    public FolderDoesNotExistException(String message) {
        super (message);
    }
}
