package com.epam.task6.action;

import static com.epam.task6.resource.Constants.ADD_PRODUCT;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.epam.task6.form.ProductForm;
import com.epam.task6.model.Product;

/**
 * This action shows add product form
 * 
 * @author Siarhei_Stsiapanau
 * 
 */
public class EditProductAction extends Action {
    public ActionForward execute(ActionMapping actionMapping,
	    ActionForm actionForm, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	ProductForm addForm = (ProductForm) actionForm;
	if (addForm.getProduct() == null) {
	    addForm.setProduct(new Product());
	}
	request.getSession().setAttribute("product", addForm.getProduct());
	return actionMapping.findForward(ADD_PRODUCT);
    }
}