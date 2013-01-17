<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Categories</title>
</head>
<body>
	<p>Choose category:</p>

	<!-- Set product form as root element -->
	<nested:root name="productForm">
		<nested:nest property="document.rootElement">

			<!-- Iterate over categories -->
			<nested:iterate property="children" indexId="categoryNumber">

				<!-- Category size variable -->
				<bean:define id="categorySize" value="0" />

				<!-- Count products in categories -->
				<!-- Iterate over subcategories in category -->
				<nested:iterate id="subcategory" property="children">

					<!-- Count products in current subcategory -->
					<nested:size id="currentSubcategorySize"
						collection="${subcategory.children}" />

					<!-- Add subcategory size to category size variable -->
					<bean:define id="categorySize"
						value="${categorySize + currentSubcategorySize }" />
				</nested:iterate>

				<!-- Setting category name variable -->
				<c:set var="categoryName">
					<nested:write property='attributes[0].value' />
				</c:set>

				<!-- Show links to subcategories -->
				<p>
					<html:link action="/ShowSubcategories">
				 		${categoryName} (${categorySize} items)
						<html:param name="categoryName" value="${categoryName}" />
						<html:param name="categoryNumber" value="${categoryNumber}" />
					</html:link>
				</p>
			</nested:iterate>
		</nested:nest>
	</nested:root>
</body>
</html>