import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Worker implements Runnable {
	public String Name;

	public State m_currentState;

	private volatile boolean threadRunning = true;

	public Semaphore energySemaphore;
	public int Energy;
	public int energyDecay;

	public Worker(String _name, int _energy, int _energyDecay, State _state) {
		Name = _name;
		Energy = _energy;
		energySemaphore = new Semaphore(1);
		energyDecay = _energyDecay;
		m_currentState = _state;
	}

	public synchronized void StopThread() {
		threadRunning = false;
	}

	public synchronized void Drink(String drinkType, int drinkEnergy) 
	{
		try 
		{
			energySemaphore.acquire();
			Energy = Math.min(100, drinkEnergy + Energy);
		}
		catch(InterruptedException e)
		{
            e.printStackTrace();
		}
		finally {
			energySemaphore.release();
		}
		System.out.println(Name + " enjoyed a " + drinkType + " with " + drinkEnergy + " energy");
	}

	public synchronized boolean IsWeak() {
		if (Energy < 30 && IsExhausted() == false)
			return true;
		else
			return false;
	}

	public synchronized boolean IsExhausted() {
		if (Energy <= 0)
			return true;
		else
			return false;
	}

	public synchronized boolean Rested() {
		if (Energy >= 100)
			return true;
		else
			return false;
	}

	public synchronized void SetState(State newState) {
		m_currentState.Exit(this);
		m_currentState = newState;
		m_currentState.Enter(this);
	}

	@Override
	public void run()
	{
		while (threadRunning)
		{
			try 
			{
				energySemaphore.acquire();
				this.m_currentState.Execute(this);
				Energy--;

			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			finally {
				energySemaphore.release();
			}
			try 
			{
				Thread.sleep(energyDecay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
