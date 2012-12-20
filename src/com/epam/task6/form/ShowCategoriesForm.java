package com.epam.task6.form;

import org.apache.struts.action.ActionForm;

/**
 * @author Siarhei_Stsiapanau
 *
 */
public class ShowCategoriesForm extends ActionForm {
    private static final long serialVersionUID = 5121419371274305257L;
    
    private String currentCategory;

    /**
     * @return the currentCategory
     */
    public String getCurrentCategory() {
        return currentCategory;
    }

    /**
     * @param currentCategory the currentCategory to set
     */
    public void setCurrentCategory(String currentCategory) {
        this.currentCategory = currentCategory;
    }
}
