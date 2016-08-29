<%@ page import="java.net.URL"%>
<%@ include file="headCode.jspf"%>
<% 
boolean search;
CCriteriaCollection c_c_collection;
CombinedCriteria c_criteria;
  if ((c_c_collection = (CCriteriaCollection)session.getAttribute("ccColl")) == null)
  {
          search = false;
  } else
  {
       c_criteria = c_c_collection.getCurrentCCriteria();
       search = c_criteria.isSet();
  }  
String pgTitle = "PETDB - Download Options";
%>
<%@ include file="head.jsp" %>
<script LANGUAGE="JavaScript">
document.title = "<%=pgTitle%>";
</script>
<div class="pad" align="left">
<br />
<h1>Download Data </h1>
<br/>
                <form name="download_frm" method="post" action="ReferenceDownload">
                  <h2>PetDB References</h2>
                    <input name="All" type="hidden" value="All">
                    XLS file containing  bibliographic information for all references in  PetDB
                    <input name="download" type="button" id="download" value="Download References" onClick="submit(); return false;">
&nbsp;</form>
<form name="download_frm" method="post" action="StationDownload">
                  <h2>PetDB Stations</h2>
      XLS file containing information on all locations for which data is available in PetDB
                      <input name="All" type="hidden" value="All">
                      <input name="download" type="button" id="download" value="Download Stations" onClick="submit(); return false;">
