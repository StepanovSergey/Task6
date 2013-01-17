<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Subcategories</title>
</head>
<body>
	<!-- Define variables -->
	<nested:define id="categoryName" name="productForm"
		property="categoryName" />
	<nested:define id="categoryNumber" name="productForm"
		property="categoryNumber" />
		
	<!-- Show header -->
	<p>Category: ${categoryName } &rarr; Choose subcategory:</p>

	<!-- Set product form as root element as root element -->
	<nested:root name="productForm">
		<nested:nest
			property="document.rootElement.children[${categoryNumber}]">

			<!-- Iterate over categories -->
			<nested:iterate id="category" indexId="subcategoryNumber"
				property="children">

				<!-- Count products in subcategory -->
				<nested:size id="subcategorySize" collection="${category.children}" />

				<!-- Setting category name variable -->
				<c:set var="subcategoryName">
					<nested:write property='attributes[0].value' />
				</c:set>

				<!-- Show links to subcategories -->
				<p>
					<html:link action="/ShowProducts">
				 		${subcategoryName} (${subcategorySize} items)
						<html:param name="subcategoryName" value="${subcategoryName }" />
						<html:param name="subcategoryNumber" value="${subcategoryNumber }" />
					</html:link>
				</p>
			</nested:iterate>
		</nested:nest>
	</nested:root>

	<!-- Back button -->
	<input type="button" value="Back"
		onclick="window.location = 'ShowCategories.do'">
</body>
</html>



