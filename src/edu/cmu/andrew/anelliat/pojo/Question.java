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
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public class Question {

    private String category;
    private String type;
    private String difficulty;
    private String question;
    private String answer;
    private List<String> options;
    private int score;

    /**
     * Default constructor of the Question class.
     */
    public Question(){}

    /**
     * Parameterized constructor of the Question class
     * @param category
     * @param type
     * @param difficulty
     * @param question
     * @param answer
     * @param options
     * @param score
     */
    public Question(String category, String type, String difficulty, String question, String answer, List<String> options, int score) {
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.answer = answer;
        this.options = options;
        this.score = score;
    }

    /**
     * Getter of the question variable
     * @return String
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Setter of the question variable
     * @param question
     */
    public void setQuestion(String question) {
        this.question = question;
    }


    /**
     * Getter of the category variable
     * @return String
     */
    public String getCategory() {
        return category;
    }

    /**
     * Setter of the category variable
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }


    /**
     * Getter of the type variable
     * @return String
     */
    public String getType() {
        return type;
    }

    /**
     * Setter of the type variable
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }


    /**
     * Getter of the difficulty variable
     * @return String
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * Setter of the difficulty variable.
     * This calls the setScore() method which
     * is dependant on the difficulty value.
     *
     * @param difficulty
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        this.setScore();
    }


    /**
     * Getter of the score variable.
     *
     * Note: Since this variable is ignored on Set (see below)
     * It must be specified while converting Object to
     * JSON. Hence we have @JsonProperty for the
     * getScore() method
     * @return int
     */
    @JsonProperty("score")
    public int getScore() {
        return score;
    }

    /**
     * Setter of the score variable.
     * This value is set based on the difficulty
     * of the question.
     *
     * Note: This variable is ignored when
     * converting the JSON to the
     * Question object, since it does not exist
     * in the 3rd Party API response.
     */
    @JsonIgnore
    public void setScore() {
        switch (this.difficulty){
            case "easy": this.score = 1; break;
            case "medium": this.score = 5; break;
            case "hard": this.score = 10; break;
        }
    }

    /**
     * Getter of the answer variable.
     *
     * Note: Since this variable is has a different
     * JsonProperty on Set (see below)
     * It must be specified for converting Object to
     * JSON. Hence we have @JsonProperty as 'answer' for the
     * method
     * @return String
     */
    @JsonProperty("answer")
    public String getAnswer() {
        return answer;
    }

    /**
     * Setter for the answer variable
     *
     * Note: @JsonProperty as 'correct_answer' has been
     * specified for the method in order to map the
     * incoming appropriate item from 3rd party API request
     * to the answer variable
     * @param answer
     */
    @JsonProperty("correct_answer")
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Getter of the options variable.
     *
     * Note: Since this variable is has a different
     * JsonProperty on Set (see below)
     * It must be specified for converting Object to
     * JSON. Hence we have @JsonProperty as 'options' for the
     * method
     * @return List<String>
     */
    @JsonProperty("options")
    public List<String> getOptions() {
        return options;
    }

    /**
     * Setter of the options variable.
     * The answer variable is also added to
     * the options list and then shuffled.
     *
     * Note: @JsonProperty as 'incorrect_answers' has been
     * specified for the method in order to map the
     * incoming appropriate item from 3rd party API request
     * to the options variable
     * @param options
     */
    @JsonProperty("incorrect_answers")
    public void setOptions(List<String> options) {
        this.options = options;
        this.options.add(answer);
        Collections.shuffle(this.options);
    }

    /**
     * The toString method overridden for the
     * Question Class
     * @return String
     */
    @Override
    public String toString() {
        return "Question{" +
                "category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", options=" + options +
                ", score=" + score +
                '}';
    }
}
