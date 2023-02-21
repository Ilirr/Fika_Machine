
public abstract class State 
{
	public abstract void Enter(Worker worker);
	
	public abstract void Execute(Worker worker);
	
	public abstract void Exit(Worker worker);

}
