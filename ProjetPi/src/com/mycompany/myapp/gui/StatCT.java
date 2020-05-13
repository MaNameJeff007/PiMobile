/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;


import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.models.XYMultipleSeriesDataset;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.renderers.XYMultipleSeriesRenderer.Orientation;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.charts.views.BarChart;
import com.codename1.charts.views.BarChart.Type;
//import com.codename1.demos.charts.AbstractDemoChart;
import com.codename1.ui.Form;
import java.util.ArrayList;
import java.util.List;

import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.AbstractChart;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.animations.FlipTransition;
import com.codename1.ui.animations.Transition;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Bulletin;
import com.mycompany.myapp.entities.Classe;
import com.mycompany.myapp.services.ServiceBulletin;
import com.mycompany.myapp.services.ServiceClasse;

/**
 *
 * @author dell
 */
public class StatCT extends Form {

    Component sp;

    protected XYMultipleSeriesDataset buildBarDataset(String[] titles, List<double[]> values) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        int length = titles.length;
        for (int i = 0; i < length; i++) {
            CategorySeries series = new CategorySeries(titles[i]);
            double[] v = values.get(i);
            int seriesLength = v.length;
            for (int k = 0; k < seriesLength; k++) {
                series.add(v[k]);
            }
            dataset.addSeries(series.toXYSeries());
        }
        return dataset;
    }

    Font smallFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.SIZE_SMALL, Font.STYLE_PLAIN);
    Font medFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.SIZE_MEDIUM, Font.STYLE_PLAIN);
    Font largeFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.SIZE_LARGE, Font.STYLE_PLAIN);

    protected XYMultipleSeriesRenderer buildBarRenderer(int[] colors) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setAxisTitleTextSize(smallFont.getHeight() / 2);
        renderer.setChartTitleTextFont(smallFont);
        renderer.setLabelsTextSize(smallFont.getHeight() / 2);
        renderer.setLegendTextSize(smallFont.getHeight() / 2);
        int length = colors.length;
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(colors[i]);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }

    protected void initRendererer(DefaultRenderer renderer) {
        renderer.setBackgroundColor(0xffffffff);
        renderer.setApplyBackgroundColor(true);
        renderer.setLabelsColor(0xff000000);
        renderer.setAxesColor(0xff000000);
        if (Font.isNativeFontSchemeSupported()) {
            Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
                    derive(Display.getInstance().convertToPixels(2.5f), Font.STYLE_PLAIN);
            renderer.setTextTypeface(fnt);
            renderer.setChartTitleTextFont(fnt);
            renderer.setLabelsTextFont(fnt);
            renderer.setLegendTextFont(fnt);

            if (renderer instanceof XYMultipleSeriesRenderer) {
                ((XYMultipleSeriesRenderer) renderer).setAxisTitleTextFont(fnt);
            }
            if (renderer instanceof XYMultipleSeriesRenderer) {
                XYMultipleSeriesRenderer x = (XYMultipleSeriesRenderer) renderer;
                x.setMarginsColor(0xfff7f7f7);
                x.setXLabelsColor(0xff000000);
                x.setYLabelsColor(0, 0xff000000);
            }
        }

    }

    protected ChartComponent newChart(AbstractChart chart) {
        ChartComponent c = new ChartComponent(chart);
        c.setFocusable(true);
        c.setZoomEnabled(true);
        c.setPanEnabled(true);
        return c;
    }

    /**
     * Sets a few of the series renderer settings.
     *
     * @param renderer the renderer to set the properties to
     * @param title the chart title
     * @param xTitle the title for the X axis
     * @param yTitle the title for the Y axis
     * @param xMin the minimum value on the X axis
     * @param xMax the maximum value on the X axis
     * @param yMin the minimum value on the Y axis
     * @param yMax the maximum value on the Y axis
     * @param axesColor the axes color
     * @param labelsColor the labels color
     */
    protected void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle,
            String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor,
            int labelsColor) {
        renderer.setChartTitle(title);
        renderer.setXTitle(xTitle);
        renderer.setYTitle(yTitle);
        renderer.setXAxisMin(xMin);
        renderer.setXAxisMax(xMax);
        renderer.setYAxisMin(yMin);
        renderer.setYAxisMax(yMax);
        renderer.setAxesColor(axesColor);
        renderer.setLabelsColor(labelsColor);
    }

    public Component execute(double v1, double v2, double v3) {
        String[] titles = new String[]{"MT<10", "10<MT<14", "MT>14"};
        List<double[]> values = new ArrayList<double[]>();
        /* for (Bulletin us : rr) {
             System.out.println(Math.round(us.getMoyenne()));
        
        }*/
        values.add(new double[]{v1, 0, 0}); // Gnrl  
        values.add(new double[]{0, v2, 0}); // Gnr Pvt
        values.add(new double[]{0, 0, v3}); // Conc.
        /*values.add(new double[]{6, 12, 20, 10}); // Pvt
        values.add(new double[]{21, 12, 22, 23}); // VA
        values.add(new double[]{11, 12, 20, 21}); // S3*/
        int[] colors = new int[]{ColorUtil.rgb(0, 76, 153), ColorUtil.rgb(0, 102, 204), ColorUtil.rgb(0, 128, 255), ColorUtil.rgb(51, 153, 255), ColorUtil.rgb(102, 178, 255), ColorUtil.rgb(153, 204, 255)};
        XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
        setChartSettings(renderer, "Pourcentage par intervalles de  moyennes", "", "Pourcentage", 0.5,
                4.5, 0, 100, ColorUtil.GRAY, ColorUtil.LTGRAY);

        renderer.setXLabels(0);
        renderer.setYLabels(0);
        renderer.setXLabelsAlign(Component.LEFT);
        renderer.setYLabelsAlign(Component.LEFT);
        renderer.setPanEnabled(true, false);
        // renderer.setZoomEnabled(false);
        renderer.setZoomRate(1.1f);
        renderer.setBarSpacing(0.5f);
        renderer.setApplyBackgroundColor(true);
        renderer.setBarWidth(70);
        renderer.addXTextLabel(1, "MT<10");
        renderer.addXTextLabel(2, "10<MT<14");
        renderer.addXTextLabel(3, "MT>14");
        //renderer.addXTextLabel(4, "7 DAY AVG");
        initRendererer(renderer);
//      renderer.setChartTitleTextSize(50);
//      renderer.setChartTitleTextSize(50);
//      renderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
        int length = renderer.getSeriesRendererCount();
        // System.out.println("Series len "+length);
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer seriesRenderer = (XYSeriesRenderer) renderer.getSeriesRendererAt(i);
            seriesRenderer.setDisplayChartValues(true);
            seriesRenderer.setChartValuesTextFont(largeFont);
//            seriesRenderer.setChartValuesFormat(new ICRNumerFormat());
        }

        BarChart chart = new BarChart(buildBarDataset(titles, values), renderer,
                Type.STACKED);
        return newChart(chart);

    }

    public StatCT(Form previous) {

        setTitle("Statistiques moyennes trimestrielles ");
        //setLayout(BoxLayout.y());
        Container cnt1 = new Container(BoxLayout.y());

        ComboBox classes = new ComboBox();
        ComboBox tr = new ComboBox();

        tr.addItem("1");
        tr.addItem("2");
        tr.addItem("3");

        ArrayList<Classe> arr = new ArrayList<>();
        ArrayList<Classe> arr2 = new ArrayList<>();

        arr2 = ServiceClasse.getInstance().getAllTasks();
        ArrayList<Bulletin> st = new ArrayList<>();
        // ArrayList<Bulletin> st1 = new ArrayList<>();

        for (int i = 0; i < ServiceClasse.getInstance().getAllTasks().size(); i++) {
            arr.add(arr2.get(i));

        }
        for (Classe us : arr) {
            classes.addItem(us.getLibelle());
        }
        Container cnt2 = new Container(BoxLayout.y());
        Container chart = new Container(BoxLayout.y());
        //Button btn = new Button("hereeeee");
        classes.addActionListener((e) -> {
            System.out.println(classes.getSelectedItem());
            cnt2.removeComponent(chart);
            chart.removeAll();
            //chart.clearClientProperties();
            for (Classe us : arr) {

                if (us.getLibelle().equals(classes.getSelectedItem())) {
                    double v1 = 0;
                    double v2 = 0;
                    double v3 = 0;

                    System.out.println(ServiceBulletin.getInstance().getStatMT1(us.getLibelle(), Integer.parseInt((String) tr.getSelectedItem())));

                    for (int i = 0; i < ServiceBulletin.getInstance().getStatMT1(us.getLibelle(), Integer.parseInt((String) tr.getSelectedItem())).size(); i++) {
                        st.add(ServiceBulletin.getInstance().getStatMT1(us.getLibelle(), Integer.parseInt((String) tr.getSelectedItem())).get(i));
                    }

                    for (Bulletin b : st) {

                        v1 = (double) Math.round(b.getMoyenne() * 100) / 100;

                        System.out.println(v1);
                    }

                    st.clear();

                    for (int i = 0; i < ServiceBulletin.getInstance().getStatMT2(us.getLibelle(), Integer.parseInt((String) tr.getSelectedItem())).size(); i++) {
                        st.add(ServiceBulletin.getInstance().getStatMT2(us.getLibelle(), Integer.parseInt((String) tr.getSelectedItem())).get(i));
                    }

                    for (Bulletin b : st) {

                        v2 = (double) Math.round(b.getMoyenne() * 100) / 100;
                        //Math.round(b.getMoyenne());
                        System.out.println(v2);
                    }

                    st.clear();

                    for (int i = 0; i < ServiceBulletin.getInstance().getStatMT3(us.getLibelle(), Integer.parseInt((String) tr.getSelectedItem())).size(); i++) {
                        st.add(ServiceBulletin.getInstance().getStatMT3(us.getLibelle(), Integer.parseInt((String) tr.getSelectedItem())).get(i));
                    }

                    for (Bulletin b : st) {

                        v3 = (double) Math.round(b.getMoyenne() * 100) / 100;
                        //Math.round(b.getMoyenne());
                        System.out.println(v3);
                    }

                    System.out.println(st);
                    sp = execute(v1, v2, v3);
                    chart.add(sp);
                    cnt2.add(chart);
                    //sp.replaceAndWait(this,sp, new FlipTransition(-1, 150));

                }
            }

        });

        cnt1.add(classes);
        addAll(cnt1, tr, cnt2);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new HomeScolarite().show());
    }
}
