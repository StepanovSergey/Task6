package com.epam.task6.action;

import static com.epam.task6.resource.Constants.COLOR_TAG;
import static com.epam.task6.resource.Constants.CURRENT_CATEGORY_PARAMETER;
import static com.epam.task6.resource.Constants.CURRENT_SUBCATEGORY_PARAMETER;
import static com.epam.task6.resource.Constants.DATE_OF_ISSUE_TAG;
import static com.epam.task6.resource.Constants.MODEL_TAG;
import static com.epam.task6.resource.Constants.NAME_ATTRIBUTE;
import static com.epam.task6.resource.Constants.NOT_IN_STOCK_TAG;
import static com.epam.task6.resource.Constants.PRICE_TAG;
import static com.epam.task6.resource.Constants.PRODUCER_TAG;
import static com.epam.task6.resource.Constants.PRODUCTS;
import static com.epam.task6.resource.Constants.SHOW_PRODUCTS;
import static com.epam.task6.resource.Constants.XML_FILE;

import java.util.ArrayList;
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

import com.epam.task6.model.Product;

/**
 * This action shows list of products
 * 
 * @author Siarhei_Stsiapanau
 * 
 */
public class ShowProductsAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception {
	SAXBuilder builder = new SAXBuilder();
	Document doc = builder.build(XML_FILE);
	Element root = doc.getRootElement();
	Map<String, Integer> map = new HashMap<>();
	String currentCategory = request
		.getParameter(CURRENT_CATEGORY_PARAMETER);
	String currentSubcategory = request
		.getParameter(CURRENT_SUBCATEGORY_PARAMETER);
	List<Product> products = new ArrayList<>();
	List<Element> categoryList = root.getChildren();
	for (int i = 0; i < categoryList.size(); i++) {
	    Element category = categoryList.get(i);
	    String categoryName = category.getAttribute(NAME_ATTRIBUTE)
		    .getValue();
	    if (currentCategory.equals(categoryName)) {
		List<Element> subcategoryList = category.getChildren();
		for (int j = 0; j < subcategoryList.size(); j++) {
		    Element subcategory = subcategoryList.get(j);
		    String subcategoryName = subcategory.getAttribute(
			    NAME_ATTRIBUTE).getValue();
		    if (currentSubcategory.equals(subcategoryName)) {
			List<Element> productList = subcategory.getChildren();
			for (Element element : productList) {
			    Product product = setProductParams(element);
			    products.add(product);
			}
		    }
		}
	    }
	}
	HttpSession session = request.getSession();
	session.setAttribute(PRODUCTS, products);
	session.setAttribute(CURRENT_CATEGORY_PARAMETER, currentCategory);
	session.setAttribute(CURRENT_SUBCATEGORY_PARAMETER, currentSubcategory);
	return mapping.findForward(SHOW_PRODUCTS);
    }

    private Product setProductParams(Element element) {
	Product product = new Product();
	product.setProducer(element.getChildText(PRODUCER_TAG));
	product.setModel(element.getChildText(MODEL_TAG));
	product.setColor(element.getChildText(COLOR_TAG));
	product.setDateOfIssue(element.getChildText(DATE_OF_ISSUE_TAG));
	String notInStock = element.getChildText(NOT_IN_STOCK_TAG);
	boolean isNotInStock = false;
	if (notInStock != null) {
	    isNotInStock = true;
	}
	if (isNotInStock) {
	    product.setNotInStock(true);
	} else {
	    product.setPrice(element.getChildText(PRICE_TAG));
	}
	return product;
    }
}
