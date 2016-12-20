package Crossover;

import EvComp_TSP.Individual;
import java.util.*;

/**
 * Implemented by Marcus, integrated by Xiaogang.
 *
 * @author Marcus
 */
class CrossoverEdgeRecomb implements CrossoverIntf {

	private Random randomGenerator;

	/**
	 * Constructor for the class CrossoverEdgeRecomb. Creates a random generator based on the milli second time.
	 */
	public CrossoverEdgeRecomb() {
		this.randomGenerator = new Random(System.currentTimeMillis());
	}

	/**
	 * @param tour1 The tour list from the first parent
	 * @param tour2 The tour list from the second parent
	 * @return Integer[][] A 2d array of the adjacency matrix between the two parents
	 * Parse both tours and insert adjacency
	 * Find adjacent indices
	 * Increment adjacency for tour1
	 * Increment adjacency for tour2
	 */
	private Integer[][] build_adjacency_matrix(Integer[] tour1, Integer[] tour2) {
		int size = tour1.length;
		Integer[][] adjacency_Matrix = new Integer[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				adjacency_Matrix[i][j] = 0;
			}
		}

		for (int i = 0; i < size; i++) {

			int before = i - 1;
			int after = i + 1;
			if (before < 0) {
				before = size - 1;
			}
			if (after >= size) {
				after = 0;
			}
			adjacency_Matrix[tour1[i] - 1][tour1[before] - 1]++;
			adjacency_Matrix[tour1[i] - 1][tour1[after] - 1]++;

			adjacency_Matrix[tour2[i] - 1][tour2[before] - 1]++;
			adjacency_Matrix[tour2[i] - 1][tour2[after] - 1]++;
		}

		return adjacency_Matrix;
	}

	/**
	 * @param parent1 The first parent from the class Individual
	 * @param parent2 The second parent from the class Individual
	 * @return ArrayList<Individual> Returns an ArrayList of offspring produced by the parents
	 * Init Children array
	 * Create Adjacency Matrix
	 * Perform Recombination
	 * Put Children into result
	 */
	@Override
	public ArrayList<Individual> crossover(Individual parent1, Individual parent2) {
		Integer[] p1 = extractIntegerArrayFromIndividual(parent1);
		Integer[] p2 = extractIntegerArrayFromIndividual(parent2);
		int size = p1.length;

		ArrayList<Integer> child = new ArrayList<>();

		Integer[][] aMatrix = build_adjacency_matrix(p1, p2);

		ArrayList<Integer> remaining = (ArrayList<Integer>) parent1.getTour().clone();

		Integer element = randomGenerator.nextInt(size) + 1;   						// 1. Pick an initial element at random.
		remaining.remove(element);
		child.add(element);                                         				// 2. Insert element into offspring
		for (int i = 0; i < size; i++) {
			aMatrix[i][element - 1] = 0;                         					// 3. Remove all references to current element
		}

		while (child.size() < size) {
			Integer[] row = aMatrix[element - 1];                 					// 4. Examine list for current element
			int next_pos = Arrays.asList(row).indexOf(2);
			if (next_pos != -1) {                                					//      - if there's a common edge,
				element = next_pos + 1;                         					//          pick it
			} else {                                            					//      - pick element which has the shortest list
				for (int i = 0; i < size; i++) { 									// loop through row
					int next_length = size;
					if (row[i] != 0) {      										// if row item contains reference
						int this_length = 0;
						for (Integer m : aMatrix[i]) { 								// measure length of lists referenced in row
							if (m != 0) {
								this_length++;
							}
						}
						if (this_length < next_length) { 							// if is shorter than previous, use it for next
							next_pos = i;
							next_length = this_length;
						}
					}
				}   																// after looping through all elements in row(list), if all entries are zero(i.e. list is empty), select random element
				if (next_pos > -1) {
					element = (Integer) (next_pos + 1);     						// list was not empty
				} else {    														// list was empty, pick an element from those remaining.
					element = remaining.get(randomGenerator.nextInt(remaining.size()));
				}
			}
			remaining.remove(element);
			child.add(element);
			for (int i = 0; i < size; i++) {
				aMatrix[i][element - 1] = 0;                          				// Remove all references to current element
			}
		}

		ArrayList<Individual> result = new ArrayList<>();
		result.add(new Individual(child));
		return result;
	}

}
