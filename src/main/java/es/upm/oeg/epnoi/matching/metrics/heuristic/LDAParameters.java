package es.upm.oeg.epnoi.matching.metrics.heuristic;

import org.uma.jmetal.solution.DoubleSolution;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LDAParameters implements DoubleSolution, Comparable<LDAParameters> {

    private enum Type{
        TOPICS(0),ALPHA(1),BETA(2);

        public int id;
        Type(int id) {
            this.id = id;
        }

        static Type from(int id){
            if (id==0) return TOPICS;
            if (id==1) return ALPHA;
            if (id==2) return BETA;
            throw new RuntimeException();
        }
    }

    Integer maxIterations;

    Integer maxTopics;
    public static final Double minTopics = 1.0;

    public static final Double maxAlpha = 20.0;
    public static final Double minAlpha = 1.1;

    public static final Double maxBeta = 20.0;
    public static final Double minBeta = 1.1;

    Map<Integer,Double> objetives = new HashMap<>();

    Map<Integer,Double> variables = new HashMap<>();

    Map<Object,Object> attributes = new HashMap<>();

    Double overallConstraintViolationDegree = 0.0;

    Integer numberOfViolatedConstraints = 0;

    public LDAParameters(Integer numTopics, Double alpha, Double beta, Integer maxIterations, Integer maxTopics) {
        setNumTopics(numTopics);
        setAlpha(alpha);
        setBeta(beta);
        this.maxIterations = maxIterations;
        this.maxTopics = maxTopics;
    }

    public Integer getMaxIterations(){
        return maxIterations;
    }

    public LDAParameters setMaxIterations( Integer maxIterations){
        this.maxIterations = maxIterations;
        return this;
    }

    public Integer getMaxTopics(){
        return maxTopics;
    }

    public LDAParameters setMaxTopics(Integer maxTopics){
        this.maxTopics = maxTopics;
        return this;
    }

    public boolean isValid(){
        return getTopics()>0 && getAlpha() > 1 && getBeta() > 1;
    }

    @Override
    public String toString() {
        return "["+ getTopics()+","+getAlpha()+","+getBeta()+"] -> " + getValue();
    }

    public void setNumTopics(Integer num){
        setVariableValue(Type.TOPICS.id, (num < 1)? 1 : Double.valueOf(num));
    }

    public Integer getTopics(){
        return variables.get(Type.TOPICS.id).intValue();
    }

    public Double getAlpha(){
        return variables.get(Type.ALPHA.id);
    }

    public void setAlpha(Double value){
        setVariableValue(Type.ALPHA.id, (value < 1)? 1.5 : value);
    }

    public Double getBeta(){
        return variables.get(Type.BETA.id);
    }

    public void setBeta(Double value){
        setVariableValue(Type.BETA.id, (value < 1) ? 1.5 : value);
    }

    public void setValue(Double value){
        setObjective(0, truncate(value));
    }

    public Double getValue(){
        return objetives.get(0);
    }

    private Double truncate(Double num){
        return Double.valueOf(new DecimalFormat("#.#").format(num));
    }

    @Override
    public void setObjective(int i, double v) {
        objetives.put(i,v);
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
        return 1;
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
    public LDAParameters copy() {
        LDAParameters clone = new LDAParameters(getTopics(),getAlpha(),getBeta(),maxIterations,maxTopics);

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
    public int compareTo(LDAParameters o) {
        return o.getValue().compareTo(getValue());
    }


    @Override
    public Double getLowerBound(int i) {
        switch (Type.from(i)){
            case TOPICS: return minTopics;
            case ALPHA: return minAlpha;
            case BETA: return minBeta;
            default: return 0.0;
        }
    }

    @Override
    public Double getUpperBound(int i) {
        switch (Type.from(i)){
            case TOPICS: return Double.valueOf(maxTopics);
            case ALPHA:return maxAlpha;
            case BETA: return maxBeta;
            default: return 0.0;
        }
    }

}
