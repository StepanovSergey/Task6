package com.epam.task6.action;

import static com.epam.task6.resource.Constants.*;
import static com.epam.task6.resource.Constants.ADD_PRODUCT_XSLT;
import static com.epam.task6.resource.Constants.CATEGORY_NAME_PARAMETER;
import static com.epam.task6.resource.Constants.CATEGORY_NUMBER_PARAMETER;
import static com.epam.task6.resource.Constants.DEFAULT_PRICE;
import static com.epam.task6.resource.Constants.NAME_ATTRIBUTE;
import static com.epam.task6.resource.Constants.NOT_IN_STOCK_TAG;
import static com.epam.task6.resource.Constants.PRICE_TAG;
import static com.epam.task6.resource.Constants.PRODUCT_PARAMETER;
import static com.epam.task6.resource.Constants.REAL_PATH;
import static com.epam.task6.resource.Constants.SHOW_CATEGORIES;
import static com.epam.task6.resource.Constants.SHOW_PRODUCTS;
import static com.epam.task6.resource.Constants.SHOW_SUBCATEGORIES;
import static com.epam.task6.resource.Constants.SUBCATEGORY_NAME_PARAMETER;
import static com.epam.task6.resource.Constants.SUBCATEGORY_NUMBER_PARAMETER;
import static com.epam.task6.resource.Constants.XML_FILE;
import static com.epam.task6.resource.Constants.XML_PATH;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.actions.MappingDispatchAction;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.epam.task6.form.ProductForm;
import com.epam.task6.model.Product;
import com.epam.task6.transform.XsltTransformerFactory;
import com.epam.task6.validation.ProductValidator;

/**
 * This class provides all actions of application
 * 
 * @author Siarhei_Stsiapanau
 * 
 */
public final class ProductAction extends MappingDispatchAction {
    private static final Logger logger = Logger.getLogger(ProductAction.class);
    private static final String ENCODING = "UTF-8";
    private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static final Lock readLock = readWriteLock.readLock();
    private static final Lock writeLock = readWriteLock.writeLock();

