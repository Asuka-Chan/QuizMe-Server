/**
 * Author: Anita Nelliat
 * Last Modified: April 3rd, 2020
 * <p>
 * This class consists of functions to
 * interact with the 3rd party API and convert
 * the response into an appropriate format
 * to send to the Android App.
 */
package edu.cmu.andrew.anelliat.utility;

import edu.cmu.andrew.anelliat.controller.QuizMeServlet;
import edu.cmu.andrew.anelliat.pojo.Categories;
import edu.cmu.andrew.anelliat.pojo.Category;
import edu.cmu.andrew.anelliat.pojo.Response;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class QuizMeUtility {

    private static final String QUESTIONS_URL = "https://opentdb.com/api.php?amount=";
    private static final String CATEGORIES_URL = "https://opentdb.com/api_category.php";
    private static final String CATEGORY = "&category=";
    private static final String DIFFICULTY = "&difficulty=";

    private Map<String, Integer> categoryMap = new HashMap<>();


    /**
     * Method to set the categories.
     * This method is called only once on initialization
     * of the QuizMeServlet.
     */
    public void setCategories() {
        //Retrieve the categories from the API
        String response = retrieveResponse(CATEGORIES_URL);
        //Construct an objectMapper object from the Jackson Library
        ObjectMapper mapper = new ObjectMapper();
        try {
            //Map the response to the Categories class
            Categories categories = mapper.readValue(response, Categories.class);
            //Iterate over the categories and put them into the map
            for (Category category : categories.getCategories()) {
                //All category names are unique, hence name can be the key for the map
                categoryMap.put(category.getName(), category.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to create the category map");
        }
    }

    /**
     * Method to getCategories of the quiz
     * @return String - JSON String representation of List of categories
     */
    public String getCategories() {
        Set<String> categories = null;
        //If the categoryMap is not null, then retrieve the keys()
        if (categoryMap != null) {
            categories = new TreeSet<>(categoryMap.keySet());
        }
        //Add 'All of them!' to the category map
        categories.add("All of them!");
        //Create a map object to place the categories into
        //This will allow for an easier way to convert the
        //list of categories into a JSON string
        Map<String, Object> jsonObject = new HashMap<>();
        jsonObject.put("categories", categories);

        //return the JSON String representation of the jsonObject
        return toJsonString(jsonObject);
    }

    /**
     * Method to convert an Object into
     * a JSON String
     * @param response - Object
     * @return String - JSON representation of the object
     */
    private String toJsonString(Object response) {
        //Construct an objectMapper object from the Jackson Library
        ObjectMapper mapper = new ObjectMapper();
        try {
            //Call the writeValueAsString method to convert the response
            //to a String
            return mapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Error while converting object to JSON String");
            //Return null on error
            return null;
        }
    }

    /**
     * Method to generate the appropriate getQuestions
     * URL and pass it on to the retrieveResponse function.
     *
     * @param difficulty - user input difficulty
     * @param category  - user input category
     * @param amount - user input no. of questions
     * @return String - JSON String of list of questions
     */
    public String getQuestions(String difficulty, String category, String amount) {
        // If the amount is blank put 10 as default
        String url = QUESTIONS_URL + (StringUtils.isBlank(amount) ? "10" : amount);
        // If the category is null or does not exist in categoryMap
        // do not set the value as a parameter.
        if (category != null && categoryMap.containsKey(category)) {
            // Else set category as a parameter
            url += CATEGORY + categoryMap.get(category);
        }
        // If the difficulty is null or does not anything
        // do not set the value as a parameter.
        if (difficulty != null && !difficulty.contains("anything")) {
            // Else set difficulty as a parameter
            url += DIFFICULTY + difficulty.toLowerCase();
        }

        //Set the log detail - API Request URL
        QuizMeServlet.dbLogger.getLog().setApiRequestUrl(url);
        //Set the log detail - selected Category
        QuizMeServlet.dbLogger.getLog().setSelectedCategory(category);
        //Set the log detail - selected difficulty
        QuizMeServlet.dbLogger.getLog().setSelectedDifficulty(difficulty);
        //Set the log detail - noOfQuestions
        QuizMeServlet.dbLogger.getLog().setNoOfQuestions(StringUtils.isBlank(amount) ? 0 : Integer.parseInt(amount));

        //Retrieve the response and convert it to the Response POJO
        Response response = jsonToPojo(retrieveResponse(url));
        //Convert the POJO into a JSON String and return
        return toJsonString(response);
    }

    /**
     * Method to connect to the 3rd Party API
     * and fetch the response
     * @param link - HTTP Url to hit
     * @return String - the JSON response String
     */
    private String retrieveResponse(String link) {
        //Note the time when the request call started
        long start = System.currentTimeMillis();
        String response = "";
        try {
            // pass the name on the URL line
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // tell the server what format we want back
            conn.setRequestProperty("Accept", "text/plain");

            // wait for response
            int status = conn.getResponseCode();
            //Log the time taken to receive the response
            QuizMeServlet.dbLogger.getLog().setApiResponseTime(System.currentTimeMillis() - start);

            // If things went poorly, don't try to read any response, just return.
            if (status != 200) {
                return null;
            }
            String output = "";
            // things went well so let's read the response
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            while ((output = br.readLine()) != null) {
                response += output;

            }
            conn.disconnect();

            return response;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("An error has occurred due to malformed URL");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error has occurred. See stack trace");
        }
        //Return null if an error has occurred
        return null;
    }

    /**
     * Method to convert a JSON to the
     * Response class object.
     * @param requestString - the JSON String
     * @return Response - The JSON mapped Response Object
     */
    public Response jsonToPojo(String requestString) {
        //Preprocess the JSON response
        requestString = preprocessResponse(requestString);
        //Construct an ObjectMapper object from the Jackson library
        ObjectMapper mapper = new ObjectMapper();
        //Configure the mapper to allow single quotes '
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        Response response = null;
        try {
            //Try converting the JSON to the Response Object
            response = mapper.readValue(requestString, Response.class);
        } catch (IOException e) {
            //Print error if exception occurs
            e.printStackTrace();
            System.out.println("An error has occurred while converting JSON to Response Object");
        }
        return response;
    }

    /**
     * Method to preprocess the API request
     * by replacing the HTML encoded special characters
     * with the appropriate character.
     * @param requestString - the JSON response String
     * @return String - the cleaned up JSON String
     */
    private String preprocessResponse(String requestString) {
        requestString = requestString.replace("&#039;", "'");
        requestString = requestString.replace("&quot;", "'");
        requestString = requestString.replace("&eacute;", "é");
        requestString = requestString.replace("&aacute;", "á");
        requestString = requestString.replace("&amp;", "&");
        requestString = requestString.replace("&shy;", "-");
        requestString = requestString.replace("&ocirc;", "ô");
        requestString = requestString.replace("&rsquo;", "’");
        requestString = requestString.replace("&Delta;", "Δ");
        requestString = requestString.replace("&Uuml;", "Ü");
        requestString = requestString.replace("&oacute;", "ó");
        requestString = requestString.replace("&Aring;", "Å");
        requestString = requestString.replace("&iacute;", "í");
        requestString = requestString.replace("&epsilon;", "ε");
        requestString = requestString.replace("&Phi;", "Φ");
        requestString = requestString.replace("&euml;", "ë");
        requestString = requestString.replace("&deg;", "°");
        requestString = requestString.replace("&ntilde;", "ñ");
        requestString = requestString.replace("&prime;", "′");
        requestString = requestString.replace("&Prime;", "″");
        requestString = requestString.replace("&sup2;", "²");
        requestString = requestString.replace("&ouml;", "ö");
        requestString = requestString.replace("&Eacute;", "É");
        return requestString;
    }

}
