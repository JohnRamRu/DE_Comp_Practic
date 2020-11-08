package sample.NumericalMethods;

import javafx.scene.chart.XYChart;

public class ImprovedEulerMethod extends NumericalMethod {

    private double next_y(double x, double y, double h) {
        return y + h*((f(x, y) + f(next_x(x, h), y + h*f(x, y)))/2);
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
        series.setName("Improved Euler's method");
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
        series.setName("Improved Euler's local error");
        return series;
    }

    public XYChart.Series<Number, Number> globalError(double x0, double y0, double X, double n0, double N) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        for(double ns = n0; ns<=N; ns++)
        {
            double h = (X - x0) / ns;
            double x = x0;
            double y = y0;
            double error = Math.abs(solution(x) - y);

            while (x <= X) {
                //
                y = next_y(x, y, h);
                x = next_x(x, h);

                double tmp = Math.abs(solution(x) - y);
                if(tmp > error)
                {
                    error = tmp;
                }
            }
            series.getData().add(new XYChart.Data<>(ns, error));
        }

        series.setName("Improved Euler's global error");
        return series;
    }
}
