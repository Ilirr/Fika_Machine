import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.random.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class CoffeeMachine implements Runnable
{
	
	public ConcurrentLinkedQueue<Worker> workerQueue;
	
    private static CoffeeMachine single_instance = null;

     static final int PRODUCTION_TIME = 2;
     
     static final int MAX_DRINK_RESERVE = 20;
     
     public HashMap<String, Integer> producedCoffee;
     
     int drinkReserve;
     
     //Double check lock
     public synchronized static CoffeeMachine GetInstance()
     {
         if (single_instance == null)
         {
        	 synchronized(CoffeeMachine.class)
        	 {
        		 if(single_instance == null)
        		 {
                     single_instance = new CoffeeMachine();
        		 }
        	 }
         }   
         return single_instance;
     }       
     private CoffeeMachine()
     {
    	 workerQueue = new ConcurrentLinkedQueue<Worker>();
    	 producedCoffee = new HashMap<String,Integer>();
    	 this.drinkReserve = 0;
     }
     public synchronized void QueueWorker(Worker worker)
     {
    	 workerQueue.add(worker);

     }
     public synchronized void ServeDrink(Worker worker)
     {
    	 
    	 Random rand = new Random();
    	 int index = rand.nextInt(producedCoffee.size());
    	 String key = (String) producedCoffee.keySet().toArray()[index];
    	 int value = producedCoffee.get(key);
    	 	 
    	 worker.Drink(key, value);
    	 
    	 drinkReserve--;
    	 System.out.println("Coffee Machine has " + drinkReserve + " drinks in reserve.");
    	 if(worker.Energy < 100)
    	 {
    		 workerQueue.add(workerQueue.poll());
    	 }
    	 else
    	 {
    		 workerQueue.remove(worker);
    		 System.out.println("REMOVED FROM QUEUE: " + worker.Name);
    		 
    	 }
     }
     public synchronized void ProduceDrink()
     {
    	 if(drinkReserve >= MAX_DRINK_RESERVE)
    	 {
    		 System.out.println("Drink reserve is full, no produced drink");
             return;
    	 }
    	 Random random = new Random();
    	 int drinkEnergy = 0;
    	 String drinkType = "";
    	 int drinkIndex = random.nextInt(3);	
    	 if(drinkIndex == 0)
    	 {
             drinkEnergy = random.nextInt(6) + 15;
    		 drinkType = "Black Coffee";
    	 }
    	 if(drinkIndex == 1)
    	 {
             drinkEnergy = random.nextInt(11) + 20;
             drinkType = "Cappuccino";
    	 }
    	 if(drinkIndex == 2)
    	 {  
    		 drinkEnergy = random.nextInt(11) + 25;
    		 drinkType = "Latte";
    	 }
    	 drinkReserve++;
    	 producedCoffee.put(drinkType, drinkEnergy);
    	 System.out.println("Drink created. " + "Coffee Machine has " + drinkReserve + " drinks in reserve.");
    	 
     }
	@Override
	public void run() {
		while(true)
		{
			synchronized(workerQueue)
			{
				if(!workerQueue.isEmpty() && drinkReserve > 0)
				{
					System.out.println("FIRST SERVE: " + workerQueue.peek().Name);

					Worker worker = workerQueue.peek();
					ServeDrink(worker);
					try 
					{
						Thread.sleep(1000);
					} catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
			}
			try 
			{
				Thread.sleep(2000);
				ProduceDrink();
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
	}
}
