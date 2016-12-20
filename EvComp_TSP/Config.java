package EvComp_TSP;

import java.io.*;

/**
 * Config file parser implemented by Zi Yang Pang, tweaked by Xiaogang
 *
 * @author Zi Yang Pang
 */
public class Config {

    /**
     * InverOver flag.
     */
    public boolean inverOver;

    /**
     * Type of crossover operator.
     */
    public String crossoverType;

    /**
     * Type of mutation operator.
     */
    public String mutationType;

    /**
     * Problem file name. e.g. "eil51.tsp", "kroA100.tsp"
     */
    public String problemName;

    /**
     * Size of the population.
     */
    public int populationSize;

    /**
     * Number of generations to calculate.
     */
    public int generationNumber;

    /**
     * Type of parent selector. Use "elitism", "tournament" or
     * "fitness_proportional".
     */
    public String parentSelectorType;

    /**
     * K ratio of parent selector. Can be any value if not used by the selector.
     */
    public int parentSelectorKRatio;

    /**
     * Elite ratio of parent selector. Can be any value if not used by the
     * selector.
     */
    public double parentSelectorERatio;

    /**
     * Type of offspring selector.
     */
    public String offSpringSelectorType;

    /**
     * K ratio of offspring selector. Can be any value if not used by the
     * selector.
     */
    public int offSpringSelectorKRatio;

    /**
     * Elite ratio of offspring selector. Can be any value if not used by the
     * selector.
     */
    public double offSpringSelectorERatio;

    /**
     * Name of the Configuration file.
     */
    public String configFileName;

    /**
     * Report method. e.g. "terminal" or fileName
     */
    public String reportMethod;

    /**
     * Map of the TSP problem.
     */
    public static Map map;

    /**
     * Size of the map.
     */
    public int mapSize;

    /**
     * Parses the config file to determine the Evolutionary Algorithm
     * @param configFileName The file name of the config file
     */
    public Config(String configFileName) {
        this.configFileName = configFileName;
        try {
            BufferedReader br = new BufferedReader(new FileReader(configFileName));

            String line = br.readLine();
            String[] headerLine = line.split(" ");

            problemName = headerLine[0];
            populationSize = Integer.parseInt(headerLine[1]);
            generationNumber = Integer.parseInt(headerLine[2]);
            map = new Map(problemName);
            mapSize = map.getMapSize();

            line = br.readLine();
            if (line.contains("inverOver")) {
                inverOver = true;
            } else {
                inverOver = false;
                if (line.contains(",")) {
                    String[] parentSplit = line.split(",");
                    if (parentSplit[0].contains("tournament")) {
                        parentSelectorType = parentSplit[0];
                        parentSelectorKRatio = Integer.parseInt(parentSplit[1]);
                    } else {
                        parentSelectorType = parentSplit[0];
                        parentSelectorERatio = Double.parseDouble(parentSplit[1]);
                    }
                } else {
                    parentSelectorType = line;
                }

                crossoverType = br.readLine();
                mutationType = br.readLine();

                line = br.readLine();

                if (line.contains(",")) {
                    String[] childSplit = line.split(",");
                    if (childSplit[0].contains("tournament")) {
                        offSpringSelectorType = childSplit[0];
                        offSpringSelectorKRatio = Integer.parseInt(childSplit[1]);
                    } else {
                        offSpringSelectorType = childSplit[0];
                        offSpringSelectorERatio = Double.parseDouble(childSplit[1]);
                    }
                } else {
                    offSpringSelectorType = line;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

}
