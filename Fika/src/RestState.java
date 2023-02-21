
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
		System.out.println(worker.Name + " is taking a BREAK with energy level " + worker.Energy);
		
		if(worker.Rested())
		{
			worker.SetState(new WorkState());
		}
		if(worker.IsExhausted() == true)
		{	
			System.out.println(worker.Name + " has gone home with energy level " + worker.Energy);
			Fika.workers.remove(worker);
			worker.StopThread();
			
		}
	}
	@Override
	public void Exit(Worker worker)
	{
		System.out.println(worker.Name + " goes back to work with energy level " + worker.Energy);
	}
	
}