&nbsp;
                </form>
                <h2>Datasets</h2>
                The following  comma-delimited (CSV) or and Excel (XLS) files are available for download.
                <h3>Global Chemistry of Back-Arc Basin Basalts</h3>
                <p>Dataset used for "Chemical Systematics and Hydrous Melting of the Mantle in Back-Arc Basins" by C.H. Langmuir, A. B&eacute;zos, S. Escrig, and S.W. Parman, in: Back-Arc Spreading Systems: Geological, Biological, Chemical, and Physical Interactions; Geophysical Monograph Series 166, AGU, 2006; DOI: 10.1029/166GM141                </p>
                <p>This dataset is a high-quality compilation of chemical analyses for about 850 back-arc basin basalt samples from PetDB. To obtain the most reliable and coherent BABB dataset, analyses of fresh glassy material were used when available, and individual major element analyses were corrected with interlaboratory correction factors. For a detailed description of data compilation and data selection see reference above (Langmuir et al. 2006).<br />
                    <a href="xsls/BABB_PETDB.xls">Download BABB Dataset</a> (xls) </p>
                  <h3>Ready-To-Use Datasets</h3>
                  <p>Ready-To-Use datasets were compiled from the PetDB database in 2002 by
  Yongjun Su. They contain global or regional compilations of data for
  specific geochemical parameters, with information about the original
  reference, analytical method, and sample location. </p>
                  <h4><span class="emphasis">PLEASE NOTE:</span> The datasets below are not updated and do not include data ingested into PetDB after 2002. </h4>
                  <p>Depending on your browser settings and platform, these files may open in your browser when clicked and the formatting may not be preserved correctly. PC users should right-click mouse to download files and Mac users should click and hold down mouse over the link to obtain a context menu showing the option to save the file to a computer drive. </p>
                <table align="center" valign="top" cellpadding="3" cellspacing="0" class="no_pad">
                    <tr>
                      <td width=50% valign=top><p><b><font size="4">Global rock data grouped by item</font></b></p>

                        <p>&nbsp; -- Rare Earth Elements:
                        <ul>
                          <li><a href="/petdbWeb/search/readydata/Joint_REE.csv">Joint_REE.csv</a></li>
                        </ul>
                        <p><font face="Arial">&nbsp; -- Incompatible Trace Elements:</font>
                        <ul>
                          <li><a href="/petdbWeb/search/readydata/Individual_TE_Ba.csv">Individual_TE_Ba.csv</a></li>

                          <li><a href="/petdbWeb/search/readydata/Individual_TE_Cs.csv">Individual_TE_Cs.csv</a></li>
                          <li><a href="/petdbWeb/search/readydata/Individual_TE_Hf.csv">Individual_TE_Hf.csv</a></li>
                          <li><a href="/petdbWeb/search/readydata/Individual_TE_Nb.csv">Individual_TE_Nb.csv</a></li>
                          <li><a href="/petdbWeb/search/readydata/Individual_TE_Pb.csv">Individual_TE_Pb.csv</a></li>
                          <li><a href="/petdbWeb/search/readydata/Individual_TE_Rb.csv">Individual_TE_Rb.csv</a></li>
                          <li><a href="/petdbWeb/search/readydata/Individual_TE_Ta.csv">Individual_TE_Ta.csv</a></li>

                          <li><a href="/petdbWeb/search/readydata/Individual_TE_Th.csv">Individual_TE_Th.csv</a></li>
                          <li><a href="/petdbWeb/search/readydata/Individual_TE_U.csv">Individual_TE_U.csv</a></li>
                          <li><a href="/petdbWeb/search/readydata/Individual_TE_Y.csv">Individual_TE_Y.csv</a></li>
                          <li><a href="/petdbWeb/search/readydata/Individual_TE_Zr.csv">Individual_TE_Zr.csv</a></li>
                        </ul>
                        <p>&nbsp; -- Sr, Nd, Pb isotopes:
                        <ul>

                          <li><a href="/petdbWeb/search/readydata/Individual_ISO_Sr.csv">Individual_ISO_Sr.csv</a></li>
                          <li><a href="/petdbWeb/search/readydata/Individual_ISO_Nd.csv">Individual_ISO_Nd.csv</a></li>
                          <li><a href="/petdbWeb/search/readydata/Joint_ISO_Pb.csv">Joint_ISO_Pb.csv</a></li>
                        </ul>
                      </td>
                      <td width=50% valign=top><p><b><font size="4">MORB data grouped by region </font></b></p>
                        <p><font face="Arial">&nbsp; -- Glass Probe Analyses Suite:</font>

                        <ul>
                          <li><a href="/petdbWeb/search/readydata/EPR23S-23N_major_probe.csv">East Pacific Rise (23&deg;S - 23&deg;N)</a></li>
                          <li><a href="/petdbWeb/search/readydata/MAR55S-52N_major_probe.csv">Mid-Atlantic Ridge (55&deg;S-52&deg;N)</a></li>
                          <li><a href="/petdbWeb/search/readydata/Indian_major_probe.csv">Indian Ocean</a></li>
                          <li><a href="/petdbWeb/search/readydata/Galapagos_major_probe.csv">Galapagos</a></li>

                          <li><a href="/petdbWeb/search/readydata/JDF_major_probe.csv">Juan De Fuca - Gorda</a></li>
                          <li><a href="/petdbWeb/search/readydata/Backarc_major_probe.csv">Back-Arc Basin</a></li>
                        </ul>
                        <p><font face="Arial">&nbsp; -- Major-Trace-Isotope Suite:</font> <br />
                          <font size="2"><font color="#FF0000">(Important notes:&nbsp; Data exist on either glass or whole rock.&nbsp; We compile all existing data for one sample into two rows, one for glass and one for whole.&nbsp; Users should decide how to make further compilation based on their needs)</font> </font>

                        <ul>
                          <li><a href="/petdbWeb/search/readydata/EPR23S-23N_major_trace_isotope.csv">East Pacific Rise (23&deg;S - 23&deg;N)</a></li>
                          <li><a href="/petdbWeb/search/readydata/MAR55S-52N_major_trace_isotope.csv">Mid-Atlantic Ridge (55&deg;S-52&deg;N)</a></li>
                          <li><a href="/petdbWeb/search/readydata/Indian_major_trace_isotope.csv">Indian Ocean</a></li>
                          <li><a href="/petdbWeb/search/readydata/Galapagos_major_trace_isotope.csv">Galapagos</a></li>

                          <li><a href="/petdbWeb/search/readydata/JDF_major_trace_isotope.csv">Juan De Fuca - Gorda</a></li>
                          <li><a href="/petdbWeb/search/readydata/Backarc_major_trace_isotope.csv">Back-Arc Basin</a></li>
                        </ul>
                      </td>
                    </tr>
  </table>
</div>
<!-- end pad -->
 <%@ include file="footer.jsp" %>
