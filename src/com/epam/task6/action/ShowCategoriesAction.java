package com.epam.task6.action;

import static com.epam.task6.resource.Constants.CATEGORY_DATA;
import static com.epam.task6.resource.Constants.NAME_ATTRIBUTE;
import static com.epam.task6.resource.Constants.REAL_PATH;
import static com.epam.task6.resource.Constants.SHOW_CATEGORIES;
import static com.epam.task6.resource.Constants.XML_FILE;
import static com.epam.task6.resource.Constants.XML_PATH;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public ActionForward execute(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception {
	if (REAL_PATH.equals("")) {
	    REAL_PATH = request.getServletContext().getRealPath("");
	}
	if (XML_FILE == null) {
	    XML_FILE = new File(REAL_PATH + XML_PATH);
	}
	SAXBuilder builder = new SAXBuilder();
	Document doc = builder.build(XML_FILE);
	Element root = doc.getRootElement();
	List<Element> categoryList = root.getChildren();
	Map<String,Integer> categoryMap = new HashMap<>();
	for (int i = 0; i < categoryList.size(); i++) {
	    Element category = categoryList.get(i);
	    int size = countProductsInCategory(category);
	    String categoryName = category.getAttributeValue(NAME_ATTRIBUTE);
	    categoryMap.put(categoryName, size);
	}
	request.getSession().setAttribute(CATEGORY_DATA, categoryMap);
	return mapping.findForward(SHOW_CATEGORIES);
    }

    private int countProductsInCategory(Element category) {
	int size = 0;
	List<Element> subcategoryList = category.getChildren();
	for (int j = 0; j < subcategoryList.size(); j++) {
	    List<Element> productList = subcategoryList.get(j).getChildren();
	    size += productList.size();
	}
	return size;
    }
}
