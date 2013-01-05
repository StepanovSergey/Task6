<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Subcategories</title>
</head>
<body>
	<p>Choose subcategory:</p>
	<c:forEach items="${subcategory_data.keySet() }" var="key">
		<p>
			<html:link action="/ShowProducts">
				<c:out value="${key} (${subcategory_data.get(key)} items)"></c:out>
				<html:param name="current_category" value="${current_category }"></html:param>
				<html:param name="current_subcategory" value="${key }"></html:param>
			</html:link>
		</p>
	</c:forEach>
	<html:link action="/ShowCategories">Back</html:link>
</body>
</html:html>