/**
 * Author: Anita Nelliat
 * Last Modified: April 3rd, 2020
 *
 * This POJO is used to perform the
 * conversion of JSON to Java Object
 * using the Jackson library
 */
package edu.cmu.andrew.anelliat.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private int responseCode;
    private List<Question> questions;

    /**
     * Default constructor of the Response class
     * It initializes the questions list.
     */
    public Response() {
        this.questions = new ArrayList<>();
    }

    /**
     * Parameterized constructor of the Response class
     * @param responseCode
     * @param questions
     */
    public Response(int responseCode, List<Question> questions) {
        this.responseCode = responseCode;
        this.questions = questions;
    }

    /**
     * Getter of the response code
     *
     * Note: We do not want to include
     * this when we convert the response object
     * to JSON string. Hence @JsonIgnore is used.
     * @return int
     */
    @JsonIgnore
    public int getResponseCode() {
        return responseCode;
    }

    /**
     * Setter of the response code.
     * The input is converted into the
     * appropriate HTTP error code
     *
     * Note: @JsonProperty as 'response_code' has been
     * specified for the method in order to map the
     * incoming appropriate item from 3rd party API request
     * to the responseCode variable
     * @param responseCode
     */
    @JsonProperty("response_code")
    public void setResponseCode(int responseCode) {
        switch (responseCode) {
            case 0:
            case 1:
                this.responseCode = 200;
                break;
            case 2:
                this.responseCode = 400;
                break;
            default:
                this.responseCode = responseCode;
        }

    }

    /**
     * Getter of the questions variable.
     *
     * Note: Since this variable is has a different
     * JsonProperty on Set (see below)
     * It must be specified for converting Object to
     * JSON. Hence we have @JsonProperty as 'questions' for the
     * method
     * @return List<Question>
     */
    @JsonProperty("questions")
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * Setter of the questions variable
     *
     * Note: @JsonProperty as 'results' has been
     * specified for the method in order to map the
     * incoming appropriate item from 3rd party API request
     * to the questions variable
     * @param questions
     */
    @JsonProperty("results")
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    /**
     * The toString method overridden for the
     * Response Class
     * @return String
     */
    @Override
    public String toString() {
        return "Response{" +
                "responseCode=" + responseCode +
                ", questions=" + questions +
                '}';
    }
}
