package Crossover;

import EvComp_TSP.Individual;
import java.util.*;

/**
 * Implemented by Marcus, integrated by Xiaogang.
 *
 * @author Marcus
 */
class CrossoverPMX implements CrossoverIntf {

	private Random randomGenerator = new Random();

	/**
	 * Constructor for PMX crossover. Creates a random generator based on the milli seconds
	 */
	public CrossoverPMX() {
		this.randomGenerator = new Random(System.currentTimeMillis());
	}

	/**
	 * 
	 * @param parent1 First parent from the Individual class
	 * @param parent2 Second parent from the Individual class
	 * @return ArrayList<Individual> Returns an ArrayList of the offspring produced by the parents
	 * Init Children arrays
	 * Generate random region positions then ensures that a region will always be selected.
	 * Stage1: Extract Region from Parent1 into C1, Extract Region from Parent2 into C2
	 * Stage2: Find items in crossover region C2 position in C1
	 * Stage3: Fill in remaining numbers P1->C2, P2->C1
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

		Integer[] subP2 = Arrays.copyOfRange(p2, region_begin, region_end);
		System.arraycopy(subP2, 0, c2, region_begin, subP2.length);

		for (int i = region_begin; i < region_end; i++) {   
			Integer num_to_insert = c2[i];

			// Do for this number only if number does not already exists in C1
			if (!Arrays.asList(c1).contains(num_to_insert)) {
				int k = Arrays.asList(p2).indexOf(c1[i]);   					// find (C1[i] in P2) ->position k
				while ((k >= region_begin) && (k < region_end)) {
					k = Arrays.asList(p2).indexOf(c1[k]);   					// bounce k until it goes out of stage1 region
				}
				c1[k] = num_to_insert;                      					// put num_to_insert into C1[k]
			}
		}

		for (int i = region_begin; i < region_end; i++) {   					// for each position in crossover region
			Integer num_to_insert = c1[i];

			// Do for this number only if number does not already exists in C2
			if (!Arrays.asList(c2).contains(num_to_insert)) {
				int k = Arrays.asList(p1).indexOf(c2[i]);   					// find (C2[i] in P1) ->position k
				while ((k >= region_begin) && (k < region_end)) {
					k = Arrays.asList(p1).indexOf(c2[k]);   					// bounce k until it goes out of stage1 region
				}
				c2[k] = num_to_insert;                      					// put num_to_insert into C2[k]
			}	
		}

		for (int i = 0; i < size; i++) {
			if (c1[i] == null) {
				c1[i] = p2[i];
			}
			if (c2[i] == null) {
				c2[i] = p1[i];
			}
		}

		// Put Children into result
		Individual child1 = new Individual(c1);
		Individual child2 = new Individual(c2);
		result.add(child1);
		result.add(child2);
		return result;
	}

}
