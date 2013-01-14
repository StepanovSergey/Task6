<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Subcategories</title>
</head>
<body>
	<p>Category: ${categoryName }</p>
	<p>Choose subcategory:</p>
	<nested:define id="subcategoryData" name="productForm"
		property="subcategoryData" />

	<!-- Iterate over categories -->
	<nested:iterate id="category" name="productForm"
		indexId="subcategoryNumber"
		property="document.rootElement.children[${categoryNumber}].children">
		<!-- Setting category name variable -->
		<c:set var="subcategoryName">
			<nested:write property='attributes[0].value' />
		</c:set>
		<!-- Show links to subcategories -->
		<p>
			<html:link action="/ShowProducts">
				 ${subcategoryName} (${subcategoryData.get(subcategoryName)} items)
				<html:param name="categoryName" value="${categoryName }" />
				<html:param name="subcategoryName" value="${subcategoryName }" />
				<html:param name="subcategoryNumber" value="${subcategoryNumber }" />
			</html:link>
		</p>
	</nested:iterate>

	<input type="button" value="Back"
		onclick="window.location = 'ShowCategories.do'">
</body>
</html:html>



