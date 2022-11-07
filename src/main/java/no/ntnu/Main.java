package no.ntnu;


/**
 * Main class is used to set up the program settings, as well as create the cycle.
 */
public class Main {
    int populationSize = 1000;
    double mutationRate = 0.01;
    String targetPhrase = "Hakon Satre";
    double crossoverRate = 1.0;
    Population population;


    private void setup(){
        population = new Population(targetPhrase, mutationRate, populationSize , crossoverRate);
    }

    public Main(){
        setup();
    }

    /**
     * Method that runs the program.
     * @param args
     */
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    /**
     * Running the main program, setting up the main process in sequence.
     * Also prints the result with information such as the number of generations
     * the final string and its fitness.
     */
    public void run(){

        //One loop is equal to one generation
        while(!population.getFinished()){
            //calculate the fitness of every string in
            population.calculateFitness();

            //Display all current strings with their fitness.
            System.out.println(population.allPhrases());

            //Uses fitness to generate a mating pool
            population.naturalSelection();

            //Run a cycle that generates children to replace parents in new generation.
            population.cycle();




        }
        //When result is found print the result and the amount of generations
        System.out.println();
        System.out.println("Best final string: " + population.getBestString());
        System.out.println("Generations: " + population.getCycles());
        System.out.println("CrossoverRate " + population.getCrossoverRate());
        System.out.println("MutationRate " + population.getMutationRate());
        System.out.println();
        System.out.println();
        System.out.println("PRESS ENTER TO EXIT.");

    }

}
