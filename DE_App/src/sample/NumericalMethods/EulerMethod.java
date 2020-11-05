package sample.NumericalMethods;

import javafx.scene.chart.XYChart;

public class EulerMethod extends NumericalMethod {
    private double next_y(double x, double y, double h) {
        return y + h*f(x, y);
    }

    public XYChart.Series<Number, Number> solve(double x0, double y0, double X, double h) {
        double x = x0;
        double y = y0;

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        while (x <= X) {
            series.getData().add(new XYChart.Data<>(x, y));

            y = next_y(x, y, h);
            x = next_x(x, h);
            if (invalidDouble(y)) {
                return null;
            }
        }
        series.setName("Euler's method");
        return series;
    }

    public XYChart.Series<Number, Number> localError(double x0, double X, double h) {
        double x = x0;
        double error = 0;

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        while (x <= X) {
            series.getData().add(new XYChart.Data<>(x, error));

            error = Math.abs(solution(next_x(x, h)) - (next_y(x, solution(x), h))); //(solution(x) + h*f(x, y));
            x = next_x(x, h);
        }
        series.setName("Euler's local error");
        return series;
    }

    public XYChart.Series<Number, Number> globalError(double x0, double y0, double X, double h) {
        double x = x0;
        double y = y0;
        double error = Math.abs(solution(x) - y);


        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        while (x <= X) {
            series.getData().add(new XYChart.Data<>(x, error));

            y = next_y(x, y, h);
            x = next_x(x, h);
            error = Math.abs(solution(x) - y);
        }
        series.setName("Euler's global error");
        return series;
    }
}
