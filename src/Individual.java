import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

public class Individual {

    /**
     * Chromosome stores the individual's genetic data as an ArrayList of characters
     * Each character represents a gene
     */
    ArrayList<Character> chromosome;
    
    /** 
     * Inital constructor to generate initial population members
     * @param c_0 The initial chromosome size
     * @param num_letters The number of letters available to choose from
     */
    public Individual(int c_0, int num_letters) {
      chromosome=new ArrayList<>(c_0);
      for(int i=0; i<c_0; i++) {
        chromosome.add(randomLetter(num_letters));
      }
    }

     /**
      * Second constructor to create parents and offspring chromosomes
      * @param parent1 The first parent chromosome
      * @param parent2 The second parent chromosome
      * @param c_max The maximum chromosome size
      * @param m The chances per round of mutation in each gene
      */
    public Individual(Individual parent1, Individual parent2, int c_max, float m) {
      int prefixLength = (int)(Math.random()*parent1.chromosome.size());
      int suffixLength = (int)(Math.random()*parent2.chromosome.size());
      
      // if it's too long and only suffix has to be changed
      if((prefixLength+suffixLength)>c_max && prefixLength<=c_max) {
        suffixLength=c_max-prefixLength;
      }
      // if prefix is also too long
      if((prefixLength+suffixLength)>c_max && prefixLength>c_max) {
        suffixLength=0;
        prefixLength=c_max;
      }
      //System.out.println("  Prefix of length " + prefixLength);
      //System.out.println("  Suffix of length " + suffixLength);
      chromosome=new ArrayList<>(prefixLength+suffixLength);

      for(int i=0; i<prefixLength; i++) {
          //System.out.println("  parent 1 added " + parent1.chromosome.get(i));
          chromosome.add(parent1.chromosome.get(i));
          if(doesMutate(m)) {
            chromosome.set(i,randomLetter(5));
            //System.out.println("    A MUTATION! Changed to " + chromosome.get(i));
            // ASSUMPTION: num_letters should be 5
          }
      }

      for(int i=0; i<suffixLength; i++) {
        chromosome.add(parent2.chromosome.get(i));
        //System.out.println("  parent 2 added " + parent2.chromosome.get(i));
        if(doesMutate(m)) {
          chromosome.set(i,randomLetter(5));
          //System.out.println("    A MUTATION! Changed to " + chromosome.get(i));
          // ASSUMPTION: num_letters should be 5
        }
      }
    }

    /**
     * Chooses a letter at random, in the range from A to the number of states indicated
     * @param num_letters The number of letters available to choose from (number of possible states)
     * @return The letter as a Character
     */
    private Character randomLetter(int num_letters) {
        return Character.valueOf((char)(65+ThreadLocalRandom.current().nextInt(num_letters)));
      }
    
    /** 
     * Method to determine whether a given gene will mutate based on the parameter ***m***
     * @param m the mutation rate
     * @return true if a number randomly chosen between 0 and 1 is less than ***m***, else false
    */
    private Boolean doesMutate(float m){
      /*
        float randomNum = ThreadLocalRandom.current().nextInt(0, 1);
        return randomNum < m;*/
        float randomNum=(float)(Math.random());
        return randomNum<m;
    }

    /**
     * Expresses the individual's chromosome as a String, for display purposes
     * @return The chromosome as a String
     */
    public String toString() {
        StringBuilder builder = new StringBuilder(chromosome.size());
        for(Character ch: chromosome) {
          builder.append(ch);
        }
        return builder.toString();
      }

    /**
     * Calculates the fitness score of each chromosome
     * @return The fitness score as an int
     */

     /*
      * Each match ***increases*** fitness by 1, and each mismatch ***decreases*** fitness by 1. If there are an odd number of genes in the chromosome, you can ***increase*** fitness by 1 for the middle gene. 
- Second, you will compare each gene with the preceding one.  If they match, ***decrease*** fitness by 1.
      */
    public int getFitness() {
        int lowerVal = 0;
        int higherVal = chromosome.size()-1;
        int fitness=0;

        while(lowerVal<higherVal) {
          // if same/mismatched
          //System.out.println("lowerVal is " + lowerVal);
          //System.out.println("higherVal is " + higherVal);
          if(chromosome.get(lowerVal).equals(chromosome.get(higherVal))) {
            fitness++;
          }
          else {
            fitness--;
          }

          // compare gene with preceding
          if(lowerVal>0 && chromosome.get(lowerVal).equals(chromosome.get(lowerVal-1))) {
            fitness--;
          }
          if(chromosome.get(higherVal).equals(chromosome.get(higherVal-1))) {
            fitness--;
          }

          // move inward
          lowerVal++;
          higherVal--;
        }
        // if it's at the middle
        if(lowerVal==higherVal) {
          fitness++;
        }

        return fitness;
    }
    
}
