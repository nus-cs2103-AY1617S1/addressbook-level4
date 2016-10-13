/** 
 *---------------------------------------------------------------------------------- STORAGE CLASS ----------------------------------------------------------------------------------
 * 
 *
 * This class handles the storing and retrieving of existing tasks from the local file "Tasks.txt".
 *
 * It has the following operations:
 *
 * -loadTasksFromFile <retrieves list of tasks (JSON format) from local file and converts it into ArrayList<Task> for easy manipulation>
 * -saveTasksIntoFile <converts ArrayList<Task> to (JSON format) String and store it in local file "Tasks.txt">
 *
 *
**/

import java.util.ArrayList;
import java.lang.reflect.Type;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;


public class SuperbTodoIO {
	/**
	 * Use Gson library and BufferedReader to load content in local file into temporary arraylist of task objects
	*/
	public static ArrayList<Task> loadTasksFromFile() throws IOException, JsonSyntaxException {
		Gson gson = new Gson();
		String jsonTasks = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader("Tasks.txt"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				jsonTasks = line;
			}
			Type type = new TypeToken<ArrayList<Task>>() {
        		}.getType();
        	ArrayList<Task> taskList = gson.fromJson(jsonTasks, type);
        	reader.close();
        	return taskList;
		} catch(IOException e) {
			return new ArrayList<Task>();
		}
	}
	
	/**
	 * Use Gson library and PrintWriter to save content in temporary arraylist into the local file in JSON format
	*/
	public static void saveTasksIntoFile() throws FileNotFoundException, UnsupportedEncodingException {
		Gson gson = new Gson();
		String jsonTasks = gson.toJson(Data.task);
		PrintWriter writer = new PrintWriter("Tasks.txt", "UTF-8");
		writer.println(jsonTasks);
		writer.close();
	}
}