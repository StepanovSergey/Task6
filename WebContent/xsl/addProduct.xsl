<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:product="xalan://com.epam.task5.model.Product"
	xmlns:validator="xalan://com.epam.task5.validation.ProductValidator">
	<xsl:param name="product" />
	<xsl:param name="current_category" />
	<xsl:param name="current_subcategory" />
	<xsl:template match="/">
		<xsl:apply-templates />
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