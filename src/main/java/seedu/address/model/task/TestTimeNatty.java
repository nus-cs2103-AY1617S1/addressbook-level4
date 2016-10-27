package seedu.address.model.task;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TestTimeNatty {
    public static void main(String[] args){
     Scanner sc=new Scanner(System.in);
     String input=sc.nextLine();
     while(input!="1"){
        
    List<Date> date = new com.joestelmach.natty.Parser().parse(input).get(0).getDates();
    System.out.println(date.toString());
    input=sc.nextLine();
     }
    
    }
}
