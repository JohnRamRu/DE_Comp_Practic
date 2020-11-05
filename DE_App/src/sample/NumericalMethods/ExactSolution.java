package sample.NumericalMethods;

import javafx.scene.chart.XYChart;

public class ExactSolution extends NumericalMethod {
    public XYChart.Series<Number, Number> solve(double x0, double y0, double X, double h) {
        double x = x0;
        double y = solution(x);

        System.out.println(y);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        while (x <= X) {
            series.getData().add(new XYChart.Data<>(x, y));

            x = next_x(x, h);
            y = solution(x);

            System.out.println(y);

            if (invalidDouble(y)) {
                return null;
            }
        }
        series.setName("Exact solution");
        return series;
    }
}
