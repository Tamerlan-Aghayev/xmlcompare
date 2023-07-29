package org.example.Utilities;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax. xml. parsers. DocumentBuilder;
import javax.xml.parsers. DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
//import org.example.ExcelOperation;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.w3c.dom. Document;
import org.w3c. dom. Element;
import org.w3c.dom. Node;
import org.w3c.dom.NodeList;
import org.xml.sax. InputSource;
import org. xmlunit. builder. DiffBuilder;
import org.xmlunit.builder. Input;
import org.xmlunit.diff.DefaultNodeMatcher;
import org. xmlunit. diff. Diff;
import org. xmlunit. diff. Difference;
import org.xmlunit.xpath.JAXPXPathEngine;
import org.xmlunit.xpath.XPathEngine;


public class Generic(){
        public static XSSFWorkbook workbook = new XSSFWorkbook ();
        public static FileOutputStream fileout = null;

        public Generic () {}


        public void comparison() throws Exception {
            runAndCopyFiles();
            Thread.sleep (10000);
            compareXmlFiles (Config.BeforeFile, Config.AfterFile);
        }


        public void XmLModifier(String xmlFilePAth) {
            try {
                Path path = Paths. get (xmlFilePAth);
                byte[] bytes = Files. readAllBytes (path);
                String xml = new String (bytes);
                if (!xml.contains ("<ConsolidatedAPP>")) {
                    xml = "<ConsolidatedAPP>\n" + xml;
                    xml = xml + "\n</ConsolidatedAPP>";

                    FileWriter fileWriter = new FileWriter (new File(xmlFilePAth));
                    fileWriter.write (xml);
                    fileWriter.close ();
                }
            } catch (IOException e) {
                e.printStackTrace ( ) ;
            }
        }
        public void readXmIToExcel (String path, String sheetName) throws Exception {

            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            style.setAlignment(HorizontalAlignment.CENTER);

            FileInputStream inputStream = new FileInputStream(path);
            XSSFSheet sheet = workbook.createSheet(sheetName);
            XSSFRow headerRow = sheet.createRow(0);
            XSSFCell headerCell = headerRow.createCell(0);

            headerCell.setCellValue("ParentTag");
            headerCell.setCellStyle(style);

            headerCell = headerRow.createCell(1);
            headerCell.setCellValue("ChildTAg");
            headerCell.setCellStyle(style);

            headerCell = headerRow.createCell(2);
            headerCell.setCellValue("Value");
            headerCell.setCellStyle(style);
            int rowNum = 1;


            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);

            NodeList parentNodes = document.getElementsByTagName("*");

