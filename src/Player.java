public class Player 
{
	private int count = 0;
	private String player;
	private int lastSat = 0;
	



	public void countIncrement()
	{
		count++;
	}
	
	public void countDecrement()
	{
		count--;
	}
	
	public void setName(String s)
	{
		player = s;
	}
	
	public int getCount()
	{
		return count;
	}

	public void setCount(int x)
    {
        count = x;
    }
	
	public String getName()
	{
		return player;
	}
	
	public int getLastSat()
	{
		return lastSat;
	}

    public void setLastSat(int x)
    {
        lastSat = x;
    }
	
	public void satIncrement()
	{
		lastSat++;
	}
	
	public void satDecrement()
	{
		lastSat--;
	}
	
	public void satReset()
	{
		lastSat = 0;
	}
	
}
