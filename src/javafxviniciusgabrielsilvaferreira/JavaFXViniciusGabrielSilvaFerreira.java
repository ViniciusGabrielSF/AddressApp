/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxviniciusgabrielsilvaferreira;

import ch.makery.address.MainApp;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Aluno
 */
public class JavaFXViniciusGabrielSilvaFerreira extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        MainApp aplicacao = new MainApp();
        aplicacao.start(primaryStage);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
