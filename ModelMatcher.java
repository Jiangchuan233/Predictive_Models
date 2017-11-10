import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Report the average log likelihood of a test String occuring in a 
 * given Markov model and detail the calculated values behind this statistic.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ModelMatcher
{

    /** log likelihoods for a teststring under a given model */
    private HashMap<String,Double> logLikelihoodMap;
    /** summary statistic for this setting */
    private double averageLogLikelihood;  
        
    /**
     * Constructor to initialise the fields for the log likelihood map for 
     * a test string and a given Markov model and 
     * the average log likelihood summary statistic
     * @param MarkovModel model a given Markov model object
     * @param String teststring
     */
    public ModelMatcher(MarkovModel model, String testString)
    {
        //initialising the hashmap
        logLikelihoodMap = new HashMap<String,Double>();
        //getting k for model
        int k = model.getK();
        int l = testString.length();
        //making new MkarkovModel using teststring and k
        MarkovModel m = new MarkovModel(k,testString);
        int i,j;
        //making every possible string of length k 
        for(i=0;i<l;i++)
        {
            String p ="";
            for(j=i;j<i+k+1;j++)
            {
                p+=testString.charAt(j%l);
            }
            //finding its laplace and putting it into map
            logLikelihoodMap.put(p,Math.log(model.laplaceEstimate(p))/Math.log(10));
        }
        //this is to keep the count of frequency
        NgramAnalyser ngram = new NgramAnalyser(k+1,testString);
        
        for(HashMap.Entry<String,Double> entry:logLikelihoodMap.entrySet())
        {   
            //updating the value based on frequency 
            logLikelihoodMap.put(entry.getKey(),ngram.getNgramFrequency(entry.getKey())*entry.getValue());
        }  
        //finding average of all value
        averageLogLikelihood=averageLogLikelihood(logLikelihoodMap,l);
    }

    /** Helper method that calculates the average log likelihood statistic
     * given a HashMap of strings and their Laplace probabilities
     * and the total number of ngrams in the model.
     * 
     * @param logs map of ngram strings and their log likelihood
     * @param ngramCount int number of ngrams in the original test string
     * @return average log likelihood: the total of loglikelihoods 
     *    divided by the ngramCount
     */
    private double averageLogLikelihood(HashMap<String,Double> logs, int ngramCount)
    {
        //callting totalloglikehood function and deviding it with ngramcount
        double key=totalLogLikelihood(logs)/ngramCount;
        return key;
    }
    
    /** Helper method to calculate the total log likelihood statistic
     * given a HashMap of strings and their Laplace probabilities
     * and the total number of ngrams in the model.
     * 
     * @param logs map of ngram strings and their log likelihood
     * @return total log likelihood: the sum of loglikelihoods in logs 
     */
    private double totalLogLikelihood(HashMap<String,Double> logs)
    {

        double key=0;
        //iterating the hash
        for(HashMap.Entry<String,Double> entry:logLikelihoodMap.entrySet())
        {    
            //summing the key
            key+=entry.getValue(); 
        }  
        return key;
    }

    
    /**
     * @return the average log likelihood statistic
     */
    public double getAverageLogLikelihood() 
    {
        return averageLogLikelihood;
    }
    
    /**
     * @return the log likelihood value for a given ngram from the input string
     */
    public double getLogLikelihood(String ngram) 
    {
        return (logLikelihoodMap.get(ngram));
    }
    
    
    /**
     * Make a String summarising the log likelihood map and its statistics
     * @return String of ngrams and their loglikeihood differences between the models
     * The likelihood table should be ordered from highest to lowest likelihood
     */
    public String toString() 
   {
        String s="";
        s+=Double.toString(averageLogLikelihood)+"\n";
        //iterating map
        for(HashMap.Entry<String,Double> entry:logLikelihoodMap.entrySet())
        {    
            //adding each key and value to string
            s+=entry.getKey()+"-";
            s+=Double.toString(entry.getValue())+"\n";
        }  
       return s;
    }
}
