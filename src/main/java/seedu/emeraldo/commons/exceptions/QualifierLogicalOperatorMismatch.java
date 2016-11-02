package seedu.emeraldo.commons.exceptions;

//@@author A0139749L
/**
 * Signals that QualifierList and LogicalOperatorList does not have the right size
 * LogicalOperatorList size must always be 1 less than QualifierList size
 */
public class QualifierLogicalOperatorMismatch extends Exception {

    public QualifierLogicalOperatorMismatch(String message) {
        super(message);
    }
}
