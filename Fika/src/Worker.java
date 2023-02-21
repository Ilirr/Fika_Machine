import java.util.concurrent.atomic.AtomicInteger;

public class Worker implements Runnable {
	public String Name;

	public State m_currentState;

	private volatile boolean threadRunning = true;
	
	Thread myThread;
	
	public int Energy;
	public int energyDecay;

	public Worker(String _name, int _energy, int _energyDecay, State _state) {
		Name = _name;
		Energy = _energy;
		energyDecay = _energyDecay;
		m_currentState = _state;
	}

	public void StopThread() {
		threadRunning = false;
	}

	public void Drink(String drinkType, int drinkEnergy) 
	{
		Energy = Math.min(100, drinkEnergy + Energy);
		System.out.println(Name + " enjoyed a " + drinkType + " with " + drinkEnergy + " energy");
	}

	public boolean IsWeak() {
		if (Energy < 30 && IsExhausted() == false)
			return true;
		else
			return false;
	}

	public boolean IsExhausted() {
		if (Energy <= 0)
			return true;
		else
			return false;
	}

	public boolean Rested() {
		if (Energy >= 100)
			return true;
		else
			return false;
	}

	public void SetState(State newState) {
		m_currentState.Exit(this);
		m_currentState = newState;
		m_currentState.Enter(this);
	}

	@Override
	public void run() {
		while (threadRunning)
		{
			AtomicInteger myInt = new AtomicInteger(3);
			myInt.getAndDecrement();
			try 
			{
			
				Thread.sleep(energyDecay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Energy--;
		}

	}
}
