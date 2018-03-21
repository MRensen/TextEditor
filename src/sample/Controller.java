package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class Controller {
    @FXML
    private TextArea textArea;

    @FXML
    private Label bottomLabel;

    private TextFile currentTextFile;
    private EditorModel model;

    public Controller(EditorModel model){
        this.model = model;
    }

    @FXML
    public void onLoad(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./"));
        File file = fileChooser.showOpenDialog(null);
        if(file != null){
            IOResult<TextFile> io = model.load(file.toPath());

            if (io.isOk() && io.hasData()){
                currentTextFile = io.getData();

                textArea.clear();
                currentTextFile.getContent().forEach(line->textArea.appendText(line +"\n"));
            } else {
                System.out.println("Failed");
            }
        }

    }

    @FXML
    public void onClose(){
        model.close();
    }

    @FXML
    public void onSave(){
        TextFile textFile;
        if(currentTextFile != null) {
            textFile = new TextFile(currentTextFile.getPath(), Arrays.asList(textArea.getText().split("\n")));
            model.save(textFile);

            bottomLabel.setText("Save successful " + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
        } else {
            onSaveAs();
        }


    }

    @FXML void onSaveAs(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./"));
        File file = fileChooser.showSaveDialog(null);

        TextFile textFile = new TextFile(file.toPath(), Arrays.asList(textArea.getText().split("\n")));
        model.save(textFile);

        bottomLabel.setText("Save successful " + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
    }

    @FXML
    public void onAbout(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("About");
        alert.setContentText("This is a simple text editor with features");
        alert.show();

    }
    @FXML public void onDelete(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to delete the current project?");
        alert.setTitle("Are you sure?");
        alert.setContentText("All unsaved progress will be lost.");
        Optional<ButtonType> confirm = alert.showAndWait();

        if(confirm.get() == ButtonType.OK){
            textArea.clear();
            currentTextFile = null;
        } else {
            alert.close();
        }

    }


}
