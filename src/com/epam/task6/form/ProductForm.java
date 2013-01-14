package com.epam.task6.form;

import java.util.Map;

import org.apache.struts.action.ActionForm;
import org.jdom.Document;

import com.epam.task6.model.Product;

/**
 * This class provides add product form
 * 
 * @author Siarhei_Stsiapanau
 * 
 */
public class ProductForm extends ActionForm {
    private static final long serialVersionUID = 2335611273728290457L;
    private Product product;
    private Document document;
    private Map<String, Integer> categoryData;
    private Map<String, Integer> subcategoryData;

    /**
     * @return the subcategoryData
     */
    public Map<String, Integer> getSubcategoryData() {
	return subcategoryData;
    }

    /**
     * @param subcategoryData
     *            the subcategoryData to set
     */
    public void setSubcategoryData(Map<String, Integer> subcategoryData) {
	this.subcategoryData = subcategoryData;
    }

    /**
     * @return the categoryParameters
     */
    public Map<String, Integer> getCategoryData() {
	return categoryData;
    }

    /**
     * @param categoryParameters
     *            the categoryParameters to set
     */
    public void setCategoryData(Map<String, Integer> categoryParameters) {
	this.categoryData = categoryParameters;
    }

    /**
     * @return the document
     */
    public Document getDocument() {
	return document;
    }

    /**
     * @param document
     *            the document to set
     */
    public void setDocument(Document document) {
	this.document = document;
    }

    /**
     * @return the product
     */
    public Product getProduct() {
	return product;
    }

    /**
     * @param product
     *            the product to set
     */
    public void setProduct(Product product) {
	this.product = product;
    }

}
