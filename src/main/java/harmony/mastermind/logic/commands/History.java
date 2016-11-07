package harmony.mastermind.logic.commands;

import java.util.ArrayList;
import java.util.Stack;

import harmony.mastermind.storage.StorageMemory;
import harmony.mastermind.memory.GenericMemory;
import harmony.mastermind.memory.Memory;

public class History {
    private static ArrayList<GenericMemory> current = null;
    private static Stack<ArrayList<GenericMemory>> back = new Stack<ArrayList<GenericMemory>>();
    private static Stack<ArrayList<GenericMemory>> forward = new Stack<ArrayList<GenericMemory>>();

    //@@author A0143378Y
    /*
     * Creates a snapshot of changes in the memory and moves previous state into the back stack. forward stack is reinitialized
     */
    public static void advance(Memory memory) {
        forward = new Stack<ArrayList<GenericMemory>>();
        back.push(current);
        current = new ArrayList<GenericMemory>();
        duplicateMemory(memory);
    }

    //@@author A0143378Y
    /*
     * Duplicates memory into the current ArrayList
     */
    private static void duplicateMemory(Memory memory) {
        for (int i = 0; i< memory.getSize(); i++) { // Duplicating of memory into snapshot
            current.add(new GenericMemory(memory.get(i).getType(),
                    memory.get(i).getName(),
                    memory.get(i).getDescription(),
                    memory.get(i).getStart(),
                    memory.get(i).getEnd(),
                    memory.get(i).getState()));
        }
    }
    
    /*
     * Unused code: 
     * Reason: For future implementations of Memory 
    //@@author A0143378Y-unused
    // Retrieves most recent snapshot in the forward stack and swap it with current memory. Current memory gets pushed into back stack
    public static void redo(Memory memory) {
        if (forward.isEmpty() || (forward.peek() == null)) {
            System.out.println("Nothing to redo!");
            return;
        }
        
        redoMemorySwap(memory);
        StorageMemory.saveToStorage(memory);
        System.out.println("Redo successful.");
    }
    
    //@@author A0143378Y
    // Swaps memory used for redo
    private static void redoMemorySwap(Memory memory) {
        assert forward.size() > 0;
        back.push(current);
        current = forward.pop();
        memory.setList(current);
    }

    //@@author A0143378Y
    // Retrieves most recent snapshot in the back stack and swap it with current memory. Current memory gets pushed into forward stack
    public static void undo(Memory memory) {
        if (back.isEmpty() || (back.peek() == null)) {
            System.out.println("Nothing to undo!");
            return;
        }

        ArrayList<GenericMemory> temp = duplicateTemp(memory);
        undoMemorySwap(memory, temp);
        StorageMemory.saveToStorage(memory);
        System.out.println("Undo successful.");
    }
    
    //@@author A0143378Y
    // Swaps memory used for undo
    private static void undoMemorySwap(Memory memory, ArrayList<GenericMemory> temp) {
        forward.push(temp);
        current = back.pop();
        assert current != null;
        memory.setList(current);
    }

    //@@author A0143378Y
    // Duplicate current memory into temp ArrayList
    private static ArrayList<GenericMemory> duplicateTemp(Memory memory) {
        ArrayList<GenericMemory> temp = new ArrayList<GenericMemory>(); // Duplicating of memory into forward stack
        for (int i = 0; i < memory.getSize(); i++) {
            temp.add(new GenericMemory(memory.get(i).getType(),
                    memory.get(i).getName(),
                    memory.get(i).getDescription(),
                    memory.get(i).getStart(),
                    memory.get(i).getEnd(),
                    memory.get(i).getState()));
        }
        return temp;
    }
    */

}
