<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="*">
		<!-- will write out the opening earthhem model tag - the root element -->
		<xsl:element name="EarthChemModel">
			<xsl:attribute name="firstResultPosition"><xsl:value-of select="current()/@firstResultPosition"/></xsl:attribute>
			<xsl:attribute name="majordateupdated"></xsl:attribute>
			<xsl:attribute name="totalResultsAvailable"></xsl:attribute>
			<xsl:attribute name="totalResultsReturned"><xsl:value-of select="current()/@numrows"/></xsl:attribute>
			<xsl:apply-templates/> <!-- now process the child elements -->
		</xsl:element>
		<!-- closes the root element -->
	</xsl:template>
	<xsl:template match="earthchemsample">
        <xsl:if test="current()/comments/comments_row">
		<xsl:element name="EarthChemSample">
			<xsl:attribute name="sample_id"><xsl:value-of select="sample_id"/></xsl:attribute>
			<xsl:attribute name="samplenumber"><xsl:value-of select="sample_num"/></xsl:attribute>
			<xsl:attribute name="source">PETDB</xsl:attribute>
			<xsl:element name="Metadata">
				<xsl:element name="Url">http://www.petdb.org/sample_info.jsp?singlenum=<xsl:value-of select="sample_num"/>
				</xsl:element>
				<!-- url -->
				<xsl:element name="CitationData">
					<xsl:for-each select="comments/comments_row">
						<xsl:element name="Citation">
							<xsl:attribute name="citationID"><xsl:value-of select="ref_num"/></xsl:attribute>
							<xsl:attribute name="journal"><xsl:value-of select="journal"/></xsl:attribute>
							<xsl:attribute name="year"><xsl:value-of select="pub_year"/></xsl:attribute>
							<xsl:element name="Title">
								<xsl:value-of select="title"/>
							</xsl:element>
							<xsl:for-each select="authors/authors_row">
								<xsl:element name="Author">
									<xsl:value-of select="last_name"/>,<xsl:value-of select="first_name"/>
								</xsl:element>
							</xsl:for-each>
						</xsl:element>
					</xsl:for-each>
				</xsl:element>
				<!-- citation data -->
				<xsl:for-each select="comments/comments_row">
					<xsl:element name="Sampletype">
						<xsl:attribute name="citationID"><xsl:value-of select="ref_num"/></xsl:attribute>
						<xsl:element name="ROCK">
							<xsl:element name="class1">
								<xsl:choose>
									<xsl:when test="contains(rocktype_name, 'igneous')">igneous</xsl:when>
									<xsl:when test="contains(rocktype_name, 'xenolith')">xenolith</xsl:when>
									<xsl:when test="contains(rocktype_name, 'metamorphic')">metamorphic</xsl:when>
									<xsl:when test="contains(rocktype_name, 'ore')">ore</xsl:when>
									<xsl:otherwise/>
								</xsl:choose>
							</xsl:element>
							<xsl:element name="class2">
								<xsl:choose>
									<xsl:when test="contains(rocktype_name, ':')">
										<xsl:if test="contains(rocktype_name, 'plutonic')">plutonic</xsl:if>
										<xsl:if test="contains(rocktype_name, 'volcanic')">volcanic</xsl:if>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="rockclass"/>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:element>
							<xsl:if test="contains(rocktype_name, ':')">
								<xsl:element name="class3">
									<xsl:choose>
										<xsl:when test="contains(rocktype_name, 'mafic')">mafic</xsl:when>
										<xsl:when test="contains(rocktype_name, 'ultramafic')">ultramafic</xsl:when>
										<xsl:when test="contains(rocktype_name, 'intermediate')">intermediate</xsl:when>
										<xsl:when test="contains(rocktype_name, 'felsic')">felsic</xsl:when>
										<xsl:otherwise/>
									</xsl:choose>
								</xsl:element>
								<xsl:element name="class4">
									<xsl:value-of select="rockclass"/>
								</xsl:element>
							</xsl:if>
						</xsl:element>
						<!-- ROCK -->
					</xsl:element>
					<!-- citation data -->
				</xsl:for-each>
				<xsl:element name="Geography">
					<xsl:for-each select="locations">
						<xsl:choose>
							<xsl:when test="count(locations_row)=1">
								<xsl:element name="Location">
									<xsl:element name="Point">
										<xsl:element name="coord">
											<xsl:element name="X">
												<xsl:value-of select="locations_row/longitude"/>
											</xsl:element>
											<xsl:element name="Y">
												<xsl:value-of select="locations_row/latitude"/>
											</xsl:element>
										</xsl:element>
									</xsl:element>
								</xsl:element>
								<xsl:element name="Location_Precision">
									<xsl:value-of select="locations_row/loc_precision"/>
								</xsl:element>
							</xsl:when>
							<xsl:when test="count(locations_row)>1">
								<xsl:element name="Location">
									<xsl:element name="LineString">
										<xsl:for-each select="locations_row">
											<xsl:element name="coord">
												<xsl:element name="X">
													<xsl:value-of select="longitude"/>
												</xsl:element>
												<xsl:element name="Y">
													<xsl:value-of select="latitude"/>
												</xsl:element>
											</xsl:element>
										</xsl:for-each>
									</xsl:element>									
								</xsl:element>
								<xsl:element name="Location_Precision">
										<xsl:value-of select="locations_row/loc_precision[position()=1]"/>
									</xsl:element>
							</xsl:when>
							<xsl:otherwise>
								<xsl:element name="locs">
									<xsl:value-of select="count(locations/locations_row)"/>
								</xsl:element>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:for-each>
				</xsl:element>
				<!-- geography -->
			</xsl:element>
			<!-- metadata -->
			<xsl:element name="EarthChemData">
				<xsl:for-each select="chemdata/chemdata_row">
					<xsl:element name="item">
						<xsl:attribute name="group">chemical</xsl:attribute>
						<xsl:attribute name="name"><xsl:value-of select="item_code"/></xsl:attribute>
						<xsl:attribute name="type">
								<xsl:choose>
										<xsl:when test="item_type_code='AGE'">age</xsl:when>
										<xsl:when test="item_type_code='EM'">end_member</xsl:when>
										<xsl:when test="item_type_code='IR'">isotopic_ratio</xsl:when>
										<xsl:when test="item_type_code='IS'">is</xsl:when>
										<xsl:when test="item_type_code='MAJ'">major_oxides</xsl:when>
										<xsl:when test="item_type_code='NGAS'">noble_gas</xsl:when>
										<xsl:when test="item_type_code='REE'">ree</xsl:when>
										<xsl:when test="item_type_code='TE'">te</xsl:when>
										<xsl:when test="item_type_code='US'">u_series</xsl:when>
										<xsl:when test="item_type_code='VO'">volatile</xsl:when>										
										<xsl:otherwise/>
									</xsl:choose>
						</xsl:attribute>
						<xsl:for-each select="chem/chem_row">
							<xsl:element name="method">
								<xsl:attribute name="citationID"><xsl:value-of select="ref_num"/></xsl:attribute>
								<xsl:attribute name="name"><xsl:value-of select="method_code"/></xsl:attribute>
								<xsl:attribute name="qualityrank"><xsl:value-of select="qualityrank"/></xsl:attribute>
								<xsl:attribute name="units"><xsl:value-of select="unit"/></xsl:attribute>
								<xsl:attribute name="value"><xsl:value-of select="value_meas"/></xsl:attribute>
							</xsl:element>
						</xsl:for-each>
					</xsl:element>
				</xsl:for-each>
			</xsl:element><!--EarthChemData -->
		</xsl:element><!--earth chem sample -->
        </xsl:if>
	</xsl:template>
</xsl:stylesheet>
