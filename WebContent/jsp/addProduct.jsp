<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add new product</title>
<link rel="stylesheet" type="text/css" href="css/style.css"></link>
<script type="text/javascript" src="js/script.js"></script>
</head>
<body>
	<bean:define name="product" id="product"
		type="com.epam.task6.model.Product"></bean:define>
	<p>Add new product:</p>
	<html:form action="/SaveProduct"
		onsubmit="return validateAddProductForm(this)">
		<table>
			<tr>
				<th>Producer</th>
				<td><html:text property="product.producer">${product.producer }</html:text></td>
				<td><div id="invalid_producer"></div></td>
			</tr>
			<tr>
				<th>Model</th>
				<td><html:text property="product.model">${product.model }</html:text></td>
				<td><div id="invalid_model"></div></td>
			</tr>
			<tr>
				<th>Color</th>
				<td><html:text property="product.color">${product.color }</html:text></td>
				<td><div id="invalid_color"></div></td>
			</tr>
			<tr>
				<th>Date of issue</th>
				<td><html:text property="product.dateOfIssue">${product.dateOfIssue }</html:text></td>
				<td><div id="invalid_date"></div></td>
			</tr>
			<tr>
				<th>Price</th>
				<td><html:text property="product.price">${product.price }</html:text></td>
				<td><div id="invalid_price"></div></td>
			</tr>
			<tr>
				<th>Not in stock</th>
				<td><c:choose>
						<c:when test="${product.notInStock }">
							<html:checkbox property="product.notInStock" value="${product.notInStock }" />
						</c:when>
						<c:otherwise>
							<html:checkbox property="product.notInStock" />
						</c:otherwise>
					</c:choose></td>
			</tr>
		</table>
		<p>
			<html:submit>Add</html:submit>
			<html:submit onclick="location.replace('ShowProducts.do')">Back</html:submit>
		</p>
		<html:hidden property="current_category" value="${current_category }" />
		<html:hidden property="current_subcategory"
			value="${current_subcategory}" />
	</html:form>
	<p></p>
</body>
</html>