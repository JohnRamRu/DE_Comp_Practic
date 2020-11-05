package sample.NumericalMethods;

import javafx.scene.chart.XYChart;

public interface NumericalMethodInterface {
    XYChart.Series<Number, Number> solve(double x0, double y0, double X, double h);

    XYChart.Series<Number, Number> localError(double x0, double X, double h);

    XYChart.Series<Number, Number> globalError(double x0, double y0, double X, double h);
}
