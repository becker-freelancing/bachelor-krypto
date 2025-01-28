package com.becker.freelance.backtest.resultviewer.app;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class BacktestResultPlotter implements Runnable {

    private final Set<BacktestResultContent> bestCumulative;
    private final Set<BacktestResultContent> bestMax;
    private final Set<BacktestResultContent> bestMin;

    public BacktestResultPlotter(Set<BacktestResultContent> bestCumulative, Set<BacktestResultContent> bestMax, Set<BacktestResultContent> bestMin) {
        this.bestCumulative = bestCumulative;
        this.bestMax = bestMax;
        this.bestMin = bestMin;
    }

    @Override
    public void run() {
        XYChart cumulativeChart = plotResults(bestCumulative, "Bestes Kumulatives Ergebnis");
        XYChart maxChart = plotResults(bestMax, "Bestes Maximales Ergebnis");
        XYChart minChart = plotResults(bestMin, "Bestes Minimales Ergebnis");

        new SwingWrapper<>(List.of(cumulativeChart, maxChart, minChart)).displayChartMatrix();
    }

    private XYChart plotResults(Set<BacktestResultContent> backtestResultContents, String title) {
        List<String> legends = new ArrayList<>();
        List<List<Double>> data = new ArrayList<>();

        backtestResultContents.forEach(resultContent -> {
            List<Double> tradeProfits = resultContent.tradeProfits();
            List<Double> series = new ArrayList<>();
            double sum = 0;
            for (Double tradeProfit : tradeProfits) {
                sum += tradeProfit;
                series.add(sum);
            }
            data.add(series);
            legends.add(resultContent.parametersJson());
        });

        XYChart chart = new XYChartBuilder().title(title).xAxisTitle("Trade Index").yAxisTitle("Cumulative Profit").build();
        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);

        for (int i = 0; i < data.size(); i++) {
            if (!data.get(i).isEmpty()) {
                chart.addSeries(legends.get(i), data.get(i));
            }
        }

        return chart;
    }
}
