package interview.butanski.viktor.solutions.sirma.employeespairfx.app;

import interview.butanski.viktor.solutions.sirma.employeespairfx.entity.Pair;
import interview.butanski.viktor.solutions.sirma.employeespairfx.service.PairEmployees;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EmployeesPairApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        List<Pair> pairList = new ArrayList<>();
        FileChooser fileChooser = new FileChooser();

        TableView<Pair> tableView = new TableView<>();

        Button addFileButton = new Button("Add file");
        addFileButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(stage);

            PairEmployees pairs = new PairEmployees(selectedFile);
            pairList.add(pairs.findPair());

            // Get the result and add it to the table
            tableView.getItems().addAll(pairList);
        });

        // Set preferred width and height on the table view, create columns
        tableView.prefHeightProperty().bind(stage.heightProperty());
        tableView.prefWidthProperty().bind(stage.widthProperty());

        TableColumn<Pair, String> column1 = new TableColumn<>("Employee #1 ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("firstEmployeeId"));
        column1.prefWidthProperty().bind(tableView.widthProperty().multiply(0.25));

        TableColumn<Pair, String> column2 = new TableColumn<>("Employee #2 ID");
        column2.setCellValueFactory(new PropertyValueFactory<>("secondEmployeeId"));
        column2.prefWidthProperty().bind(tableView.widthProperty().multiply(0.25));

        TableColumn<Pair, String> column3 = new TableColumn<>("Project ID");
        column3.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        column3.prefWidthProperty().bind(tableView.widthProperty().multiply(0.25));

        TableColumn<Pair, String> column4 = new TableColumn<>("Days Worked Together");
        column4.setCellValueFactory(new PropertyValueFactory<>("daysWorkedTogether"));
        column4.prefWidthProperty().bind(tableView.widthProperty().multiply(0.25));

        // Add the columns to the table view.
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);

        // Create the grid pane layout and add the elements to it.
        GridPane gridPane = new GridPane();
        gridPane.add(addFileButton, 0, 0);
        gridPane.add(tableView, 0, 1);

        Scene scene = new Scene(gridPane, 800, 400);
        stage.setTitle("Employee Pair");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
