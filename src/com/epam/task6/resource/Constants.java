package com.epam.task6.resource;

import java.io.File;

/**
 * @author Siarhei_Stsiapanau
 * 
 */
public final class Constants {
    private Constants() {
    }

    // Common constants
    public static String REAL_PATH = "";
    public static File XML_FILE;
    public static final String XML_PATH = "/xml/products.xml";
    public static final String CATEGORY_NAME_PARAMETER = "categoryName";
    public static final String SUBCATEGORY_NAME_PARAMETER = "subcategoryName";
    public static final String CATEGORY_NUMBER_PARAMETER = "categoryNumber";
    public static final String SUBCATEGORY_NUMBER_PARAMETER = "subcategoryNumber";
    // Add product constants
    public static final String ADD_PRODUCT_XSLT = "/xsl/addProduct.xsl";
    public static final String PRODUCT_PARAMETER = "product";
    // XML tags
    public static final String CATEGORY_TAG = "category";
    public static final String SUBCATEGORY_TAG = "subcategory";
    public static final String PRODUCT_TAG = "product";
    public static final String PRODUCER_TAG = "producer";
    public static final String MODEL_TAG = "model";
    public static final String DATE_OF_ISSUE_TAG = "date_of_issue";
    public static final String PRICE_TAG = "price";
    public static final String COLOR_TAG = "color";
    public static final String NOT_IN_STOCK_TAG = "not_in_stock";
    public static final String NAME_ATTRIBUTE = "name";
    // Validator constants
    public static final String VALIDATOR_PARAMETER = "validator";
    public static final String MODEL_PATTERN = "model_pattern";
    public static final String PRICE_PATTERN = "price_pattern";
    public static final String DATE_PATTERN = "date_pattern";
    public static final String COLOR_PATTERN = "color_pattern";
    public static final String DEFAULT_PRICE = "0";
    // Task6
    public static final String PRODUCTS = "products";
    public static final String PRODUCT = "product";
    //Forwards
    public static final String SHOW_CATEGORIES = "show_categories";
    public static final String SHOW_SUBCATEGORIES = "show_subcategories";
    public static final String SHOW_PRODUCTS = "show_products";
    public static final String SHOW_PRODUCTS_ACTION = "show_products_action";
    public static final String ADD_PRODUCT = "add_product";
}
