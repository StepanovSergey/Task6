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
<title>Products</title>
</head>
<body>
	<p>List of products:</p>
	<table border=1>
		<tr>
			<th>Producer</th>
			<th>Model</th>
			<th>Color</th>
			<th>Date of issue</th>
			<th>Price</th>
		</tr>
		<c:forEach items="${products }" var="product">
			<tr>
				<td>${product.producer }</td>
				<td>${product.model }</td>
				<td>${product.color }</td>
				<td>${product.dateOfIssue }</td>
				<td><c:choose>
						<c:when test="${product.price > 0 }">${product.price }</c:when>
						<c:otherwise>Not in stock</c:otherwise>
					</c:choose></td>
			</tr>
		</c:forEach>
	</table>
	<p>
		<html:link action="/AddProduct">Add product
			<html:param name="current_category" value="${current_category }"></html:param>
			<html:param name="current_subcategory" value="${current_subcategory}"></html:param>
		</html:link>
	</p>
	<p>
		<html:link action="/ShowSubcategories">Back
		<html:param name="current_category" value="${current_category }"></html:param>
		</html:link>
	</p>
</body>
</html>