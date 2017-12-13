package com.bbrighttaer.rl.util;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import javax.swing.*;
import java.awt.*;

public class PlotUtil {
    public static XYDataset createDataSet(INDArray features, INDArray labels) {
        int nRows = features.rows();

        int nClasses = labels.columns();

        XYSeries[] series = new XYSeries[nClasses];
        for (int i = 0; i < nClasses; i++) {
            series[i] = new XYSeries(String.valueOf(i));
        }
        INDArray classIdx = Nd4j.argMax(labels, 1);
        for (int i = 0; i < nRows; i++) {
            int idx = classIdx.getInt(i);
            series[idx].add(features.getDouble(i, 0), features.getDouble(i, 1));
        }

        XYSeriesCollection c = new XYSeriesCollection();
        for (XYSeries s : series) c.addSeries(s);
        return c;
    }

    private static DefaultCategoryDataset createCategoryDataSet(INDArray yRowVector, String series) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < yRowVector.columns(); i++) {
            dataset.addValue((Number) yRowVector.getDouble(0, i), series, i+1);
        }
        return dataset;
    }

    public static JFreeChart createChart(INDArray yRowVector, double axisMin,
                                         double axisMax, String series, String title) {

        // XYDataset dataset = createDataSet(features, labels);

        JFreeChart chart = ChartFactory.createLineChart(title,
                "X", "Y",
                createCategoryDataSet(yRowVector, series)
                , PlotOrientation.VERTICAL, true, true, false);

        /*CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.getRenderer().setBaseOutlineStroke(new BasicStroke(0));
        plot.setNoDataMessage("NO DATA");

        //plot.setDomainPannable(false);
        plot.setRangePannable(false);
        //plot.setDomainZeroBaselineVisible(true);
        plot.setRangeZeroBaselineVisible(true);

        plot.setDomainGridlineStroke(new BasicStroke(0.0f));
        plot.setDomainGridlinePaint(Color.blue);
        plot.setRangeGridlineStroke(new BasicStroke(0.0f));
        plot.setRangeMinorGridlineStroke(new BasicStroke(0.0f));
        plot.setRangeGridlinePaint(Color.blue);

        plot.setRangeMinorGridlinesVisible(true);

        LineAndShapeRenderer renderer
                = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesOutlinePaint(0, Color.black);
        renderer.setUseOutlinePaint(true);
        CategoryAxis domainAxis = plot.getDomainAxis();

        domainAxis.setTickMarkInsideLength(2.0f);
        domainAxis.setTickMarkOutsideLength(2.0f);

        domainAxis.setMinorTickMarksVisible(true);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setTickMarkInsideLength(2.0f);
        rangeAxis.setTickMarkOutsideLength(2.0f);
        rangeAxis.setMinorTickCount(2);
        rangeAxis.setMinorTickMarksVisible(true);
        rangeAxis.setRange(axisMin, axisMax);*/


        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        JFrame jFrame = new JFrame("Chart");
        jFrame.setContentPane(chartPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.pack();
        jFrame.setVisible(true);

        return chart;
    }
}
