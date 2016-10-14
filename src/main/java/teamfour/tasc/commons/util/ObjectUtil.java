package teamfour.tasc.commons.util;

/**
 * Utility methods related to Objects
 */
public class ObjectUtil {
    /**
     * Are both objects either both null, or both non-null and have the same
     * content?
     * 
     * Returns true if that's the case, otherwise returns false
     */
    public static boolean isEquivalentOrBothNull(Object object1, Object object2) {
        if (object1 == null && object2 == null) {
            return true;
        }
        
        if (object1 == null && object2 != null) {
            return false;
        }
        
        if (object1 != null && object2 == null) {
            return false;
        }
        
        return object1.equals(object2);
    }
}
