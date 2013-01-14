package com.epam.task6.utils;

import static com.epam.task6.resource.Constants.NAME_ATTRIBUTE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionForm;
import org.jdom.Document;
import org.jdom.Element;

import com.epam.task6.form.ProductForm;

/**
 * This class counts products in categories and subcategories
 * 
 * @author Siarhei_Stsiapanau
 * 
 */
public class ProductCounter {
    /**
     * Count products in categories(if currentCategoryName parameter is null) or
     * subcategories otherwise.
     * 
     * @param form
     *            actionForm of action class
     * @param doc
     *            JDOM document object
     * @param currentCategoryName
     *            current category name. Needed to count products for categories
     *            or subcategories
     */
    public static void countProducts(ActionForm form, Document doc,
	    String currentCategoryName) {
	// Declaring variables
	Element root = doc.getRootElement();
	List<Element> categoryList = root.getChildren();
	Map<String, Integer> categoryData = new HashMap<>();
	Map<String, Integer> subcategoryData = new HashMap<>();
	// Iterate over categories
	for (int i = 0; i < categoryList.size(); i++) {
	    Element category = categoryList.get(i);
	    String categoryName = category.getAttributeValue(NAME_ATTRIBUTE);
	    // Count products in categories or subcategories?
	    if (currentCategoryName != null) {
		// Is current subcategory is selected by user?
		if (currentCategoryName.equals(categoryName)) {
		    // Count product in subcategories
		    List<Element> subcategoryList = category.getChildren();
		    for (int j = 0; j < subcategoryList.size(); j++) {
			Element subcategory = subcategoryList.get(j);
			int size = countProductsInSubcategory(subcategory);
			String subcategoryName = subcategory
				.getAttributeValue(NAME_ATTRIBUTE);
			subcategoryData.put(subcategoryName, size);
		    }
		}
	    } else {
		// Count products in categories
		int size = countProductsInCategory(category);
		categoryData.put(categoryName, size);
	    }
	}
	ProductForm productForm = (ProductForm) form;
	// Set categories data to form
	if (!categoryData.isEmpty()) {
	    productForm.setCategoryData(categoryData);
	}
	// Set subcategories data to form
	if (!subcategoryData.isEmpty()) {
	    productForm.setSubcategoryData(subcategoryData);
	}
    }

    private static int countProductsInCategory(Element category) {
	int size = 0;
	List<Element> subcategoryList = category.getChildren();
	for (int j = 0; j < subcategoryList.size(); j++) {
	    List<Element> productList = subcategoryList.get(j).getChildren();
	    size += productList.size();
	}
	return size;
    }

    private static int countProductsInSubcategory(Element subcategory) {
	int size = 0;
	List<Element> productList = subcategory.getChildren();
	size += productList.size();
	return size;
    }
}
