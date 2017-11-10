import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Scanner;
import java.io.*;

/** Create and manipulate Markov models and model matchers for lists of training data 
 * a test data String and generate output from it for convenient display.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 *
 */
public class MatcherController {

    /** list of training data string used to generate markov models */
    ArrayList<String> trainingDataList;
    /** test data to be matched with the models */
    String testData;
    /** order of the markov models*/
    int k;
    /** generated list of markov models for the given training data*/
    ArrayList<MarkovModel> modelList;
    /** generated list of matchers for the given markov models and test data*/
    ArrayList<ModelMatcher> matcherList;
   

    /** Generate models for analysis
     * @param k order of the markov models to be used
     * @param testData String to check against different models
     * @throw unchecked exceptions if the input order or data inputs are invalid
     */
    public MatcherController(int k, ArrayList<String> trainingDataList, String testData) 
    {

        int i;
        //initialising the variables
        this.k=k;
        this.testData = testData;
        modelList = new ArrayList<MarkovModel>();
        matcherList = new ArrayList<ModelMatcher>();
        //inserting the markovModel in arraylist
        for(i=0;i<trainingDataList.size();i++)
        {
            //initialising the markov model
            MarkovModel m = new MarkovModel(k,trainingDataList.get(i));
            modelList.add(m);
        }
        //isnerting the Modelmatch for each markovModel and test string into arraylist
        for(i=0;i<modelList.size();i++)
        {
            ModelMatcher match = new ModelMatcher(modelList.get(i),testData);
            matcherList.add(match);
        }
    }

 

    /** @return a string containing all lines from a file 
     * ff file contents can be got, otherwise null
     * This method should process any exceptions that arise.
     */
    private  static String getFileContents(String filename) {

        //reading input from file
        try 
        {
            String s="";
            File file = new File(filename);
            Scanner input = new Scanner(file);
            //reading input line by line 
            while (input.hasNextLine()) 
            {
                //adding line to string s
                s+=input.nextLine();
            }
            input.close();
            //returning the string s
            return  s;
        }
        catch (Exception ex) 
        {
            ex.printStackTrace();
            System.out.println("File not found.");
        } 
        return null;
    }

 

    /**
     * @return the ModelMatcher object that has the highest average loglikelihood
     * (where all candidates are trained for the same test string
     */
    public ModelMatcher getBestMatch(ArrayList<ModelMatcher> candidates) 
    {

        int j=0,i;
        //iterating over array list and finding the best match
        for(i=0;i<matcherList.size();i++)
        {
            if(matcherList.get(i).getAverageLogLikelihood()>matcherList.get(j).getAverageLogLikelihood())
                j=i;
        }
        //returning he best match
        return matcherList.get(j);
    }

    /** @return String an *explanation* of
     * why the test string is the match from the candidate models
     */
    public String explainBestMatch(ModelMatcher best) {
        StringBuffer sb = new StringBuffer("Result of match: ");
        sb.append(best.toString());
        return sb.toString();
    }

    /** Display an error to the user in a manner appropriate
     * for the interface being used.
     * 
     * @param message
     */
    public void displayError(String message) {
        return;
    }

}
