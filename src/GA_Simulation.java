import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.Comparator;

public class GA_Simulation {
  ArrayList<Individual> pop;
  int n; // individuals in a generation
  int k; // number of winner allowed in a gen
  int r; // number of rounds an evolution runs
  int c_0; // initial chromosome size
  int c_max; // max chromosome size
  float m; // change of mutation each round
  int g; // number of states possible per gene
// ASSUMPTION: g means the number of chars that a chromosome can have


  /**
   * Constructor
   * @param n int individuals in a generation
   * @param k int number of winner allowed in a gen
   * @param r int number of rounds an evolution runs
   * @param c_0 int initial chromosome size
   * @param c_max int max chromosome size
   * @param m float change of mutation each round
   * @param g int number of states possible per gene
   */
  public GA_Simulation(int n, int k, int r, int c_0, int c_max, float m, int g) {
    this.pop=new ArrayList<>();
    this.n=n;
    this.k=k;
    this.r=r;
    this.c_0=c_0;
    this.c_max=c_max;
    this.m=m;
    this.g=g;
  }

  /**
   * Populates the initial population arraylist
   */
  private void init() {
    for(int i=0; i<n; i++) {
      Individual x = new Individual(c_0, g);
      //System.out.println("Now adding " + x.toString());
      pop.add(x);
      //System.out.println("Added " + pop.get(i).toString() + "\n");
    }
  }

  
  /**
   * Ranks the population, populates the next generation based on the genetics of randomly assigned top fitness parents
   */
  private void evolve() {
    rankPopulation(pop);
    ArrayList<Individual> copied = (ArrayList)pop.clone();
    
    // removes elements of pop after k
    for(int i=k-1; i<copied.size(); i++) {
      copied.remove(i);
    }

    for(int i=0; i<n; i++) {
      Individual parent1 = copied.get((int)(Math.random()*copied.size()));
      //System.out.println("parent1: " + parent1.toString() + " with " + parent1.getFitness() + " fitness.");
      Individual parent2 = copied.get((int)(Math.random()*copied.size()));
      //System.out.println("parent2: " + parent2.toString() + " with " + parent2.getFitness() + " fitness.");
      Individual child = new Individual(parent1, parent2, c_max, m);
      //System.out.println("Child: " + child.toString() + " with " + child.getFitness() + " fitness.");
      pop.add(i, child);
    }
  }

  /** Sorts population by fitness score, best first 
  * @param pop: ArrayList of Individuals in the current generation
  * @return: An ArrayList of Individuals sorted by fitness
  */
  public void rankPopulation(ArrayList<Individual> pop) {
      // sort population by fitness
      Comparator<Individual> ranker = new Comparator<>() {
        // this order will sort higher scores at the front
        public int compare(Individual c1, Individual c2) {
          return (int)Math.signum(c2.getFitness()-c1.getFitness());
        }
      };
    pop.sort(ranker); 
  }

  /**
   * Prints information about the current generation
   */
  public void describeGeneration() {
    rankPopulation(pop);
    System.out.println("\nFittest individual: " + pop.get(0).getFitness());
    System.out.println("Kth individual: " + pop.get(k-1).getFitness());
    System.out.println("Least fit individual: " + pop.get(pop.size()-1).getFitness());
    System.out.println("Fittest individual's chromosome: " + pop.get(0).toString());
  }

  /**
   * Runs a the genetic simulator
   * @param sim GA_Simulation that the user wants to run
   */
  public static void run(GA_Simulation sim) {
    sim.init();
    sim.describeGeneration();
    for(int i=0; i<sim.r; i++) {
      sim.evolve();
      sim.describeGeneration();
    }
  }

  public static void main(String[] args) {
    GA_Simulation sim = new GA_Simulation(100, 20, 20, 8, 20, 0.01f, 5);
    run(sim);
  }

}