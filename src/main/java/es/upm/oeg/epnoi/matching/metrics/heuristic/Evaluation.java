package es.upm.oeg.epnoi.matching.metrics.heuristic;

public interface Evaluation {

    public class Result{
        public Double o1,o2,o3;
        public Result(Double o1, Double o2, Double o3) {
            this.o1 = o1; // loglikelihood
            this.o2 = o2; // logprior
            this.o3 = o3; // topics
        }
    }

    Result evaluate(LDAParameters parameters);
}
