package elevatorThreads;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import bean.Elevator;
import bean.Person;

import common.Utility;

public class RequestListener implements Runnable { 

    public void run() { 

        while (true) { 
            String floorNumberStr = null;
            ArrayList<Person> perObjList =new  ArrayList<Person>();
            try { 
                // Read input from console 
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in)); 
                floorNumberStr = bufferedReader.readLine(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
            } 

            if (Utility.isValidFloorNumber(floorNumberStr)) { 
                System.out.println("User Pressed : " + floorNumberStr);
          	    int sum = perObjList.stream().map(Person::getLength).mapToInt(Integer::intValue).sum();

                if()
                
                
                
                Elevator elevator = Elevator.getInstance(); 
                elevator.addFloor(Integer.parseInt(floorNumberStr)); 
            } else { 
                System.out.println("Floor Request Invalid : " + floorNumberStr); 
            } 
        } 
    } 
}