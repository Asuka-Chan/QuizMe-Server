<%--
  Created by IntelliJ IDEA.
  User: Anita Nelliat (anelliat)
  Date: 3/28/2020
  Time: 4:14 PM

  QuizMe Dashboard JSP.
  This JSP contains the HMTL/Java code for
  displaying 3 operational statistics
  and the logs for the QuizMe web server.
--%>
<%@ page import="org.bson.Document" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>QuizMe Usage Dashboard</title>
    <style>
        table {
            font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        td, th {
            border: 1px solid #ddd;
            padding: 8px;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        tr:hover {
            background-color: #ddd;
        }

        th {
            padding-top: 12px;
            padding-bottom: 12px;
            text-align: left;
            background-color: #FF4500;
            color: white;
        }

        #mostSelectedCat th {
            background-color: #04CB9A;
        }

        #androidStats th {
            background-color: #33DCFF;
        }

        #sizeAndTimeStats th {
            background-color: #790933;
        }

    </style>

</head>
<body>
<br>
<br>
<h1 align="center">QuizMe Usage Dashboard</h1>
<br>
<br>
<h3>Top 15 Categories Selected By Users</h3>
<br>
<%--
  Below is the code to create an HTML table
  of the top selected categories for the QuizMe WebServer
--%>
<div>
    <table id="mostSelectedCat" style="width:50%">
        <th>Category</th>
        <th>Number of Requests</th>
        <th>Total No. of Questions Requested</th>
        <%--
          Retreive the data and iterate over it
        --%>
        <% List<Document> categ = (List<Document>) request.getAttribute("mostSelectedCat"); %>
        <% for (int i = 0; i < categ.size(); i++) { %>
        <tr>
            <td><%= categ.get(i).get("_id") %>
            </td>
            <td><%= categ.get(i).get("count") %>
            </td>
            <td><%= categ.get(i).get("noOfQuest") %>
            </td>
        </tr>
        <% } %>

    </table>
</div>
<br>
<br>
<h3>Top 5 Android Phones making Requests</h3>
<br>
<%--
  Below is the code to create an HTML table
  of the androidStats for the QuizMe WebServer
--%>
<div>
    <table id="androidStats" style="width:50%">
        <th>Android Model</th>
        <th>Number of Requests</th>
        <%--
          Retreive the androidStats data and iterate over it.
        --%>
        <% List<Document> androidStats = (List<Document>) request.getAttribute("androidStats"); %>
        <% for (int i = 0; i < androidStats.size(); i++) { %>
        <tr>
            <td><%= androidStats.get(i).get("_id") %>
            </td>
            <td><%= androidStats.get(i).get("total") %>
            </td>
        </tr>
        <% } %>

    </table>
