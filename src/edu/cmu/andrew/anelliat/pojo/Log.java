/**
 * Author: Anita Nelliat
 * Last Modified: April 3rd, 2020
 *
 * This POJO represents the values to
 * be inserted while logging.
 * See comments above variables to understand the
 * values being logged.
 */
package edu.cmu.andrew.anelliat.pojo;

import java.sql.Timestamp;

public class Log {

    // The Android App Request URL
    private String appRequest;
    // The time request was received
    private Timestamp timeReceived;
    // The selected category - parameter in request
    private String selectedCategory;
    // The selected difficulty - parameter in request
    private String selectedDifficulty;
    // The input amount of questions - parameter in request
    private int noOfQuestions;
    // The user-agent of the request
    private String userAgent;
    // The 3rd party API request URL
    private String apiRequestUrl;
    // The time taken by the 3rd party API to respond
    private long apiResponseTime;
    // The size of the response sent back to the Android app
    private long responseSize;
    // The status of the response sent back to the app
    private int responseStatus;
    // The time taken to respond to the app after receiving the request
    private long appResponseTime;

    /**
     * Default constructor of the Log class
     */
    public Log() {}

    /**
     * Getter of the appRequest variable
     * @return String
     */
    public String getAppRequest() {
        return appRequest;
    }

    /**
     * Setter of the appRequest variable
     * @param appRequest
     */
    public void setAppRequest(String appRequest) {
        this.appRequest = appRequest;
    }

    /**
     * Getter of the timeReceived variable
     * @return TimeStamp
     */
    public Timestamp getTimeReceived() {
        return timeReceived;
    }

    /**
     * Setter of the timeReceived variable
     * @param timeReceived
     */
    public void setTimeReceived(Timestamp timeReceived) {
        this.timeReceived = timeReceived;
    }

    /**
     * Getter of the selectedCategory variable
     * @return String
     */
    public String getSelectedCategory() {
        return selectedCategory;
    }

    /**
     * Setter of the selectedCategory variable
     * @param selectedCategory
     */
    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    /**
     * Getter of the selectedDifficulty variable
     * @return String
     */
    public String getSelectedDifficulty() {
        return selectedDifficulty;
    }

    /**
     * Setter of the selectedDifficulty variable
     * @param selectedDifficulty
     */
    public void setSelectedDifficulty(String selectedDifficulty) {
        this.selectedDifficulty = selectedDifficulty;
    }

    /**
     * Getter of the noOfQuestions variable
     * @return int
     */
    public int getNoOfQuestions() {
        return noOfQuestions;
    }

    /**
     * Setter of the noOfQuestions variable
     * @param noOfQuestions
     */
    public void setNoOfQuestions(int noOfQuestions) {
        this.noOfQuestions = noOfQuestions;
    }

    /**
     * Getter of the userAgent variable
     * @return String
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * Setter of the userAgent variable
     * @param userAgent
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * Getter of the apiRequestUrl variable
     * @return String
     */
    public String getApiRequestUrl() {
        return apiRequestUrl;
    }

    /**
     * Setter of the apiRequestUrl variable
     * @param apiRequestUrl
     */
    public void setApiRequestUrl(String apiRequestUrl) {
        this.apiRequestUrl = apiRequestUrl;
    }

    /**
     * Getter of the apiResponseTime variable
     * @return long
     */
    public long getApiResponseTime() {
        return apiResponseTime;
    }

    /**
     * Setter of the apiResponseTime variable
     * @param apiResponseTime
     */
    public void setApiResponseTime(long apiResponseTime) {
        this.apiResponseTime = apiResponseTime;
    }

    /**
     * Getter of the responseSize variable
     * @return long
     */
    public long getResponseSize() {
        return responseSize;
    }

    /**
     * Setter of the responseSize variable
     * @param responseSize
     */
    public void setResponseSize(long responseSize) {
        this.responseSize = responseSize;
    }

    /**
     * Getter of the appResponseTime variable
     * @return long
     */
    public long getAppResponseTime() {
        return appResponseTime;
    }

    /**
     * Setter of the appResponseTime variable
     * @param appResponseTime
     */
    public void setAppResponseTime(long appResponseTime) {
        this.appResponseTime = appResponseTime;
    }

    /**
     * Getter of the responseStatus variable
     * @return String
     */
    public int getResponseStatus() {
        return responseStatus;
    }

    /**
     * Setter of the responseStatus variable
     * @param responseStatus
     */
    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    /**
     * The toString method overridden for the
     * Log Class
     * @return String
     */
    @Override
    public String toString() {
        return "Log{" +
                "appRequest='" + appRequest + '\'' +
                ", timeReceived=" + timeReceived +
                ", selectedCategory='" + selectedCategory + '\'' +
                ", selectedDifficulty='" + selectedDifficulty + '\'' +
                ", noOfQuestions=" + noOfQuestions +
                ", userAgent='" + userAgent + '\'' +
                ", apiRequestUrl='" + apiRequestUrl + '\'' +
                ", apiResponseTime=" + apiResponseTime +
                ", responseSize=" + responseSize +
                ", responseStatus=" + responseStatus +
                ", appResponseTime=" + appResponseTime +
                '}';
    }
}
