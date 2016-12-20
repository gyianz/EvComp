/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Selector;

import EvComp_TSP.Individual;
import java.util.*;

/**
 *
 * @author Xiaogang
 */
public interface SelectorIntf {

    static Random randomGenerator = new Random(System.currentTimeMillis());

    /**
     * The interface to select which selector operator to use
     * @param curr_gen The ArrayList of individuals that are going through the selection process
     * @param howManyToSelect Decides how many to select from the population
     * @param kRatio the K ratio for tournament
     * @param eRatio the elite ratio for elitism
     * @return The list of selected individual based on the selector process
     */
    public ArrayList<Individual> selectFrom(ArrayList<Individual> curr_gen, int howManyToSelect, int kRatio, double eRatio);

}
