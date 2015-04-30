package es.upm.oeg.epnoi.matching.metrics.heuristic;

import org.uma.jmetal.algorithm.multiobjective.nsgaiii.NSGAIII;
import org.uma.jmetal.algorithm.multiobjective.nsgaiii.NSGAIIIBuilder;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.operator.impl.selection.NaryTournamentSelection;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.comparator.DominanceComparator;

/**
 * Created by cbadenes on 30/04/15.
 */
public class LDAOptimizer {

    public static LDAParameters search(Long size, Evaluation function){
        // Try to search best parameters for a LDA problem
        LDAProblem problem = new LDAProblem(size, function);

        // Crossover
        Double crossoverProbability        = 0.9 ;
        Double crossoverDistributionIndex  = 20.0 ;
        SBXCrossover crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex) ;

        // Mutation
        Double mutationDistributionIndex   = 20.0 ;
        Double mutationProbability  = 1.0 / problem.getNumberOfVariables() ;
        PolynomialMutation mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex) ;

        // Selection
        BinaryTournamentSelection selection = new BinaryTournamentSelection();
//        NaryTournamentSelection selection = new NaryTournamentSelection(3,new DominanceComparator());

        // NSGA algorithm
        Integer maxEvaluations              = 15; // 2500
        Integer divisions                   = 12; // 12
        NSGAIII algorithm  = new NSGAIIIBuilder(problem)
                .setCrossoverOperator(crossover)
                .setMutationOperator(mutation)
                .setSelectionOperator(selection)
                .setMaxEvaluations(maxEvaluations)
                .setDivisions(divisions)
                .build() ;


        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute() ;
        Solution result = algorithm.getResult().get(0);;
        Long computingTime = algorithmRunner.getComputingTime() ;
        System.out.println("Total execution time: "+ computingTime +"ms");
        return (LDAParameters) result;
    }

}
