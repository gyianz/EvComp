package EvComp_TSP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

public class Individual {

	private ArrayList<Integer> tour;
	private double fitness;

	/**
	 * Constructor for the Individual class
	 * @param tour The ArrayList of Integer that contains all the cities
	 */
	public Individual(ArrayList<Integer> tour) {
		this.tour = tour;
		this.calculateFitness();
	}

	/**
	 * Converts the Integer[] to ArrayList<Integer>
	 * @param tour The Integer[] of tours
	 */
	public Individual(Integer[] tour) {
		this.tour = new ArrayList<>(Arrays.asList(tour));
		this.calculateFitness();
	}

	/**
	 * Returns the tour list
	 * @return ArrayList<Integer>
	 */
	public ArrayList<Integer> getTour() {
		return tour;
	}

	/**
	 * Prints the tour of a single individual
	 */
	public void printTour() {
		for (Integer value : tour) {
			System.out.print(value + " ");
		}
		System.out.println();
	}

	/**
	 * To String method for the individual class
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%.2f", fitness));
		sb.append(",").append(tour.get(0));
		for (int i = 1; i < tour.size(); i++) {
			sb.append(" ").append(tour.get(i));
		}
		return sb.toString();
	}

	/**
	 * Generates a random initial individual.
	 */
	public static Individual getNewIndividual(int mapSize) {
		ArrayList<Integer> tour = new ArrayList<>();

		for (int i = 1; i <= mapSize; i++) {     		// tours all start from 1, not zero
			tour.add(i);
		}
		Collections.shuffle(tour, new Random(System.nanoTime()));

		return new Individual(tour);
	}

	/**
	 * Make a deep copy of individual instance.
	 *
	 * @return A deep copy of this individual.
	 */
	public Individual makeClone() {
		ArrayList<Integer> copy = new ArrayList<>(this.tour);
		Individual result = new Individual(copy);
		return result;
	}

	/**
	 * Swap two elements.
	 *
	 * @param a Element 1.
	 * @param b Element 2.
	 */
	public void swap(int a, int b) {
		int temp = tour.get(a);
		tour.set(a, tour.get(b));
		tour.set(b, temp);
	}

	public void calculateFitness() {
		// calculate fitness
		fitness = 0;

		for (int i = 0; i < tour.size() - 1; i++) {    		// loop through each consecutive pair
			int idx_this = tour.get(i) - 1;           		// map index is tour city - 1
			int idx_next = tour.get(i + 1) - 1;

			fitness += Config.map.getDistance(idx_this, idx_next);
		}

		// also add distance of last city to first city (hamilton loop tour)
		int idx_last = tour.get(tour.size() - 1) - 1;
		int idx_first = tour.get(0) - 1;
		fitness += Config.map.getDistance(idx_last, idx_first);
	}

	public double getFitness() {
		return fitness;
	}

	/**
	 * Normalise a tour. This method produces a tour arrayList that aligns with
	 * the individual's tour, for comparison.
	 *
	 * @param tourB Tour arrayList of another individual.
	 * @return Normalised tour.
	 */
	private ArrayList<Integer> normaliseTour(ArrayList<Integer> tourB) {
		Integer start = tour.get(0);
		int index = tourB.indexOf(start);
		ArrayList<Integer> result = new ArrayList<>();
		if (tour.get(tour.size() - 1).equals(tourB.get((index + 1) % tourB.size()))) {
			// inverse order
			for (int i = index; i >= 0; i--) {
				result.add(tourB.get(i));
			}
			for (int i = tourB.size() - 1; i > index; i--) {
				result.add(tourB.get(i));
			}
		} else {
			// normal order
			for (int i = index; i < tourB.size(); i++) {
				result.add(tourB.get(i));
			}
			for (int i = 0; i < index; i++) {
				result.add(tourB.get(i));
			}
		}
		return result;
	}

	/**
	 * Checks whether two tours are equal or not in a closed loop
	 * @param individualB The individual to be compared with
	 * @return true if both individual are the same false otherwise
	 */
	public boolean equals(Individual individualB) {
		ArrayList<Integer> normalisedTour = normaliseTour(individualB.getTour());
		return tour.equals(normalisedTour);
	}

}
