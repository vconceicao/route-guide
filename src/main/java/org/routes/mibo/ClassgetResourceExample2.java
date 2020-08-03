package org.routes.mibo;
//import statements  
import java.net.URL;   
import java.lang.*;  
public class ClassgetResourceExample2 {  
   public static void main(String[] args) throws Exception {  
      ClassgetResourceExample2 obj = new ClassgetResourceExample2();  
      Class class1 = obj.getClass();  
      URL url = class1.getResource("file1.txt");  
      System.out.println("Value URL = " + url);  
      url = class1.getResource("newfolder/file2.txt");  
      System.out.println("Value URL = " + url);  
   }  
}  