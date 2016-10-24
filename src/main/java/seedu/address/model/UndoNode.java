package seedu.address.model;

public class UndoNode {
    
    public String command;
    public UndoNode next, prev;
    
    public UndoNode(String text, UndoNode prev){
        this.command = text;
        this.prev = prev;
        this.next = null;
    }
    
    public UndoNode getNext(){
        return this.next;
    }
    
    public UndoNode getPrev(){
        return this.prev;
    }

    public void setNext(UndoNode next){
        this.next = next;
    }
    
    public void setPrev(UndoNode prev){
        this.prev = prev;
    }

}
