package bean;
import java.util.TreeSet;

import constants.Direction;

public class Elevator { 
	
    private Elevator() {}; 

     
    private static Elevator elevator = null; 
     
    private TreeSet requestSet = new TreeSet(); 
     
    private int currentFloor = 0; 

    private Direction direction = Direction.UP; 

     
    private Thread requestProcessorThread; 

    /** 
     * @return singleton instance 
     */ 
    public static Elevator getInstance() { 
        if (elevator == null) { 
            elevator = new Elevator(); 
        } 
        return elevator; 
    }

	public TreeSet getRequestSet() {
		return requestSet;
	}

	public void setRequestSet(TreeSet requestSet) {
		this.requestSet = requestSet;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}
	
	 public void setCurrentFloor(int currentFloor) throws InterruptedException { 
	        if (this.currentFloor > currentFloor) { 
	            setDirection(Direction.DOWN); 
	        } else { 
	            setDirection(Direction.UP); 
	        } 
	        this.currentFloor = currentFloor; 
	         
	        System.out.println("Floor : " + currentFloor); 
	         
	        Thread.sleep(3000); 
	    } 

	

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Thread getRequestProcessorThread() {
		return requestProcessorThread;
	}

	public void setRequestProcessorThread(Thread requestProcessorThread) {
		this.requestProcessorThread = requestProcessorThread;
	} 
    
    
    
	 public synchronized void addFloor(int f) { 
	        requestSet.add(f); 
	         
	        if(requestProcessorThread.getState() == Thread.State.WAITING){ 
	            // Notify processor thread that a new request has come if it is waiting 
	            notify(); 
	        }else{ 
	            // Interrupt Processor thread to check if new request should be processed before current request or not.  
	            requestProcessorThread.interrupt(); 
	        } 
	         
	    } 

	    /** 
	     * @return next request to process based on elevator current floor and direction 
	     */ 
	    public synchronized int nextFloor() { 

	        Integer floor = null; 

	        if (direction == Direction.UP) { 
	            if (requestSet.ceiling(currentFloor) != null) { 
	            	
	                floor = (Integer) requestSet.ceiling(currentFloor); 
	            } else { 
	                floor = (Integer) requestSet.floor(currentFloor); 
	            } 
	        } else { 
	            if (requestSet.floor(currentFloor) != null) { 
	                floor = (Integer) requestSet.floor(currentFloor); 
	            } else { 
	                floor = (Integer) requestSet.ceiling(currentFloor); 
	            } 
	        } 
	        if (floor == null) { 
	            try { 
	                System.out.println("Waiting at Floor :" + getCurrentFloor()); 
	                wait(); 
	            } catch (InterruptedException e) { 
	                e.printStackTrace(); 
	            } 
	        } else { 
	            // Remove the request from Set as it is the request in Progress. 
	            requestSet.remove(floor); 
	        } 
	        return (floor == null) ? -1 : floor; 
	    } 

    
    
    
}