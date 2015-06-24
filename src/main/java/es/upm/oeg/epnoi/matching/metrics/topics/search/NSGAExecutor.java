package es.upm.oeg.epnoi.matching.metrics.topics.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uma.jmetal.algorithm.multiobjective.nsgaiii.NSGAIII;
import org.uma.jmetal.algorithm.multiobjective.nsgaiii.NSGAIIIBuilder;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.operator.impl.selection.NaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.comparator.DominanceComparator;

/**
 * Created by cbadenes on 30/04/15.
 */
public class NSGAExecutor {

    private static final Logger log = LoggerFactory.getLogger(NSGAExecutor.class);

    public static LDASolution search(Problem problem, Integer maxEvaluations){
        log.info("Executing NSGA: maxEvaluations=" + maxEvaluations);

        // Crossover
        Double crossoverProbability        = 0.9 ;
        Double crossoverDistributionIndex  = 20.0 ;
        SBXCrossover crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex) ;

        // Mutation
        Double mutationDistributionIndex   = 20.0 ;
        Double mutationProbability  = 0.2 / problem.getNumberOfVariables() ;
        PolynomialMutation mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex) ;

        // Selection
//        BinaryTournamentSelection selection = new BinaryTournamentSelection();
//        NaryTournamentSelection selection = new NaryTournamentSelection(10,new DominanceComparator());
        NaryTournamentSelection selection = new NaryTournamentSelection();

        // NSGAIII algorithm
        Integer divisions                   = 12; //12
        NSGAIII algorithm  = new NSGAIIIBuilder(problem)
                .setCrossoverOperator(crossover)
                .setMutationOperator(mutation)
                .setSelectionOperator(selection)
                .setMaxEvaluations(maxEvaluations)
                .setDivisions(divisions)
                .build() ;


        log.info("NSGA-III Algorithm ready: " + algorithm);
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute() ;
        Solution result = algorithm.getResult().get(0);
        Long computingTime = algorithmRunner.getComputingTime() ;
        log.info("NSGA Execution time: "+ computingTime +"ms");
        return (LDASolution) result;
    }

}
