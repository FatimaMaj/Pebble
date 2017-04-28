import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class pebble
{	
	//static Scanner inputInt = new Scanner(System.in);
	static Scanner input = new Scanner(System.in);
	static ArrayList<String> queueOfPebbles = new ArrayList<String>();
	static Map<String, Boolean> checkedpositions = new HashMap<String, Boolean>();
	
	// oo- ==> --o  **Pebble moves from left to right**
	public static String generateWithRule1( String s, int n )
	{
		StringBuilder newStatus = new StringBuilder(s);
		newStatus.setCharAt(n,'-'); 
		newStatus.setCharAt(n+1,'-');
		newStatus.setCharAt(n+2, 'o'); 	
    	return newStatus.toString();
	}
	
	// -oo ==> o-- **Pebble moves from right to left**
	public static String generateWithRule2( String s, int n )
	{
		StringBuilder newStatus = new StringBuilder(s);
		newStatus.setCharAt(n-1, 'o'); 
		newStatus.setCharAt(n, '-'); 
		newStatus.setCharAt(n+1, '-'); 	
    	return newStatus.toString();
	}

	//get acceptable integer from user
	public static int getInteger()
    {
        int acceptableInteger=0;
        while(input.hasNext())
        {
            try
            {
            	acceptableInteger = Integer.parseInt( input.nextLine() );  	
            	if((acceptableInteger>=11) || (acceptableInteger<=-1))
                {
//                   System.out.println("Please Enter Number Between 0 and 10");
                   continue;
                }   	
            	return acceptableInteger;
            }
              
            catch (NumberFormatException e)
            {
//            	System.out.println("Please Enter Ancceptable Number");
            }           
        }
        
        return -1;
        }            
	//get list of cavities and pebbles from user as string 
    public static String getString()
    {
        String acceptableString="";
        while(true)
        {
        	acceptableString = input.next();
        	if(!(acceptableString.length()==23))
            {
//                    System.out.println("Please Enter exactly 23 characters");
            }
//            else if ( checkPebble(acceptableString) == false )
//				System.out.println("Input contains wrong characters");
//  				else 
        	else return acceptableString;
        }
    }
    // check if line of pebble has charachters other than 'o' and '-'
    public static boolean checkPebble (String s)
    {
    	for (int i=0; i < s.length(); i++)
    	{
    		//if ( !(s.charAt(i) == '-' || s.charAt(i) == 'o') )
    		if ( (s.charAt(i) != '-' && s.charAt(i) != 'o') )
    		{
    			System.out.println( s.charAt(i) );
    			return false;
    		}
    	}  	
    	return true;
    }
	
	public static void checkIfExists (String s)
	{
		if ( !checkedpositions.containsKey(s) )
    	{
			checkedpositions.put(s, true);
			queueOfPebbles.add(s); 
    	//	System.out.println(s);
    	}
	}
	// Generate all possible movements using Breadth First search solution
	public static boolean generateState(String boardLine)
	{
		boolean hasNextMovement = false;	
		for ( int i = 0; i < boardLine.length(); i++ ) 
		{
			//check is it possible for movement pebbles
	        if ( boardLine.charAt(i) == 'o' &&
	        (i + 1 < boardLine.length() && 
	        boardLine.charAt(i + 1) == 'o') ) 
	        {
	        	//check is it possible for pebbles to move from left to right
	            if ( i + 2 < boardLine.length() && boardLine.charAt(i + 2) == '-' ) 
	            {
	            	 String leftToRightMovement = generateWithRule1(boardLine, i);
	            	 checkIfExists ( leftToRightMovement );
	            	 hasNextMovement = true;
	            }
	          //check is it possible for pebbles to move from right to left
	            if ( i - 1 >= 0 && boardLine.charAt(i - 1) == '-' ) 
	            {
	            	String rightToLeftMovement = generateWithRule2(boardLine, i);
	            	checkIfExists ( rightToLeftMovement );
	            	hasNextMovement = true;
	            }
	        }
	    }
		return hasNextMovement;
	}
	
	static int countPebble (String s)
	{
	    int counter = 0;
	    for ( int i = 0; i < s.length(); i++ ) 
	    {
	        if ( s.charAt(i) == 'o' )
	            counter++;
	        
	    }
	    return counter;
	}
	//Sample for testing
	//01 =   "---oo------oo----------";
	//02 =   "-o--o-oo-----o--o-oo---";
	//03 ="-o----ooo----o----ooo--";
	//04 ="ooooooooooooooooooooooo";
	//05 ="oooooooooo-ooooooooooo-";
	
	public static void main(String[] args) 
	{
		int pebbleCount;
		String boardLine;
		int n;
	    n = getInteger();
         
        String[] list = new String[n];
     
        for(int i = 0; i<n; i++)
        {
             list[i] = getString();
        }
		
		for ( int i = 0; i < list.length; i++)
		{
			String input = list[i];
			pebbleCount = input.length();
			queueOfPebbles.add(input); 
	        checkedpositions.put(input, true); 
	 
	        while ( !queueOfPebbles.isEmpty() )
	        {
	        	boardLine = (String)queueOfPebbles.get(0);
	            queueOfPebbles.remove(0);
	          //  System.out.println("Current state: " + boardLine);
	 
	            //if ( !generateState (boardLine) ) 
	            if ( generateState (boardLine) == false )
	            {
	                int currentPebbleCount = countPebble (boardLine);
	                if ( currentPebbleCount < pebbleCount )
	                	pebbleCount = currentPebbleCount;
	            }
	        }
	 
	        System.out.println(pebbleCount);
	        
	        queueOfPebbles.clear();
	        checkedpositions.clear();
	    }
	}
}
