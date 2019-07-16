/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.makery.address;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Jessica
 */
public class Aplicação extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainApp agenda = MainApp.getInstance();
        agenda.primaryStage = primaryStage;
        agenda.primaryStage.setTitle("AddressApp");
        agenda.primaryStage.getIcons().add(new Image("file:resources/images/agenda.png"));
        agenda.initRootLayout();
        agenda.showPersonOverview();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
