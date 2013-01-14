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
<title>Categories</title>
</head>
<body>
	<p>Choose category:</p>
	<nested:define id="categoryData" name="productForm"
		property="categoryData" />
	<!-- Iterate over categories -->
	<nested:iterate id="category" name="productForm"
		property="document.rootElement.children" indexId="categoryNumber">
		<!-- Setting category name variable -->
		<c:set var="categoryName">
			<nested:write property='attributes[0].value' />
		</c:set>
		<!-- Show links to subcategories -->
		<p>
			<html:link action="/ShowSubcategories">
				 ${categoryName} (${categoryData.get(categoryName)} items)
				<html:param name="categoryName" value="${categoryName}" />
				<html:param name="categoryNumber" value="${categoryNumber}" />
			</html:link>
		</p>
	</nested:iterate>
</body>
</html>