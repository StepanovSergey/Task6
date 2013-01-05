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
<title>Categories</title>
</head>
<body>
<p>Choose category:</p>
	<c:forEach items="${category_data.keySet() }" var="key">
		<p>
			<html:link action="/ShowSubcategories">
				<c:out value="${key} (${category_data.get(key)} items)"></c:out>
				<html:param name="current_category" value="${key }"></html:param>
			</html:link>
		</p>
	</c:forEach>
</body>
</html>