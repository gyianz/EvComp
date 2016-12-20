package EvComp_TSP;

import java.util.ArrayList;
import Crossover.*;
import Mutation.*;
import Selector.*;

public class TSPProblem extends Thread {

	private final Population population;
	private final Config config;
	private final Reporter reporter;

	/**
	 * Initialize a single TSP Problem with the configuration file input.
	 * @param config The configuration class object that has been initialized.
	 */
	public TSPProblem(Config config) {
		this.config = config;
		reporter = new Reporter(config.reportMethod);

		// Initializes the population
		population = Population.getInitialPopulation(config.populationSize, config.mapSize);
	}


	/**
	 * Prints the tour of an individual
	 * @param List An ArrayList of Individual
	 */
	public void printArrayList(ArrayList<Individual> List) {
		for (Individual indiv : List) {
			indiv.printTour();
		}
	}

	/**
	 * Returns the population after the nth generation. Throws Exception when
	 * the current generation number is larger than n.
	 *
	 * @return Final generation.
	 */
	public Population solveTSP() {
		Individual currentBest;
		Individual oldBest = Individual.getNewIndividual(config.mapSize);  // random
		int same_counter = 0;
		reporter.printCSVHeader();
		int generation_cutoff = 1000;
		while ((population.getGen() < config.generationNumber) && (same_counter < generation_cutoff)) {
			ArrayList<Individual> offsprings = new ArrayList<Individual>();

			// Applies crossover for each pair of parents selected from the population.
			CrossoverIntf crossoverOperator = CrossoverFactory.getCrossover(config.crossoverType);
			SelectorIntf parentSelector = SelectorFactory.getSelector(config.parentSelectorType);

			Population parentTempPool = population.clone();
			for (int i = 0; i < config.populationSize; i += 2) {
				ArrayList<Individual> parents = parentSelector.selectFrom(parentTempPool.getPopulation(),
						2 , config.parentSelectorKRatio, config.parentSelectorERatio);

				for (Individual p : parents) {
					parentTempPool.removeIndividual(p);
				}

				offsprings.addAll(crossoverOperator.crossover(parents.get(0), parents.get(1)));
			}

			MutationIntf mutationOperator = MutationFactory.getMutation(config.mutationType);
			ArrayList<Individual> afterMutation = new ArrayList<>();
			for (Individual offspring : offsprings) {
				afterMutation.add(mutationOperator.mutate(offspring));
			}

			afterMutation.addAll(population.getPopulation());

			SelectorIntf offspringSelector = SelectorFactory.getSelector(config.offSpringSelectorType);
			ArrayList<Individual> nextPopulation = offspringSelector.selectFrom(afterMutation,
					config.populationSize, config.offSpringSelectorKRatio, config.offSpringSelectorERatio);

			population.updatePopulation(nextPopulation);
			// Get best individual of current generation.
			currentBest = population.getBestIndividual();

			if (population.getGen() == 5000 || population.getGen() == 10000 || population.getGen() == 20000){
				reporter.report(population);
			}

			if (!currentBest.equals(oldBest)) {
				oldBest = currentBest;
				same_counter = 0;
			} else {
				same_counter++;
			}

		}

		// results
		if (same_counter >= generation_cutoff) {
			System.out.println("iteration cut off after > " + generation_cutoff + " repeats of best-individual-in-generation.");
			reporter.report(population);
		}

		return population;
	}

	// variant of solve function for inverOver
	public Population solveInverOver() {
		int repeat_counter = 0;
		int repeat_limit = 10;
		Population P = Population.getInitialPopulation(config.populationSize, config.mapSize);
		Individual best = P.getBestIndividual();

		int counter=0;
		while (P.getGen() <= config.generationNumber) {
			counter++;
			P.updatePopulation(InverOver.InverOver(P, 0.02));

			if (counter >= config.generationNumber) {
				reporter.report(population);
				break;
			}

			Individual current_best = P.getBestIndividual();
			if (current_best.equals(best)) {
				repeat_counter++;
			} else {
				best = current_best;
				repeat_counter = 0;
			}
		}

		return P;
	}

	@Override
	public void run() {
		solve();
	}

	void solve() {
		if (config.inverOver) {
			solveInverOver();
		} else {
			solveTSP();
		}
		reporter.reportTimeElapsed();
	}
}
