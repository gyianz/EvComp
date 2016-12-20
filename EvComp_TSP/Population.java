package EvComp_TSP;

import java.util.ArrayList;
import java.util.Arrays;

public class Population {

	private ArrayList<Individual> population;
	private double mean;
	private double variance;
	private double std_dev;
	private double median;
	private double IQR;
	private int gen;


	/**
	 * Prints the tour of the particular individual
	 */
	public void printPopulation() {
		for (Individual indiv : population) {
			indiv.printTour();
		}
	}

	/**
	 * Constructor of the Population class
	 * @param population An ArrayList of Individuals
	 */
	public Population(ArrayList<Individual> population) {
		this.population = population;
		gen = 0;
	}

	/**
	 * Generates an initial population with random individuals.
	 *
	 * @param populationSize Size of population.
	 * @param mapSize Size of map, i.e. length of an individual.
	 * @return Generated population.
	 */
	public static Population getInitialPopulation(int populationSize, int mapSize) {
		ArrayList<Individual> initialPopulation = new ArrayList<Individual>();

		for (int i = 0; i < populationSize; i++) {
			initialPopulation.add(Individual.getNewIndividual(mapSize));
		}
		return new Population(initialPopulation);
	}

	/**
	 * Updates the current population with offsprings and increase the generation count
	 * @param offsprings An ArrayList of offsprings
	 */
	public void updatePopulation(ArrayList<Individual> offsprings) {
		population = offsprings;
		gen++;
	}

	/**
	 * Returns the current population
	 * @return ArrayList<Individual> An ArrayList of all the Individual of the current population
	 */
	public ArrayList<Individual> getPopulation() {
		return population;
	}

	/**
	 * Get the generation count of the population.
	 *
	 * @return Generation count.
	 */
	public int getGen() {
		return gen;
	}

	/**
	 * Get the best individual from the current population
	 * @return Individual of the best fitness
	 */
	public Individual getBestIndividual() {
		Individual best = new Individual(population.get(0).getTour());
		double currentBestFitness = best.getFitness();
		for (Individual I : population) {
			if (I.getFitness() < currentBestFitness) {
				best = I;
				currentBestFitness = I.getFitness();
			}
		}
		return best;
	}

	/**
	 * Creates a copy of the population.
	 */
	public Population clone() {
		return new Population(new ArrayList<Individual>(population));
	}

	/**
	 * Removes an individual from the population
	 * @param I The individual to be removed
	 */
	public void removeIndividual(Individual I) {
		if (population.contains(I)) {
			population.remove(I);
		}
	}

	/**
	 * Gets the standard deviation of the population
	 * @return standard deviation of the population
	 */
	public double getStdDev() {
		return std_dev;
	}

	/**
	 * Gets the mean of the population
	 * @return mean of the population
	 */
	public double getMean() {
		return mean;
	}

	/**
	 * Gets the median of the population
	 * @return median of the population
	 */
	public double getMedian() {
		return median;
	}

	/**
	 * Gets the interquartile range of the population
	 * @return interquartile range of the population
	 */
	public double getIQR() {
		return IQR;
	}

	/**
	 * Calculates the statistics for the whole population
	 */
	public void doStatistics() {
		double sum = 0;
		double[] individuals = new double[population.size()];
		int index = 0;
		for (Individual i : population) {
			sum += i.getFitness();
			individuals[index] = i.getFitness();
			index++;
		}
		mean = sum / population.size();
		variance = 0;
		for (Individual i : population) {
			variance += Math.pow(i.getFitness() - mean, 2);
		}
		variance /= population.size();
		std_dev = Math.sqrt(variance);

		// works for both odd and even size of population, although not necessary
		Arrays.sort(individuals);
		median = (individuals[(int) Math.ceil((double) (population.size() - 1) / 2)] + individuals[(int) Math.floor((double) (population.size() - 1) / 2)]) / 2;

		// *DOES NOT* work for population size <= 3, but it is OK to ignore those cases
		double iqr1 = (individuals[(int) Math.ceil(population.size() * 0.25)] + individuals[(int) Math.floor(population.size() * 0.25)]) / 2;
		double iqr3 = (individuals[(int) Math.ceil(population.size() * 0.75)] + individuals[(int) Math.floor(population.size() * 0.75)]) / 2;
		IQR = iqr3 - iqr1;
	}

}
