package seedu.address.history;

public interface InputHistory {
    
    public void updateInputHistory(String userInput);
    
    public boolean isEarliestInput();
    
    public boolean isLatestInput();
    
    public String popPrevInput();
    
    public String pushPrevInput(String input);
    
    public String popNextInput();
    
    public String pushNextInput(String input);
    
    public void updateCurrentShownInput(String input);
    
    public String getStoredCurrentShownInput();
}
