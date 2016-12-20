
package Crossover;

import EvComp_TSP.Individual;
import java.util.*;

/**
 * Implemented by Marcus, integrated by Xiaogang.
 *
 * @author Marcus
 */
class CrossoverOrder implements CrossoverIntf {

	private Random randomGenerator = new Random();

	/**
	 * Constructor for the CrossoverOrder class. Generates a random generator based on the milli seconds of the time
	 */
	public CrossoverOrder() {
		this.randomGenerator = new Random(System.currentTimeMillis());
	}

	/**
	 * @param parent1 First parent from the Individual class
	 * @param parent2 Second parent from the Individual class
	 * @return ArrayList<Individual> Returns an ArrayList of offspring produced by the parents.
	 * Init Children arrays
	 * Generate random region positions and ensures that a region will always be selected.
	 * C1 Handling
	 * Extract Region from Parent1 into C1
	 * Fill numbers from region_end of Parent2 into C1
	 * C2 Handling
	 * Extract Region from Parent2 into C2
	 * Fill numbers from region_end of Parent1 into C2
	 * Put Children into result
	 */

	@Override
	public ArrayList<Individual> crossover(Individual parent1, Individual parent2) {
		Integer[] p1 = extractIntegerArrayFromIndividual(parent1);
		Integer[] p2 = extractIntegerArrayFromIndividual(parent2);

		int size = p1.length;

		ArrayList<Individual> result = new ArrayList<>();
		Integer[] c1 = new Integer[size];
		Integer[] c2 = new Integer[size];

		Integer region_begin = randomGenerator.nextInt(size - 1);         
		Integer region_end = region_begin + randomGenerator.nextInt(size - region_begin - 1) + 1;  
		Integer[] subP1 = Arrays.copyOfRange(p1, region_begin, region_end);
		System.arraycopy(subP1, 0, c1, region_begin, subP1.length);

		Integer i = region_end;
		Integer j = region_end;
		while (Arrays.asList(c1).contains(null)) {   
			Integer temp = p2[j];
			while (Arrays.asList(c1).contains(temp)) {   
				j++;
				if (j >= size) {
					j = 0;
				}
				temp = p2[j];
			}
			c1[i] = temp;

			i++;
			if (i >= size) {
				i = 0;
			}
			j++;
			if (j >= size) {
				j = 0;
			}
		}

		Integer[] subP2 = Arrays.copyOfRange(p2, region_begin, region_end);
		System.arraycopy(subP2, 0, c2, region_begin, subP2.length);


		i = region_end;
		j = region_end;
		while (Arrays.asList(c2).contains(null)) {   				// while C2 has empty spots
			Integer temp = p1[j];

			while (Arrays.asList(c2).contains(temp)) {   			// while C2 contains number Parent1.getTour()[j]
				j++;
				if (j >= size) {
					j = 0;
				}
				temp = p1[j];
			}
			c2[i] = temp;

			// Increment step in C2, if at end, go back to beginning
			i++;
			if (i >= size) {
				i = 0;
			}
			// Increment step in Parent1, if at end, go back to beginning
			j++;
			if (j >= size) {
				j = 0;
			}
		}

		Individual child1 = new Individual(c1);
		Individual child2 = new Individual(c2);
		result.add(child1);
		result.add(child2);
		return result;
	}

}
