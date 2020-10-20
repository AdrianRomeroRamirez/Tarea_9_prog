package javafxbebidas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación donde crea un escenario y lo muestra
 * @author Adrián Romero Ramírez
 */
public class JavaFXBebidas extends Application {
    
    private static ArrayList <Bebida> listaBebidas = new ArrayList(); // ArrayList de Bebidas
    private final static String RUTA = "bebidas.dat"; // Ruta del archivo
    private static Label nombre = new Label("Nombre:"); // Etiqueta "Nombre"
    private static TextField tFNombre = new TextField(); // Caja de texto para el nombr
    private static Label color = new Label("Color:"); // Etiqueta "Color"
    private static TextField tFColor = new TextField(); // Caja de texto para el color
    private static Label unidades = new Label("Unidades:"); // Etiqueta "Unidades"
    private static TextField tFUnidades = new TextField(); // Caja de texto para las unidades
    private static ListView listView= new ListView(); // ListView donde se mostrarán las Bebidas  
    private static Button añadir = new Button("Añadir a la lista"); // Boton "Añadir"
    private static Button borrar = new Button("Borrar de la lista"); // Boton "Borrar"
    private static Button guardar = new Button("Guardar a disco"); // Boton "Guardar"

    @Override
    public void start(Stage primaryStage) {
        
        tFUnidades.setPrefColumnCount(4); // Longitud predeterminada para la caja de texto de unidades
        listView.setPrefHeight(120); // Largo predefinido del ListView
        
        // Aquí especifico que pasa cuando se pulsa el boton añadir
        
        añadir.setOnAction((ActionEvent event) -> {
            añadir();
        });
        
        // Aquí especifico que pasa cuando se pulsa el boton borrar
        
        borrar.setOnAction((ActionEvent event) ->{
            borrar();
        });
        
        // Aquí especifico que pasa cuando se pulsa el boton guardar
        
        guardar.setOnAction((ActionEvent event) ->{
            guardar();
        });
        
        // Creo la raiz
        
        FlowPane root = new FlowPane(Orientation.HORIZONTAL);
        root.setPadding(new Insets(10));
        root.setVgap(8);
        root.setHgap(4);
        
        // Añado los elementos a la raiz
        
        root.getChildren().addAll(nombre, tFNombre, color, tFColor, unidades, 
                tFUnidades, listView, añadir, borrar, guardar);
        
        // Creo la escena con la raiz dentro y le doy estilo con un css
        
        Scene scene = new Scene(root, 600, 300);
        scene.getStylesheets().add("recursos/css/estilo.css");
        
        primaryStage.setTitle("Bebidas"); // Titulo de el escenario
        primaryStage.setScene(scene); // Meto la escena en el escenario
        primaryStage.show(); // Muestro el escenario

        // Llamo al método cargarLista y si devuelve true copia el ArrayList en el ListView
        
        if(JavaFXBebidas.cargarLista()){
            for (Bebida b : listaBebidas){
                listView.getItems().add(b);
            }
        }

        
    }
    
    /**
     * Metodo que crea una bebida con los datos introducidos en los TextField y lo muestra en el ListView
     */
    public static void añadir(){
        try{
            // Si algún campo está vacio te lo informa con un mensaje 
            if(tFNombre.getText().isEmpty() || tFColor.getText().isEmpty() || tFUnidades.getText().isEmpty()){
                Alert alerta = new Alert(AlertType.INFORMATION);
                alerta.setTitle("Error.");
                alerta.setHeaderText(null);
                alerta.setContentText("Todos los datos deben estar rellenos.");
                alerta.showAndWait();
            }else{
                String sTNombre = tFNombre.getText();
                String sTColor = tFColor.getText();
                int iUnidades = Integer.parseInt(tFUnidades.getText());
                Bebida bebida = new Bebida(sTNombre, sTColor, iUnidades);
                listaBebidas.add(bebida);
                listView.getItems().add(bebida);
            }
        // Si al intentar pasar el campo unidades a int da error, te lo informa con un mensaje
        }catch (NumberFormatException e){
            Alert alerta = new Alert(AlertType.INFORMATION);
            alerta.setTitle("Error.");
            alerta.setHeaderText(null);
            alerta.setContentText("Se espera un número entero para las unidades.");
            alerta.showAndWait();
        }
    }
    
    /**
     * Método que borra del ListView y del ArrayList el objeto seleccionado en el ListView
     */
    public static void borrar(){
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        listaBebidas.remove(selectedIndex);
        listView.getItems().remove(selectedIndex);
    }
    
    /**
     * Método que guarda el ArrayList en un fichero .dat
     */
    public static void guardar(){
        try{
            FileOutputStream fichero = new FileOutputStream(new File(RUTA));
            try (ObjectOutputStream ficheroSalida = new ObjectOutputStream(fichero)) {
                ficheroSalida.writeObject(listaBebidas);
            }
            Alert alerta = new Alert(AlertType.INFORMATION);
            alerta.setTitle("Información.");
            alerta.setHeaderText(null);
            alerta.setContentText("Fichero guardado con exito.");
            alerta.showAndWait();
        }catch(FileNotFoundException e){
            Alert alerta = new Alert(AlertType.INFORMATION);
            alerta.setTitle("Información.");
            alerta.setHeaderText(null);
            alerta.setContentText("Fichero no encontrado.");
            alerta.showAndWait();
        }catch(IOException ex){
            Alert alerta = new Alert(AlertType.INFORMATION);
            alerta.setTitle("Información.");
            alerta.setHeaderText(null);
            alerta.setContentText("Error al guardar el fichero.");
            alerta.showAndWait();
        }
    }
    
    /**
     * Método que intenta leer el fichero Bebidas.dat, lo mete en el ArrayList y lo muestra
     * a traves del ListView.
     * Si el fichero no existe o está vacio, te avisa con un mensaje.
     * @return True o false dependiendo si pudo cargar el fichero o no
     */
    public static boolean cargarLista(){
        boolean cargado = false;
        
        try{
            FileInputStream fichero = new FileInputStream(new File (RUTA));
            try (ObjectInputStream ficheroEntrada = new ObjectInputStream(fichero)) {
                listaBebidas = (ArrayList<Bebida>) ficheroEntrada.readObject();
                cargado = true;
                if (listaBebidas.isEmpty()){
                    Alert alerta = new Alert(AlertType.INFORMATION);
                    alerta.setTitle("Aviso.");
                    alerta.setHeaderText(null);
                    alerta.setContentText("No se cargaron datos previos en la aplicación."
                    + "\n Fichero no encontrado o vacío.");
                    alerta.showAndWait();
                }
            }
        } catch (ClassNotFoundException cnfe) {
            Alert alerta = new Alert(AlertType.INFORMATION);
            alerta.setTitle("Aviso.");
            alerta.setHeaderText(null);
            alerta.setContentText("No se ha podido revertir la clase.");
            alerta.showAndWait();
        } catch (FileNotFoundException fnfe) {
            Alert alerta = new Alert(AlertType.INFORMATION);
            alerta.setTitle("Aviso.");
            alerta.setHeaderText(null);
            alerta.setContentText("No se cargaron datos previos en la aplicación."
            + "\n Fichero no encontrado o vacío.");
            alerta.showAndWait();
        } catch (IOException ioe) {
            Alert alerta = new Alert(AlertType.INFORMATION);
            alerta.setTitle("Aviso.");
            alerta.setHeaderText(null);
            alerta.setContentText("No se cargaron datos previos en la aplicación."
            + "\n Fichero no encontrado o vacío.");
            alerta.showAndWait();
        }
        return cargado;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
