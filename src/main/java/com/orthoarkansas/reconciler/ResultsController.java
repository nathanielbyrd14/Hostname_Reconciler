package com.orthoarkansas.reconciler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Controller for the Results page
 */
public class ResultsController {
    @FXML private TableView<Row> table;
    @FXML private TableColumn<Row, String> c1;
    @FXML private TableColumn<Row, String> c2;

    private HashSet<String> inFile1NotFile2;
    private HashSet<String> inFile2NotFile1;

    private String filename1;
    private String filename2;

    private File lastDirectory;

    /**
     * Populates the table cells with the entries from the HashSets
     * @param inFile1NotFile2 HashSet of Strings containing all entries in file1 but not in file2
     * @param inFile2NotFile1 HashSet of Strings containing all entries in file2 but not in file1
     * @param filename1 Text to display for the name of file1 (recommended to use the name of the file, not the path)
     * @param filename2 Text to display for the name of file2 (recommended to use the name of the file, not the path)
     */
    public void populateCells(HashSet<String> inFile1NotFile2, HashSet<String> inFile2NotFile1, String filename1, String filename2) {
        this.inFile1NotFile2 = inFile1NotFile2;
        this.inFile2NotFile1 = inFile2NotFile1;
        this.filename1 = filename1;
        this.filename2 = filename2;

        c1.setText(String.format("Entries in %s but not in %s", filename1, filename2));
        c2.setText(String.format("Entries in %s but not in %s", filename2, filename1));

        ArrayList<Row> rows = new ArrayList<>();

        Iterator<String> i1 = inFile1NotFile2.iterator();
        Iterator<String> i2 = inFile2NotFile1.iterator();

        while (i1.hasNext() && i2.hasNext()) {
            String s1 = i1.next();
            String s2 = i2.next();

            Row r = new Row(s1, s2);
            rows.add(r);
        }

        while (i1.hasNext()) {
            String s1 = i1.next();

            Row r = new Row(s1, "");
            rows.add(r);
        }

        while (i2.hasNext()) {
            String s2 = i2.next();

            Row r = new Row("", s2);
            rows.add(r);
        }

        table.getItems().addAll(rows);

        c1.setCellValueFactory(new PropertyValueFactory<>("s1"));
        c2.setCellValueFactory(new PropertyValueFactory<>("s2"));
    }

    /**
     * FXML method to handle the press of the save button
     * @param event ActionEvent passed in by JavaFX
     */
    @FXML protected void handleSaveButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a file");
        if (lastDirectory != null) {
            fileChooser.setInitialDirectory(lastDirectory);
        }
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        File file = fileChooser.showSaveDialog(table.getScene().getWindow());
        if (file != null) {
            lastDirectory = file.getParentFile();
            try (PrintStream out = new PrintStream(file)) {
                out.printf("Items in %s not in %s:, Items in %s not in %s:\n", filename1, filename2, filename2, filename1);
                Iterator<String> i1 = inFile1NotFile2.iterator();
                Iterator<String> i2 = inFile2NotFile1.iterator();
                String s1;
                String s2;
                while (i1.hasNext() && i2.hasNext()) {
                    s1 = i1.next();
                    s2 = i2.next();
                    out.printf("%s,%s\n", s1, s2);
                }
                while (i1.hasNext()) {
                    s1 = i1.next();
                    out.printf("%s,\n", s1);
                }
                while (i2.hasNext()) {
                    s2 = i2.next();
                    out.printf(",%s\n", s2);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
