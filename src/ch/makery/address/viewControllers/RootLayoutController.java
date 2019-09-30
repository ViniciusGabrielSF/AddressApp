package ch.makery.address.viewControllers;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import ch.makery.address.MainApp;
import ch.makery.address.jdbc.AcessDB;
import ch.makery.address.model.Person;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 * 
 * @author Marco Jakob
 */
public class RootLayoutController {

    /**
     * Abre uma janela para mostrar as estatísticas de aniversário.
     */
    public void showBirthdayStatistics() {
        try {
            // Carrega o arquivo fxml e cria um novo palco para o popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/BirthdayStatistics.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Birthday Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(MainApp.getInstance().getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Define a pessoa dentro do controller.
            BirthdayStatisticsController controller = loader.getController();
            controller.setPersonDataToBirthdayStatistics(MainApp.getInstance().getPersonData());

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Creates an empty address book.
     */
    @FXML
    private void handleNew() {
        MainApp.getInstance().getPersonData().clear();
        MainApp.getInstance().getPersonDataFile().getPersonFilePath().setPersonFilePath(null);
    }

    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(MainApp.getInstance().getPrimaryStage());

        if (file != null) {
            MainApp.getInstance().getPersonDataFile().loadPersonDataFromFile(file);
        }
    }

    /**
     * Saves the file to the person file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSave() {
            try{
                AcessDB acess = new AcessDB();
                List<Person> persons = new ArrayList<Person>();
                persons.addAll( MainApp.getInstance().getPersonsAsArrayList() );
                acess.savePersonDataToDB(persons);
            } catch( Exception e){
            File personFile =  MainApp.getInstance().getPersonDataFile().getPersonFilePath().getPersonFilePath();
            if (personFile != null) {
                MainApp.getInstance().getPersonDataFile().savePersonDataToFile(personFile);
            } else {
                handleSaveAs();
            } 
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {
        try{
            AcessDB acess = new AcessDB();
            List<Person> persons = new ArrayList<Person>();
            persons.addAll(  MainApp.getInstance().getPersonsAsArrayList());
            acess.savePersonDataToDB(persons);
        } catch( Exception e){
            FileChooser fileChooser = new FileChooser();

            // Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                    "XML files (*.xml)", "*.xml");
            fileChooser.getExtensionFilters().add(extFilter);

            // Show save file dialog
            File file = fileChooser.showSaveDialog(MainApp.getInstance().getPrimaryStage());

            if (file != null) {
                // Make sure it has the correct extension
                if (!file.getPath().endsWith(".xml")) {
                    file = new File(file.getPath() + ".xml");
                }
                MainApp.getInstance().getPersonDataFile().savePersonDataToFile(file);

            }   
        }
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("AddressApp");
        alert.setHeaderText("About");
        alert.setContentText("Author: Marco Jakob\nWebsite: http://code.makery.ch");

        alert.showAndWait();
    }
    /**
    * Abre as estatísticas de aniversário.
    */
   @FXML
   private void handleShowBirthdayStatistics() {
     showBirthdayStatistics();
   }
    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
    
}