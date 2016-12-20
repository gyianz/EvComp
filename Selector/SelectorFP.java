package Selector;

import EvComp_TSP.Individual;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Implemented by Marcus, integrated by Xiaogang.
 *
 * @author Marcus
 */
class SelectorFP implements SelectorIntf {

	/**
	 * Calculates the fitness value of each of the individual in the population
	 * @param pop The population to be calculated
	 * @return The double array of all the fitness for each individual
	 */
    private Double[] calcAllFitnessValues(ArrayList<Individual> pop) {
        Double[] fitness_values = new Double[pop.size()];
        for (int i = 0; i < pop.size(); i++) {
            fitness_values[i] = pop.get(i).getFitness();
        }
        return fitness_values;
    }
    
    /**
     * Simplifies an array of values to a smaller values but with (almost) similar ratio to each other (within 1 order of magnitude)
     * @param values
     * @return
     */
    public Double[] downSimplifyProportions(Double[] values) {
        //normalise all numbers to 0-1 range, then multiply by 100.
        int size = values.length;
        Double[] copy = values.clone();
        Arrays.sort(copy);
        Double max = copy[size-1];
        Double min = copy[0];

        Double[] Z = new Double[size];
        for(int i=0; i<size; i++) {
            Z[i] = 1.0 + (10.0 * ((values[i]-min) / (max - min)));
        }
        return Z;
    }

    /**
     * Finds the proportion of fitness within a population
     * @param fitness_values The double array that contains all the fitness for each individual in the population
     * @return The double array that has the fitness proportion
     */
    private Double[] findProportionsForLowerIsBetterValues(Double[] fitness_values) {
        Double[] prop_values = new Double[fitness_values.length];
        // find max and min values
        Double max = 0.0;
        Double min = fitness_values[0];
        for (Double dd : fitness_values) {
            if (dd < min) {
                min = dd;
            } else if (dd > max) {
                max = dd;
            }
        }

        // invert lower-is-better values across local space
        for (int i = 0; i < fitness_values.length; i++) {
            prop_values[i] = (max + min) - fitness_values[i];
        }

        Double[] normalised_prop_values = downSimplifyProportions(prop_values);

        return normalised_prop_values;
    }

    /**
     * Select from the population based on the fitness proportion
     */
    @Override
    public ArrayList<Individual> selectFrom(ArrayList<Individual> curr_gen, int howManyToSelect, int kRatio, double eRatio) {
        ArrayList<Individual> next_gen = new ArrayList<>();
        Double[] fitness_values = calcAllFitnessValues(curr_gen);

        // Invert fitness values and find proportions for roulette wheel
        Double[] proportional_values = findProportionsForLowerIsBetterValues(fitness_values);

        // Create fitness roulette wheel and fill with proportion levels.
        ArrayList<Individual> wheel = (ArrayList<Individual>)curr_gen.clone();
        for (int i = 0; i < proportional_values.length; i++) {     	// for each proportion number fi
            double fi = proportional_values[i];
            for (int j = 0; j < fi; j++) {                         	// place corresponding Individual fi-times into wheel
                wheel.add(curr_gen.get(i));
            }
        }															// each Individual occupies fi/sum(fi) space on the wheel

        for (int i = 0; i < howManyToSelect; i++) {
            Individual nextToAdd = wheel.get(randomGenerator.nextInt(wheel.size()));
            next_gen.add(nextToAdd);
        }

        return next_gen;
    }

}
