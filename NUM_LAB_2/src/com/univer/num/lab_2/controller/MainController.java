package com.univer.num.lab_2.controller;

import com.univer.num.lab_2.model.Formula;
import com.univer.num.lab_2.model.FunctionManager;
import com.univer.num.lab_2.model.Point;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.Scanner;

public class MainController {
    private Stage primaryStage;
    @FXML
    private TabPane mainPane;
    @FXML
    private AnchorPane analiticTool;
    @FXML
    private TableView<Formula> analiticTable;
    @FXML
    private TableColumn<Formula, Double> beginColumn;
    @FXML
    private TableColumn<Formula, Double> endColumn;
    @FXML
    private TableColumn<Formula, String> expressionColumn;
    @FXML
    private LineChart<Double, Double> chart;
    @FXML
    private TextArea xmlEditor;
    @FXML
    private AnchorPane nodeTool;
    @FXML
    private TableView<Point> nodeTable;
    @FXML
    private TableColumn<Point, Double> xColumn;
    @FXML
    private TableColumn<Point, Double> yColumn;
    private Alert infoAlert;
    private FunctionManager functionManager;
    private FileChooser chooser;
    @FXML
    private HBox statusBar;
    @FXML
    private Label statusBarLabel;
    @FXML
    private TextField beginFormula;
    @FXML
    private TextField endFormula;
    @FXML
    private TextField expressionFormula;
    @FXML
    private TextField xPoint;
    @FXML
    private TextField yPoint;
    private boolean contentChanged;
    private XYChart.Series<Double, Double> seriesInterpolated;
    private XYChart.Series<Double, Double> seriesReal;
    @FXML
    private ChoiceBox<String> interpolMethod;

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public FunctionManager getFunctionManager() {
        return functionManager;
    }

    public void setFunctionManager(FunctionManager functionManager) {
        this.functionManager = functionManager;
    }

