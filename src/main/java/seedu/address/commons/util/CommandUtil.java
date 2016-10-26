package seedu.address.commons.util;


/**
 * Utility methods related to Command
 */
public class CommandUtil {

    /**
     * Returns true if either of the lists contains the given index (offset by +1)
     * 
     */
    public static boolean isValidIndex(int givenIndex, int UndatedListSize, int DatedListSize, int DatedListOffset) {
        if (givenIndex > DatedListOffset + DatedListSize){
            //givenIndex points to non-existent dated task
            return false;
        }
        if (givenIndex <= DatedListOffset && givenIndex > UndatedListSize){
            //givenIndex points to non-existent undated task
            return false;
        }
        if (givenIndex <= UndatedListSize) {
            //givenIndex points to an undated task
            return true;
        }
        if (givenIndex <= DatedListSize + DatedListOffset) {
            //givenIndex points to an undated task
            return true;
        }
        return false;
    }
    
}
