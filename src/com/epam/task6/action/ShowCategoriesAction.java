/**
 * 
 */
package com.epam.task6.action;

import static com.epam.task6.resource.Constants.CATEGORIES_XSLT;

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

import com.epam.task6.command.CommandFactory;
import com.epam.task6.transform.XsltTransformerFactory;

/**
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
	System.out.println("Starting action");
	/*try {
	    Transformer transformer = XsltTransformerFactory
		    .getTransformer(CATEGORIES_XSLT);
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
	}*/
	System.out.println("End of action");
	return actionMapping.findForward("");
    }

}
