/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SparkEA.SparkGA.IntegerRepresentation;

import SparkEA.Work;
import SparkEA.Accessories;
import SparkEA.Chromosome;
import SparkEA.Params;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anshal
 */
public class Worker implements Work,Serializable{
    ArrayList<IntegerChromosome> population;
    Solver solve;
    
    
    public Worker(Solver solve, Accessories access, int populationSize, int chromosomeLength){
        population = new ArrayList<>();
        for(int i=0; i<populationSize; i++){
            IntegerChromosome ind = new IntegerChromosome(chromosomeLength, access);
            population.add(ind);
        }
        this.solve = solve;
    }
    
    public Worker(Solver solve, Accessories access, int populationSize, int chromosomeLength, int geneLowerBound, int geneUpperBound){
        population = new ArrayList<>();
        for(int i=0; i<populationSize; i++){
            IntegerChromosome ind = new IntegerChromosome(chromosomeLength, access, geneLowerBound, geneUpperBound);
            population.add(ind);
        }
        this.solve = solve;
    }
    
    public Worker(Solver solve, ArrayList<IntegerChromosome> pop){
        population = new ArrayList<>();
        for(int i=0; i<pop.size(); i++){
            this.population.add(pop.get(i));
        }
        this.solve = solve;
    }
    
    @Override
    public Chromosome solver(){
        return (Chromosome)solve.solver(population);
    }
    
    
    
     public List<Work> fork(int slices){
        List<Work> list = new ArrayList<>(slices);
        for(int i=0; i<slices-1; i++){
            Worker work = clone();
            
            list.add(work);
        }
        list.add(this);
        return list;
    }
    
    public Worker clone(){
        ArrayList<IntegerChromosome> clonePopulation = new ArrayList<>();
        for(int i=0; i<population.size(); i++){
            IntegerChromosome ind = new IntegerChromosome(population.get(i));
            clonePopulation.add(ind);
        }
        
        Solver nSolve = solve.clone();
        
        Worker work=new Worker(nSolve, clonePopulation);
        return work;
    }
    
    public static IntegerChromosome getFittest(List<IntegerChromosome> population){
        if(population.size()==0)
            return null;
        IntegerChromosome best = population.get(0);
        double maxFitness = population.get(0).getFitnessValue();
        for(int i=1; i<population.size(); i++){
            if(maxFitness < population.get(i).getFitnessValue()){
                maxFitness = population.get(i).getFitnessValue();
                best = population.get(i);
            }
        }
        return best;
        
    }
    
    public IntegerChromosome getFittest(){
       if(population.isEmpty())
           return null;
       IntegerChromosome best = population.get(0);
       double maxFitness = population.get(0).getFitnessValue();
       for(int i=1; i<population.size(); i++){
           if(maxFitness < population.get(i).getFitnessValue()){
               maxFitness = population.get(i).getFitnessValue();
               best = population.get(i);
           }
       }
       return best;

   }

   @Override
    public List<Work> fork(int slices, ArrayList<Params> params) {
        List<Work> list = new ArrayList<>(slices);
        int ind=0;
        for(int i=0; i<slices-1; i++){
            Worker work = clone(params.get(ind));
            ind=(ind+1)%params.size();
            list.add(work);
        }
        list.add(this);
        return list;
    }
    public Worker clone(Params p){
        ArrayList<IntegerChromosome> clonePopulation = new ArrayList<>();
        for(int i=0; i<population.size(); i++){
            IntegerChromosome ind = new IntegerChromosome(population.get(i),p);
            clonePopulation.add(ind);
        }
        
        Solver nSolve = solve.clone();
        
        Worker work=new Worker(nSolve, clonePopulation);
        System.out.println(work.population.size()+"CCCC");
        return work;
    }
    
    @Override
    public Chromosome getPopulation(int index){
        return population.get(index);
    }
    
}
