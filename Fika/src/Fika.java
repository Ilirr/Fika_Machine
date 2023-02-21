import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fika 
{
	static List<Worker> workers = new ArrayList<Worker>();

	static String[] workerNames = {
			"Erik",
			"Simon",
			"Adrian",
			"Nora"
	};
	static Thread[] workerThreads;

	public static void main(String[] args) throws InterruptedException
	{
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
			Worker worker = new Worker(workerNames[i],energy,energyDecay);
			worker.m_currentState = new WorkState();
			workers.add(worker);
		}	
		
		//Worker Threads
		workerThreads = new Thread[workers.size()];
		for (int i = 0; i < workerThreads.length; i++) 
		{
			workerThreads[i] = new Thread(workers.get(i));
			workerThreads[i].start();
		}
		while(true)
		{
			Thread.sleep(1500);
			for (Worker worker : workers)
			{
				if(worker.m_currentState != null)
				{
					worker.m_currentState.Execute(worker);
				}
				
			}
		}
	}
	public static void RemoveWorker(Worker worker)
	{
		workers.remove(worker);
	}

}
