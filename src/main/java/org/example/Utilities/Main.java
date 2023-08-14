package org.example.Utilities;

import java.io.*;
import java.util.List;
import java.util.Stack;


public class Main {
    public static void main(String[] args) throws Exception{
        Generic g= new Generic();
        g.compareXmlFiles(Config.BeforeFile, Config.AfterFile);
//        String inputFilePath = "C:\\Users\\ASUS\\Desktop\\test.txt";
//        String outputFilePath = "C:\\Users\\ASUS\\Desktop\\test2.txt";
//
//
//        Generic g = new Generic();
//        List<XMLModification> list = g.compareXmlFilesNew(inputFilePath, outputFilePath);
//

//        try {
//            String xmlContent = readXmlFile(inputFilePath);
//            String correctedXml = fixMismatchedTags(xmlContent);
//            writeXmlFile(outputFilePath, correctedXml);
//
//            System.out.println("Mismatched XML tags fixed and saved to output file.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    private static String readXmlFile(String filePath) throws IOException {
//        StringBuilder content = new StringBuilder();
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                content.append(line).append("\n");
//            }
//        }
//        return content.toString();
//    }
//
//    private static void writeXmlFile(String filePath, String content) throws IOException {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
//            writer.write(content);
//        }
//    }
//
//    private static String fixMismatchedTags(String xmlContent) {
//        Stack<String> tagStack = new Stack<>();
//        StringBuilder correctedXml = new StringBuilder();
//
//        int index = 0;
//        while (index < xmlContent.length()) {
//            if (xmlContent.charAt(index) == '<') {
//                int endIndex = xmlContent.indexOf('>', index);
//                if (endIndex != -1) {
//                    String tag = xmlContent.substring(index + 1, endIndex);
//                    if (tag.startsWith("/")) {
//                        if (!tagStack.isEmpty()) {
//                            String openingTag = tagStack.pop();
//                            correctedXml.append("<").append(openingTag).append(">");
//                        }
//                    } else {
//                        correctedXml.append("<").append(tag).append(">");
//                        tagStack.push(tag);
//                    }
//                    index = endIndex + 1;
//                } else {
//                    // Invalid XML, just append the rest of the content
//                    correctedXml.append(xmlContent.substring(index));
//                    break;
//                }
//            } else {
//                correctedXml.append(xmlContent.charAt(index));
//                index++;
//            }
//        }
//
//        return correctedXml.toString();
//    }
    }
}

