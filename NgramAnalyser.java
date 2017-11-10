import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

/**
 * Perform n-gram analysis of a string.
 * 
 * Analyses the frequency with which distinct n-grams, of length n,
 * appear in an input string. For the purposes of all analyses of the input
 * string, the final n-1 n-grams appearing in the string should be
 * "filled out" to a length of n characters, by adding
 * a sequence of contiguous characters from the start of the string.
 * e.g. "abbc" includes "bca" and "cab" in its 3-grams
 * 
 * 
 */
public class NgramAnalyser
{
    /** dictionary of all distinct n-grams and their frequencies */
    private HashMap<String,Integer> ngram;

    /** number of distinct characters in the input */
    private int alphabetSize;

    /** n-gram size for this object (new field) */
    private int ngramSize;

    /** 
     * Analyse the frequency with which distinct n-grams, of length n,
     * appear in an input string. 
     * n-grams at the end of the string wrap to the front
     * e.g. "abbbbc" includes "bca" and "cab" in its 3-grams
     * @param int n size of n-grams to create
     * @param String inp input string to be modelled
     */
    public NgramAnalyser(int n, String inp) 
    { 
        if(inp==null || n<=0 || n>inp.length())
            return;
        //iniliasing the HashMap
        ngram = new HashMap<String,Integer>();
        int i,j,l;
        l=inp.length();
        //finding ngrams
        for(i=0;i<l;i++)
        {
            String s="";
            //making string of length n from position i
            for(j=i;j<i+n;j++)
            {
                s+=inp.charAt((j)%l);
            }
            //if string is alread in hashMap increase count
            if(ngram.containsKey(s)==true)
            {
                ngram.put(s,ngram.get(s)+1);
            } 
            //else add string with count q   
            else
                ngram.put(s,1);
        }
        //array arr to mark the character we have seen already
        int[] arr = new int[255];
        for(i=0;i<255;i++)
            arr[i]=0;
        //set alphabestSize =0
        alphabetSize=0;

        for(i=0;i<l;i++)
        {
            //increase the alphabetSize by 1 if character is not seen
            if(arr[inp.charAt(i)]==0)
                alphabetSize++;
            //Mark the charater as seen
            arr[inp.charAt(i)]=1;
        }
    }

    /** 
     * Analyses the input text for n-grams of size 1.
     */
    public NgramAnalyser(String inp) 
    {
        this(1,inp);
    }

    /**
     * @return int the size of the alphabet of a given input
     */
    public int getAlphabetSize() {
        //returing alphabetSize
        return alphabetSize;
    }

    /**
     * @return the total number of distinct n-grams appearing
     *         in the input text.
     */
    public int getDistinctNgramCount() {
        return ngram.size();
    }

    /** 
     * @return Return a set containing all the distinct n-grams
     *         in the input string.
     */
    public Set<String> getDistinctNgrams() {
        Set<String> s = new HashSet<String>();
        //iteratring map
        for(HashMap.Entry<String,Integer> entry:ngram.entrySet())
        {    
            String key=entry.getKey(); 
            //adding key to set 
            s.add(key);
        }    
        //returing set
        return s;
    }

    /**
     * @return the total number of n-grams appearing
     *         in the input text (not requiring them to be distinct)
     */
    public int getNgramCount() {
        int key=0;
        //iterating map
        for(HashMap.Entry<String,Integer> entry:ngram.entrySet())
        {    
            //adding count of each string to key
            key+=entry.getValue();  
        } 
        //returing key
        return key;
    }

    /** Return the frequency with which a particular n-gram appears
     * in the text. If it does not appear at all, return 0.
     * 
     * @param ngram The n-gram to get the frequency of
     * @return The frequency with which the n-gram appears.
     */
    public int getNgramFrequency(String gram) {
        //if string not in hash return 0
        if(ngram.containsKey(gram)==false)
            return 0;
        //return count of string
        return ngram.get(gram);
    }
    
    /**
     * Generate a summary of the ngrams for this object.
     * @return a string representation of the n-grams in the input text 
     * comprising the ngram size and then each ngram and its frequency
     * where ngrams are presented in alphabetical order.     
     */
    public String toString()
    {
        String s="";
        //iterating map
        for(HashMap.Entry<String,Integer> entry:ngram.entrySet())
        {    
            //adding each key and value to string
            s+=entry.getKey()+"-";
            s+=Integer.toString(entry.getValue())+", ";
        }  
        //returing string
        return s;
    }

}
