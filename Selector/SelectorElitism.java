package Selector;

import EvComp_TSP.Individual;
import java.util.ArrayList;

/**
 * Implemented by Marcus, integrated by Xiaogang.
 *
 * @author Marcus
 */
class SelectorElitism implements SelectorIntf {

    //======================= ELITISM
    private ArrayList<Individual> sortIndividualList(ArrayList<Individual> input) {
        ArrayList<Individual> copy = (ArrayList<Individual>) input.clone();
        ArrayList<Individual> output = new ArrayList<>();

        while (copy.size() > 0) {
            Double min_fitness = Double.MAX_VALUE;
            ArrayList<Integer> temp = new ArrayList<>();
            int temp_pos = 0;
            for (int i = 0; i < copy.size(); i++) {
                Double f = copy.get(i).getFitness();
                if (f < min_fitness) {
                    temp = (ArrayList<Integer>) copy.get(i).getTour().clone();
                    temp_pos = i;
                    min_fitness = f;
                }
            }
            output.add(new Individual(temp));
            copy.remove(temp_pos);
        }
        return output;
    }

    // NOTE: eliteRatio must be between [0.0 - 1.0]. The actual number of elites chosen will be floor(eliteRatio * howManyToSelect)
    @Override
    public ArrayList<Individual> selectFrom(ArrayList<Individual> curr_gen, int howManyToSelect, int kRatio, double eliteRatio) {
        ArrayList<Individual> t_curr_gen = (ArrayList<Individual>) curr_gen.clone();
        ArrayList<Individual> next_gen = new ArrayList<>();

        // sort t_curr_gen (lowest fitness to highest)
        ArrayList<Individual> sorted_curr_gen = sortIndividualList(t_curr_gen);

        // pick from front of sorted_curr_gen, based on eliteRatio
        int eliteGroupSize = (int) (eliteRatio * howManyToSelect);

        for (int i = 0; i < eliteGroupSize; i++) {
            next_gen.add(new Individual(sorted_curr_gen.get(0).getTour()));
            sorted_curr_gen.remove(0);
        }

        Individual x;
        // fill up the remaining spots with random picking
        while (next_gen.size() < howManyToSelect) {
            int pos = randomGenerator.nextInt(sorted_curr_gen.size());
            x = sorted_curr_gen.get(pos);
            next_gen.add(new Individual(x.getTour()));
            sorted_curr_gen.remove(pos);
        }

        return next_gen;
    }

}
