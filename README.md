# EvComp_TSP
Repository for the Group Assignment 1 in Evolutionary Computation

## GROUP MEMBERS
- Marcus Low      (a1600254)
- Xiaogang Dong   (a1652708)
- Zhou Chuyu      (a1692264)
- Pang Zi Yang    (a1681939)

-----------------------
## HOW-TO-USE

### Building + Running of All Config Files:
```
./runTest.sh
```
- The runTest.sh script we provide by default runs each configuration file once, with one thread and writes output to terminal. To change this behaviour, modify the script according to the description below(see Running An Example section).

### Building only:
```
./build.sh

```
### Running An Example:
- Set up your configuration file (please see section on Configuration Files):
- after building the program, run it with:
```
java EvComp_TSP.Driver <configPath> <X> <T> [terminal]
```
- `configPath` = path to the config file
- `X` = Number of repetitions to run the experiment
- `T` = Number of threads to use (4 threads = 4 simultaneous experiments)
- `terminal` -> if the word "terminal" is included, the program will output to the terminal. Otherwise, the output will be written to .log files in the `output` directory.


-----------------------
## CONFIGURATION FILES
Configuration files (.cfg) are used to explain to the program what sort of algorithm to use. All configuration files should be stored in the `config` directory with the `.cfg` extension. Not placing configuration files in the `config` directory can lead to an improperly-defined path for the output log files.

A configuration file consists of:
- a configuration header containing the problem file(.tsp), the population size of each generation, and the number of generations to run the experiment.
- a formatted description of the algorithm to use

The general algorithm consists of the following:
- Parent Selection Stage
- Crossover Stage
- Mutation Stage
- Children Selection Stage

Thus to run the EIL51 problem over 10000 generations with an initial population of 50, one example of a possible configuration would be:
```
./testSet/eil51.tsp 50 10000
fitness_proportional
pmx
swap
tournament,5
```
To use the InverOver algorithm, simply replace all the stages of the algorithm as such:
```
./testSet/eil51.tsp 50 10000
inverOver
```
### Selection Stage Operators
- `elitism,R` -- uses the elitism method of selection, where `R` should be replaced with a value (elitism ratio) in the range [0.0 , 1.0].
- `tournament,K` -- uses the tournament method of selection, where `K` should be replaced with an integer value (bucket size).
- `fitness_proportional` -- uses the fitness proportional method of selection 

### Crossover Variation Operators
- `order` -- uses the Order Crossover method
- `pmx` -- uses the PMX Crossover method
- `cycle` -- uses the Cycle Crossover method
- `edge` -- uses the Edge-Recombination method

### Mutation Variation Operators
- `insert` -- uses the Insert Mutation method
- `inversion` -- uses the Inversion Mutation method
- `scramble` -- uses the Scramble Mutation method
- `swap` -- uses the Swap Mutation method

-----------------------
## PACKAGES

### EvComp_TSP:
	Config		- Parses and stores configuration data
	Driver		- Starting run point of the program. Handles all execution
	Individual	- Class for storing a single tour
	InverOver	- Operator for performing the Inver-Over algorithm
	Map			- Class for parsing a problem file and storing the distances between all cities.
	Population	- Class for holding a of Individuals
	Reporter	- Operator for calculating and reporting statistics about a population
	TSPProblem	- Class for initialising a population and running an experiment based on Configuration and Map data.
	
### Selector
Class for defining a Selector operator by specifying the method type.
Available methods are described in the configuration files.

### Crossover
Class for defining a Crossover operator by specifying the method type.
Available methods are described in the configuration files.

### Mutator
Class for defining a Mutator operator by specifying the method type.
Available methods are described in the configuration files.
