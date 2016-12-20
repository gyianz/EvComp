package Selector;

import EvComp_TSP.Individual;
import java.util.ArrayList;

/**
 * Implemented by Marcus, integrated by Xiaogang.
 *
 * @author Marcus
 */
class SelectorTournament implements SelectorIntf {


	/**
	 * Selects the ArrayList of Individual based on the tournament scheme
	 */
    @Override
    public ArrayList<Individual> selectFrom(ArrayList<Individual> curr_gen, int howManyToSelect, int k, double eRatio) {
        ArrayList<Individual> t_curr_gen = (ArrayList<Individual>) curr_gen.clone();
        ArrayList<Individual> next_gen = new ArrayList<>();

        // Loop until next_gen is full
        while (next_gen.size() < howManyToSelect) {
            // select random individual
            int random_position = randomGenerator.nextInt(t_curr_gen.size());
            Individual temp = t_curr_gen.get(random_position);

            // select k-1 individuals
            for (int i = 0; i < k - 1; i++) {
                random_position = randomGenerator.nextInt(t_curr_gen.size());
                if (t_curr_gen.get(random_position).getFitness() < temp.getFitness()) {
                    temp = t_curr_gen.get(random_position);
                }
            }
            next_gen.add(temp);
            t_curr_gen.remove(temp);
        }

        return next_gen;
    }

}
