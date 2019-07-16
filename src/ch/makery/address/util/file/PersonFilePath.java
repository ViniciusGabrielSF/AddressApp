/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.makery.address.util.file;

import ch.makery.address.MainApp;
import java.io.File;
import java.util.prefs.Preferences;

/**
 *
 * @author Vinicius
 */
public class PersonFilePath {

    
    
   /**
    * Retorna o arquivo de preferências da pessoa, o último arquivo que foi aberto.
    * As preferências são lidas do registro específico do SO (Sistema Operacional). 
    * Se tais prefêrencias não puderem  ser encontradas, ele retorna null.
    * 
    * @return
    */
   public File getPersonFilePath() {
       Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
       String filePath = prefs.get("filePath", null);
       if (filePath != null) {
           return new File(filePath);
       } else {
           return null;
       }
   }

   /**
    * Define o caminho do arquivo carregado atual. O caminho é persistido no
    * registro específico do SO (Sistema Operacional).
    * 
    * @param file O arquivo ou null para remover o caminho
    */
   public void setPersonFilePath(File file) {
       Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
       if (file != null) {
           prefs.put("filePath", file.getPath());

           // Update the stage title.
           MainApp.getInstance().getPrimaryStage().setTitle("AddressApp - " + file.getName());
       } else {
           prefs.remove("filePath");

           // Update the stage title.
           MainApp.getInstance().getPrimaryStage().setTitle("AddressApp");
       }
   }
}
