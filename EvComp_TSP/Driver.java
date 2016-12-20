package EvComp_TSP;

import java.util.concurrent.*;

/**
 *
 * @author Xiaogang
 */
public class Driver {

	/**
	 * @param args Command line arguments: configuration file name, X times to
	 * run, thread count.
	 */
	public static void main(String[] args) {
		if ((args.length < 3) || (args.length > 4)) {
			System.out.println("Usage: java EvComp.Driver ConfigurationFile X T PrintMethod");
			System.out.println("'X' specifies how many times to run this configuration.");
			System.out.println("'T' specifies how many threads to run at the same time.");
			System.out.println("PrintMethod should be either \"terminal\" or left blank.");
			System.out.println("The program will write to files if PrintMethod is left blank");
			System.exit(0);
		}

		int maxRun = Integer.parseInt(args[1]);
		int maxThread = Integer.parseInt(args[2]);

		Config config = new Config(args[0]);
		if ((args.length == 4) && (args[3].toLowerCase().equals("terminal"))) {
			config.reportMethod = "terminal";
		}
		ExecutorService executor = Executors.newFixedThreadPool(maxThread);
		for (int i = 0; i < maxRun; i++) {
			// rename output file
			if (args.length == 3) {
				int count = i + 1;
				config.reportMethod = config.configFileName.replace(".cfg", "_" + count + ".csv");
				config.reportMethod = config.reportMethod.replace("config", "output");
			}
			TSPProblem tsp = new TSPProblem(config);
			executor.execute(tsp);
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}

	}

}
