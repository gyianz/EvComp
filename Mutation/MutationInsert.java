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
class MutationInsert implements MutationIntf {

	/**
	 * Takes an individual to go through the insert mutation
	 * @param individual The selected individual to be mutated
	 */
    @Override
    public Individual mutate(Individual individual) {
        // make a copy of individual, so the original individual can be unchanged
        Individual result = individual.makeClone();
        int size = individual.getTour().size();
        int[] allele = MutationIntf.picktwo(size);

        // calculate stop index
        int index = allele[1];
        int temp = individual.getTour().get(index);
        int stop = (allele[0] + 1) % size;

        // pull elements backwards
        int sub;
        while (index != stop) {
            sub = (index + size - 1) % size;
            result.getTour().set(index, individual.getTour().get(sub));
            index--;
            index += size;
            index %= size;
        }
        result.getTour().set(stop, temp);
        result.calculateFitness();
        return result;
    }

}
