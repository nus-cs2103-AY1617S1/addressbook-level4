package seedu.task.logic;

import java.util.ArrayList;

public class HistoryList {
    
    private static ArrayList<RollBackCommand> undoList;
    
    private ArrayList<RollBackCommand> redoList;
    
    private static ArrayList<String> previousCommand;
    
    private int previousCommandCounter;
    
    public HistoryList() {
        undoList = new ArrayList<RollBackCommand>();
        redoList = new ArrayList<RollBackCommand>();
        previousCommand = new ArrayList<String>();
        previousCommandCounter = 0;
    }
    
    public void setUndoList(ArrayList<RollBackCommand> undoList){
        this.undoList = undoList;
    }
    
    public void setRedoList(ArrayList<RollBackCommand> redoList){
        this.redoList = redoList;
    }
    public void setPreviousCommand(ArrayList<String> previousCommand){
        this.previousCommand = previousCommand;
    }
    
     public void setPreviousCommandCounter(int previousCommandCounter){
        this.previousCommandCounter = previousCommandCounter;
    }
    
     public static ArrayList<RollBackCommand> getUndoList(){
         return undoList;
     }
     
     public ArrayList<RollBackCommand> getRedoList(){
         return redoList;
     }
     public static ArrayList<String> getPreviousCommand(){
         return previousCommand;
     }
     
     public int getPreviousCommandCounter(){
         return previousCommandCounter;
     }

}
