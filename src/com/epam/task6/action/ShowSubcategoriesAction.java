package com.epam.task6.action;

import static com.epam.task6.resource.Constants.*;
import static com.epam.task6.resource.Constants.SHOW_SUBCATEGORIES;
import static com.epam.task6.resource.Constants.XML_FILE;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;

import com.epam.task6.form.ProductForm;
import com.epam.task6.utils.ProductCounter;

/**
 * This action shows list of subcategories
 * 
 * @author Siarhei_Stsiapanau
 * 
 */
public class ShowSubcategoriesAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception {
	// Get JDOM document
	SAXBuilder builder = new SAXBuilder();
	Document doc = builder.build(XML_FILE);
	// Set document to form
	ProductForm productForm = (ProductForm) form;
	productForm.setDocument(doc);
	// Get category name and parameter
	String categoryName = request.getParameter(CATEGORY_NAME_PARAMETER);
	String categoryNumber = request.getParameter(CATEGORY_NUMBER_PARAMETER);
	// Set parameters to session
	HttpSession session = request.getSession();
	session.setAttribute(CATEGORY_NAME_PARAMETER, categoryName);
	session.setAttribute(CATEGORY_NUMBER_PARAMETER, categoryNumber);
	// Count products in subcategories
	ProductCounter.countProducts(form, doc, categoryName);
	// Forward to page
	return mapping.findForward(SHOW_SUBCATEGORIES);
    }
}
