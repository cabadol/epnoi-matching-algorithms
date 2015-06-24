package es.upm.oeg.epnoi.matching.metrics.topics.search;

import org.uma.jmetal.solution.DoubleSolution;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LDASolution implements DoubleSolution {

    private enum Variable{
        TOPICS(0), ALPHA(1), BETA(2);

        public int id;
        Variable(int id) {
            this.id = id;
        }

        static Variable from(int id){
            if (id==0) return TOPICS;
            if (id==1) return ALPHA;
            if (id==2) return BETA;
            throw new RuntimeException();
        }
    }

    private enum Objetive{
        LOGLIKELIHOOD(0), LOGPRIOR(1), TOPICS(2), DISTRIBUTION(3);

        public int id;
        Objetive(int id) {
            this.id = id;
        }

        static Objetive objetive(int id){
            if (id==0) return LOGLIKELIHOOD;
            if (id==1) return LOGPRIOR;
            if (id==2) return TOPICS;
            if (id==3) return DISTRIBUTION;
            throw new RuntimeException();
        }
    }

    Integer maxIterations;
    Integer maxTopics;

    public static final Double minTopics = 1.0;

    public static final Double maxAlpha = 20.0;
    public static final Double minAlpha = 5.1;

    public static final Double maxBeta = 5.0;
    public static final Double minBeta = 1.1;

    Map<Integer,Double> objetives = new HashMap<>();

    Map<Integer,Double> variables = new HashMap<>();

    Map<Object,Object> attributes = new HashMap<>();

    Double overallConstraintViolationDegree = 0.0;

    Integer numberOfViolatedConstraints = 0;


    public LDASolution(Integer numTopics, Double alpha, Double beta, Integer maxIterations, Integer maxTopics) {
        setNumTopics(numTopics);
        setAlpha(alpha);
        setBeta(beta);
        this.maxIterations = maxIterations;
        this.maxTopics = maxTopics;
    }

    public Integer getMaxIterations(){
        return maxIterations;
    }

    public LDASolution setMaxIterations( Integer maxIterations){
        this.maxIterations = maxIterations;
        return this;
    }

    public Integer getMaxTopics(){
        return maxTopics;
    }

    public LDASolution setMaxTopics(Integer maxTopics){
        this.maxTopics = maxTopics;
        return this;
    }

    public boolean isValid(){
        return getTopics()>minTopics && getAlpha() > minAlpha && getBeta() > minBeta;
    }

    public LDASolution setLoglikelihood (Double value){
        setObjective(Objetive.LOGLIKELIHOOD.id,value);
        return this;
    }

    public Double getLoglikelihood(){
        return objetives.get(Objetive.LOGLIKELIHOOD.id);
    }


    public LDASolution setDistribution (Double value){
        setObjective(Objetive.DISTRIBUTION.id,value);
        return this;
    }

    public Double getDistribution(){
        return objetives.get(Objetive.DISTRIBUTION.id);
    }


    public LDASolution setLogprior (Double value){
        setObjective(Objetive.LOGPRIOR.id,value);
        return this;
    }

    public Double getLogPrior(){
        return objetives.get(Objetive.LOGPRIOR.id);
    }

    public LDASolution setTopicsObj (Double value){
        setObjective(Objetive.TOPICS.id,value);
        return this;
    }

    public Double getTopicsObj(){
        return objetives.get(Objetive.TOPICS.id);
    }


    @Override
    public String toString() {
        return "var[topics:"+ getTopics()+", alpha:"+getAlpha()+", beta:"+getBeta()+"] \t obj[logLikelihood(abs):"+getLoglikelihood()+", logPrior(abs):"+getLogPrior()+"]";
    }

    public void setNumTopics(Integer num){
        setVariableValue(Variable.TOPICS.id, (num < 1)? 1 : Double.valueOf(num));
    }

    public Integer getTopics(){
        return variables.get(Variable.TOPICS.id).intValue();
    }

    public Double getAlpha(){
        return variables.get(Variable.ALPHA.id);
    }

    public void setAlpha(Double value){
        setVariableValue(Variable.ALPHA.id, (value < 1)? minAlpha : value);
    }

    public Double getBeta(){
        return variables.get(Variable.BETA.id);
    }

    public void setBeta(Double value){
        setVariableValue(Variable.BETA.id, (value < 1) ? minBeta : value);
    }

    private Double truncate(Double num){
        return Double.valueOf(new DecimalFormat("#.#").format(num));
    }

    @Override
    public void setObjective(int i, double v) {
        objetives.put(i,truncate(v));
    }

    @Override
    public double getObjective(int i) {
        return objetives.get(i);
    }

    @Override
    public Double getVariableValue(int i) {
        return variables.get(i);
    }

    @Override
    public void setVariableValue(int i, Double aDouble) {
        variables.put(i,truncate(aDouble));
    }

    @Override
    public String getVariableValueString(int i) {
        return String.valueOf(variables.get(i));
    }

    @Override
    public int getNumberOfVariables() {
        return 3;
    }

    @Override
    public int getNumberOfObjectives() {
        return 3;
    }

    @Override
    public double getOverallConstraintViolationDegree() {
        return overallConstraintViolationDegree;
    }

    @Override
    public void setOverallConstraintViolationDegree(double v) {
        overallConstraintViolationDegree = v;
    }

    @Override
    public int getNumberOfViolatedConstraints() {
        return numberOfViolatedConstraints;
    }

    @Override
    public void setNumberOfViolatedConstraints(int i) {
        this.numberOfViolatedConstraints = i;
    }

    @Override
    public LDASolution copy() {
        LDASolution clone = new LDASolution(getTopics(),getAlpha(),getBeta(),maxIterations,maxTopics);

        // Objetives
        Iterator<Integer> it = objetives.keySet().iterator();
        while(it.hasNext()){
            Integer key = it.next();
            clone.setObjective(key, objetives.get(key));
        }

        clone.setOverallConstraintViolationDegree(overallConstraintViolationDegree);
        clone.setNumberOfViolatedConstraints(numberOfViolatedConstraints);

        return clone;
    }

    @Override
    public void setAttribute(Object o, Object o1) {
        attributes.put(o,o1);
    }

    @Override
    public Object getAttribute(Object o) {
        return attributes.get(o);
    }

    @Override
    public Double getLowerBound(int i) {
        switch (Variable.from(i)){
            case TOPICS: return minTopics;
            case ALPHA: return minAlpha;
            case BETA: return minBeta;
            default: return 0.0;
        }
    }

    @Override
    public Double getUpperBound(int i) {
        switch (Variable.from(i)){
            case TOPICS: return Double.valueOf(maxTopics);
            case ALPHA:return maxAlpha;
            case BETA: return maxBeta;
            default: return 0.0;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LDASolution that = (LDASolution) o;

        if (maxIterations != null ? !maxIterations.equals(that.maxIterations) : that.maxIterations != null)
            return false;
        return !(variables != null ? !variables.equals(that.variables) : that.variables != null);

    }

    @Override
    public int hashCode() {
        int result = maxIterations != null ? maxIterations.hashCode() : 0;
        result = 31 * result + (variables != null ? variables.hashCode() : 0);
        return result;
    }
}
