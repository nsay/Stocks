package edu.uml.nsay.services;

import edu.uml.nsay.model.StockQuote;
import edu.uml.nsay.utilities.DatabaseConnectionException;
import edu.uml.nsay.utilities.DatabaseUtils;
import org.apache.http.annotation.Immutable;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the StockService interface that gets
 * stock data from a database.
 *
 * @author Narith Say
 * @version 10/02/2017
 */
@Immutable
public final class DatabaseStockService implements StockService {

    /**
     * Return the current price for a share of stock  for the given symbol
     *
     * @param symbol the stock symbol of the company you want a quote for.
     *               e.g. APPL for APPLE
     * @return a StockQuote instance
     * @throws StockServiceException
     */
    public final StockQuote getQuote(String symbol) throws StockServiceException {
        List<StockQuote> stockQuotes = null;
        try {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();
            String queryString = "select * from quotes where symbol = '" + symbol + "'";

            ResultSet resultSet = statement.executeQuery(queryString);
            stockQuotes = new ArrayList<StockQuote>(resultSet.getFetchSize());
            while(resultSet.next()) {
                String symbolValue = resultSet.getString("symbol");
                BigDecimal price = resultSet.getBigDecimal("price");
                Timestamp time = resultSet.getTimestamp("time");
                DateTime givenDate = new DateTime(time);
                stockQuotes.add(new StockQuote(givenDate, price, symbolValue));
            }
        } catch (DatabaseConnectionException | SQLException exception) {
            throw new StockServiceException(exception.getMessage(), exception);
        }
        if (stockQuotes.isEmpty()) {
            throw new StockServiceException("There is no stock data for:" + symbol);
        }
        return stockQuotes.get(stockQuotes.size() - 1);
    }

    /**
     * Get a historical list of stock quotes for the provide symbol
     *
     * @param symbol the stock symbol of the company you want a quote for.
     *               e.g. APPL for APPLE
     * @param startRange beginning of the date range
     * @param endRange end of the date range
     * @return a StockQuote instance
     * @throws StockServiceException
     */
    public final List<StockQuote> getQuote(String symbol, DateTime startRange, DateTime endRange) throws StockServiceException {
        List<StockQuote> stockQuotes = null;
        try {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();
            String queryString = "select * from quotes where symbol = '" + symbol + "' and time between '"
                    + startRange.toString(StockQuote.DATE_FORMAT) + "' and '" + endRange.toString(StockQuote.DATE_FORMAT)+ "'";
            ResultSet resultSet = statement.executeQuery(queryString);
            stockQuotes = new ArrayList<StockQuote>(resultSet.getFetchSize());
            while(resultSet.next()) {
                String symbolValue = resultSet.getString("symbol");
                BigDecimal price = resultSet.getBigDecimal("price");
                Timestamp time = resultSet.getTimestamp("time");
                DateTime givenDate = new DateTime(time.getTime());
                stockQuotes.add(new StockQuote(givenDate, price, symbolValue));
            }
        } catch (DatabaseConnectionException | SQLException e) {
            throw new StockServiceException(e.getMessage(), e);
        }
        if (stockQuotes.isEmpty()) {
            throw new StockServiceException("There is no stock data for:" + symbol);
        }
        return stockQuotes;
    }

    /**
     * Get a historical list of stock quotes in an interval for the provide symbol
     *
     * @param symbol the stock symbol of the company you want a quote for.
     *               e.g. APPL for APPLE
     * @param startRange beginning of the date range
     * @param endRange end of the date range
     * @param interval time between each StockQuote instances
     * @return a StockQuote instance
     * @throws StockServiceException
     */
    public final List<StockQuote> getQuote(String symbol, DateTime startRange, DateTime endRange,
                                           Interval interval) throws StockServiceException{
        List<StockQuote> stockQuotes = null;
        try {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();
            String queryString = "select * from quotes where symbol = '" + symbol + "' and time between '" +
                                 startRange.toString(StockQuote.DATE_FORMAT) + "' and '" +
                                 endRange.toString(StockQuote.DATE_FORMAT) + "'";
            ResultSet resultSet = statement.executeQuery(queryString);
            stockQuotes = new ArrayList<StockQuote>(resultSet.getFetchSize());
            DateTime intervalEnd = new DateTime(startRange);
            while (resultSet.next()) {
                String symbolValue = resultSet.getString("symbol");
                BigDecimal price = resultSet.getBigDecimal("price");
                Timestamp time = resultSet.getTimestamp("time");
                DateTime givenDate = new DateTime(time);
                if (!givenDate.isBefore(intervalEnd)) {
                    stockQuotes.add(new StockQuote((DateTime) givenDate, price, symbolValue));
                    intervalEnd.plusDays(interval.amount());
                }
            }
        } catch (DatabaseConnectionException | SQLException e) {
            throw new StockServiceException(e.getMessage(), e);
        }
        if (stockQuotes.isEmpty()) {
            throw new StockServiceException("There is no stock data for:" + symbol);
        }
        return stockQuotes;
    }
}

