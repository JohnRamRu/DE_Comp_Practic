package sample.NumericalMethods;

import javafx.scene.chart.XYChart;

public class NumericalMethod implements NumericalMethodInterface
{
    boolean invalidDouble(Double y) {
        return Double.isInfinite(y) || Double.isNaN(y);
    }

    static double f(double x, double y){
        return (2 * Math.pow(x, 3) + 2 * y/x);
    }

    static double solution(double x) {
        return (Math.pow(x, 2) + Math.pow(x, 4));
    }

    double next_x(double x, double h) {
        return x + h;
    };

    public XYChart.Series<Number, Number> solve(double x0, double y0, double X, double h) {
        return null;
    }

    public XYChart.Series<Number, Number> localError(double x0, double X, double h) {
        return  null;
    }

    public XYChart.Series<Number, Number> globalError(double x0, double y0, double X, double h) {
        return  null;
    }
}
