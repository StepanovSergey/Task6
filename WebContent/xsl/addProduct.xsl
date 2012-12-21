<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:product="xalan://com.epam.task5.model.Product"
	xmlns:validator="xalan://com.epam.task5.validation.ProductValidator">
	<xsl:param name="product" />
	<xsl:param name="current_category" />
	<xsl:param name="current_subcategory" />
	<xsl:param name="validator" />
	<xsl:template match="/">
		<xsl:choose>
			<xsl:when test="validator:isProductValid($validator)">
				<xsl:apply-templates />
			</xsl:when>
			<xsl:otherwise>
				<html>
					<head>
						<link rel="stylesheet" type="text/css" href="css/style.css" />
						<title>Add product</title>
					</head>
					<body>
						<p>Add product</p>
						<table>
							<xsl:call-template name="addProduct" />
							<tr>
								<td></td>
								<td>
									<form action="ShowProducts.do" metod="POST">
										<input type="hidden" name="current_category" value="{$current_category}" />
										<input type="hidden" name="current_subcategory" value="{$current_subcategory}" />
										<input type="submit" value="Back" />
									</form>
								</td>
							</tr>
						</table>
					</body>
				</html>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>


	<xsl:template name="addProduct">
		<form action="AddProduct.do" metod="POST">
			<input type="hidden" name="current_category" value="{$current_category}" />
			<input type="hidden" name="current_subcategory" value="{$current_subcategory}" />
			<tr>
				<th>Producer</th>
				<td>
					<input type="text" name="producer" value="{product:getProducer($product)}" />
				</td>
				<td>
					<xsl:if
						test="validator:isProducerInvalid(product:getProducer($product))">
						<p class="error">Producer must be specified</p>
					</xsl:if>
				</td>
			</tr>
			<tr>
				<th>Model</th>
				<td>
					<input type="text" name="model" value="{product:getModel($product)}" />
				</td>
				<td>
					<xsl:if test="validator:isModelInvalid(product:getModel($product))">
						<p class="error">Model must contain 2 letters and 3 digits</p>
					</xsl:if>
				</td>
			</tr>
			<tr>
				<th>Date Of Issue</th>
				<td>
					<input type="text" name="date_of_issue" value="{product:getDateOfIssue($product)}" />
				</td>
				<td>
					<xsl:if test="validator:isDateInvalid(product:getDateOfIssue($product))">
						<p class="error">Date must be dd-MM-yyyy</p>
					</xsl:if>
				</td>
			</tr>
			<tr>
				<th>Color</th>
				<td>
					<input type="text" name="color" value="{product:getColor($product)}" />
				</td>
				<td>
					<xsl:if test="validator:isColorInvalid(product:getColor($product))">
						<p class="error">Color must be specified</p>
					</xsl:if>
				</td>
			</tr>
			<tr>
				<th>Price</th>
				<td>
					<input type="text" name="price" value="{product:getPrice($product)}" />
				</td>
				<td>
					<xsl:if test="validator:isPriceInvalid(product:getPrice($product))">
						<p class="error">Price must be e.g 14, 10.0 or 17.12</p>
					</xsl:if>
				</td>
			</tr>
			<tr>
				<th>Not in stock</th>
				<td>
					<xsl:variable name="notInStock" />
					<xsl:choose>
						<xsl:when test="product:isNotInStock($product)">
							<input type="checkbox" name="not_in_stock" checked="true" />
						</xsl:when>
						<xsl:otherwise>
							<input type="checkbox" name="not_in_stock" />
						</xsl:otherwise>
					</xsl:choose>

				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td>
					<input type="submit" value="Save" />
				</td>
			</tr>
		</form>
	</xsl:template>

	<xsl:template match="node()|@*">
		<xsl:copy>
			<xsl:apply-templates select="node()|@*" />
		</xsl:copy>
	</xsl:template>

	<xsl:template
		match="products/category[@name=$current_category]/subcategory[@name=$current_subcategory]">
		<xsl:copy>
			<xsl:apply-templates select="node()|@*" />
			<xsl:element name="product">
				<xsl:element name="producer">
					<xsl:value-of select="product:getProducer($product)" />
				</xsl:element>
				<xsl:element name="model">
					<xsl:value-of select="product:getModel($product)" />
				</xsl:element>
				<xsl:element name="date_of_issue">
					<xsl:value-of select="product:getDateOfIssue($product)" />
				</xsl:element>
				<xsl:element name="color">
					<xsl:value-of select="product:getColor($product)" />
				</xsl:element>
				<xsl:choose>
					<xsl:when test="product:getPrice($product) != 0">
						<xsl:element name="price">
							<xsl:value-of select="product:getPrice($product)" />
						</xsl:element>
					</xsl:when>
					<xsl:otherwise>
						<xsl:element name="not_in_stock" />
					</xsl:otherwise>
				</xsl:choose>
			</xsl:element>
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>