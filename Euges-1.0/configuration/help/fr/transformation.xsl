<?xml version="1.0"?> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="xml" indent="yes"/>




<xsl:template match="AIDE">
<HTML>


<HEAD>
<script type="text/javascript">
	var w;
	function fenetrevolante(url)
	{
	        w = window.open(url, "photo_courante", "toolbar=no, location=no, directories=no, status=yes, scrollbars=no, resizable=yes, copyhistory=no, width=400, height=300, left=0, top=0");
		w.focus();
    	}


	function testclick(titre, texte1, texte2, texte3, texte4, texte5, image1, image2, image3, image4, image5)
	{
		tailleTitre = 4;
		var1 = '100%';
		var2 = '20%';
		varImg = '100';
		varAlign ='center';

		varFct1 = 'javascript:fenetrevolante("' + image1 +'");';
		varFct2 = 'javascript:fenetrevolante("' + image2 + '");';
		varFct3 = 'javascript:fenetrevolante("' + image3 + '");';
		varFct4 = 'javascript:fenetrevolante("' + image4 + '");';
		varFct5 = 'javascript:fenetrevolante("' + image5 + '");';

		txt.innerHTML ="<P><B><FONT size=" + tailleTitre + ">" + titre + "</FONT></B></P>";
		if (texte1 != null) txt.innerHTML = txt.innerHTML + "<P>" + texte1 + "</P>";
		if (texte2 != null) txt.innerHTML = txt.innerHTML + "<P>" + texte2 + "</P>";
		if (texte3 != null) txt.innerHTML = txt.innerHTML + "<P>" + texte3 + "</P>";
		if (texte4 != null) txt.innerHTML = txt.innerHTML + "<P>" + texte4 + "</P>";
		if (texte5 != null) txt.innerHTML = txt.innerHTML + "<P>" + texte5 + "</P>";

		txt.innerHTML = txt.innerHTML + "<BR/>";
		txt.innerHTML = txt.innerHTML + "<TABLE WIDTH=" + var1 + "><TR><TD ALIGN=" + varAlign +" WIDTH=" + var2 + ">";
		
		if (image1 != '') txt.innerHTML = txt.innerHTML + "<A HREF=" + varFct1 +"><IMG HEIGHT=" + varImg + " SRC=" + image1 + " /></A></TD>";
		if (image2 != '') txt.innerHTML = txt.innerHTML + "<TD WIDTH=" + var2 + "><A HREF=" + varFct2 +"><IMG HEIGHT=" + varImg + " SRC=" + image2 + " ONCLICK=" + varFct2 + "/></A></TD>";
		if (image3 != '') txt.innerHTML = txt.innerHTML + "<TD WIDTH=" + var2 + "><A HREF=" + varFct3 +"><IMG HEIGHT=" + varImg + " SRC=" + image3 + " ONCLICK=" + varFct3 + "/></A></TD>";
		if (image4 != '') txt.innerHTML = txt.innerHTML + "<TD WIDTH=" + var2 + "><A HREF=" + varFct4 +"><IMG HEIGHT=" + varImg + " SRC=" + image4 + " ONCLICK=" + varFct4 + "/></A></TD>";
		if (image5 != '') txt.innerHTML = txt.innerHTML + "<TD WIDTH=" + var2 + "><A HREF=" + varFct5 +"><IMG HEIGHT=" + varImg + " SRC=" + image5 + " ONCLICK=" + varFct5 + "/></A></TD>";

		txt.innerHTML = txt.innerHTML + "</TR></TABLE>";
	}
</script>
</HEAD>




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
			<BR/>
			<TABLE WIDTH='100%'>
				<TR>
					<TD bgcolor='#000000'>
						<IMG SRC='images/icone.jpg' HEIGHT='20'/>
							<B><FONT color='#FFFFFF' size='2' face='arial'>Chapitre(s)</FONT></B>
					</TD>
				</TR>
				<xsl:apply-templates select="BALISE"/>
			</TABLE>
		</TD>

		<TD WIDTH='80%' align='left'>
			<div id='txt'/>
		</TD>
	</TR>

</TABLE>

<BR/><BR/><BR/>

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






<xsl:template match="BALISE">
	<TR>
		<TD>

			<xsl:variable name="titre">
				<xsl:value-of select="@NOM"/>
			</xsl:variable>

			<xsl:variable name="texte1">
				<xsl:value-of select="PARTIE[1]"/>
			</xsl:variable>

			<xsl:variable name="texte2">
				<xsl:value-of select="PARTIE[2]"/>
			</xsl:variable>

			<xsl:variable name="texte3">
				<xsl:value-of select="PARTIE[3]"/>
			</xsl:variable>

			<xsl:variable name="texte4">
				<xsl:value-of select="PARTIE[4]"/>
			</xsl:variable>

			<xsl:variable name="texte5">
				<xsl:value-of select="PARTIE[5]"/>
			</xsl:variable>

			<xsl:variable name="image1">
				<xsl:value-of select="IMG[1]/@SRC"/>
			</xsl:variable>

			<xsl:variable name="image2">
				<xsl:value-of select="IMG[2]/@SRC"/>
			</xsl:variable>

			<xsl:variable name="image3">
				<xsl:value-of select="IMG[3]/@SRC"/>
			</xsl:variable>

			<xsl:variable name="image4">
				<xsl:value-of select="IMG[4]/@SRC"/>
			</xsl:variable>

			<xsl:variable name="image5">
				<xsl:value-of select="IMG[5]/@SRC"/>
			</xsl:variable>

			<IMG SRC='images/scroll_rt.gif'/>
			<A HREF="javascript:testclick('{$titre}', '{$texte1}', '{$texte2}', '{$texte3}', '{$texte4}', '{$texte5}', '{$image1}', '{$image2}', '{$image3}', '{$image4}', '{$image5}');">
				<xsl:value-of select="@NOM"/>
			</A>
		</TD>
	</TR>
</xsl:template>



</xsl:stylesheet>
