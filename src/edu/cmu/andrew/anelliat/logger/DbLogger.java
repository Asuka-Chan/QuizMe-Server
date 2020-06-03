/**
 * Author: Anita Nelliat
 * Last Modified: April 3rd, 2020
 *
 * This program contains the connection
 * and query logic of MongoDB.
 * It consists of an instance of the Log
 * POJO which is used by the server to insert
 * logs into the database.
 * The queries used to retrieve the logs
 * and other operational statistics
 * are preset in the class.
 * */
package edu.cmu.andrew.anelliat.logger;

import edu.cmu.andrew.anelliat.pojo.Log;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.ne;
import static com.mongodb.client.model.Filters.regex;

public class DbLogger {

    //We do not want the possibility of external modification to the collection.
    //Hence there are no getters/setters for logCollection
    private MongoCollection<Document> logCollection;
    private Log log;
    private static final String DB_URL ="mongodb+srv://nelli:nellikai@dsproject4cluster-raad1.mongodb.net/test?retryWrites=true&w=majority";
    private static final String DB_NAME = "project4db";
    private static final String LOG_COLLECTION = "serverLogs";

    /**
     * Default constructor of the DbLogger
     * class.
     * This constructor performs 2 initializations:
     * 1) makes the connection between
     *    the webServer and MongoDB.
     * 2) initializes the instance of
     *    the log pojo
     */
    public DbLogger() {
        initializeDB();
        initializeLog();
    }

    /**
     * Method to construct
     * the log instance.
     */
    public void initializeLog() {
        log = new Log();
    }

    /**
     * Method to return the
     * log instance
     * @return Log
     */
    public Log getLog() {
        return log;
    }

    /**
     * Method to initialize the connection
     * between the server and the mongoDB
     * database.
     */
    private void initializeDB() {
        //Create a MongoClient uri from the DB_URL
        MongoClientURI uri = new MongoClientURI(DB_URL);
        //Construct the MongoClient with the uri. This will
        //proceed to establish a connection with the MongoDB
        MongoClient mongoClient = new MongoClient(uri);
        //Get the database - project4db and get the serverLogs collection.
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        logCollection = database.getCollection(LOG_COLLECTION);
    }

    /**
     * Method to insert the log into the collection.
     */
    public void log() {
        Document doc = new Document().append("appRequest", log.getAppRequest()).append("timeReceived", log.getTimeReceived())
                .append("noOfQuestions", log.getNoOfQuestions()).append("selectedCategory", log.getSelectedCategory())
                .append("selectedDifficulty", log.getSelectedDifficulty()).append("userAgent", log.getUserAgent())
                .append("apiRequestUrl", log.getApiRequestUrl()).append("apiResponseTime", log.getApiResponseTime())
                .append("responseSize", log.getResponseSize()).append("appResponseTime", log.getAppResponseTime())
                .append("responseStatus", log.getResponseStatus());
        logCollection.insertOne(doc);
    }

    /**
     * Method to retrieve all the logs
     * from the collection.
     * @return List<Document>
     */
    public List<Document> getLogs() {
        List<Document> logs = new ArrayList<>();
        //Retrieve all logs from the collection
        for (Document cur : logCollection.find()) {
            logs.add(cur);
        }
        return logs;
    }

    /**
     * Method to get the top 15 selected categories by the
     * user. The count of such requests and the total number of
     * questions is also found.
     * @return List<Document>
     */
    public List<Document> getMostSelectedCategories() {
        //Perform the aggregate function in order to be able
        //to group the collection on selectedCategory.
        //Ref: https://www.programcreek.com/java-api-examples/?api=com.mongodb.client.model.Aggregates
        AggregateIterable<Document> documents
                = logCollection.aggregate(
                Arrays.asList(
                        //Ensure that null category values are not included
                        Aggregates.match(ne("selectedCategory", null)),
                        Aggregates.group("$selectedCategory",
                                //Determine the number of specific category requests
                                Accumulators.sum("count", 1),
                                //Determine the total number of questions for the category
                                Accumulators.sum("noOfQuest", "$noOfQuestions")),
                        //Sort by highest to lowest count
                        Aggregates.sort(Sorts.descending("count"))
                ));
        List<Document> documentList = new ArrayList<>();
        int count = 0;
        //Convert AggregateIterable to List of Documents
        for (Document doc : documents) {
            documentList.add(doc);
            count++;
            //Return only the first 15 documents
            if(count == 15){
                break;
            }
        }
        return documentList;
    }

    /**
     * Method to get the Response size and Server plus 3rd Party
     * API response time statistics - Maximum, Minimum and Average
     * for Request - GetCategories and GetQuestions
     * @return List<Document>
     */
    public List<Document> getSizeAndResponseTimeStats() {
        //Perform the aggregate function in order to be able
        //to group the collection on appRequest.
        //Ref: https://www.programcreek.com/java-api-examples/?api=com.mongodb.client.model.Aggregates
        AggregateIterable<Document> documents
                = logCollection.aggregate(
                Arrays.asList(
                        Aggregates.group("$appRequest",
                                //Find the maximum values for size, app and api response time
                                Accumulators.max("apiTimeMax", "$apiResponseTime"),
                                Accumulators.max("sizeMax", "$responseSize"),
                                Accumulators.max("appTimeMax", "$appResponseTime"),
                                //Find the minimum values for size, app and api response time
                                Accumulators.min("apiTimeMin", "$apiResponseTime"),
                                Accumulators.min("sizeMin", "$responseSize"),
                                Accumulators.min("appTimeMin", "$appResponseTime"),
                                //Find the average values for size, app and api response time
                                Accumulators.avg("apiTimeAvg", "$apiResponseTime"),
                                Accumulators.avg("sizeAvg", "$responseSize"),
                                Accumulators.avg("appTimeAvg", "$appResponseTime")),
                        Aggregates.sort(Sorts.ascending("_id"))
                ));
        List<Document> documentList = new ArrayList<>();
        //Convert AggregateIterable to List of Documents
        for (Document doc : documents) {
            documentList.add(doc);
        }
        return documentList;
    }

    /**
     * Method to retrieve the top 5 android
     * phone models making the request to the
     * web server.
     * @return List<Document>
     */
    public List<Document> getAndroidUserAgentStats() {
        //Perform the aggregate function in order to be able
        //to group the collection on userAgent.
        //Ref: https://www.programcreek.com/java-api-examples/?api=com.mongodb.client.model.Aggregates
        AggregateIterable<Document> documents
                = logCollection.aggregate(
                Arrays.asList(
                        //Match the userAgent on pattern .*Android.*
                        Aggregates.match(regex("userAgent", ".*Android*")),
                        Aggregates.group("$userAgent",
                                //Count the number of requests from device model
                                Accumulators.sum("total", 1)),
                        Aggregates.sort(Sorts.descending("total"))
                ));
        int count =0;
        List<Document> documentList = new ArrayList<>();
        //Convert AggregateIterable to List of Documents
        for (Document doc : documents) {
            documentList.add(doc);
            count++;
            //Return only the first 5
            if(count == 5){
                break;
            }
        }
        return documentList;
    }
}
