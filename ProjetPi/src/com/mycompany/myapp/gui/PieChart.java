/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.models.MultipleCategorySeries;
import com.codename1.charts.models.SeriesSelection;
import com.codename1.charts.models.TimeSeries;
import com.codename1.charts.models.XYMultipleSeriesDataset;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.AbstractChart;
import com.codename1.charts.views.DoughnutChart;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.geom.Rectangle;
import com.codename1.ui.geom.Shape;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Bulletin;
import com.mycompany.myapp.entities.Classe;
import com.mycompany.myapp.services.ServiceBulletin;
import com.mycompany.myapp.services.ServiceClasse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author dell
 */
public class PieChart extends Form {

    Form current;

    Component sp;
    Font smallFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.SIZE_SMALL, Font.STYLE_PLAIN);
    Font medFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.SIZE_MEDIUM, Font.STYLE_PLAIN);
    Font largeFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.SIZE_LARGE, Font.STYLE_PLAIN);

    protected DefaultRenderer buildCategoryRenderer(int[] colors) {
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLabelsTextSize(smallFont.getHeight() / 2);
        renderer.setLegendTextSize(smallFont.getHeight() / 2);
        renderer.setMargins(new int[]{medFont.getHeight(), medFont.getHeight(), medFont.getHeight(), medFont.getHeight()});
        for (int color : colors) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(color);
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

    protected CategorySeries buildCategoryDataset(String title, double[] values) {
        CategorySeries series = new CategorySeries(title);

        String ch = "";
        int k = 0;

        for (double value : values) {

            ++k;

            if (k == 1) {
                ch = "MG<10";
            } else if (k == 2) {
                ch = "10<MG<12";
            } else if (k == 3) {
                ch = "12<MG<15";
            } else if (k == 4) {
                ch = "MG>15";
            }
            series.add("" + ch, value);

        }

        return series;
    }

    protected ChartComponent newChart(AbstractChart chart) {
        ChartComponent c = new ChartComponent(chart);
        c.setFocusable(true);
        c.setZoomEnabled(true);
        c.setPanEnabled(true);
        return c;
    }

    private int shiftColor(int color, double factor) {
        return ColorUtil.rgb(
                (int) Math.min(ColorUtil.red(color) * factor, 255),
                (int) Math.min(ColorUtil.green(color) * factor, 255),
                (int) Math.min(ColorUtil.blue(color) * factor, 255)
        );
    }

    public Component execute(double v1, double v2, double v3, double v4) {
        double[] values = new double[]{v1, v2, v3, v4};

        int[] colors = new int[]{ColorUtil.GREEN, ColorUtil.BLUE, ColorUtil.MAGENTA, ColorUtil.YELLOW, ColorUtil.CYAN};
        final DefaultRenderer renderer = buildCategoryRenderer(colors);
        for (SimpleSeriesRenderer r : renderer.getSeriesRenderers()) {
            r.setGradientEnabled(true);
            r.setGradientStart(0, shiftColor(r.getColor(), 0.8));
            r.setGradientStop(0, shiftColor(r.getColor(), 1.5));
        }
        renderer.setZoomButtonsVisible(true);
        renderer.setZoomEnabled(true);
        renderer.setChartTitleTextFont(largeFont);
        renderer.setDisplayValues(true);
        renderer.setShowLabels(true);
        initRendererer(renderer);

        final CategorySeries seriesSet = buildCategoryDataset("Moyennes générales par classe", values);
        final com.codename1.charts.views.PieChart chart = new com.codename1.charts.views.PieChart(seriesSet, renderer);
        ChartComponent comp = new ChartComponent(chart) {

            private boolean inDrag = false;

            @Override
            public void pointerPressed(int x, int y) {
                inDrag = false;
                super.pointerPressed(x, y); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void pointerDragged(int x, int y) {
                inDrag = true;
                super.pointerDragged(x, y); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            protected void seriesReleased(SeriesSelection sel) {

                if (inDrag) {
                    // Don't do this if it was a drag operation
                    return;
                }

                for (SimpleSeriesRenderer r : renderer.getSeriesRenderers()) {
                    r.setHighlighted(false);
                }
                SimpleSeriesRenderer r = renderer.getSeriesRendererAt(sel.getPointIndex());
                r.setHighlighted(true);

                Shape seg = chart.getSegmentShape(sel.getPointIndex());
                Rectangle bounds = seg.getBounds();
                bounds = new Rectangle(
                        bounds.getX() - 40,
                        bounds.getY() - 40,
                        bounds.getWidth() + 80,
                        bounds.getHeight() + 80
                );

                this.zoomToShapeInChartCoords(bounds, 500);

            }

        };
        comp.setZoomEnabled(true);
        comp.setPanEnabled(true);
        comp.getStyle().setBgColor(0xff0000);
        comp.getStyle().setBgTransparency(255);

        return comp;

    }

    public PieChart(Form previous) {
        setTitle("Statistiques moyennes générales  ");
        //setLayout(BoxLayout.y());

        ArrayList<Bulletin> st = new ArrayList<>();
        Container cnt2 = new Container(BoxLayout.y());
        Container chart = new Container(BoxLayout.y());

        ComboBox classes = new ComboBox();

        Container cnt1 = new Container(BoxLayout.y());
        ArrayList<Classe> arr = new ArrayList<>();
        for (int i = 0; i < ServiceClasse.getInstance().getAllTasks().size(); i++) {
            arr.add(ServiceClasse.getInstance().getAllTasks().get(i));

        }
        for (Classe us : arr) {
            classes.addItem(us.getLibelle());
        }

        classes.addActionListener((e) -> {

            System.out.println(classes.getSelectedItem());
            cnt2.removeComponent(chart);
            chart.removeAll();
            //removeComponent(sp);
            for (Classe us : arr) {

                if (us.getLibelle().equals(classes.getSelectedItem())) {
                    double v1 = 0;
                    double v2 = 0;
                    double v3 = 0;
                    double v4 = 0;

                    for (int i = 0; i < ServiceBulletin.getInstance().getStatMG1(us.getLibelle()).size(); i++) {
                        st.add(ServiceBulletin.getInstance().getStatMG1(us.getLibelle()).get(i));
                    }

                    for (Bulletin b : st) {

                        v1 = (double) Math.round(b.getMoyenne() * 100) / 100;

                        System.out.println(v1);
                    }
                    st.clear();
                    for (int i = 0; i < ServiceBulletin.getInstance().getStatMG2(us.getLibelle()).size(); i++) {
                        st.add(ServiceBulletin.getInstance().getStatMG2(us.getLibelle()).get(i));
                    }

                    for (Bulletin b : st) {

                        v2 = (double) Math.round(b.getMoyenne() * 100) / 100;

                        System.out.println(v2);
                    }

                    st.clear();
                    for (int i = 0; i < ServiceBulletin.getInstance().getStatMG3(us.getLibelle()).size(); i++) {
                        st.add(ServiceBulletin.getInstance().getStatMG3(us.getLibelle()).get(i));
                    }

                    for (Bulletin b : st) {

                        v3 = (double) Math.round(b.getMoyenne() * 100) / 100;

                        System.out.println(v3);
                    }
                    st.clear();
                    for (int i = 0; i < ServiceBulletin.getInstance().getStatMG4(us.getLibelle()).size(); i++) {
                        st.add(ServiceBulletin.getInstance().getStatMG4(us.getLibelle()).get(i));
                    }

                    for (Bulletin b : st) {

                        v4 = (double) Math.round(b.getMoyenne() * 100) / 100;

                        System.out.println(v4);
                    }

                    sp = execute(v1, v2, v3, v4);
                    chart.add(sp);

                    cnt2.add(chart);
                }
            }

        });

        cnt1.add(classes);
        Button m = new Button("Premiers de classe");

        m.addActionListener((e) -> {

            new ListePremiers(current, classes.getSelectedItem().toString()).show();
        }
        );

        addAll(cnt1, m, cnt2);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new HomeScolarite().show());
    }
}
