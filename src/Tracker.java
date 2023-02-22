import java.util.*;
/*
todo:	
        -	[X]	Figure out why the mistake method isn't perfectly fixing things
		-	[X]	add methods to remove/add players. added players should have gamesplayed compareable to whoever has the least gamesplayed
		- 	[]	
*/
public class Tracker 
{
	int numRounds = 0;
	static Scanner input = new Scanner(System.in);
	static Random rand = new Random();
	
	
	
	
	public static void main(String[] args) 
	{
		
		
		System.out.print("How many players are there?: ");
		int numPlayers = input.nextInt();
		
		Player[] players;
		
		players = new Player[numPlayers];
		
		for (int i = 0; i < players.length; i++ )
		{
			System.out.printf("Enter player # %ds name: ", i + 1);
			String name = input.next();
			players[i] = new Player();
			players[i].setName(name);	
		
		}
        System.out.println();
		
		LinkedList<Player> playersLL = new LinkedList<>();
		
		for (int i = 0; i < players.length; i++)
		{
			playersLL.add(players[i]);
		}
		
		int[] satStates = new int[playersLL.size()];
        for (int i = 0; i < playersLL.size(); i++)
        {
            satStates[i] = playersLL.get(i).getLastSat();
        }
		
		Shuffle(playersLL);
		Round(playersLL, satStates);
		
		System.out.print("Another round? (0 for no, 1 for yes, 2 for mistake, 3 for add new player, 4 for remove player): ");
		int round = input.nextInt();
		
		
		while (round != 0)
		{
			if (round == 1)
			{
				Round(playersLL, satStates);
			}
			
			else if (round == 2)
			{
				System.out.print("What player didn't play?");
				String notPlay = input.next();
				System.out.print("What player played instead?");
				String played = input.next();
				
				Mistake(playersLL, notPlay, played, satStates);
				
			}

			else if (round == 3)
			{
				addPlayer(playersLL, satStates);
			}

			else if (round == 4)
			{
				removePlayer(playersLL, satStates);
			}
			
			System.out.print("Another round? (0 for no, 1 for yes, 2 for mistake, 3 for add new player, 4 for remove player): ");
			round = input.nextInt();
		}
		
		
		
	}
	

    public static void SortCount(LinkedList<Player> playersLL)
    {
        for (int i = 0; i < playersLL.size(); i++)
		{
			    for (int j = 0; j < playersLL.size(); j++)
		    	{
				    if (i == j)
				    	continue;
			
				    else if (playersLL.get(j).getCount() < playersLL.get(i).getCount())
				    {
				    	playersLL.addFirst(playersLL.get(j));
				    	playersLL.remove(j+1);
				    }
			    }
        }
    }

    public static void SortSat(LinkedList<Player> playersLL)
    {
        for (int x = 0; x < 20; x++)
        {
        for (int i = 0; i < playersLL.size(); i++)
        {
            if (i == playersLL.size() - 1)
            {
                break;
            }
            else if (playersLL.get(i).getCount() != playersLL.get(i+1).getCount())
            {
                continue;
            }

            if (playersLL.get(i).getLastSat() > playersLL.get(i+1).getLastSat())
            {
                playersLL.add(i+2, playersLL.get(i));
                playersLL.remove(i);
            }
        }
        }
    }

	
	public static void Round(LinkedList<Player> playersLL, int[] satStates)
	{
		Shuffle(playersLL);
        SortCount(playersLL);
        SortSat(playersLL);
       
		satStates = new int[playersLL.size()];
        for (int i = 0; i < playersLL.size(); i++)
        {
            satStates[i] = playersLL.get(i).getLastSat();
        }

		for (int j = 0; j < playersLL.size(); j++)
        {
            satStates[j] = playersLL.get(j).getLastSat();
        }
		System.out.print("Players who don't play: ");


		for (int i = 5; i < playersLL.size(); i++)
		{
			System.out.printf("%s ",playersLL.get(i).getName());
			playersLL.get(i).satReset();
		}

		System.out.println();
		
		for (int i = 0; i < 5; i++)
		{
			playersLL.get(i).countIncrement();
			playersLL.get(i).satIncrement();
		}
		
		printStates(playersLL);
        System.out.println();
		
	}
	
	public static void Shuffle(LinkedList<Player> playersLL)
	{
		for (int i = 0; i < 20; i++)
		{
			int random = rand.nextInt(playersLL.size());
			playersLL.add(playersLL.get(random));
			playersLL.remove(random);
			
		}
	}
	
	public static void Mistake(LinkedList<Player> playersLL, String notPlay, String played, int[] satStates)
	{
        /*for (int j = 0; j < playersLL.size(); j++)
        {
            playersLL.get(j).setLastSat(satStates[j]);
        }
		*/
		for (int i = 0; i < playersLL.size(); i++)
		{
			if (playersLL.get(i).getName().equals(notPlay))
			{
				playersLL.get(i).countDecrement();
                playersLL.get(i).satReset();
				
			}
			else if (playersLL.get(i).getName().equals(played))
			{
				playersLL.get(i).setLastSat(satStates[i]);
				playersLL.get(i).countIncrement();
                playersLL.get(i).satIncrement();
			}
			else
				continue;
		}
		printStates(playersLL);
	}

	public static void printStates(LinkedList<Player> playersLL)
	{
		
		for (int i = 0; i < playersLL.size(); i++)
		{
			System.out.printf("%s : %d%n", playersLL.get(i).getName(), playersLL.get(i).getCount());
		}
		
		
		
	}

	public static void addPlayer(LinkedList<Player> playersLL, int[] satStates)
	{
		playersLL.addLast(new Player());
		System.out.print("What is the new player's name?");
		playersLL.get(playersLL.size() - 1).setName(input.next());

		int min = 10000;
		for (int i = 0; i < playersLL.size() - 1; i++)
		{
			if (playersLL.get(i).getCount() < min)
				min = playersLL.get(i).getCount();
		}

		playersLL.get(playersLL.size() - 1).setCount(min);

		satStates = new int[playersLL.size()];
        for (int i = 0; i < playersLL.size(); i++)
        {
            satStates[i] = playersLL.get(i).getLastSat();
        }
		printStates(playersLL);
	}


	public static void removePlayer(LinkedList<Player> playersLL, int[] satStates)
	{
		System.out.print("Who is being removed?");
		String buch = input.next();

		for (int i = 0; i < playersLL.size(); i++)
		{
			if (playersLL.get(i).getName().equals(buch))
			{
				playersLL.remove(i);
				break;
			}
			
		}
		satStates = new int[playersLL.size()];
        for (int i = 0; i < playersLL.size(); i++)
        {
            satStates[i] = playersLL.get(i).getLastSat();
        }
		printStates(playersLL);
	}
}