package harmony.mastermind.commons.exceptions;

//@@author A0139194X
/*
 * An exception say that a filepath has no write permission
 */
public class UnwrittableFolderException extends Exception {
    
    public UnwrittableFolderException(String message) {
        super(message);
    }
    
}
