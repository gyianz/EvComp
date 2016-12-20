package EvComp_TSP;

import java.io.*;

/**
 * Reports statistical data of the algorithm.
 *
 * @author Xiaogang
 */
public class Reporter {

	/**
	 * Print out the first line of CSV file.
	 */
	public void printCSVHeader() {
		output.println("Generation Count,Average Fitness,Standard Deviation,Median Fitness,Interquartile Range,Best Fitness,Best Individual");
		output.flush();
	}

	private long startTime;
	private PrintWriter output;

	/**
	 * Creates a reporter that prints statistical data of the population.
	 *
	 * @param method Use "terminal" to print to terminal, otherwise specify the
	 * file name to print to.
	 */
	public Reporter(String method) {
		startTime = System.currentTimeMillis();
		method = method.toLowerCase();
		switch (method) {
		case "terminal":
			output = new PrintWriter(System.out);
			break;
		default: {
			try {
				output = new PrintWriter(method);
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
		}
	}

	/**
	 * Reports the time elapsed for the current Evolutionary Algorithm
	 */
	public void reportTimeElapsed() {
		output.print("Time elapsed: ");
		output.format("%.2f", (double) (System.currentTimeMillis() - startTime) / 1000);
		output.print(" s");
		output.println();
		output.flush();
	}

	/**
	 * Write verbose info about given population. It writes directly to
	 * System.out in the format of a line of a CSV file, in the order of:
	 * Generation count, average fitness, standard deviation of fitness, median
	 * fitness, interquartile range of fitness, fitness of the best individual,
	 * best individual("waypoint1 waypoint2 waypoint3...").
	 *
	 * @param population Population to report.
	 */
	public void report(Population population) {
		population.doStatistics();

		output.print(population.getGen());
		output.print(",");
		output.format("%.2f", population.getMean());
		output.print(",");
		output.format("%.2f", population.getStdDev());
		output.print(",");
		output.format("%.2f", population.getMedian());
		output.print(",");
		output.format("%.2f", population.getIQR());
		output.print(",");
		Individual best = population.getBestIndividual();
		output.print(best.toString());

		output.println();
		output.flush();
	}

}
