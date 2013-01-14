package com.epam.task6.action;

import static com.epam.task6.resource.Constants.ADD_PRODUCT_XSLT;
import static com.epam.task6.resource.Constants.CATEGORY_NAME_PARAMETER;
import static com.epam.task6.resource.Constants.SUBCATEGORY_NAME_PARAMETER;
import static com.epam.task6.resource.Constants.DEFAULT_PRICE;
import static com.epam.task6.resource.Constants.PRODUCT_PARAMETER;
import static com.epam.task6.resource.Constants.XML_FILE;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
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
import org.apache.struts.action.ActionRedirect;

import com.epam.task6.form.ProductForm;
import com.epam.task6.model.Product;
import com.epam.task6.transform.XsltTransformerFactory;

/**
 * This action add product to xml
 * 
 * @author Siarhei_Stsiapanau
 * 
 */
public class SaveProductAction extends Action {
    private static final Logger logger = Logger
	    .getLogger(SaveProductAction.class);
    private static final String ENCODING = "UTF-8";
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    public ActionForward execute(ActionMapping actionMapping,
	    ActionForm actionForm, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	String currentCategory = request
		.getParameter(CATEGORY_NAME_PARAMETER);
	String currentSubcategory = request
		.getParameter(SUBCATEGORY_NAME_PARAMETER);
	ProductForm addForm = (ProductForm) actionForm;
	Product product = addForm.getProduct();
	if (product == null) {
	    addForm.setProduct(new Product());
	}
	if (product.isNotInStock()) {
	    product.setPrice(DEFAULT_PRICE);
	}
	Transformer transformer = XsltTransformerFactory
		.getTransformer(ADD_PRODUCT_XSLT);
	transformer.setParameter(CATEGORY_NAME_PARAMETER, currentCategory);
	transformer.setParameter(SUBCATEGORY_NAME_PARAMETER,
		currentSubcategory);
	transformer.setParameter(PRODUCT_PARAMETER, product);
	Writer result = new StringWriter();
	try {
	    readLock.lock();
	    transformer.transform(new StreamSource(XML_FILE), new StreamResult(
		    result));

	} catch (TransformerException e) {
	    if (logger.isEnabledFor(Level.ERROR)) {
		logger.error(e.getMessage(), e);
	    }
	} finally {
	    readLock.unlock();
	}

	write(XML_FILE, result);

	String link = "ShowProducts.do?current_category=" + currentCategory
		+ "&current_subcategory=" + currentSubcategory;
	return new ActionRedirect(link);
    }

    private void write(File xmlFile, Writer writer) {
	try {
	    Writer fileWriter = new PrintWriter(xmlFile, ENCODING);
	    writeLock.lock();
	    fileWriter.write(writer.toString());
	    fileWriter.flush();
	} catch (IOException e) {
	    if (logger.isEnabledFor(Level.ERROR)) {
		logger.error(e.getMessage(), e);
	    }
	} finally {
	    writeLock.unlock();
	}
    }
}