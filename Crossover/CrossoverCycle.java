package Crossover;

import EvComp_TSP.Individual;
import java.util.*;

/**
 * Implemented by Marcus, integrated by Xiaogang.
 *
 * @author Marcus
 */
class CrossoverCycle implements CrossoverIntf {

	/**
	 * Takes in two Integer[] and copy from one to the other
	 * @param copyFrom  The first Integer[] that has all the data to copy
	 * @param copyTo 	The second Integer[] to contain all the copy
	 */
	private void copyNonNullValues(Integer[] copyFrom, Integer[] copyTo) {
		for (int i = 0; i < copyFrom.length; i++) {
			if (copyFrom[i] != null) {
				copyTo[i] = copyFrom[i];
			}
		}
	}
	/**
	 * Finds for a cycle beginning from the index.
	 * @param parents An ArrayList of Integer[]
	 * @param index The starting index
	 * @return ArrayList<Integer[]>
	 */
	private ArrayList<Integer[]> find_cycle(ArrayList<Integer[]> parents, int index) { 
		ArrayList<Integer[]> result = new ArrayList<>();
		Integer[] p1 = parents.get(0);
		Integer[] p2 = parents.get(1);

		int size = parents.get(0).length;
		Integer[] f1 = new Integer[size];
		Integer[] f2 = new Integer[size];

		f1[index] = p1[index];
		f2[index] = p2[index];
		int i = Arrays.asList(p1).indexOf(p2[index]);

		while (i != index) {
			f1[i] = p1[i];
			f2[i] = p2[i];
			i = Arrays.asList(p1).indexOf(p2[i]);
		}

		result.add(f1);
		result.add(f2);
		return result;
	}

	/**
	 * @param parent1 The first parent from the class Individual
	 * @param parent2 The second parent from the class Individual
	 * @return ArrayList<Individual> Returns an ArrayList of offspring produced by the parents
	 * Init children arrays
	 * Find cycles
	 * Put children into result
	 */
	@Override
	public ArrayList<Individual> crossover(Individual parent1, Individual parent2) {
		Integer[] p1 = extractIntegerArrayFromIndividual(parent1);
		Integer[] p2 = extractIntegerArrayFromIndividual(parent2);

		int size = p1.length;

		ArrayList<Individual> result = new ArrayList<>();
		Integer[] c1 = new Integer[size];
		Integer[] c2 = new Integer[size];

		ArrayList<Integer[]> container = new ArrayList<>();
		container.add(p1);
		container.add(p2);

		boolean flip = false;
		while (Arrays.asList(c1).contains(null)) {       			// while null numbers exists
			int index = Arrays.asList(c1).indexOf(null);
			ArrayList<Integer[]> cycle = find_cycle(container, index);

			if (!flip) {                               				// write each cycle alternately
				copyNonNullValues(cycle.get(0), c1);
				copyNonNullValues(cycle.get(1), c2);
			} else {
				copyNonNullValues(cycle.get(0), c2);
				copyNonNullValues(cycle.get(1), c1);
			}
			flip = !flip; 											// next round cycle will invert
		}

		Individual child1 = new Individual(c1);
		Individual child2 = new Individual(c2);
		result.add(child1);
		result.add(child2);
		return result;
	}

}
