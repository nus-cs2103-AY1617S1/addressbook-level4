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
    // Creates a snapshot of changes in the memory and moves previous state into the back stack. forward stack is reinitialized
    public static void advance(Memory memory) {
        forward = new Stack<ArrayList<GenericMemory>>();
        back.push(current);
        current = new ArrayList<GenericMemory>();
        duplicateMemory(memory);
    }

    //@@author A0143378Y
    // Duplicates memory into the current ArrayList
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
}
