# Stocks

This demo implements classes for managing stock data. It contains a local database 'stocks' as well a Yahoo Finance stocks api.

Included is a web GUI by adding servlet and javabean classes for searching stock quotes from one of three data sources (database; web/yahoo) executing off of a Tomcat web server.

Database is accessed via Hibernate database configuration relying on the existence of a database called 'stocks' granting all privileges to user 'monty' with password 'some_pass'.

====
RESTful web app

- A web app that displays stock data from a local database or Yahoo Stocks API.
- Developed using Java as a back-end with Hibernate to access local database with MySQL
- Implemented a web GUI by adding Java servlets and JavaBeans executing off of a Tomcat web server.

===
*Currently Yahoo Stocks api is discontinued and not for public use.
