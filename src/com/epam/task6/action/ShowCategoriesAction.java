package com.epam.task6.action;

import static com.epam.task6.resource.Constants.CATEGORIES_XSLT;
import static com.epam.task6.resource.Constants.REAL_PATH;
import static com.epam.task6.resource.Constants.XML_FILE;
import static com.epam.task6.resource.Constants.XML_PATH;

import java.io.File;
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
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.epam.task6.transform.XsltTransformerFactory;

/**
 * This action shows list of categories
 * 
 * @author Siarhei_Stsiapanau
 * 
 */
public class ShowCategoriesAction extends Action {
    private static final Logger logger = Logger
	    .getLogger(ShowCategoriesAction.class);
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();

    public ActionForward execute(ActionMapping actionMapping,
	    ActionForm actionForm, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	if (REAL_PATH.equals("")) {
	    REAL_PATH = request.getServletContext().getRealPath("");
	}
	if (XML_FILE == null) {
	    XML_FILE = new File(REAL_PATH + XML_PATH);
	}
	Transformer transformer = XsltTransformerFactory
		.getTransformer(CATEGORIES_XSLT);
	try {
	    readLock.lock();
	    transformer.transform(new StreamSource(XML_FILE), new StreamResult(
		    response.getWriter()));
	} catch (TransformerException | IOException e) {
	    if (logger.isEnabledFor(Level.ERROR)) {
		logger.error(e.getMessage(), e);
	    }
	} finally {
	    readLock.unlock();
	}
	return actionMapping.findForward("");
    }
}
