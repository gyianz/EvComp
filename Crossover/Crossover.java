package Crossover;

import EvComp_TSP.Individual;
import java.util.*;

/**
 * @author Norm
 * @class Crossover 
 */
public class Crossover {

	private static final String[] operations = {"order", "pmx", "cycle", "edge"};
	private static CrossoverIntf operator;
	private static ArrayList<CrossoverIntf> operators;
	private static boolean randomMode;

	/**
	 * Set the Crossover Operator to be used. 
	 * Currently supports "order", "pmx","cycle", "edge" and "random".
	 * @param operatorType Type of operator.
	 * 
	 */
	public static void setOperator(String operatorType) {
		operatorType = operatorType.toLowerCase();
		switch (operatorType) {
		case "random":
			operator = null;
			operators = new ArrayList<CrossoverIntf>();
			for (String str : operations) {
				operators.add(CrossoverFactory.getCrossover(str));
			}
			randomMode = true;
			break;
		default:
			operators = null;
			operator = CrossoverFactory.getCrossover(operatorType);
			randomMode = false;
		}
	}

	/**
	 * Takes in two parent of Individual class to begin the crossover function
	 * 
	 * @param parent1  The first parent
	 * @param parent2  The second parent
	 * @return an ArrayList<Individual> that contains the offspring of the two parents
	 */

	public static ArrayList<Individual> doCrossover(Individual parent1, Individual parent2) {
		ArrayList<Individual> result;
		if (randomMode) {
			Random r = new Random(System.currentTimeMillis());
			result = operators.get(r.nextInt(operations.length)).crossover(parent1, parent2);
		} else {
			result = operator.crossover(parent1, parent2);
		}
		return result;
	}

}
