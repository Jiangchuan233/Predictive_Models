import java.util.Set;

public class MarkovModel
{

    /** Markov model order parameter */
    int k; 
    /** ngram model of order k */
    NgramAnalyser ngram; 
    /** ngram model of order k+1 */
    NgramAnalyser n1gram; 

    /**
     * Construct an order-k Markov model from string s
     * @param k int order of the Markov model
     * @param s String input to be modelled
     */
    public MarkovModel(int k, String s) 
    {
        this.k=k;
        ngram = new NgramAnalyser(k,s);
        n1gram = new NgramAnalyser(k+1,s);
    }

    /**
     * @return order of this Markov model
     */
    public int getK()
    {
        return k;
    }

    /** Estimate the probability of a sequence appearing in the text 
     * using simple estimate of freq seq / frequency front(seq).
     * @param sequence String of length k+1
     * @return double probability of the last letter occuring in the 
     * context of the first ones or 0 if front(seq) does not occur.
     */
    public double simpleEstimate(String sequence) {

        if(n1gram.getNgramFrequency(sequence)==0)
            return 0;
        return (double)n1gram.getNgramFrequency(sequence)/(ngram.getNgramFrequency(sequence.substring(0,k)));

    }
    /**
     * Calculate the Laplacian probability of string obs given this Markov model
     * @input sequence String of length k+1
     */
    public double laplaceEstimate(String sequence) 
    { 

        double upper_part = n1gram.getNgramFrequency(sequence)+1;
        double lower_part = ngram.getNgramFrequency(sequence.substring(0,k))+ngram.getAlphabetSize();
        return upper_part/lower_part;
    }

    /**
     * @return String representing this Markov model
     */
    public String toString()
    {

        int i = n1gram.getAlphabetSize();
        return "k = "+Integer.toString(k)+"\n Alphabet Size = "+i+"\n"+ngram+"\n"+n1gram;
    }

}
