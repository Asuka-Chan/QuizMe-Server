/**
 * Author: Anita Nelliat
 * Last Modified: April 3rd, 2020
 * <p>
 * This program is the servlet for
 * the web server application.
 * All requests to the server are handled
 * by this program which directs them to the
 * appropriate method/class.
 * This web server only processes GET requests.
 * The requests accepted are - /getQuestions, /getCategories
 * and /openDashboard
 **/
package edu.cmu.andrew.anelliat.controller;

import edu.cmu.andrew.anelliat.logger.DbLogger;
import edu.cmu.andrew.anelliat.utility.QuizMeUtility;
import org.apache.catalina.connector.ResponseFacade;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;


@WebServlet(name = "QuizMeServlet", urlPatterns = {"/getQuestions", "/getCategories", "/openDashboard"})
public class QuizMeServlet extends HttpServlet {

    private QuizMeUtility quizMeUtility;
    public static DbLogger dbLogger;

    /**
     * This overridden init() method
     * is used perform the initialization
     * of MongoDB connection and
     * retrieve the categories for the
     * quiz.
     * It also initializes the quizMeUtility
     * object.
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        super.init();
        quizMeUtility = new QuizMeUtility();
        dbLogger = new DbLogger();
        quizMeUtility.setCategories();
    }

    /**
     * Method to perform the appropriate GET
     * request actions. Logging of the appropriate
     * details from the request are also performed.
     * Based on the urlPattern, one of the below actions
     * are performed:
     * '/openDashboard' - opens the dashboard.jsp page containing
     * logs and operational statistics in a tabular format
     * '/getCategories' - fetches a list of available categories for the quiz
     * '/getQuestions' - fetches a list of questions and corresponding details
     * such as the answer, options and score.
     *
     * @param request  - HttpServletRequest
     * @param response - HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Note the time the request was received
        long start = System.currentTimeMillis();
        //Get the url pattern
        String urlPattern = request.getHttpServletMapping().getPattern();
        /*
            if the pattern equals /openDashboard then call the log
            operation statistic functions. Set them into the request Object
            and forward the view to the 'dashboard.jsp'
        */
        if (urlPattern.equals("/openDashboard")) {
            request.setAttribute("logs", dbLogger.getLogs());
            request.setAttribute("mostSelectedCat", dbLogger.getMostSelectedCategories());
            request.setAttribute("sizeAndTimeStats", dbLogger.getSizeAndResponseTimeStats());
            request.setAttribute("androidStats", dbLogger.getAndroidUserAgentStats());
            RequestDispatcher view = request.getRequestDispatcher("dashboard.jsp");
            view.forward(request, response);
        } else {
            // initialize a log for the request.
            dbLogger.initializeLog();
            // Set log detail - Time Request was received
            dbLogger.getLog().setTimeReceived(new Timestamp(start));
            // Set log detail - user agent
            dbLogger.getLog().setUserAgent(request.getHeader("User-Agent"));
            // Set log detail - request url
            dbLogger.getLog().setAppRequest(request.getRequestURL().toString());

            String apiResponse = null;

            if (urlPattern.equals("/getQuestions")) {
                // Call the getQuestions method and pass the request parameters - difficulty, category and amount
                apiResponse = quizMeUtility.getQuestions(request.getParameter("difficulty"), request.getParameter("category"), request.getParameter("amount"));
            } else if (urlPattern.equals("/getCategories")) {
                // Call the getCategories method
                apiResponse = quizMeUtility.getCategories();
            } else {
                // If the request does not fall under either,
                // then set the response status as 404
                apiResponse = "404";
                response.setStatus(404);
            }
            PrintWriter out = response.getWriter();
            // Write the response to the out buffer only if apiResponse
            // is not null or 404
            if (apiResponse != null && !apiResponse.equals("404")) {
                out.println(apiResponse);
                response.setStatus(200);
            }
            // If apiResponse is null, set response status as 500
            else if (apiResponse == null) {
                response.setStatus(500);
            }

            // tell the client the type of the response
            response.setContentType("application/json;charset=UTF-8");

            // Set log detail - server response time
            dbLogger.getLog().setAppResponseTime(System.currentTimeMillis() - start);
            // Set log detail - Size of the response
            dbLogger.getLog().setResponseSize(((ResponseFacade) response).getContentWritten());
            // Set log detail - response status
            dbLogger.getLog().setResponseStatus(response.getStatus());
            // Insert the log into the DB
            dbLogger.log();
        }
    }
}
