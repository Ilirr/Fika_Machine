
public class RestState extends State 
{
	@Override
	public void Enter(Worker worker)
	{
		CoffeeMachine.GetInstance().QueueWorker(worker);
	}
	@Override
	public void Execute(Worker worker)
	{
		if(worker.Rested())
		{
			worker.SetState(new WorkState());
		}
		else if(worker.IsExhausted() == true)
		{	
			System.out.println(worker.Name + " has gone home with energy level " + worker.Energy);
			Fika.workers.remove(worker);
			worker.StopThread();
			
		}
		else
		{
			System.out.println(worker.Name + " is taking a break with energy level " + worker.Energy);			
		}

	}
	@Override
	public void Exit(Worker worker)
	{
		System.out.println(worker.Name + " goes back to work with energy level " + worker.Energy);
	}
	
}
	