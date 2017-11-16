<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--

This example JSP uses JTSL rather than scriplets to access data.

In the MoodSurvey servlet, an instance of a Mood is placed in the http session.

The code on this page gets that instance of out the session and uses'
it values to determine what to display to the user.

--%>

<%-- get the Mood instance out of the session context --%>
<jsp:useBean id="search" class="edu.uml.nsay.model.StockSearchBean" scope="session">
    <c:set target='${search}'  value='${sessionScope.get("search")}'/>
</jsp:useBean>

<html>
<head>
    <title>Yahoo Stock Quote Search Result</title>
    <link href="styles.css" rel="stylesheet" type="text/css">
</head>
<body>

Here is the result of your stock quote search: <br>
<c:out value="${search.quotesStr}"/>

</body>
</html>
