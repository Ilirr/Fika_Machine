import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Fika 
{
	static ArrayList<Worker> workers;
	
	static final int workerSpeed = 1000;

	static String[] workerNames = {
			"Erik",
			"Simon",
			"Adrian",
			"Nora"
	};
	static Thread[] workerThreads;

	public static void main(String[] args) throws InterruptedException
	{
		Collections.synchronizedCollection(new ArrayList<Worker>());
		//Coffee Thread
		CoffeeMachine machine = CoffeeMachine.GetInstance();
		Thread coffeeThread = new Thread(machine::run);
		coffeeThread.start();
		
		//Create workers
		List<Worker> workers = new ArrayList<Worker>();	
		for (int i = 0; i < workerNames.length; i++) 
		{
			int energy = new Random().nextInt(61)+30;
			int energyDecay = new Random().nextInt(1001) + 500;
			State state = new WorkState();
			Worker worker = new Worker(workerNames[i],energy,energyDecay, state);
			workers.add(worker);
		}	
		
		//Worker Threads
		workerThreads = new Thread[workers.size()];
		for (int i = 0; i < workerThreads.length; i++) 
		{
			workerThreads[i] = new Thread(workers.get(i));
			workerThreads[i].start();
		}
	/*	  for (Thread workerThread : workerThreads) {
	            workerThread.join();
	        }
	        */
		while(true)
		{
			synchronized(workers)
			{
				Thread.sleep(workerSpeed);
				for (Worker worker : workers)
				{
					if(worker.m_currentState != null)
					{
						worker.m_currentState.Execute(worker);
					}
					
				}
			}
			
		}
	}
}
