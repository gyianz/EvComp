/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mutation;

import EvComp_TSP.Individual;

/**
 *
 * @author Xiaogang
 */
public class MutationSwap implements MutationIntf {

	/**
	 * Selects an individual to undergo the swap mutation process
	 * @param individual The selected individual to be mutated
	 */
    @Override
    public Individual mutate(Individual individual) {
        // make a copy of individual, so the original individual can be unchanged
        Individual result = individual.makeClone();
        int size = individual.getTour().size();
        int[] allele = MutationIntf.picktwo(size);

        // swap two selected elements
        result.swap(allele[0], allele[1]);
        result.calculateFitness();

        return result;
    }

}
