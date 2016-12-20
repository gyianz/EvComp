/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mutation;

import EvComp_TSP.Individual;
import java.util.Random;

/**
 * The mutator interface
 * @author Xiaogang
 */
public interface MutationIntf {

    public Individual mutate(Individual individual);

    static Random RNG = new Random(System.currentTimeMillis());
    
    /**
     * Selects two points in the individual
     * @param bound The limit in the index to be selected
     * @return int[] of points to be mutated.
     */
    static int[] picktwo(int bound) {
        int[] result = new int[2];
        int index1 = RNG.nextInt(bound);
        int index2 = index1;
        // possibility to get into infinite loop is ignored
        // as a streak breaker will introduce artificial number
        while (index1 == index2) {
            index2 = RNG.nextInt(bound);
        }
        result[0] = index1;
        result[1] = index2;
        return result;
    }

}
