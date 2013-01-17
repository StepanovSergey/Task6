package com.epam.task6.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
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
    private String categoryName;
    private String categoryNumber;
    private String subcategoryName;
    private String subcategoryNumber;
    private Integer[] checkboxes;

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

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
	return categoryName;
    }

    /**
     * @param categoryName
     *            the categoryName to set
     */
    public void setCategoryName(String categoryName) {
	this.categoryName = categoryName;
    }

    /**
     * @return the categoryNumber
     */
    public String getCategoryNumber() {
	return categoryNumber;
    }

    /**
     * @param categoryNumber
     *            the categoryNumber to set
     */
    public void setCategoryNumber(String categoryNumber) {
	this.categoryNumber = categoryNumber;
    }

    /**
     * @return the subcategoryName
     */
    public String getSubcategoryName() {
	return subcategoryName;
    }

    /**
     * @param subcategoryName
     *            the subcategoryName to set
     */
    public void setSubcategoryName(String subcategoryName) {
	this.subcategoryName = subcategoryName;
    }

    /**
     * @return the subcategoryNumber
     */
    public String getSubcategoryNumber() {
	return subcategoryNumber;
    }

    /**
     * @param subcategoryNumber
     *            the subcategoryNumber to set
     */
    public void setSubcategoryNumber(String subcategoryNumber) {
	this.subcategoryNumber = subcategoryNumber;
    }

    /**
     * @return the checkboxes
     */
    public Integer[] getCheckboxes() {
	return checkboxes;
    }

    /**
     * @param checkboxes
     *            the checkboxes to set
     */
    public void setCheckboxes(Integer[] checkboxes) {
	this.checkboxes = checkboxes;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
	Integer[] resetArray = {};
	checkboxes = resetArray;
    }
}
