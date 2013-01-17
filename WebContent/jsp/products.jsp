<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/script.js"></script>
<title>Products</title>
</head>
<body>
	<!-- Header -->
	<p>
		Category:
		<nested:write name="productForm" property="categoryName" />
		&rarr; Subcategory:
		<nested:write name="productForm" property="subcategoryName" />
	</p>
	<nested:form action="UpdateProduct.do" method="post">
		<!-- Table header -->
		<p>List of products:</p>
		<table border=1>
			<tr>
				<th>Name</th>
				<th>Producer</th>
				<th>Model</th>
				<th>Date of issue</th>
				<th>Color</th>
				<th>Price</th>
				<th>Not In Stock</th>
			</tr>

			<!-- Define variables -->
			<nested:define id="categoryNumber" name="productForm"
				property="categoryNumber" />
			<nested:define id="subcategoryNumber" name="productForm"
				property="subcategoryNumber" />

			<nested:root name="productForm">
				<nested:nest
					property="document.rootElement.children[${categoryNumber}].children[${subcategoryNumber}]">
					<!-- Iterate over products -->
					<nested:iterate id="product" property="children"
						indexId="productIndex">
						<tr>
							<td><nested:text property="attributes[0].value" size="15"
									styleClass="name" onblur="validateProductName(this)" /></td>
							<!-- Class name is name of tag -->
							<td><nested:text styleClass="producer"
									property="child(producer).text" size="15" /></td>
							<td><nested:text styleClass="model"
									property="child(model).text" size="15" /></td>
							<td><nested:text styleClass="date_of_issue"
									property="child(date_of_issue).text" size="15" /></td>
							<td><nested:text styleClass="color"
									property="child(color).text" size="15" /></td>
							<nested:notEmpty property="child(price)">
								<td><nested:text styleClass="price"
										property="child(price).text" size="15" /></td>
							</nested:notEmpty>
							<nested:empty property="child(price)">
								<td><nested:text styleClass="price"
										property="child(not_in_stock).text" size="15" /></td>
							</nested:empty>
							<td><nested:multibox name="productForm"
									property="checkboxes" value="${productIndex}" /></td>
						</tr>
					</nested:iterate>
				</nested:nest>
			</nested:root>
		</table>

		<!-- Buttons -->
		<p>
			<input type="button" value="Add"
				onclick="window.location = 'AddProduct.do'"> <input
				type="submit" value="Update"> <input type="button"
				value="Back"
				onclick="window.location = '/Task6/ShowSubcategories.do'">
		</p>
	</nested:form>
</body>
</html>