</div>
<br>
<br>
<br>
<h3>Statistics on Response Size and Time by the Server and 3rd Party API</h3>
<br>
<div>
    <table id="sizeAndTimeStats" style="width:95%">
        <th>App Request</th>
        <th>Maximum</th>
        <th>Minimum</th>
        <th>Average</th>
        <%--
          Retreive the sizeAndTime stats data. Since the display format
          contains inner tables, the code below contains the creation of
          the table and its inner table.
        --%>
        <% List<Document> sizeAndTimeStats = (List<Document>) request.getAttribute("sizeAndTimeStats"); %>
        <% if(sizeAndTimeStats.size() >= 2) { %>
        <tr>
            <td><%= sizeAndTimeStats.get(0).get("_id") %>
            </td>
            <td>
                <table>
                    <tr>
                        <td><b>3rd Party Response Time(ms):</b> <%= sizeAndTimeStats.get(0).get("apiTimeMax") %>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Server Response Time(ms):</b> <%= sizeAndTimeStats.get(0).get("appTimeMax") %>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Server Response Size(bytes):</b> <%= sizeAndTimeStats.get(0).get("sizeMax") %>
                        </td>
                    </tr>
                </table>
            </td>
            <td>
                <table>
                    <tr>
                        <td><b>3rd Party Response Time(ms):</b> <%= sizeAndTimeStats.get(0).get("apiTimeMin") %>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Server Response Time(ms):</b> <%= sizeAndTimeStats.get(0).get("appTimeMin") %>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Server Response Size(bytes):</b> <%= sizeAndTimeStats.get(0).get("sizeMin") %>
                        </td>
                    </tr>
                </table>
            </td>
            <td>
                <table>
                    <tr>
                        <td><b>3rd Party Response Time(ms):</b> <%= sizeAndTimeStats.get(0).get("apiTimeAvg") %>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Server Response Time(ms):</b> <%= sizeAndTimeStats.get(0).get("appTimeAvg") %>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Server Response Size(bytes):</b> <%= sizeAndTimeStats.get(0).get("sizeAvg") %>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td><%= sizeAndTimeStats.get(1).get("_id") %>
            </td>
            <td>
                <table>
                    <tr>
                        <td><b>3rd Party Response Time(ms):</b> <%= sizeAndTimeStats.get(1).get("apiTimeMax") %>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Server Response Time(ms):</b> <%= sizeAndTimeStats.get(1).get("appTimeMax") %>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Server Response Size(bytes):</b> <%= sizeAndTimeStats.get(1).get("sizeMax") %>
                        </td>
                    </tr>
                </table>
            </td>
            <td>
                <table>
                    <tr>
                        <td><b>3rd Party Response Time(ms):</b> <%= sizeAndTimeStats.get(1).get("apiTimeMin") %>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Server Response Time(ms):</b> <%= sizeAndTimeStats.get(1).get("appTimeMin") %>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Server Response Size(bytes):</b> <%= sizeAndTimeStats.get(1).get("sizeMin") %>
                        </td>
                    </tr>
                </table>
            </td>
            <td>
                <table>
                    <tr>
                        <td><b>3rd Party Response Time(ms):</b> <%= sizeAndTimeStats.get(1).get("apiTimeAvg") %>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Server Response Time(ms):</b> <%= sizeAndTimeStats.get(1).get("appTimeAvg") %>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Server Response Size(bytes):</b> <%= sizeAndTimeStats.get(1).get("sizeAvg") %>
                        </td>
                    </tr>
                </table>
            </td>
           <% } %>
    </table>
</div>
<br>
<br>
<h3>QuizMe Application Logs</h3>
<br>
<%--
  Below is the code to create an HTML table
  of the logs for the QuizMe WebServer
--%>
<div style="overflow-x:auto;">
    <table id="logs">
        <tr>
            <th>App: Request</th>
            <th>Request Timestamp</th>
            <th>Parameter: No. of Questions</th>
            <th>Parameter: Category</th>
            <th>Parameter: Difficulty</th>
            <th>User Agent</th>
            <th>3rd Party: API URL</th>
            <th>3rd Party: Response Time(ms)</th>
            <th>Server: Response Size(bytes)</th>
            <th>Server: Response Time(ms)</th>
            <th>Server: Response Status</th>
        </tr>
        <%--
          Retreive the logs and iterate over it
        --%>
        <% List<Document> logs = (List<Document>) request.getAttribute("logs"); %>
        <% for (int row = 0; row < logs.size(); row++) { %>
        <tr>
            <td><%= logs.get(row).get("appRequest") %>
            </td>
            <td><%= logs.get(row).get("timeReceived") %>
            </td>
            <td><%= logs.get(row).get("noOfQuestions") %>
            </td>
            <td><%= logs.get(row).get("selectedCategory") %>
            </td>
            <td><%= logs.get(row).get("selectedDifficulty") %>
            </td>
            <td><%= logs.get(row).get("userAgent") %>
            </td>
            <td><%= logs.get(row).get("apiRequestUrl") %>
            </td>
            <td><%= logs.get(row).get("apiResponseTime") %>
            </td>
            <td><%= logs.get(row).get("responseSize") %>
            </td>
            <td><%= logs.get(row).get("appResponseTime") %>
            </td>
            <td><%= logs.get(row).get("responseStatus") %>
            </td>

        </tr>
        <% } %>
    </table>
</div>
</body>
</html>
