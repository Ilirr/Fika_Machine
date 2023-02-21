
public class WorkState extends State
{

	@Override
	public void Enter(Worker worker) 
	{
		
	}

	@Override
	public void Execute(Worker worker) 
	{
		System.out.println(worker.Name + " is working with energy level " + worker.Energy);
		
		if(worker.IsWeak()== true)
		{
			worker.SetState(new RestState());
		}
		
	}

	@Override
	public void Exit(Worker worker) 
	{
		
	}
	

}
