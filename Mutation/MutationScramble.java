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
public class MutationScramble implements MutationIntf {

	/**
	 * Takes in an individual and mutate it through scramble
	 * @param individual The selected individual to mutate
	 */
    @Override
    public Individual mutate(Individual individual) {
        // make a copy of individual, so the original individual can be unchanged
        Individual result = individual.makeClone();
        int size = individual.getTour().size();

        for (int i = 0; i < size * 0.25; i++) {
            int[] allele = MutationIntf.picktwo(size);
            result.swap(allele[0], allele[1]);
        }
        result.calculateFitness();

        return result;
    }

}
