
package Crossover;

/**
 *
 * @author Xiaogang
 */
public class CrossoverFactory {

	/**
	 * @param crossoverType The name of the crossover operator to be selected
	 * @return CrossoverIntf The crossover interface that will be used.
	 */
	public static CrossoverIntf getCrossover(String crossoverType) {
		crossoverType = crossoverType.toLowerCase();
		switch (crossoverType) {
		case "order":
			return new CrossoverOrder();
		case "pmx":
			return new CrossoverPMX();
		case "cycle":
			return new CrossoverCycle();
		case "edge":
			return new CrossoverEdgeRecomb();
		default:
			return null;
		}
	}

}
