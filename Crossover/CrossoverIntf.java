
package Crossover;

import EvComp_TSP.Individual;
import java.util.ArrayList;

/**
 *
 * @author Xiaogang
 */
public interface CrossoverIntf {

	/**
	 * @param x Of class Individual
	 * @return Integer[] array that has all the tour from the parameter x
	 */

	default Integer[] extractIntegerArrayFromIndividual(Individual x) {
		Integer[] result = x.getTour().toArray(new Integer[x.getTour().size()]);
		return result;
	}

	/**
	 * @param parent1 First parent from the class Individual
	 * @param parent2 Second parent from the class Individual
	 * @return ArrayList<Individual> The ArrayList of the offspring by the parents
	 */
	public ArrayList<Individual> crossover(Individual parent1, Individual parent2);

}
