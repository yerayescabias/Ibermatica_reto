package ibermatica.multidioma;

import java.io.IOException;
import java.util.Properties;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Idioma extends Properties {
    public static boolean lan= true;
    public Idioma(String idioma){

        switch(idioma){
            case "Español":
                    getProperties("Spanish.properties");
                    setLan(true);
                    break;
            case "Ingles":
                    getProperties("English.properties");
                    setLan(false);
                    break;
            default:
                    getProperties("Spanish.properties");
        }

    }

    private void getProperties(String idioma) {
        try {
            this.load( getClass().getResourceAsStream(idioma) );
        } catch (IOException ex) {

        }
    }

    public void botones(Button[] botoene){
        for(Button boton : botoene){
            boton.setText(getProperty(boton.getId()));
        }
    }
    public void textfield(TextField[] textFields){
        for(TextField textField: textFields){
            textField.setPromptText(getProperty(textField.getId()));
        }
    }
    public void text(Text[] texts){
        for(Text text: texts){
            text.setText(getProperty(text.getId()));
            text.autosize();
        }
    }
    public void label(Label[] labels){
        for(Label label: labels){
            label.setText(getProperty(label.getId()));
        }
    }
    public void tab(Tab[] tabs){
        for(Tab tab: tabs){
            tab.setText(getProperty(tab.getId()));
        }
    }

    public void combo(ComboBox[] combos){
        for(ComboBox combo: combos){
            combo.setPromptText(getProperty(combo.getId()));
        }
    }
    public void menu_item(MenuItem[] menuItems){
        for(MenuItem menuItem : menuItems){
            menuItem.setText(getProperty(menuItem.getId()));
        }
    }
    
    public static String idioma_default(){
        if(lan==true){
        return("Español");
        }else if(lan==false){
            return("Ingles");
        }else{
            return "";
        }
    }

    public void setLan(boolean lan) {
        this.lan = lan;
    }

}
