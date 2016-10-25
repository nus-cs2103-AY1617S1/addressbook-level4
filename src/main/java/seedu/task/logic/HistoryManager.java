package seedu.task.logic;

import java.util.ArrayList;

public class HistoryManager {
    
    private ArrayList<RollBackCommand> undoList;
    
    private ArrayList<String> previousCommandList;
    
    public HistoryManager() {
        undoList = new ArrayList<RollBackCommand>();
        previousCommandList = new ArrayList<String>();
        
    }
    
    public void setUndoList(ArrayList<RollBackCommand> undoList){
        this.undoList = undoList;
    }
    
    
    public void setPreviousCommand(ArrayList<String> previousCommand){
        this.previousCommandList = previousCommand;
    }
    
     
    
     public ArrayList<RollBackCommand> getUndoList(){
         return undoList;
     }
     
     
     public ArrayList<String> getPreviousCommandList(){
         return previousCommandList;
     }
     
   

}
