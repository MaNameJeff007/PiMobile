/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.charts.ChartComponent;
import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.CubicLineChart;
import com.codename1.charts.views.PointStyle;
import com.codename1.ui.Component;
import com.codename1.ui.Form;
import com.mycompany.myapp.entities.Moyenne;
import com.mycompany.myapp.services.MoyenneService;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rami2
 */
public class MoyennesChart  extends AbstractDemoChart {
  /**
   * Returns the chart name.
   * 
   * @return the chart name
   */
  public String getName() {
    return "Moyennes: ";
  }

  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  public String getDesc() {
    return "Evolution des moyennes";
  }

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
   
  public Form execute() 
  {     
    ArrayList<Moyenne> tab=MoyenneService.getInstance().getMoyenneStats(MoyennesEleveForm.eleveMoyenne, MoyennesEleveForm.matiereMoyenne); 
    
    double tri[];
    tri=new double [tab.size()];
   
    for(int i=0;i<tab.size();i++)
    {
        tri[i]=tab.get(i).getTrimestre();
    }
    
    
    double v[];
    v= new double [tab.size()];
    
    for(int i=0;i<tab.size();i++)
    {
        v[i]=tab.get(i).getMoyenne();
    }
        
    String[] titles = new String[] {MoyennesEleveForm.nomMatiereMoyenne};
    
    
    List<double[]> x = new ArrayList<double[]>();
    List<double[]> values = new ArrayList<double[]>();
    
    x.add(tri);
      
    values.add(v);
    
    int[] colors = new int[] { ColorUtil.BLUE};
    PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE };
    XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
    int length = renderer.getSeriesRendererCount();
    for (int i = 0; i < length; i++) {
      ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
    }
    setChartSettings(renderer, "", "Trimestre", "Moyenne", 1, 3, 0, 20,
        ColorUtil.LTGRAY, ColorUtil.LTGRAY);
    
    /*
    setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle,
      String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor,
      int labelsColor)
    */
    
    renderer.setXLabels(12);
    renderer.setYLabels(10);
    renderer.setShowGrid(true);
    renderer.setXLabelsAlign(Component.RIGHT);
    renderer.setYLabelsAlign(Component.RIGHT);
    renderer.setZoomButtonsVisible(true);
    renderer.setPanLimits(new double[] { -10, 20, -10, 40 });
    renderer.setZoomLimits(new double[] { -10, 20, -10, 40 });
    CubicLineChart chart = new CubicLineChart(
            buildDataset(titles, x, values),
            renderer,
            0.33f
    );
    ChartComponent c = new ChartComponent(chart);
    c.getStyle().setBgColor(0x0);
    return wrap("Moyenne", c);   
  }
}