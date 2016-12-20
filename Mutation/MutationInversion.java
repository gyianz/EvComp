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
public class MutationInversion implements MutationIntf {

	/**
	 * Takes in an individual and mutate it through inversion
	 * @param individual The selected Individual to be mutated
	 */
    @Override
    public Individual mutate(Individual individual) {
        // make a copy of individual, so the original individual can be unchanged
        Individual result = individual.makeClone();
        int size = individual.getTour().size();
        int[] allele = MutationIntf.picktwo(size);

        // calculate the length of the sequence to be swapped then halve it
        int length;
        int head = allele[0];
        int tail = allele[1];
        if (head < tail) {
            length = tail - head;
        } else {
            length = size - head + tail;
        }
        length++;
        length /= 2;

        for (int i = 0; i < length; i++) {
            result.swap((head + i) % size, (tail + size - i) % size);
        }
        result.calculateFitness();

        return result;
    }

}