    public void init(){
        functionManager = new FunctionManager();
        analiticTable.setItems(functionManager.getFormulas());
        nodeTable.setItems(functionManager.getValues());
        contentChanged = false;

        statusBar.prefWidthProperty().bind(primaryStage.widthProperty());
        statusBar.prefHeightProperty().setValue(50);
        mainPane.prefWidthProperty().bind(primaryStage.widthProperty());
        mainPane.prefHeightProperty().bind(primaryStage.heightProperty().add(-statusBar.prefHeightProperty().getValue()));
        analiticTool.prefWidthProperty().setValue(512);
        analiticTable.prefWidthProperty().bind(mainPane.widthProperty().add(-analiticTool.prefWidthProperty().getValue()));
        beginColumn.prefWidthProperty().bind(analiticTable.widthProperty().multiply(0.2));
        endColumn.prefWidthProperty().bind(analiticTable.widthProperty().multiply(0.2));
        expressionColumn.prefWidthProperty().bind(analiticTable.widthProperty().multiply(0.6));
        chart.prefWidthProperty().bind(mainPane.widthProperty());
        chart.prefHeightProperty().bind(mainPane.heightProperty());
        xmlEditor.prefWidthProperty().bind(mainPane.widthProperty());
        nodeTool.prefWidthProperty().setValue(512);
        nodeTable.prefWidthProperty().bind(mainPane.widthProperty().add(-nodeTool.prefWidthProperty().getValue()));
        xColumn.prefWidthProperty().bind(nodeTable.widthProperty().multiply(0.5));
        yColumn.prefWidthProperty().bind(nodeTable.widthProperty().multiply(0.5));

        beginColumn.setCellValueFactory(new PropertyValueFactory<Formula, Double>("begin"));
        endColumn.setCellValueFactory(new PropertyValueFactory<Formula, Double>("end"));
        expressionColumn.setCellValueFactory(new PropertyValueFactory<Formula, String>("expression"));

        xColumn.setCellValueFactory(new PropertyValueFactory<Point, Double>("x"));
        yColumn.setCellValueFactory(new PropertyValueFactory<Point, Double>("y"));

        infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("Чисельні методи: лабораторна робота №2");
        infoAlert.setHeaderText("МЕТОДИ НАБЛИЖЕННЯ ФУНКЦІЙ");
        infoAlert.setContentText("Виконав:\nстудент КН-26\nМишенін А.С.\nПеревірив:\nасистент Мельник М.Р.");

        chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Document", "*.xml"));
        chooser.setInitialDirectory(new File("D:\\univer\\NUM_LAB_2\\src\\com\\univer\\num\\lab_2\\res"));

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                try {
                    quit();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (TransformerException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        seriesInterpolated = new XYChart.Series<Double, Double>();
        seriesInterpolated.setName("Інтерпольована функція");
        seriesReal = new XYChart.Series<Double, Double>();
        seriesReal.setName("Початкова фукнція");
    }

    public void info(){
        infoAlert.show();
    }

    public void open() throws IOException, SAXException, ParserConfigurationException, TransformerException {
        File function = chooser.showOpenDialog(primaryStage);
        if (function != null){
            functionManager = new FunctionManager(function);
            analiticTable.setItems(functionManager.getFormulas());
            nodeTable.setItems(functionManager.getNodes());
            xmlEditor.setText(fileContent(function));
            statusBarLabel.setText("File: " + function.getAbsolutePath());
        }
    }

    private String fileContent(File file) throws FileNotFoundException {
        StringBuilder content = new StringBuilder();
        Scanner input = new Scanner(new FileInputStream(file));
        while (input.hasNext()){
            content.append(input.nextLine() + "\n");
        }
        input.close();
        return content.toString();
    }

    public void save() throws IOException, TransformerException, ParserConfigurationException {
        File function = chooser.showSaveDialog(primaryStage);
        if(function != null){
            if(!function.exists()){
                function.createNewFile();
            }
            functionManager.toFile(function);
            statusBarLabel.setText("File: " + function.getAbsolutePath());
        }
    }

    public void quit() throws ParserConfigurationException, TransformerException, IOException {
        System.exit(0);
    }

    public void create() throws ParserConfigurationException, TransformerException, IOException {
        functionManager = new FunctionManager();
        analiticTable.setItems(functionManager.getFormulas());
        nodeTable.setItems(functionManager.getNodes());
        xmlEditor.setText("");
    }

    public void addFormula() throws ParserConfigurationException, TransformerException, IOException {
        functionManager.getFormulas().add(new Formula(Double.parseDouble(beginFormula.getText()),
                                                      Double.parseDouble(endFormula.getText()),
                                                               expressionFormula.getText()));
        refresh();
        functionManager.rebuiltRealFunction();
    }

    public void updateFormula() throws ParserConfigurationException, TransformerException, IOException {
        functionManager.getFormulas().set(analiticTable.getSelectionModel().getFocusedIndex(), new Formula(Double.parseDouble(beginFormula.getText()),
                Double.parseDouble(endFormula.getText()),
                expressionFormula.getText()));
        refresh();
        functionManager.rebuiltRealFunction();
    }

    public void deleteFormula() throws ParserConfigurationException, TransformerException, IOException {
        functionManager.getFormulas().remove(analiticTable.getSelectionModel().getFocusedIndex());
        refresh();
        functionManager.rebuiltRealFunction();
    }

    public void addPoint() throws ParserConfigurationException, TransformerException, IOException {
        functionManager.getNodes().add(new Point(Double.parseDouble(xPoint.getText()),
                                                 Double.parseDouble(yPoint.getText())));
        refresh();
    }

    public void updatePoint() throws ParserConfigurationException, TransformerException, IOException {
        functionManager.getNodes().set(nodeTable.getSelectionModel().getFocusedIndex(),
                new Point(Double.parseDouble(xPoint.getText()), Double.parseDouble(yPoint.getText())));

        refresh();
    }

    public void deletePoint() throws ParserConfigurationException, TransformerException, IOException {
        functionManager.getNodes().remove(nodeTable.getSelectionModel().getFocusedIndex());
        refresh();
    }

    private void refresh() throws ParserConfigurationException, TransformerException, IOException {
        beginFormula.setText("");
        endFormula.setText("");
        xPoint.setText("");
        yPoint.setText("");

        if(statusBarLabel.getText().equals("Untitled.xml")){
            save();
            analiticTable.setItems(functionManager.getFormulas());
            nodeTable.setItems(functionManager.getNodes());
            xmlEditor.setText(fileContent(new File(statusBarLabel.getText().substring(6))));
        } else {
            File file = new File(statusBarLabel.getText().substring(6));
            functionManager.toFile(file);
            analiticTable.setItems(functionManager.getFormulas());
            nodeTable.setItems(functionManager.getNodes());
            xmlEditor.setText(fileContent(file));
        }
    }

    public void changed(){
        contentChanged = true;
    }

    public void saveXML() throws IOException, SAXException, ParserConfigurationException {
        File file = null;
        if(contentChanged){
            if(statusBarLabel.equals("Untitled.xml")){
                file = chooser.showSaveDialog(primaryStage);
                PrintStream output = new PrintStream(new FileOutputStream(file));
                output.print(xmlEditor.getText());
                output.close();
            } else {
                file = new File(statusBarLabel.getText().substring(6));
                PrintStream output = new PrintStream(new FileOutputStream(file));
                output.print(xmlEditor.getText());
                output.close();
            }

            functionManager = new FunctionManager(file);
            analiticTable.setItems(functionManager.getFormulas());
            nodeTable.setItems(functionManager.getNodes());
            contentChanged = false;
        }
    }

    public void buildPlot() throws IOException {
        functionManager.valuesFirst(0.001);
        for (int i = 0; i < functionManager.getValues().size(); i++) {
            seriesInterpolated.getData().add(new XYChart.Data<Double, Double>(functionManager.getValues().get(i).getX(),
                    functionManager.getValues().get(i).getY()));
        }
        chart.getData().add(seriesInterpolated);

        functionManager.valuesReal(0.001);
        for (int i = 0; i < functionManager.getValues().size(); i++){
            seriesReal.getData().add(new XYChart.Data<Double, Double>(functionManager.getValues().get(i).getX(),
                    functionManager.getValues().get(i).getY()));
        }
        chart.getData().add(seriesReal);

        mainPane.getSelectionModel().select(2);
    }
}
