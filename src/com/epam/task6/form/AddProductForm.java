package com.epam.task6.form;

import org.apache.struts.action.ActionForm;

import com.epam.task6.model.Product;

/**
 * This class provides add product form
 * 
 * @author Siarhei_Stsiapanau
 * 
 */
public class AddProductForm extends ActionForm {
    private static final long serialVersionUID = 2335611273728290457L;
    private Product product;

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
