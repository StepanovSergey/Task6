package com.epam.task6.action;

import static com.epam.task6.resource.Constants.CURRENT_CATEGORY_PARAMETER;
import static com.epam.task6.resource.Constants.NAME_ATTRIBUTE;
import static com.epam.task6.resource.Constants.SHOW_SUBCATEGORIES;
import static com.epam.task6.resource.Constants.SUBCATEGORY_DATA;
import static com.epam.task6.resource.Constants.XML_FILE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * This action shows list of subcategories
 * 
 * @author Siarhei_Stsiapanau
 * 
 */
public class ShowSubcategoriesAction extends Action {
    public ActionForward execute(ActionMapping actionMapping,
	    ActionForm actionForm, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	SAXBuilder builder = new SAXBuilder();
	Document doc = builder.build(XML_FILE);
	Element root = doc.getRootElement();
	Map<String, Integer> subcategoryData = new HashMap<>();
	String currentCategory = request
		.getParameter(CURRENT_CATEGORY_PARAMETER);
	List<Element> categoryList = root.getChildren();
	for (int i = 0; i < categoryList.size(); i++) {
	    Element category = categoryList.get(i);
	    String categoryName = category.getAttribute(NAME_ATTRIBUTE)
		    .getValue();
	    if (currentCategory.equals(categoryName)) {
		List<Element> subcategoryList = category.getChildren();
		for (int j = 0; j < subcategoryList.size(); j++) {
		    Element subcategory = subcategoryList.get(j);
		    int size = countProductsInSubcategory(subcategory);
		    String subcategoryName = subcategory
			    .getAttributeValue(NAME_ATTRIBUTE);
		    subcategoryData.put(subcategoryName, size);
		}
	    }
	}
	HttpSession session = request.getSession();
	session.setAttribute(SUBCATEGORY_DATA, subcategoryData);
	session.setAttribute(CURRENT_CATEGORY_PARAMETER, currentCategory);
	return actionMapping.findForward(SHOW_SUBCATEGORIES);
    }

    private int countProductsInSubcategory(Element subcategory) {
	int size = 0;
	List<Element> productList = subcategory.getChildren();
	size += productList.size();
	return size;
    }
}
