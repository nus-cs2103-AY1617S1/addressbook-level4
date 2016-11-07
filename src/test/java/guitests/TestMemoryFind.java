package guitests;

import org.junit.Test;

import harmony.mastermind.memory.GenericMemory;
import harmony.mastermind.memory.Memory;
import harmony.mastermind.storage.StorageMemory;
import harmony.mastermind.logic.commands.FindCommand;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TestMemoryFind { 
    
    //@@author A0143378Y
    @Test
    public void test() { 
        Memory testMem = new Memory();
        StorageMemory.setSaveFileAddress("test2.txt");
        StorageMemory.checkForFileExists(testMem);
        
        testSearchExact(testMem);
        testFindDate(testMem);
    }
    
    //@@author A0143378Y
    private void testSearchExact(Memory testMem) { 
        testSearchExact_EmptyMem();
        testSearchExact_NonEmptyMem(testMem);
    }
    
    //@@author A0143378Y
    private void testFindDate(Memory testMem){
        Calendar date = new GregorianCalendar();
        testFindDate_NoneExistent(testMem, date);
        testFindDate_OnEndDate(testMem, date);
        testFindDate_OnStartDate(testMem, date);
        testFindDate_Containing(testMem, date);
    }
    
    //@@author A0143378Y
    private void testSearchExact_EmptyMem() { 
        Memory testMem = new Memory();
        
        ArrayList<GenericMemory> testCase1 = FindCommand.searchExact("I want to eat", testMem);
        assertEquals("If mem is not empty, test if item cannot be found", testCase1.size(), 0);
    }
    
    //@@author A0143378Y
    private void testSearchExact_NonEmptyMem(Memory testMem) { 
        ArrayList<GenericMemory> testCase1 = FindCommand.searchExact("I want to eat", testMem);
        assertEquals("If mem is not empty, test if item cannot be found", testCase1.size(), 0);
        
        ArrayList<GenericMemory> testCase2 = FindCommand.searchExact("I am hungry", testMem);
        assertEquals("If mem is not empty, test if item can be found", testCase2.size(), 1);
    }
    
    //@@author A0143378Y
    /*
     * Completely outside of any range
     */
    private void testFindDate_NoneExistent(Memory testMem, Calendar date){
        
        date.set(1999, 10, 9);
        ArrayList<GenericMemory> result1 =  FindCommand.findDate(date, testMem);
        assertEquals("Cannot find date required", result1.size(), 0);
        
        //A day before earliest startdate
        date.set(2013, 7, 26);
        result1 =  FindCommand.findDate(date, testMem);
        assertEquals("Cannot find date required", result1.size(), 0);
        
        //A day after latest enddate
        date.set(2015, 5, 31);
        result1 =  FindCommand.findDate(date, testMem);
        assertEquals("Cannot find date required", result1.size(), 0);
        
    }
    
    //@@author A0143378Y
    private void testFindDate_OnEndDate(Memory testMem, Calendar date){
        
        date.set(2015, 4, 30);
        ArrayList<GenericMemory> result1 =  FindCommand.findDate(date, testMem);
        assertEquals("Only 1 item has this enddate", result1.size(), 1);
        
    }
    
    //@@author A0143378Y
    private void testFindDate_OnStartDate(Memory testMem, Calendar date){
        
        date.set(2013, 8, 27);
        ArrayList<GenericMemory> result2 =  FindCommand.findDate(date, testMem);
        assertEquals("Only 1 item has this start date", result2.size(), 1);
        
    }
    
    //@@author A0143378Y
    private void testFindDate_Containing(Memory testMem, Calendar date){
        date.set(2014, 1, 27);
        ArrayList<GenericMemory> result3 =  FindCommand.findDate(date, testMem);
        assertEquals("Date within start and end range", result3.size(), 4);
    }
}