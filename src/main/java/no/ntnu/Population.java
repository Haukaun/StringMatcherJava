package no.ntnu;

import java.util.ArrayList;
import java.util.Random;

/**
 * Population class is used to control the population of chromosomes.
 */
public class Population {
    private Random random;
    private Chromosome[] population;
    private String target;
    private double mutationRate;
    private ArrayList<Chromosome> matingPool;
    private int cycles;
    private boolean finished;
    private int bestScore;
    private String bestString;
    private double crossoverRate;

    /**
     * Initializes the population with random chromosomes.
     * @param target target string to compare the chromosomes to.
     * @param mutationRate the rate at which the chromosomes mutate.
     * @param populationSize the size of the population.
     * @param crossoverRate the rate at which the chromosomes crossover.
     */
    public Population(String target, double mutationRate, int populationSize, double crossoverRate) {
        random = new Random();
        this.crossoverRate = crossoverRate;
        this.target = target;
        this.mutationRate = mutationRate;
        this.population = new Chromosome[populationSize];

    
        for (int i = 0; i<population.length;i++){
            this.population[i] = new Chromosome(target.length());
        }
        //calculate initial fitness of all strings
        calculateFitness();
        matingPool = new ArrayList<Chromosome>();
        finished = false;
        cycles = 0;
        bestScore = 1;
    }

    /**
     * Calculates the fitness of every chromosome in the population.
     */
    public void calculateFitness(){
        for(int i=0;i< population.length;i++){
            this.population[i].fitnessFuntion(target);
        }

    }

    /**
     * Determines the amount of times a chromosome appears in the mating pool
     * and populates the mating pool.
     */
    public void naturalSelection(){
        matingPool.clear();

        float maxFitness = 0;
        //finds the maxfitness of the population for use
        //in mapping 0-maxfitness to 0-1.
        for(int i = 0; i < population.length; i++){
            if(population[i].getFitness() > maxFitness){
                maxFitness = population[i].getFitness();
            }
        }

        //Populates the actual mating pool
        for (int i = 0; i< population.length;i++){
            //re-maps the fitness from a range of 0 to maxFitness to a range of 0 to 1
            float fitness = population[i].getFitness()*(1/maxFitness);
            //calculates how many appearances a chromosome will have in the mating pool
            int n = (int)(fitness*100);
            for (int j =0; j<= n ;j++){
                matingPool.add(population[i]);
            }
        }

    }

    /**
     * Cycle that generates new chromosomes(children) from two previous chromosomes(parents)
     * and place them at index i in the population.
     */
    public void cycle(){
        bestString = getBest();
        for (int i = 0;i<population.length;i++){
            //determine index of parents
            int a = random.nextInt(matingPool.size());
            int b = random.nextInt(matingPool.size());
            Chromosome partnerA = matingPool.get(a);
            Chromosome partnerB = matingPool.get(b);
            //Mate the two parents

            if (random.nextDouble() <= crossoverRate){
                Chromosome child = partnerA.crossover(partnerB);
                //Mutate the child
                child.mutate(mutationRate);
                //Place the child in the population
                population[i] = child;
            }
        }
        //Counts the generations.
        cycles++;
    }

    /**
     * Determines the best chromosome in the population and ends the
     * simulation if a perfect score is achieved.
     * @return the string that is the best current output.
     */
    public String getBest(){
        double currentBest = 0.0;
        int index = 0;
        for ( int i = 0; i< population.length;i++){
            if(population[i].getFitness() > currentBest){
                index = i;
                currentBest = population[i].getFitness();
            }
        }
        if(currentBest == bestScore){
            finished = true;
        }
        return population[index].getPhrase();
    }

    /**
     * used to tell main the program has finished
     * @return boolean on if the program is finished.
     */
    public boolean getFinished(){
        return finished;
    }

    /**
     * Used to tell main the amount of generations used in the program.
     * @return number of cycles
     */
    public int getCycles(){
        return cycles;
    }

    /**
     * used to display the average fitness of the population.
     * @return avrerage fitness
     */
    public double getAverageFitness(){
        double total = 0;
        for(int i = 0;i< population.length;i++){
            total +=population[i].getFitness();
        }
        return total/(population.length);
    }

    /**
     * Used to return every chromosome in the population.
     * @return String with every chromosome in the population.
     */
    public String allPhrases(){
        String everything = "";

        for(int i = 0;i < population.length;i++){
            everything += population[i].getPhrase()+"\n";
        }
        return everything;
    }

    /**
     * returns the current best string from all generations.
     * @return current best string
     */
    public String getBestString(){
        return bestString;
    }

    public double getCrossoverRate() {
        return crossoverRate;
    }

    public double getMutationRate() {
        return mutationRate;
    }


}