    /**
     * Show categories of products
     * 
     * @param actionMapping
     *            current mapping
     * @param actionForm
     *            current form
     * @param request
     *            current request
     * @param response
     *            current response
     * @return forward to show categories page
     * @throws Exception
     *             if something wrong
     */
    public ActionForward showCategories(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception {
	// Setting application real path
	if (REAL_PATH.equals("")) {
	    REAL_PATH = request.getServletContext().getRealPath("");
	}

	// Setting xml file variable
	if (XML_FILE == null) {
	    XML_FILE = new File(REAL_PATH + XML_PATH);
	}

	// Get product form
	ProductForm productForm = (ProductForm) form;
	createNewDocument(productForm);

	// Forward to page
	return mapping.findForward(SHOW_CATEGORIES);
    }

    /**
     * Show subcategories of products
     * 
     * @param actionMapping
     *            current mapping
     * @param actionForm
     *            current form
     * @param request
     *            current request
     * @param response
     *            current response
     * @return forward to show subcategories page
     * @throws Exception
     *             if something wrong
     */
    public ActionForward showSubcategories(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	// Get product form
	ProductForm productForm = (ProductForm) form;
	createNewDocument(productForm);

	// Get category name and number parameter. Set parameters to form if
	// it's needed
	String categoryName = request.getParameter(CATEGORY_NAME_PARAMETER);
	if (categoryName == null) {
	    categoryName = productForm.getCategoryName();
	} else {
	    productForm.setCategoryName(categoryName);
	}
	String categoryNumber = request.getParameter(CATEGORY_NUMBER_PARAMETER);
	if (categoryNumber == null) {
	    categoryNumber = productForm.getCategoryNumber();
	} else {
	    productForm.setCategoryNumber(categoryNumber);
	}

	// Forward to page
	return mapping.findForward(SHOW_SUBCATEGORIES);
    }

    /**
     * Show products
     * 
     * @param actionMapping
     *            current mapping
     * @param actionForm
     *            current form
     * @param request
     *            current request
     * @param response
     *            current response
     * @return forward to show products page
     * @throws Exception
     *             if something wrong
     */
    public ActionForward showProducts(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception {
	// Get product form
	ProductForm productForm = (ProductForm) form;
	createNewDocument(productForm);

	// Get category and subcategory parameters
	String categoryName = productForm.getCategoryName();
	String subcategoryName = request
		.getParameter(SUBCATEGORY_NAME_PARAMETER);
	if (subcategoryName == null) {
	    subcategoryName = productForm.getSubcategoryName();
	} else {
	    productForm.setSubcategoryName(subcategoryName);
	}
	String subcategoryNumber = request
		.getParameter(SUBCATEGORY_NUMBER_PARAMETER);
	if (subcategoryNumber == null) {
	    subcategoryNumber = productForm.getSubcategoryNumber();
	} else {
	    productForm.setSubcategoryNumber(subcategoryNumber);
	}

	// Creating array of checked checkboxes
	Integer[] checkboxes = checkBox(productForm, categoryName,
		subcategoryName);
	productForm.setCheckboxes(checkboxes);

	// Forward to page
	return mapping.findForward(SHOW_PRODUCTS);
    }

    /**
     * Show add product page
     * 
     * @param actionMapping
     *            current mapping
     * @param actionForm
     *            current form
     * @param request
     *            current request
     * @param response
     *            current response
     * @return forward to add products page
     * @throws Exception
     *             if something wrong
     */
    public ActionForward addProduct(ActionMapping actionMapping,
	    ActionForm actionForm, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	// Defining variables
	ProductValidator validator = new ProductValidator();
	Product product = setProductParameters(request);

	ProductForm productForm = (ProductForm) actionForm;
	Transformer transformer = XsltTransformerFactory
		.getTransformer(ADD_PRODUCT_XSLT);
	String currentCategory = productForm.getCategoryName();
	String currentSubcategory = productForm.getSubcategoryName();
	Writer result = new StringWriter();
	long lastModified = XML_FILE.lastModified();

	// Setting transformer parameters
	transformer.setParameter(CATEGORY_NAME_PARAMETER, currentCategory);
	transformer
		.setParameter(SUBCATEGORY_NAME_PARAMETER, currentSubcategory);
	transformer.setParameter(VALIDATOR_PARAMETER, validator);
	transformer.setParameter(PRODUCT_PARAMETER, product);
	// Transforming file
	readLock.lock();
	try {
	    Source source = new StreamSource(XML_FILE);
	    Result res = new StreamResult(result);
	    transformer.transform(source, res);
	} catch (TransformerException e) {
	    throw e;
	} finally {
	    readLock.unlock();
	}
	try {
	    Writer writer = response.getWriter();
	    if (validator.isProductValid()) {
		if (lastModified == XML_FILE.lastModified()) {
		    // Write to file
		    write(result);
		    // Show products page
		    return new ActionRedirect("ShowProducts.do");
		} else {
		    // Retry to transform
		    Source source = new StreamSource(XML_FILE);
		    Result res = new StreamResult(result);
		    transformer.transform(source, res);
		}
	    } else {
		// Display add form with error messages
		writer.write(result.toString());
	    }
	} catch (IOException | TransformerException e) {
	    throw e;
	}
	return actionMapping.findForward("");
    }

    /**
     * Save changes of products on show products page when user click on button
     * update
     * 
     * @param actionMapping
     *            current mapping
     * @param actionForm
     *            current form
     * @param request
     *            current request
     * @param response
     *            current response
     * @return redirect to show products page
     * @throws Exception
     *             if something wrong
     */
    public ActionForward updateProduct(ActionMapping actionMapping,
	    ActionForm actionForm, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	// Define variables
	ProductForm productForm = (ProductForm) actionForm;
	Document doc = productForm.getDocument();
	String categoryName = productForm.getCategoryName();
	String subcategoryName = productForm.getSubcategoryName();
	Integer[] checkboxes = productForm.getCheckboxes();

	Element root = doc.getRootElement();
	List<Element> categoryList = root.getChildren();
	// Iterate over categories
	for (Element category : categoryList) {
	    String currentCategory = category.getAttribute(NAME_ATTRIBUTE)
		    .getValue();
	    if (categoryName.equals(currentCategory)) {
		List<Element> subcategoryList = category.getChildren();
		// Iterate over subcategories
		for (Element subcategory : subcategoryList) {
		    String currentSubcategory = subcategory.getAttribute(
			    NAME_ATTRIBUTE).getValue();
		    if (subcategoryName.equals(currentSubcategory)) {
			List<Element> productList = subcategory.getChildren();
			// Iterate over products
			for (int i = 0; i < productList.size(); i++) {
			    // Change element with new properties
			    Element element = productList.get(i);
			    changeDocument(element, i, checkboxes);
			}
		    }
		}
	    }
	}
	writeXml(doc);
	ActionRedirect redirect = new ActionRedirect(
		actionMapping.findForward(SHOW_PRODUCTS));
	return redirect;
    }

    /**
     * Create new document from xml file and set it to form
     * 
     * @param productForm
     *            form to set
     * @throws JDOMException
     *             if something wrong
     * @throws IOException
     *             if something wrong
     */
    private void createNewDocument(ProductForm productForm)
	    throws JDOMException, IOException {
	// Get JDOM document
	SAXBuilder builder = new SAXBuilder();
	Document doc = builder.build(XML_FILE);

	// Set document to form
	productForm.setDocument(doc);
    }

    /**
     * Get product data from request and return product instance
     * 
     * @param request
     *            get parameters from this request
     * @return product instance
     */
    private Product setProductParameters(HttpServletRequest request) {
	Product product = new Product();
	String name = request.getParameter(NAME_ATTRIBUTE);
	if (name == null) {
	    product.setName("");
	} else {
	    product.setName(name);
	}
	String producer = request.getParameter(PRODUCER_TAG);
	if (producer == null) {
	    product.setProducer("");
	} else {
	    product.setProducer(producer);
	}
	String model = request.getParameter(MODEL_TAG);
	if (model == null) {
	    product.setModel("");
	} else {
	    product.setModel(model);
	}
	String color = request.getParameter(COLOR_TAG);
	if (color == null) {
	    product.setColor("");
	} else {
	    product.setColor(color);
	}
	String dateOfIssue = request.getParameter(DATE_OF_ISSUE_TAG);
	if (dateOfIssue == null) {
	    product.setDateOfIssue("");
	} else {
	    product.setDateOfIssue(dateOfIssue);
	}
	String notInStock = request.getParameter(NOT_IN_STOCK_TAG);
	if (notInStock != null) {
	    product.setNotInStock(true);
	    product.setPrice(DEFAULT_PRICE);
	} else {
	    String price = request.getParameter(PRICE_TAG);
	    if (price != null && price != "") {
		product.setPrice(price);
	    } else {
		product.setPrice(DEFAULT_PRICE);
	    }
	}
	return product;
    }

    /**
     * Save changes to document
     * 
     * @param element
     *            current element
     * @param productIndex
     *            index of product
     * @param checkboxes
     *            array of checked checkboxes
     */
    private void changeDocument(Element element, Integer productIndex,
	    Integer[] checkboxes) {

	boolean isNotInStock = isProductNotInStock(productIndex, checkboxes);
	if (isNotInStock) {
	    Element priceElement = element.getChild(PRICE_TAG);
	    if (priceElement != null) {
		priceElement.setName(NOT_IN_STOCK_TAG);
		priceElement.setText("");
	    }
	} else {
	    Element notInStockElement = element.getChild(NOT_IN_STOCK_TAG);
	    if (notInStockElement != null) {
		notInStockElement.setName(PRICE_TAG);
	    }
	}
	/*
	 * if (isNotInStock) { boolean isDeleted =
	 * element.removeChild(PRICE_TAG); if (isDeleted) { Element
	 * notInStockElement = new Element(NOT_IN_STOCK_TAG);
	 * notInStockElement.setText(""); element.addContent(notInStockElement);
	 * } } else { String priceValue =
	 * element.getChildText(NOT_IN_STOCK_TAG); boolean isDeleted =
	 * element.removeChild(NOT_IN_STOCK_TAG); if (isDeleted) { Element price
	 * = new Element(PRICE_TAG); price.setText(priceValue);
	 * element.addContent(price); } }
	 */
    }

    /**
     * Create array of checked checkboxes on products page
     * 
     * @param doc
     *            current document
     * @param categoryName
     *            name of category
     * @param subcategoryName
     *            name of subcategory
     * @return array of checked checkboxes
     */
    private Integer[] checkBox(ProductForm form, String categoryName,
	    String subcategoryName) {
	List<Integer> boxes = new ArrayList<>();
	Element root = form.getDocument().getRootElement();
	List<Element> categoryList = root.getChildren();
	// Iterate over categories
	for (Element category : categoryList) {
	    String currentCategory = category.getAttribute(NAME_ATTRIBUTE)
		    .getValue();
	    if (categoryName.equals(currentCategory)) {
		List<Element> subcategoryList = category.getChildren();
		// Iterate over subcategories
		for (Element subcategory : subcategoryList) {
		    String currentSubcategory = subcategory.getAttribute(
			    NAME_ATTRIBUTE).getValue();
		    if (subcategoryName.equals(currentSubcategory)) {
			List<Element> productList = subcategory.getChildren();
			// Iterate over products
			for (int i = 0; i < productList.size(); i++) {
			    Element product = productList.get(i);
			    String notInStock = product
				    .getChildText(NOT_IN_STOCK_TAG);
			    if (notInStock != null) {
				boxes.add(i);
			    }
			}
		    }
		}
	    }
	}
	Integer[] checkboxes = boxes.toArray(new Integer[boxes.size()]);
	return checkboxes;
    }

    /**
     * Write document to xml file
     * 
     * @param doc
     *            document to write
     */
    private void writeXml(Document doc) {

	XMLOutputter xmlOutput = new XMLOutputter();

	// display nice nice
	xmlOutput.setFormat(Format.getPrettyFormat());
	writeLock.lock();
	try {
	    xmlOutput.output(doc, new FileWriter(REAL_PATH + XML_PATH));
	} catch (IOException e) {
	    if (logger.isEnabledFor(Level.ERROR)) {
		logger.error(e.getMessage(), e);
	    }
	} finally {
	    writeLock.unlock();
	}
    }

    /**
     * Is current product is not in stock when it's edited by form on products
     * page?
     * 
     * @param productIndex
     *            index of product
     * @param checkboxes
     *            array of checked checkboxes from show products page
     * @return true if current product is not in stock, false otherwise
     */
    private boolean isProductNotInStock(Integer productIndex,
	    Integer[] checkboxes) {
	if (checkboxes.length != 0) {
	    for (Integer index : checkboxes) {
		if (productIndex.equals(index)) {
		    return true;
		}
	    }
	}
	return false;
    }

    /**
     * Write xml file from writer
     * 
     * @param writer
     *            where it's storing new xml file structure
     */
    private void write(Writer writer) {
	try {
	    Writer fileWriter = new PrintWriter(XML_FILE, ENCODING);
	    writeLock.lock();
	    fileWriter.write(writer.toString());
	    fileWriter.flush();
	} catch (IOException e) {
	    if (logger.isEnabledFor(Level.ERROR)) {
		logger.error(e.getMessage(), e);
	    }
	} finally {
	    writeLock.unlock();
	}
    }

}
