package com.orthoarkansas.reconciler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Controller for the Home page
 */
public class HomeController {
    @FXML private TextField file1Path;
    @FXML private TextField file2Path;
    @FXML private Button file1Button;
    @FXML private Button file2Button;

    private File lastDirectory;

    /**
     * FXML method for handling the press of the reconcile button
     * @param event The ActionEvent passed by JavaFX
     */
    @FXML protected void handleReconcileButtonAction(ActionEvent event) {
        Reconciler reconciler = new Reconciler();
        reconciler.reconcile(file1Path.getText(), file2Path.getText());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("results-view.fxml"));
            Scene scene = new Scene(loader.load(), 500, 650);

            ResultsController controller = loader.getController();
            controller.populateCells(reconciler.getInFile1NotFile2(), reconciler.getInFile2NotFile1(), file1Path.getText().substring(file1Path.getText().lastIndexOf('\\') + 1), file2Path.getText().substring(file2Path.getText().lastIndexOf('\\') + 1));

            Stage stage = new Stage();
            stage.setTitle("Results");
            stage.setMinHeight(scene.getHeight() + 25);
            stage.setMinWidth(scene.getWidth());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * FXML method for handling the press of the file selector button
     * @param event ActionEvent passed in by JavaFX
     */
    @FXML protected void handleFileButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a file");
        if (lastDirectory != null) {
            fileChooser.setInitialDirectory(lastDirectory);
        }
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("CSV Files", "*.csv"),
                new ExtensionFilter("All Files", "*.*")
        );
        Button source = (Button)event.getSource();
        File file = fileChooser.showOpenDialog(source.getScene().getWindow());
        if (file != null) {
            lastDirectory = file.getParentFile();
            if (source == file1Button) {
                file1Path.setText(file.getPath());
            } else if (source == file2Button) {
                file2Path.setText(file.getPath());
            }
        }
    }
}