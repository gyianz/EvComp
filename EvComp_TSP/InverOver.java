package EvComp_TSP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Marcusljx on 8/18/2015.
 */
public class InverOver {

	public static Random randomGenerator = new Random(System.nanoTime());

	/**
	 * Prints the array of the ArrayList of Integer
	 * @param x The ArrayList of Integer
	 */
	public static void printArray(ArrayList<Integer> x) {
		for (Integer num : x) {
			System.out.print(num + " ");
		}
		System.out.println();
	}

	/**
	 * Checks whether two points are adjacent in the ArrayList of Integer
	 * @param Arr The ArrayList of Integer
	 * @param a The first point
	 * @param b The second point
	 * @return true if they are adjacent false otherwise
	 */
	public static boolean checkAdjacent(ArrayList<Integer> Arr, Integer a, Integer b) {
		int posA = Arr.indexOf(a);
		int posB = Arr.indexOf(b);

		if( Math.abs(posA - posB) == 1 ) {
			return true;
		}

		if(posA == Arr.size()-1) {
			if( (posB == 0) || (posB == posA - 1) ) {
				return true;
			}
		} else if(posB == Arr.size()-1) {
			if( (posA == 0) || (posA == posB - 1) ) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Inverse the region of points according to the Integer parsed in and Cprime
	 * @param tour	The ArrayList of Integer
	 * @param C		The point C / city
	 * @param Cprime	The point Cprime / the second city
	 */
	public static void invertRegion(ArrayList<Integer> tour, Integer C, Integer Cprime) {
		int pos1 = tour.indexOf(C);
		int pos2 = tour.indexOf(Cprime);
		// normalise order
		if (pos1 > pos2) {
			int temp = pos2;
			pos2 = pos1;
			pos1 = temp;
		}
		ArrayList<Integer> region = new ArrayList<>(tour.subList(pos1, pos2));
		Collections.reverse(region);
		int count = 0;
		for (int i = pos1; i < pos2; i++) {
			tour.set(i, region.get(count));
			count++;
		}
	}

	/**
	 * THIS VERSION RUNS ONE GENERATION ONLY. INCLUDE THIS FUNCTION IN A WHILE LOOP TO UTILISE TERMINATION-CONDITION
	 * The inverover algorithm
	 * @param P	The population 
	 * @param p	The p number to determine the cprime
	 * @return an ArrayList of Individual after one generation of inverover algorithm
	 */
	public static ArrayList<Individual> InverOver(Population P, double p) {
		int popsize = P.getPopulation().size();

		ArrayList<Individual> result = new ArrayList<>();

		for (int i = 0; i < P.getPopulation().size(); i++) {
			Individual Si = P.getPopulation().get(i).makeClone();
			Individual Sp = new Individual(new ArrayList<Integer>(Si.getTour()));
			ArrayList<Integer> Sprime = (ArrayList<Integer>) Sp.getTour().clone();
			int toursize = Sprime.size();
			Integer c = Sprime.get(randomGenerator.nextInt(toursize));
			Integer cprime;

			while (true) {
				// part 1: select cprime
				if (randomGenerator.nextDouble() <= p) {                       				//  if rand() <= p
					do {                                                            		//  select cprime from remaining in Sprime
						cprime = Sprime.get(randomGenerator.nextInt(toursize));
					} while (cprime == c);
				} else {                                                        			//  else

					// choose a random individual that isn't Si.
					ArrayList<Integer> randomIndiv = new ArrayList<>();
					do {
						randomIndiv = P.getPopulation().get(randomGenerator.nextInt(popsize)).getTour();
					} while ( Si.getTour().equals(randomIndiv) );

					//circular check!
					if (randomIndiv.indexOf(c) + 1 >= randomIndiv.size()) {        			//  if intended cprime value is out of bounds
						cprime = randomIndiv.get(0);                                		//  go back to beginning
					} else {                                                   				//  else
						cprime = randomIndiv.get(randomIndiv.indexOf(c) + 1);       		//  assign cprime to adjacent(+1) city to c in this individual
					}
				}

				// part 2: detecting adjacency of cprime to c ( break or inversion )

				if (checkAdjacent(Sprime, c, cprime)) {
					break;
				} else {
					invertRegion(Sprime, c, cprime);
					c = cprime;
				}
			}


			// part 3: comparing each offspring with its parent
			Individual newSp = new Individual(Sprime);
			if (newSp.getFitness() <= Si.getFitness()) {

				result.add(newSp);
			} else {
				result.add(Si);
			}
		}

		return result;
	}
}