            for (int i = 0; i < parentNodes.getLength(); i++) {
                Node parentNode = parentNodes.item(i);
                if (parentNode.hasChildNodes()) {
                    NodeList childNodes = parentNode.getChildNodes();
                    for (int j = 0; i < childNodes.getLength(); j++) {
                        Node childNode = childNodes.item(j);
//
                        if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element childElement = (Element) childNode;

                            XSSFRow row = sheet.createRow(rowNum++);
                            XSSFCell cell = row.createCell(0);
                            cell.setCellValue(parentNode.getNodeName());
                            cell = row.createCell(1);
                            cell.setCellValue(childNode.getNodeName());
                            cell = row.createCell(2);
                            cell.setCellValue(childNode.getTextContent());
                        }
                    }
                }
            }
            for (int i = 0; i < 3; i++) {
                sheet.autoSizeColumn(i);
            }
            inputStream.close();
            fileout = new FileOutputStream(Config.resultPath);
            workbook.write(fileout);
        }
            public void compareXmlFiles(String path1, String path2 ){
                XmLModifier (path1 );
                try {
                    readXmIToExcel (path1, "BeforeCode");
                    System.out.println ("BeforeCode ready");
                } catch (Exception e) {
                    e.printStackTrace ( );
                }
                XmLModifier (path2 );
                try {
                    readXmIToExcel (path2, "AfterCode");
                    System.out.println ("AfterCode ready");
                }catch (Exception e){
                    e.printStackTrace ( );
                }
                try {
                    List<XMLModification> modifications=compareXmlFilesNew(path1, path2 );
                    writeToExcel (modifications, Config.resultPath);
                    System.out.println ("Differences ready");
                }catch (Exception e) {
                    e.printStackTrace ();
                }

            }
            public List<XMLModification> compareXmlFilesNew(String filePath1, String filePath2){
                List<XMLModification> modifications=new ArrayList<>();
                List<XMLModification> modificationsNew=new ArrayList<>();
                File file1=new File (filePath1);
                File file2=new File(filePath2);
                Diff diff = DiffBuilder.compare (Input.fromFile (file1))
                        .withTest (Input.fromFile (file2))
                        .withNodeMatcher (new DefaultNodeMatcher( ))
                        .checkForSimilar ( )
                        .ignoreWhitespace ( )
                        .ignoreComments ( )
                        .withDifferenceEvaluator (new DetailedDifferenceEvaluator(modifications))
                        .build ( );
                Iterable<Difference> differences =diff.getDifferences ( ) ;
                for (Difference difference:differences ) {
                    String xPath=difference.getComparison().getControlDetails ( ). getXPath ();
                    String expectedValue=String.valueOf(difference.getComparison ().getControlDetails ().getValue () );
                    String actualValue=String.valueOf(difference.getComparison().getTestDetails ().getValue ( ));
                    if (xPath==null) {
                        System.out.println("xpath-"+xPath);
                        xPath=difference.getComparison ().getTestDetails ().getXPath();
                        Pattern pattern =Pattern.compile("LoanApplication\\[\\d+\\]");
                        Matcher matcher=pattern.matcher (xPath);
                        matcher.find ( );
                        int start=matcher.start ( );
                        int end=matcher.end ( ) ;
                        String xPathRoot=xPath.substring(0,end);
                        XPathRoot+="/applicationID";
                        XPathEngine xPathEngine=new JAXPXPathEngine();
                        String applicationID=xPathEngine.evaluate(xPathRoot, diff.getControlSource ( ));
                        modificationsNew.add (new XMLModification (xPath, expectedValue, actualValue,difference.toString(), applicationID));
                    }
                    return modificationsNew;
                }
                public void writeToExcel(List<XMLModification> modifications, String excelFilePath)throws Exception{
                    Sheet sheet= workbook.createSheet ("Difference");
                    XSSFFont font = workbook.createFont ( ) ;
                    font.setBold(true);

                    CellStyle styleHeader=workbook.createCellStyle ();
                    styleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
                    styleHeader.setAlignment (HorizontalAlignment.CENTER);
                    styleHeader.setFont (font);
                    CellStyle styleMain=workbook.createCellStyle();
                    styleMain.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
                    styleMain.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    styleMain.setVerticalAlignment(VerticalAlignment.CENTER);
                    styleMain.setVerticalAlignment(VerticalAlignment.CENTER);
                    styleMain.setAlignment (HorizontalAlignment.CENTER);
                    styleMain.setFont (font);

                    int rownumber =0 ;
                    Row rowTotal=sheet.createRow(rownumber++);
                    rowTotal.createCell(1).setCellValue("Total Difference Found");
                    rowTotal.createCell(2).setCellValue(modifications.size());

                    rowTotal.getCell (1).setCellStyle (styleMain);
                    rowTotal.getCell (2).setCellStyle (styleMain);

                    rownumber++;

                    Row headerRow = sheet.createRow(rownumber++);
                    Cell cell = headerRow.createCell ( 0);
                    cell.setCellValue ("Application ID");
                    cell.setCellStyle (styleHeader);
                    cell= headerRow.createCell (1);
                    cell.setCellValue ("Tag Hierarchy");
                    cell.setCellStyle (styleHeader);
                    cell= headerRow.createCell (2);
                    cell.setCellValue ( "Data in BeforeCode XML" );
                    cell.setCellStyle (styleHeader);
                    cell=headerRow.createCell (3);
                    cell.setCellValue ("Data in AfterCode XMI");
                    cell.setCellstyle(styleHeader);
                    cell=headerRow.createCell (4);
                    cell.setCellValue ( "MisMatch Details");
                    cell.setCellStyle (styleHeader);
                    for (XMLModification modification:modifications) {
                        Row row=sheet.createRow(rownumber++);
                        row.createCell (0).setCellValue (modification.getApplicationId ( ));
                        row.createCell (1).setCellValue (modification.getXpath));
                        row.createCell (2).setCellValue (modification.getExpectedValue ( ));
                        row.createCell (3).setCellValue (modification.getActualValue ( ));
                        row.createCell (4).setCellValue (modification.getDescription ());

                        for (int i=0;i<6;i++) {
                            sheet.autoSizeColumn (i);
                        }
                        fileout=new FileOutputStream(excelFilePath);
                        workbook.write (fileout);
                        fileout.close ( );}}

                public void runAndCopyFiles(){
                    try {
                        FileInputStream inputStream = new FileInputStream(Config.envPath);
                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder =factory.newDocumentBuilder ();
                        Document document= builder.parse (inputStream);
                        document.getDocumentElement ().normalize ();

                        NodeList parentNodes = document. getElementsByTagName ( "environment");
                        for (int i=0;i<parentNodes.getLength();i++) {
                            Node parentNode = parentNodes.item(i);
                            if (parentNode.getNodeType () == Node. ELEMENT_NODE) {
                                Element parentelement = (Element)parentNode;
                                NodeList childNodes = parentelement.getChildNodes ( );

                                for (int j=0;j<childNodes.getLength();j++) {
                                    Node childNode = childNodes.item(j);
                                    if (childNode.getNodeType ( )==Node. ELEMENT_NODE) {
                                        Element subelement = (Element)childNode;
                                        String tagName = subelement.getTagName ( );
                                        String tagValue= subelement.getTextContent ();
                                        if (tagName. equalsIgnoreCase ("beforeAL03")&&tagValue.equalsIgnoreCase("Y")) {
                                            System.out.println ("Run the batch of AL03");
                                            runBat (Config.AL03);
                                            Thread.sleep (20000);
                                            CopyFile (Config.folderAL03, Config. BeforeFile);
                                        }if (tagName.equalsIgnoreCase("beforeAL51")&&tagValue.equalsIgnoreCase("Y")) {
                                            System.out.printin ("Run the batch of AL51");
                                            runBat (Config.AL51);
                                            CopyFile (Config.folderAL51, Config. BeforeFile);
                                        }if (tagName. equalsIgnoreCase ("beforeAL81")&&tagValue.equalsIgnoreCase("Y")) {
                                            System.out.println ("Run the batch of AL81");
                                            runBat (Config.AL81);
                                            CopyFile (Config.folderAL81, Config.BeforeFile);
                                        }if (tagName.equalsIgnoreCase("beforeCIDI")&&tagValue.equalsIgnoreCase("Y")) {
                                            System.out.println("Run the batch of CIDI ");
                                            runBat (Config.CIDI);
                                            Thread.sleep (20000);
                                            CopyFile (Config.folderCIDI, Config. BeforeFile);
                                        }if (tagName.equalsIgnoreCase("afterAL03")&&tagValue.equalsIgnoreCase("Y")) {
                                            System.out.println ("Run the batch of ALO3");
                                            runBat (Config.AL03);
                                            CopyFile(Config.folderAL03,Config.AfterFile);
                                        }if (tagName.equalsIgnoreCase("afterAL51")&&tagValue.equalsIgnoreCase("Y")) {
                                            System.out.printIn("Run the batch of AL51");
                                            runBat (Config.AL51);
                                            Thread.sleep (2000);
                                            CopyFile (Config.folderAL51, Config.AfterFile);
                                        }if (tagName.equalsIgnoreCase ("afterAL81") &&tagValue.equalsIgnoreCase("Y")) {
                                            System.out.println("Run the batch of AL81");
                                            runBat (Config.AL81);
                                            CopyFile (Config.folderAL81, Config.AfterFile);
                                        }if (tagName.equalsIgnoreCase("afterCIDI")&&tagValue.equalsIgnoreCase("Y")) {
                                            System.out.println ("Run the batch of CIDI ");
                                            runBat (Config.CIDI);
                                            Thread. sleep (20000);
                                            CopyFile(Config.folderCIDI,Config.AfterFile);
                                        }
                                    }
                                }}
                        }}catch (Exception e) {
                        e.printStackTrace ( );
                    }
                }

                public void CopyFile(String srcFile, String desFile) throws IOException {
                    File sourceFile = new File(srcFile);
                    File destFile = new File (desFile);

                    if (!sourceFile.exists()) {
                        return;
                    }
                    if (!destFile.exists ( )) {
                        destFile.createNewFile ( );
                    }
                    FileUtils.copyFile (sourceFile, destFile);
                }

                public void runBat (String filepath) {
                    File file = new File (filepath);
                    try {
                        Desktop desk = Desktop.getDesktop ( ) ;
                        desk.open (file);
                        System.out.println ("Successfully Executed");
                    }    catch (Exception e) {
                        e.printStackTrace ( );
                    }
                }}



