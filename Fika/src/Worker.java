
public class Worker implements Runnable {
	public String Name;

	public State m_currentState;

	private volatile boolean threadRunning = true;

	public int Energy = 0;
	public int energyDecay;

	public Worker(String _name, int _energy, int _energyDecay) {
		Name = _name;
		Energy = _energy;
		energyDecay = _energyDecay;
	}

	public void StopThread() {
		threadRunning = false;
	}

	public void Drink(String drinkType, int drinkEnergy) {
		Energy += drinkEnergy;
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
