import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class GoldbachConjecture {

public static HashMap<Integer, Integer> sumOfPrimes = new HashMap<Integer, Integer>();
public static ArrayList<Integer> listOfPrime = new ArrayList<Integer>();

	public static void main(String[] args) {
		int x = 100000; //Set value to what you want your list to prime number list to generate to 
		eratosthenes(x);	//Calculate list of primes
		System.out.println("Done Calculating Primes");
		for(int i = 4; i <= x; i = i+ 2) {
			twoSumsBinaryVersion(i); 	//Calculate all possible sums of two primes for an even number
		}
		System.out.println("Done Calculating All Possible Sums");		
		fileOutput();  //Output to file 
		System.out.println("Done Outputing to File");
	}
	/**
	 * This method calculates all the sum of all combinations of the sum of two primes for a given target. Enters 
	 * target and sum into a hashmap. Faster than using a hashmap since complexity can equals O(n*log(n))
	 * @param target
	 */
	public static void twoSumsBinaryVersion(int target) {
		int keyValue = 0; 
		int keyValueOfHalfTarget = 1; 
		int halfTarget = target/2;
		for(int i = 0; i < listOfPrime.size() && listOfPrime.get(i) < target; i++) {
			int complement = target - listOfPrime.get(i);
			int low = 0; 
			int high = listOfPrime.size() - 1;
			if(halfTarget == listOfPrime.get(i)) {
				keyValue = keyValue + keyValueOfHalfTarget;
	    		keyValueOfHalfTarget = 0; 
	    		sumOfPrimes.put(target, keyValue);
	    	}
			while(high >=low && listOfPrime.get(i) < complement) {
				int middle = (low + high) / 2; 	   
				if(listOfPrime.get(middle) == complement) {
					keyValue = keyValue + 1;
		    		sumOfPrimes.put(target, keyValue);
		    		break;
		       }
		       if(listOfPrime.get(middle) < complement) {
		    	   low = middle + 1;
		       }
		       if(listOfPrime.get(middle) > complement) {
		    	   high = middle - 1;
		       }
			}
			
		}
	}
	/**
	 * This method calculates all the sum of all combinations of the sum of two primes for a given target. Enters 
	 * target and sum into a hashmap. However, this is slower than binary search since complexity can equal O(n^2)
	 * @param target
	 */
	public static void twoSumsHashMapVersion(int target) {
		    HashMap<Integer, Integer> map = new HashMap<>();
		    int keyValue = 0; 
		    
		    if(listOfPrime.contains(target/2)) {
		    	keyValue = 1; 
		    } 
		    for (int i = 0; i < listOfPrime.size() && i <= target; i++) {
		        int complement = target - listOfPrime.get(i);
		        if (map.containsKey(complement)) {
		        	
		        	keyValue = keyValue + 1; 
		        	sumOfPrimes.put(target, keyValue);
		        }
		        else {
		        	map.put(listOfPrime.get(i), i);
		        	sumOfPrimes.put(target, keyValue);
		        }
		    }  
	}
	/**
	 * This method is the famous seive of Eratosthenes algorithm. It determines if a number is prime, and if it is, 
	 * adds it to the list of primes ArrayList. Complexity of this algorithm is O(n(logn)(loglogn)) 
	 * @param max - determines all the primes until this number
	 * @return - nothing
	 */
	public static void eratosthenes(int max) {     
		int sieveSize = max + 1;                 
	    boolean[] list = new boolean[sieveSize];
	    Arrays.fill(list,true);             // Assume the entire list is fill of primes  
	    list[0] = list[1] = false;          // However, 0 and 1 are not primes so set to false 
	    for (long i = 2; i < sieveSize; i++)
	    	if (list[(int)i]) {	
	    		for (long j = i * i; j < sieveSize; j += i) {
	    			list[(int)j] = false; // i is a prime but multiples of i are not. Set those to false
	    	}
	        listOfPrime.add((int)i);  // Add i to are list since it is a prime
	      }   
	  }	
	/**
	 * This method also calculates if a given number is Prime however, it is not as fast as 
	 * sieve of Eratosthenes 
	 * @param n - any number
	 * @return - nothing 
	 */
	
    public static boolean isPrime(int n) {
        int i;
        for (i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
    /**
     * This method outputs the sumOfPrimes Hashmap to a text file
     */
    public static void fileOutput() {
		String fileName = "GoldbachConjecture.txt";
		try {
			PrintWriter outputStream = new PrintWriter(fileName);
			outputStream.printf("%10s\t%10s\n","Even Numbers","Number of Combinations");
			
			for (Integer key : sumOfPrimes.keySet()) {
			    outputStream.printf("%-14d\t%-10d\n",key,sumOfPrimes.get(key));
			    outputStream.flush();
			}
			outputStream.close();			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}


