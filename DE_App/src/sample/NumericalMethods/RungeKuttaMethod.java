package sample.NumericalMethods;

import javafx.scene.chart.XYChart;

public class RungeKuttaMethod extends NumericalMethod{
    private double next_y(double x, double y, double h) {
        return y + (k1(x, y, h) + 2*k2(x, y, h) + 2*k3(x,y, h) + k4(x, y, h))/6;
    }

    private double k1(double x, double y, double h) {
        return h*f(x, y);
    }

    private double k2(double x, double y, double h) {
        return h*f(x + h/2, y + k1(x, y, h)/2);
    }

    private double k3(double x, double y, double h) {
        return h*f(x + h/2, y + k2(x, y, h)/2);
    }

    private double k4(double x, double y, double h) {
        return h*f(x + h, y + k3(x, y, h));
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
        series.setName("Runge–Kutta method");
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
        series.setName("Runge–Kutta's local error");
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
        series.setName("Runge–Kutta's global error");
        return series;
    }
}
