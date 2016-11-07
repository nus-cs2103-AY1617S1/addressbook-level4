# A0147890Uunused
###### \java\seedu\simply\ui\UiPart.java
``` java
    //unused because find cannot search by 12hr formats
    /**
     * @param 24 hour string values
     * @return 12 hour string value
     * this method converts 24 hour format to 12 hour format for display in event and deadline cards
     */
    protected String twelveHourConvertor(String value) {
        int toBeConverted = Integer.parseInt(value);
        int firstTwoDigits = toBeConverted / 100;
        int twelveHourFormat = 0;
        String twelveHourClock;
        if (firstTwoDigits == 12) {
            twelveHourFormat = toBeConverted;
        } 
        else {
            twelveHourFormat = toBeConverted % 1200;
        }
        
        twelveHourClock = Integer.toString(twelveHourFormat);
        
        twelveHourClock = new StringBuilder(twelveHourClock).insert(twelveHourClock.length() - 2, '.').toString();
        
        if (firstTwoDigits == 12) {
            twelveHourClock = twelveHourClock + "pm";
        } else if (firstTwoDigits > 12) {
            twelveHourClock = twelveHourClock + "pm";
        } else {
            twelveHourClock = twelveHourClock + "am";
        }
            
        return twelveHourClock;
    } 
    
```
