package com.epam.task6.action;

import static com.epam.task6.resource.Constants.REAL_PATH;
import static com.epam.task6.resource.Constants.SHOW_CATEGORIES;
import static com.epam.task6.resource.Constants.XML_FILE;
import static com.epam.task6.resource.Constants.XML_PATH;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;

import com.epam.task6.form.ProductForm;
import com.epam.task6.utils.ProductCounter;

/**
 * This action shows list of categories
 * 
 * @author Siarhei_Stsiapanau
 * 
 */
public class ShowCategoriesAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception {
	// Setting application real path
	if (REAL_PATH.equals("")) {
	    REAL_PATH = request.getServletContext().getRealPath("");
	}
	// Setting xml file variable
	if (XML_FILE == null) {
	    XML_FILE = new File(REAL_PATH + XML_PATH);
	}
	// Get JDOM document
	SAXBuilder builder = new SAXBuilder();
	Document doc = builder.build(XML_FILE);
	// Set document to form
	ProductForm productForm = (ProductForm) form;
	productForm.setDocument(doc);
	// Count products in categories
	ProductCounter.countProducts(form, doc, null);
	// Forward to page
	return mapping.findForward(SHOW_CATEGORIES);
    }
}
