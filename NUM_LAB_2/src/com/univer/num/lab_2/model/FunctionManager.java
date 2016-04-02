package com.univer.num.lab_2.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class FunctionManager implements Serializable{
    private ObservableList<Formula> formulas;
    private ObservableList<Point> nodes;
    private ObservableList<Point> values;
    private double[][] deltas;

    public FunctionManager() {
        formulas = FXCollections.observableArrayList();
        nodes = FXCollections.observableArrayList();
    }

    public FunctionManager(ObservableList<Formula> formulas, ObservableList<Point> nodes, ObservableList<Point> values, double[][] deltas) {
        this.formulas = formulas;
        this.nodes = nodes;
        this.values = values;
        this.deltas = deltas;
    }

    public FunctionManager(File file) throws ParserConfigurationException, IOException, SAXException {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);

        formulas = FXCollections.observableArrayList();
        NodeList formulaTags = document.getElementsByTagName("formula");
        for (int i = 0; i < formulaTags.getLength(); i++) {
            formulas.add(new Formula(Double.parseDouble(formulaTags.item(i).getAttributes().getNamedItem("begin").getTextContent()),
                                    Double.parseDouble(formulaTags.item(i).getAttributes().getNamedItem("end").getTextContent()),
                                    formulaTags.item(i).getAttributes().getNamedItem("expression").getTextContent()));
        }

        nodes = FXCollections.observableArrayList();
        NodeList nodeTag = document.getElementsByTagName("point");
        for (int i = 0; i < nodeTag.getLength(); i++) {
            nodes.add(new Point(Double.parseDouble(nodeTag.item(i).getAttributes().getNamedItem("x").getTextContent()),
                                Double.parseDouble(nodeTag.item(i).getAttributes().getNamedItem("y").getTextContent())));
        }

        rebuiltRealFunction();

    }

    public ObservableList<Formula> getFormulas() {
        return formulas;
    }

    public void setFormulas(ObservableList<Formula> formulas) {
        this.formulas = formulas;
    }

    public ObservableList<Point> getNodes() {
        return nodes;
    }

    public void setNodes(ObservableList<Point> nodes) {
        this.nodes = nodes;
    }

    public ObservableList<Point> getValues() {
        return values;
    }

    public void setValues(ObservableList<Point> values) {
        this.values = values;
    }

    public double[][] getDeltas() {
        return deltas;
    }

    public void setDeltas(double[][] deltas) {
        this.deltas = deltas;
    }

    public void toFile(File file) throws ParserConfigurationException, TransformerException, IOException {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

        Element functionTag = document.createElement("function");
        document.appendChild(functionTag);

        Element analiticTag = document.createElement("analitic");
        functionTag.appendChild(analiticTag);
        Element formulaTag = null;
        for (int i = 0; i < formulas.size(); i++){
            formulaTag = document.createElement("formula");
            formulaTag.setAttribute("begin", String.valueOf(formulas.get(i).getBegin()));
            formulaTag.setAttribute("end", String.valueOf(formulas.get(i).getEnd()));
            formulaTag.setAttribute("expression", formulas.get(i).getExpression());

            analiticTag.appendChild(formulaTag);
        }

        Element nodesTag = document.createElement("nodes");
        functionTag.appendChild(nodesTag);
        Element pointTag = null;
        for (int i = 0; i < nodes.size(); i++) {
            pointTag = document.createElement("point");
            pointTag.setAttribute("x", String.valueOf(nodes.get(i).getX()));
            pointTag.setAttribute("y", String.valueOf(nodes.get(i).getY()));

            nodesTag.appendChild(pointTag);
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.transform(new DOMSource(document), new StreamResult(file));

    }

    private int factorial(int n){
        int result = 1;
        for (int i = n; i >0 ; i--) {
            result *= i;
        }
        return result;
    }

    private int C(int k, int n){
        return factorial(n) / (factorial(k) * factorial(n - k));
    }

    private void setupDeltas(){
        deltas = new double[nodes.size()][];

        for(int i = 0, size = nodes.size(); i < nodes.size(); i++, size--){
            deltas[i] = new double[size];
            for(int j = 0; j < size; j++){
                for (int k = 0; k < i; k++) {
                    deltas[i][j] += Math.pow(-1, k) * C(k, i) * nodes.get(i + j - k).getY();
                }
            }
        }
    }

    private double getStart(){
        double min = Double.MAX_VALUE;
        for (int i = 0; i < nodes.size(); i++) {
            if(min > nodes.get(i).getX()){
                min = nodes.get(i).getX();
            }
        }
        return min;
    }

    private double getEnd(){
        double max = Double.MIN_VALUE;
        for (int i = 0; i < nodes.size(); i++) {
            if(max < nodes.get(i).getX()){
                max = nodes.get(i).getX();
            }
        }
        return max;
    }

    private double valueInPointFirst(double x){
        setupDeltas();
        double result = nodes.get(0).getY();
        double tmp = 1;
        double h = nodes.get(1).getX() - nodes.get(0).getX();
        for (int i = 1; i < nodes.size(); i++) {
            tmp = 1;
            for (int j = 0; j < i; j++) {
                tmp *= (x - nodes.get(j).getX());
            }
            tmp *= (deltas[i][0] / (factorial(i) * Math.pow(h, i)));
            result += tmp;
        }
        return result;
    }

    public void valuesFirst(double step){
        values = FXCollections.observableArrayList();
        for (double x = getStart(); x <= getEnd(); x+=step){
            values.add(new Point(x, valueInPointFirst(x)));
        }
    }

    private String makeItJava(String formulaString){
        StringBuilder result = new StringBuilder(formulaString);
        String[] math = {"abs", "acos", "asin", "atan", "cbrt", "cos", "cosh", "exp", "log", "log10", "sin", "sinh",
                         "sqrt", "tan", "tanh", "pow"};
        int start = 0;
        for (int i = 0; i < math.length; i++) {
            while (result.indexOf(math[i], start) != -1){
                result.replace(result.indexOf(math[i], start), result.indexOf(math[i], start) + math[i].length(), "Math." + math[i]);
                start += (result.indexOf(math[i], start) + math[i].length());
            }
            start = 0;
        }
        return result.toString();
    }

    public void rebuiltRealFunction() throws IOException {
        File codeFile = new File("src/com/univer/num/lab_2/model/RealFunction.java");
        codeFile.delete();
        codeFile.createNewFile();

        PrintStream codePrinter = new PrintStream(new FileOutputStream(codeFile), true);
        codePrinter.append("package com.univer.num.lab_2.model;\n\n" +
                "public class RealFunction {\n" +
                "\tpublic static double valueInPointReal(double x){\n");
        for (int i = 0; i < formulas.size(); i++) {
            codePrinter.append("\t\tif ((x >= " + formulas.get(i).getBegin() + ")&&(x < " + formulas.get(i).getEnd()
                    + ")) return " + makeItJava(formulas.get(i).getExpression()) + ";\n");
        }
        codePrinter.append("\t\treturn 0;\n");
        codePrinter.append("\t}\n" +
                "}");
        codePrinter.close();
    }

    private void compile(){
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int answer = compiler.run(null, null, null, "src/com/univer/num/lab_2/model/RealFunction.java");
        if (answer == 0) {
            new File("src/com/univer/num/lab_2/model/RealFunction.class").renameTo(new File("out/production/NUM_LAB_2/com/univer/num/lab_2/model/RealFunction.class"));
        }
    }

    public void valuesReal(double step){
        compile();
        values = FXCollections.observableArrayList();
        for (double x = getStart(); x <= getEnd(); x+=step){
            values.add(new Point(x, RealFunction.valueInPointReal(x)));
        }
    }

}
