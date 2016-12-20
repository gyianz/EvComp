/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mutation;

/**
 *
 * @author Xiaogang
 */
public class MutationFactory {

	/**
	 * Selects a mutator operator based on the input String
	 * @param mutationType The mutator that is to be selected
	 * @return the Mutation interface to be used
	 */
    static public MutationIntf getMutation(String mutationType) {
        mutationType = mutationType.toLowerCase();
        switch (mutationType) {
            case "insert":
                return new MutationInsert();
            case "swap":
                return new MutationSwap();
            case "inversion":
                return new MutationInversion();
            case "scramble":
                return new MutationScramble();
            default:
                return null;
        }
    }

}
