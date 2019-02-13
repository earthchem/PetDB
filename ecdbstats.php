<?php
$jsondata=file_get_contents("https://ecapi.earthchem.org/statistics/entitystatistics");
$obj = json_decode($jsondata);
$obj0 = $obj->data[0];
$reftotal   = number_format($obj0->citations);
$sampletotal = number_format($obj0->specimens);
$rock_points = number_format($obj0->rock_values);
$minerals = number_format($obj0->mineral_values);
$volcanics = number_format($obj0->volc_glass_values);
$inclusions = number_format($obj0->inclusion_values);
$totalvalues = number_format($obj0->total_chem_values);
$stations = number_format($obj0->stations);

echo "<ul style=\"font-family:Verdana,&quot;DejaVu Sans&quot;,Sans;font-size:11.2px; line-height: 16px;color:#666666;margin-top:1px;margin-bottom:1px;font-wight:400;\">";

echo "<li>References:   $reftotal";
echo "</li><li>Samples: &nbsp;  $sampletotal ";
echo "</li><li>Bulk rock data points:&nbsp;$rock_points ";
echo "</li><li>Minerals:$minerals";
echo "</li><li>Volcanic glasses: $volcanics";
echo "</li><li>Melt inclusions:&nbsp; $inclusions";
echo "</li><li>Total individual values:&nbsp;$totalvalues";
echo "</li><li> Number of stations:  $stations";
echo "</li></ul>";

?>
