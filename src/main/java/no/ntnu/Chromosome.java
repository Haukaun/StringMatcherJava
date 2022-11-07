package no.ntnu;

import java.util.Random;

/**
 * Used to control every individual chromosome and gene
 */
public class Chromosome {
    private Random random;
    private char[] genes;
    private float fitness;

    /**
     * Initializes every individual chromosome with random genes
     * @param num length of each chromosome
     */
    public Chromosome(int num){
        random = new Random();
        genes = new char[num];
        for (int i = 0;i<genes.length;i++){
            genes[i] = (char) (random.nextInt(128-32)+32);
        }
        fitness = 0;
    }

    /**
     * takes the chromosome and combines it with the fitness.
     * @return chromosome and fitness.
     */
    public String getPhrase() {
        return (new String(genes))+" Fitness: "+getFitness();
    }

    /**
     * calculates the fitness of a certain chromosome
     * @param target target string to compare the chromosome to.
     */
    public void fitnessFuntion(String target){
        int score= 0;
        for(int i=0; i < genes.length; i++){
            if(genes[i] == target.charAt(i)){
                score++;
            }
        }
        this.fitness = (float) score / (float)target.length();
    }

    /**
     * Crosses over this chromosomes genes with a partners genes
     * to make a child chromosome.
     * @param partner the chromosome whos genes will combine with this gene
     * @return the new child chromosome.
     */
    public Chromosome crossover(Chromosome partner){
        Chromosome offspring = new Chromosome(genes.length);

        int midpoint = (random.nextInt(genes.length));

        for(int i = 0;i<genes.length;i++){
            if(i>midpoint){
                offspring.setGenesAtIndex(getGenes(),i);
            } else {
                offspring.setGenesAtIndex(partner.getGenes(), i);
            }
        }
        return offspring;
    }

    /**
     * Gives a chromosome a chance to mutate at every gene.
     * @param mutationRate the likelihood of a mutation happening.
     */
    public void mutate(double mutationRate){
        for(int i = 0;i < genes.length;i++){
            double randInt = random.nextDouble();
            if(randInt < mutationRate){
                genes[i] = (char) (random.nextInt(128-32)+32);
            }
        }
    }

    /**
     * changes the gene at a specific index.
     * @param genes new gene value
     * @param index index of the gene to be changed
     */
    public void setGenesAtIndex(char[] genes, int index){
        this.genes[index] = genes[index];
    }

    /**
     * a method to get all genes in a chromosome.
     * @return all genes in the chromosome.
     */
    public char[] getGenes(){
        return genes;
    }

    /**
     * A method to get the fitness of a specific gene
     * @return the fitness value of the specific gene.
     */
    public float getFitness(){
        return this.fitness;
    }
}
