<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<jsp:useBean id="search" class="edu.uml.nsay.model.StockSearchBean" scope="request"/>
<jsp:setProperty name="search" property="*"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Yahoo Stock Quote Search</title>
    <link href="styles.css" rel="stylesheet" type="text/css">
</head>
<body>

<h2>
    Enter information your stock information. <br>
</h2>

<P></P>

<form name="myform" action="servlets/StockSearchServlet/" method="post">
    <fieldset>

        <b>Stock Symbol :</b><br> Available Symbols: GOOG, AAPL, AMZN<br>
                                    <input type="text" name="symbol" %><br><br>
        <b>Start Time:</b><br> Please USE this format: yyyy-MM-dd HH:mm:ss (ex. 2000-01-01 00:00:00)<br>
                                    <input type="datetime" name="startRange"><br><br>
        <b>End Time :</b><br> Please USE this format: yyyy-MM-dd HH:mm:ss (ex. 2017-01-01 00:00:00)<br>
                                    <input type="datetime" name="endRange"><br><br>
        <b>Interval :</b><br>      <input type="radio" name="interval" value="DAY" checked> Daily<br>
                                    <input type="radio" name="interval" value="WEEK"> Weekly<br>
                                    <input type="radio" name="interval" value="MONTH"> Monthly<br>
        <b>Data Source :</b><br>   <input type="radio" name="serviceType" value="DATABASE"> Database<br>
                                    (Must Have Database Tables: person, person_stocks, quotes, stock_symbols)<br>
                                    <input type="radio" name="serviceType" value="YAHOO"> Yahoo <br>
                                    (NOTE: Yahoo Service is currently discontinued)<br>
    </fieldset>
    <input type="SUBMIT" value="OK">
    <input type="HIDDEN" name="submit" value="true">
</form>

</body>
</html>