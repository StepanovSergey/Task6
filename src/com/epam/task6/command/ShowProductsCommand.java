package com.epam.task6.command;

import static com.epam.task6.resource.Constants.CURRENT_CATEGORY_PARAMETER;
import static com.epam.task6.resource.Constants.CURRENT_SUBCATEGORY_PARAMETER;
import static com.epam.task6.resource.Constants.PRODUCTS_XSLT;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.epam.task6.transform.XsltTransformerFactory;

/**
 * This command shows products list
 * 
 * @author Siarhei_Stsiapanau
 * 
 */
public class ShowProductsCommand implements ICommand {
    private static final Logger logger = Logger
	    .getLogger(ShowProductsCommand.class);
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.epam.task5.command.ICommand#execute(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
	try {
	    Transformer transformer = XsltTransformerFactory
		    .getTransformer(PRODUCTS_XSLT);
	    String currentCategory = request
		    .getParameter(CURRENT_CATEGORY_PARAMETER);
	    transformer.setParameter(CURRENT_CATEGORY_PARAMETER,
		    currentCategory);
	    String currentSubcategory = request
		    .getParameter(CURRENT_SUBCATEGORY_PARAMETER);
	    transformer.setParameter(CURRENT_SUBCATEGORY_PARAMETER,
		    currentSubcategory);
	    readLock.lock();
	    transformer.transform(
		    new StreamSource(CommandFactory.getXmlFile()),
		    new StreamResult(response.getWriter()));
	} catch (TransformerException | IOException e) {
	    if (logger.isEnabledFor(Level.ERROR)) {
		logger.error(e.getMessage(), e);
	    }
	} finally {
	    readLock.unlock();
	}

    }

}
