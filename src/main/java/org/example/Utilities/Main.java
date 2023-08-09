package org.example.Utilities;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Generic g =new Generic();
        g.XmLModifier("C:\\Users\\ASUS\\Desktop\\test.txt");
        g.XmLModifier("C:\\Users\\ASUS\\Desktop\\test2.txt");
        List<XMLModification> list = g.compareXmlFilesNew("C:\\Users\\ASUS\\Desktop\\test.txt", "C:\\Users\\ASUS\\Desktop\\test2.txt");
        System.out.println(list);
    }
}
