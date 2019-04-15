package ch.makery.address.viewControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ch.makery.address.MainApp;
import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PersonOverviewController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * O construtor.
     * O construtor é chamado antes do método inicialize().
     */
    public PersonOverviewController() {
    }

    /**
     * Inicializa a classe controller. Este método é chamado automaticamente
     *  após o arquivo fxml ter sido carregado.
     */
@FXML
private void initialize() {
    // Inicializa a tabela de pessoas com duas colunas.
    firstNameColumn.setCellValueFactory(
            cellData -> cellData.getValue().firstNameProperty());
    lastNameColumn.setCellValueFactory(
            cellData -> cellData.getValue().lastNameProperty());

    // Limpa os detalhes da pessoa.
    showPersonDetails(null);

    // Detecta mudanças de seleção e mostra os detalhes da pessoa quando houver mudança.
    personTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showPersonDetails(newValue));
}

    /**
     * É chamado pela aplicação principal para dar uma referência de volta a si mesmo.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Adiciona os dados da observable list na tabela
        personTable.setItems(mainApp.getPersonData());
    }
    
    /**
    * PReenche todos os campos de texto para mostrar detalhes sobre a pessoa.
    * Se a pessoa especificada for null, todos os campos de texto são limpos.
    * 
    * @param person a pessoa ou null
    */
    private void showPersonDetails(Person person) {
        if (person != null) {
            // Preenche as labels com informações do objeto person.
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());

            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
            // birthdayLabel.setText(...);
        } else {
            // Person é null, remove todo o texto.
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }
       /**
    * Abre uma janela para editar detalhes para a pessoa especificada. Se o usuário clicar
    * OK, as mudanças são salvas no objeto pessoa fornecido e retorna true.
    * 
    * @param person O objeto pessoa a ser editado
    * @return true Se o usuário clicou OK,  caso contrário false.
    */
   public boolean showPersonEditDialog(Person person) {
       try {
           // Carrega o arquivo fxml e cria um novo stage para a janela popup.
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
           AnchorPane page = (AnchorPane) loader.load();

           // Cria o palco dialogStage.
           Stage dialogStage = new Stage();
           dialogStage.setTitle("Edit Person");
           dialogStage.initModality(Modality.WINDOW_MODAL);
           dialogStage.initOwner(mainApp.getPrimaryStage());
           Scene scene = new Scene(page);
           dialogStage.setScene(scene);

           // Define a pessoa no controller.
           PersonEditDialogController controller = loader.getController();
           controller.setDialogStage(dialogStage);
           controller.setPerson(person);

           // Mostra a janela e espera até o usuário fechar.
           dialogStage.showAndWait();

           return controller.isOkClicked();
       } catch (IOException e) {
           e.printStackTrace();
           return false;
       }
   }
    /**
    * Chamado quando o usuário clica no botão delete.
    */
   @FXML
   private void handleDeletePerson() {
       int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
       if (selectedIndex >= 0) {
           personTable.getItems().remove(selectedIndex);
       } else {
           // Nada selecionado.

       Alert alert = new Alert(AlertType.WARNING);
               alert.setTitle("Nenhuma seleção");
               alert.setHeaderText("Nenhuma Pessoa Selecionada");
               alert.setContentText("Por favor, selecione uma pessoa na tabela.");

               alert.showAndWait();
       }
   }
   /**
    * Chamado quando o usuário clica no botão novo. Abre uma janela para editar
    * detalhes da nova pessoa.
    */
   @FXML
   private void handleNewPerson() {
       Person tempPerson = new Person();
       boolean okClicked = showPersonEditDialog(tempPerson);
       if (okClicked) {
           mainApp.getPersonData().add(tempPerson);
       }
    }

   /**
    * Chamado quando o usuário clica no botão edit. Abre a janela para editar
    * detalhes da pessoa selecionada.
    */
   @FXML
   private void handleEditPerson() {
       Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
       if (selectedPerson != null) {
           boolean okClicked = showPersonEditDialog(selectedPerson);
           if (okClicked) {
               showPersonDetails(selectedPerson);
           }

       } else {
           // Nada seleciondo.
           Alert alert = new Alert(AlertType.WARNING);
               alert.setTitle("Nenhuma seleção");
               alert.setHeaderText("Nenhuma Pessoa Selecionada");
               alert.setContentText("Por favor, selecione uma pessoa na tabela.");
               alert.showAndWait();
       }
   }
}