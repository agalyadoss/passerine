package com.heaerie.common.utils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class XMLEditCreate {

    void createUser(String user, String password) throws DocumentException {
        File inputFile = new File("/tmp/input.txt");
        SAXReader saxBuilder = new SAXReader();
        Document document = saxBuilder.read(inputFile);

        Element classElement = document.getRootElement();

        List<Node> nodes = document.selectNodes("/class/student[@rollno = '493']" );
        for (Node node : nodes) {
            System.out.println("\nCurrent Element :"
                    + node.getName());
            System.out.println("Student roll no : "
                    + node.valueOf("@rollno") );
            System.out.println("First Name : "
                    + node.selectSingleNode("firstname").getText());
            System.out.println("Last Name : "
                    + node.selectSingleNode("lastname").getText());
            System.out.println("First Name : "
                    + node.selectSingleNode("nickname").getText());
            System.out.println("Marks : "
                    + node.selectSingleNode("marks").getText());
        }

        // Pretty print the document to System.out
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer;

        try {
            writer = new XMLWriter( System.out, format );
            writer.write( document );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String argv []) {
        XMLEditCreate a = new XMLEditCreate();
        try {
            a.createUser("durai", "1234");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}
