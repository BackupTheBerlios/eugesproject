<?xml version="1.0"?> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="xml" indent="yes"/>


<xsl:template match="AIDE">
<HTML>
<BODY BGCOLOR='#EEEEEE'>

<TABLE WIDTH='100%'>
	<TR bgcolor='#000000'>
		<TD WIDTH='20%' align='center'>
			<IMG WIDTH='100' src='images/logo.png'/>
		</TD>
		<TD WIDTH='80%' align='center'>
			<B><FONT color='#FFFFFF' size='5' face='arial'>Aide du logiciel EUGES </FONT></B>
		</TD>
	</TR>
</TABLE>

<BR/>

<p align='right'>
<B><FONT color='#000000' size='5' face='arial'>
	<xsl:value-of select="@NOM"/>
</FONT></B>
</p>




<TABLE WIDTH='100%'>
	<TR WIDTH='100%' valign='top'>
		<TD WIDTH='20%'>
			<TABLE WIDTH='100%'>
				<xsl:apply-templates select="VOIR"/>
			</TABLE>
		</TD>
		<TD>
			<TABLE WIDTH='100%'>
				<TR>
					<TD>
						<xsl:apply-templates select="CONTENU"/>
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
</TABLE>



</BODY>
</HTML>
</xsl:template>



<xsl:template match="VOIR">
	<TR>
		<TD bgcolor='#000000'>
			<IMG SRC='images/icone.jpg' HEIGHT='20'/>
			<B><FONT color='#FFFFFF' size='2' face='arial'>Voir aussi</FONT></B>
		</TD>
	</TR>
	<xsl:apply-templates select="PAGE"/>
</xsl:template>





<xsl:template match="PAGE">
	<TR>
		<TD>
			<xsl:variable name="url_page" select="@src"/>
			<IMG SRC='images/scroll_rt.gif'/>
			<A HREF='{$url_page}'>
				<xsl:value-of select="@NOM"/>
			</A>
		</TD>
	</TR>
</xsl:template>



<xsl:template match="CONTENU">
	<BR/><BR/>
	<P>		
		<xsl:apply-templates select="PARTIE"/>
	</P>
</xsl:template>



<xsl:template match="PARTIE">
		<IMG SRC='images/scroll_rt.gif'/>
		<B><U><FONT size='4' face='arial'>
			<xsl:value-of select="@NOM"/>
		</FONT></U></B>
		<BR/>

		<xsl:copy-of select="."/>
		<BR/><BR/><BR/>
</xsl:template>


</xsl:stylesheet>
