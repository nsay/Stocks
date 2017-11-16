package edu.uml.nsay.servlet;

import edu.uml.nsay.model.StockSearchBean;
import edu.uml.nsay.services.ServiceType;
import edu.uml.nsay.services.StockServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This class is a servlet for a stock quote search web application.
 *
 * @author Narith Say
 */
public class StockSearchServlet extends HttpServlet {

    private static final String SYMBOL_PARAMETER_KEY = "symbol";
    private static final String STARTRANGE_PARAMETER_KEY = "startRange";
    private static final String ENDRANGE_PARAMETER_KEY = "endRange";
    private static final String INTERVAL_PARAMETER_KEY = "interval";
    private static final String SERVICETYPE_PARAMETER_KEY = "serviceType";

    /**
     * Requests access to form submission data and, after gaining access, uses the data to generate a response
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String symbol = request.getParameter(SYMBOL_PARAMETER_KEY);
        String startRange = request.getParameter(STARTRANGE_PARAMETER_KEY);
        String endRange = request.getParameter(ENDRANGE_PARAMETER_KEY);
        String interval = request.getParameter(INTERVAL_PARAMETER_KEY);
        String serviceType = request.getParameter(SERVICETYPE_PARAMETER_KEY);

        HttpSession session = request.getSession();

        StockSearchBean search = new StockSearchBean(symbol, startRange, endRange, interval);
        try {
            switch (serviceType) {
                case ("DATABASE"):
                    search.processData(ServiceType.DATABASE);
                    break;
                case ("YAHOO"):
                    search.processData(ServiceType.YAHOO);
                    break;
                default:
                    search.processData(ServiceType.YAHOO);
            }
        } catch (StockServiceException e) {
            throw new RuntimeException(e.getMessage());
        }
        session.setAttribute("search", search);

        ServletContext servletContext = getServletContext();
        RequestDispatcher dispatcher =
                servletContext.getRequestDispatcher("/stockquoteResults.jsp");
        dispatcher.forward(request, response);
    }
}
