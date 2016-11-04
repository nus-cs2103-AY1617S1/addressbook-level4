# A0112898Ureused
###### /java/seedu/cmdo/commons/util/SearchUtil.java
``` java
    /**
	 * String similarity checking and returns a percentage of
	 * how similar the strings are
	 * 
	 * @param s1 - First string to check with.
	 * @param s2 - Secnd string to check with.
	 * @return double The percentage of the similarity of strings
	 */
    public static double checkStringSimilarity(String s1, String s2) {
    	// s1 should always be bigger, for easy check thus the swapping.
    	if (s2.length() > s1.length()) {
            String tempStr = s1; 
            s1 = s2; 
            s2 = tempStr;
        }
    	
        int bigLen = s1.length();
        
        if (bigLen == 0) { 
        	return LEVENSHTEIN_FULL; 
        }
        return ((bigLen - extractLDistance(s1, s2)) /
        		(double) bigLen) * LEVENSHTEIN_FULL;
    }
    
```
###### /java/seedu/cmdo/commons/util/SearchUtil.java
``` java
    /**
     * Computes the distance btw the 2 strings, via the Levenshtein Distance Algorithm
     * 
	 * @param s1 - First string to check with.
	 * @param s2 - Second string to check with.
     * @return the new cost to change to make the string same
     */
    private static int extractLDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        int[] costToChange = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                	costToChange[j] = j;
                }  else {
                    if (j > 0) {
                        int newValue = costToChange[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1)) {
                            newValue = Math.min(Math.min(newValue, lastValue),
                            		costToChange[j]) + 1;
                        }
                        costToChange[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0) {
            	costToChange[s2.length()] = lastValue;
            }
        }
        return costToChange[s2.length()];
    }
    
```
