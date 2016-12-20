package Selector;

/**
 *
 * @author Xiaogang
 */
public class SelectorFactory {

	/**
	 * The selector factory that takes in a string to select the selector operator
	 * @param selectorType The string that determines the selector operator
	 * @return The interface of the selector operator
	 */
	static public SelectorIntf getSelector(String selectorType) {
		selectorType = selectorType.toLowerCase();
		switch (selectorType) {
		case "elitism":
			return new SelectorElitism();
		case "tournament":
			return new SelectorTournament();
		case "fitness_proportional":
			return new SelectorFP();
		default:
			return null;
		}
	}

}
