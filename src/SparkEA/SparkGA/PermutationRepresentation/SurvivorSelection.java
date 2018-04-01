/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SparkEA.SparkGA.PermutationRepresentation;

import SparkEA.Chromosome;
import SparkEA.Selection.SelectionMethods;
import java.util.ArrayList;

/**
 *
 * @author anshal
 */
public class SurvivorSelection {
    
    public static ArrayList<PermutationChromosome> RouletteWheelSelection(ArrayList<PermutationChromosome> population, ArrayList<PermutationChromosome> offSpring){
        int selectionSize = population.size();
        population.addAll(offSpring);
        ArrayList<Chromosome> tmp = new ArrayList<>();
        for(int i=0; i<population.size(); i++)
            tmp.add(population.get(i));
        tmp = SelectionMethods.RouletteWheel(tmp, selectionSize);
        population.removeAll(population);
        for(int i=0; i<tmp.size(); i++)
            population.add((PermutationChromosome)tmp.get(i));
        return population;
        
        
    }
}