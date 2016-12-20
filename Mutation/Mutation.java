package Mutation;

import EvComp_TSP.Individual;
import java.util.*;

/**
 *
 * @author Xiaogang
 */
public class Mutation {

    private static final String[] operations = {"insert", "swap", "inversion", "scramble"};
    private static MutationIntf operator;
    private static ArrayList<MutationIntf> operators;
    private static boolean randomMode;

    /**
     * Set the Mutation Operator to be used. Currently supports "insert",
     * "swap", "inversion", "scramble" and "random".
     *
     * @param operatorType Type of mutation operator.
     */
    public static void setOperator(String operatorType) {
        operatorType = operatorType.toLowerCase();
        switch (operatorType) {
            case "random":
                operator = null;
                operators = new ArrayList<MutationIntf>();
                for (String str : operations) {
                    operators.add(MutationFactory.getMutation(str));
                }
                randomMode = true;
                break;
            default:
                operators = null;
                operator = MutationFactory.getMutation(operatorType);
                randomMode = false;
        }
    }
    
    /**
     * Takes in an individual to go through the mutation process and returns the mutated individual 
     * @param individual The selected individual to be mutated
     * @return The mutated individual
     */
    public static Individual doMutation(Individual individual) {
        Individual result;
        if (randomMode) {
            Random r = new Random(System.currentTimeMillis());
            result = operators.get(r.nextInt(operations.length)).mutate(individual);
        } else {
            result = operator.mutate(individual);
        }
        return result;
    }

}
