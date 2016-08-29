package petdb.data;

import java.util.*;
import java.awt.*;
import java.io.PrintWriter;
import javax.servlet.http.HttpSession;


import org.jfree.chart.labels.*; 
import org.jfree.chart.labels.CustomXYToolTipGenerator;
import org.jfree.chart.plot.*;
import org.jfree.chart.entity.*;
import org.jfree.chart.urls.*;
import org.jfree.chart.servlet.*;
import org.jfree.chart.renderer.*; 
import org.jfree.chart. *;
import org.jfree.chart.axis.*;
import org.jfree.data.*;
 
public class XYChartExample
{

	private Vector series;
	private XYSeriesCollection dataset= null;
	private JFreeChart chart;
	FinalSampleDS data;

	public XYChartExample(FinalSampleDS ds, int[] al) throws Exception
	{
		data = ds;
		series = new Vector(al.length-1);
		for (int i=1; i< al.length; i++)  series.add(new XYSeries("Series " + i));
		setSeries(al);
	}

	public Plot getPlot()
	{
		return chart.getPlot();
	}


    	public void setSeries(int[] al) throws Exception 
	{
		while (data.next())
		{
			for (int i=1; i<al.length; i++)
			{
			if (
				(!" ".equals(data.getExlValue(al[0])))
				&&
				(!" ".equals(data.getExlValue(al[i])))
			   )
				
        			((XYSeries)series.elementAt(i-1)).add( new Double(data.getExlValue(al[0]))
							   	     , new Double(data.getExlValue(al[i]))
							   	     );
			}
		}
	}

	
	public void setDataset()
	{
        	dataset = new XYSeriesCollection((XYSeries)series.elementAt(0));
		for (int i = 1; i< series.size(); i++)
        		dataset.addSeries((XYSeries)series.elementAt(i));
        }

	public void createChart()
	{
        	 chart = ChartFactory.createScatterPlot(
            				"XY Chart Example",
            				"Domain (X)", "Range (Y)",
            				dataset,
            				PlotOrientation.VERTICAL,
            				true,  // legend
            				true,  // tool tips
            				false  // URLs
        				);
        
        	chart.setBackgroundPaint(Color.white);
        
        	XYPlot plot = chart.getXYPlot();
        	XYItemRenderer renderer = plot.getRenderer(); 
 		renderer.setBaseToolTipGenerator(new CustomXYToolTipGenerator());

		plot.setOutlinePaint(Color.black);
        	plot.setBackgroundPaint(Color.white);
        	plot.setForegroundAlpha(0.65f);
        	plot.setDomainGridlinePaint(Color.black);
        	plot.setRangeGridlinePaint(Color.black);
        	ValueAxis domainAxis = plot.getDomainAxis();
        	/*
		domainAxis.setTickMarkPaint(Color.black);
        	domainAxis.setLowerMargin(0.0);
        	domainAxis.setUpperMargin(0.0);
        
		XYItemRenderer renderer = ((XYPlot)plot).getRenderer();
		renderer.setShape(new Rectangle(new Dimension(1,1)));
        	ValueAxis rangeAxis = plot.getRangeAxis();
        	rangeAxis.setTickMarkPaint(Color.red);
        	*/
	}

	public String createChart(String title, HttpSession session, PrintWriter pw)
	{
            
        	createChart();
        	return createFile(session, pw);
        
    	}

	private String createFile(HttpSession session, PrintWriter pw)
	{
		String filename = "";
		try
		{
 		 ChartRenderingInfo info =
                 	new ChartRenderingInfo(new StandardEntityCollection());

                        filename = ServletUtilities.saveChartAsPNG(chart, 500, 300, info,session);

                        ChartUtilities.writeImageMap(pw, filename, info);
                        pw.flush();

                } catch (Exception e) {
                        System.out.println("Exception - " + e.toString());
                        e.printStackTrace(System.out);
                        filename = "public_error_500x300.png";
                }
                return filename;

	}
    
    
}
