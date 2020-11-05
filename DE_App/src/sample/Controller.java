package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import sample.NumericalMethods.*;


public class Controller {
    @FXML LineChart<Number, Number> graph;
    @FXML LineChart<Number, Number> errorGraph;

    @FXML TextField init_y;
    @FXML TextField init_x;
    @FXML TextField init_X;
    @FXML TextField init_N;

    @FXML CheckBox eulerBox;
    @FXML CheckBox impEulerBox;
    @FXML CheckBox RKBox;
    @FXML CheckBox solutionBox;
    @FXML CheckBox localErrorBox;
    @FXML CheckBox globalErrorBox;

    @FXML Text methodError;
    @FXML Text valError;

    private XYChart.Series<Number, Number> eulerSeries = new XYChart.Series<>();
    private XYChart.Series<Number, Number> impEulerSeries = new XYChart.Series<>();
    private XYChart.Series<Number, Number> RKSeries = new XYChart.Series<>();
    private XYChart.Series<Number, Number> exactSolSeries = new XYChart.Series<>();

    private EulerMethod eulerMethod = new EulerMethod();
    private ImprovedEulerMethod improvedEulerMethod = new ImprovedEulerMethod();
    private RungeKuttaMethod rungeKuttaMethod = new RungeKuttaMethod();
    private ExactSolution exactSolution = new ExactSolution();

    private double x0, y0, X, h;

    public static double f(double x, double y){
        return (2 - Math.pow(y, 2))/(2 * y * Math.pow(x, 2));
    }

    private boolean checkVar(String y0, String x0, String X, String h) {
        String doubleRegex = "-?[0-9]+\\.?[0-9]*";
        String intRegex = "-?[0-9]*";
        if (!y0.matches(doubleRegex) || y0.equals("")) {
            return false;
        }
        if (!x0.matches(doubleRegex) || x0.equals("")) {
            return false;
        }
        if (!X.matches(doubleRegex) || X.equals("")) {
            return false;
        }
        return h.matches(intRegex) && !h.equals("");
    }

    private boolean invalidDouble(Double y) {
        return Double.isInfinite(y) || Double.isNaN(y);
    }

    private boolean parseInput() {
        valError.setVisible(false);

        if (!checkVar(init_y.getText(), init_x.getText(), init_X.getText(), init_N.getText())) {
            valError.setText("Give appropriate input values");
            valError.setVisible(true);
            return false;
        }
        if (invalidDouble(f(Double.parseDouble(init_x.getText()), Double.parseDouble(init_y.getText())))) {
            valError.setText("Function f(x, y) is undefined at this initial values");
            valError.setVisible(true);
            return false;
        }
        if (Double.parseDouble(init_N.getText()) <= 0) {
            valError.setText("N must be greater than zero");
            valError.setVisible(true);
            return false;
        }

        y0 = Double.parseDouble(init_y.getText());
        x0 = Double.parseDouble(init_x.getText());
        X = Double.parseDouble(init_X.getText());


        int N;
        N = Integer.parseInt(init_N.getText());
        h = (X - x0) / N;

        return true;
    }

    public void increaseStep(ActionEvent event) {

    }

    public void decreaseStep(ActionEvent event) {

    }

    public void plot(ActionEvent event) {
        graph.getData().clear();
        errorGraph.getData().clear();

        boolean error = !parseInput();
        methodError.setVisible(false);

        boolean euler = eulerBox.isSelected();
        boolean impEuler = impEulerBox.isSelected();
        boolean RK = RKBox.isSelected();
        boolean solution = solutionBox.isSelected();

        boolean localError = localErrorBox.isSelected();
        boolean globalError = globalErrorBox.isSelected();

        if (!(euler || impEuler || RK)) {
            methodError.setVisible(true);
            error = true;
        }

        if (error) {
            return;
        }

        if (euler) {
            eulerSeries = eulerMethod.solve(x0, y0, X, h);
            if (eulerSeries != null) {
                graph.getData().add(eulerSeries);
                if (localError) {
                    errorGraph.getData().add(eulerMethod.localError(x0, X, h));
                }

                if (globalError) {
                    errorGraph.getData().add(eulerMethod.globalError(x0, y0, X, h));
                }
            } else {
                valError.setText("Function y(x) is undefined at this initial values");
                valError.setVisible(true);
                graph.getData().clear();
                return;
            }
        }

        if (impEuler) {
            impEulerSeries = improvedEulerMethod.solve(x0, y0, X, h);
            if (impEulerSeries != null) {
                graph.getData().add(impEulerSeries);

                if (localError) {
                    errorGraph.getData().add(improvedEulerMethod.localError(x0, X, h));
                }

                if (globalError) {
                    errorGraph.getData().add(improvedEulerMethod.globalError(x0, y0, X, h));
                }
            } else {
                valError.setText("Function y(x) is undefined at this initial values");
                valError.setVisible(true);
                graph.getData().clear();
                return;
            }
        }
        if (RK) {
            RKSeries = rungeKuttaMethod.solve(x0, y0, X, h);
            if (RKSeries != null) {
                graph.getData().add(RKSeries);

                if (localError) {
                    errorGraph.getData().add(rungeKuttaMethod.localError(x0, X, h));
                }

                if (globalError) {
                    errorGraph.getData().add(rungeKuttaMethod.globalError(x0, y0, X, h));
                }
            } else {
                valError.setText("Function y(x) is undefined at this initial values");
                valError.setVisible(true);
                graph.getData().clear();
                return;
            }
        }
        if (solution) {
            exactSolSeries = exactSolution.solve(x0, y0, X, h);
            if (exactSolSeries != null) {
                graph.getData().add(exactSolSeries);
            } else {
                valError.setText("Function y(x) is undefined at this initial values");
                valError.setVisible(true);
                graph.getData().clear();
                return;
            }
        }
    }
}
