package com.epam.task6.action;

import static com.epam.task6.resource.Constants.REAL_PATH;
import static com.epam.task6.resource.Constants.XML_FILE;
import static com.epam.task6.resource.Constants.XML_PATH;

import java.io.File;
import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * This action shows list of categories
 * 
 * @author Siarhei_Stsiapanau
 * 
 */
public class ShowCategoriesAction extends Action {

    public ActionForward execute(ActionMapping actionMapping,
	    ActionForm actionForm, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	if (REAL_PATH.equals("")) {
	    REAL_PATH = request.getServletContext().getRealPath("");
	}
	if (XML_FILE == null) {
	    XML_FILE = new File(REAL_PATH + XML_PATH);
	}
	SAXBuilder builder = new SAXBuilder();
	Document doc = builder.build(XML_FILE);
	Writer writer = response.getWriter();
	Element root = doc.getRootElement();
	List<Element> categoryList = root.getChildren("category");
	for (int i = 0; i < categoryList.size(); i++) {
	    List<Element> subcategoryList = categoryList.get(i).getChildren();
	    //TODO How to calculate products in category? 
	}
	return actionMapping.findForward("");
    }
}
