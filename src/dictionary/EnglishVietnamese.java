/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary;

import com.sun.javafx.binding.StringFormatter;
import com.sun.org.apache.xpath.internal.functions.FuncId;
import com.sun.xml.internal.ws.api.pipe.Engine;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import jdk.nashorn.internal.ir.TryNode;

/**
 *
 * @author Admin
 */
public class EnglishVietnamese {

    TreeMap<String, LinkedList<String>> eL = new TreeMap();
    String fileName = "EnglishVietnamese.txt";

    public void readFileEL() {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                return;
            }
            String details = "";
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            while ((details = br.readLine()) != null) {
                StringTokenizer stk = new StringTokenizer(details, ":");
                String key = stk.nextToken().trim();
                String value = stk.nextToken().trim();
                LinkedList<String> tmpLinkedList = new LinkedList<>();
                StringTokenizer stk1 = new StringTokenizer(value, ",");
                while(stk1.hasMoreElements()){
                    tmpLinkedList.add(stk1.nextToken().trim());
                }
                eL.put(key, tmpLinkedList);
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String searchEnglish(String word) {
        readFileEL();
        if (!eL.containsKey(word)) 
            return "We did not find the word you requested!";
        String meanList = "";
        for (String x : eL.get(word)) {
            if(meanList.equals(""))
                    meanList = x;
            else meanList = meanList + ", " + x;
        }
        return meanList;

    }

    public void addEnglishVietnamese(String Eword, String Vword) {
        LinkedList<String> menLinkedList = new LinkedList();
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                return;
            }
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            String meanList = "";
            StringTokenizer stk = new StringTokenizer(Vword, ",");
            while(stk.hasMoreElements())
                menLinkedList.add(stk.nextToken().trim());    
            String key = Eword.toString();
            for (String x : menLinkedList) {
                if(meanList.equals(""))
                    meanList = x;
                else meanList = meanList + ", " + x;
            }
            String value = Vword.toString();
            String formatData = String.format("%s:%s", key, meanList);
            pw.println(formatData);
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public LinkedList<String> checkEV(String key) {
        readFileEL();
        return eL.get(key);
    }

//    public static void main(String[] args) {
//        EnglishVietnamese  el = new EnglishVietnamese();
//        el.readFileEL();
//        System.out.println(el.searchEnglish("computer"));
//       
//        
//    }
    public void AddEV(String Eword, String Vword) {
        readFileEL();
        if (checkEV(Eword) == null) {
            addEnglishVietnamese(Eword, Vword);
        } else {
            LinkedList<String> tmpLinklist = checkEV(Eword);
            LinkedList<String> meanLinkedList = new LinkedList<>();
            StringTokenizer stk = new StringTokenizer(Vword, ",");
             
                
            while(stk.hasMoreElements())
                meanLinkedList.add(stk.nextToken().trim());
            for (int i = 0; i < meanLinkedList.size() - 1; i++)
                for (int j = i + 1; j < meanLinkedList.size(); j++)    
                    if (meanLinkedList.get(i).trim().equalsIgnoreCase(meanLinkedList.get(j).trim()))
                        meanLinkedList.remove(j);
            
            for (int i = 0; i < tmpLinklist.size(); i++) {
                for (int j = 0; j < meanLinkedList.size(); j++) {
                    if(tmpLinklist.get(i).trim().equalsIgnoreCase(meanLinkedList.get(j).trim()))
                        meanLinkedList.remove(j);
                }
                
            }
            for (int i = 0; i < meanLinkedList.size(); i++) {
                tmpLinklist.add(meanLinkedList.get(i));
            }
            eL.remove(Eword);
            eL.put(Eword, tmpLinklist);
                try {
                    File file = new File(fileName);
                    if (!file.exists()) 
                        return;
                    
                    FileWriter fw = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter pw = new PrintWriter(bw);

                    for (Map.Entry<String, LinkedList<String>> entry : eL.entrySet()) {
                        String meanString = "";
                        for (String x: entry.getValue()) {
                            if(meanString.equals(""))
                                meanString = x;
                             else meanString = meanString + ", " + x;
                        }
                        
                        String format = String.format("%s:%s",entry.getKey(),meanString);
                        pw.println(format);
                        
                    }
    
                    pw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            
            
        }
        
    }
    public static void main(String[] args) {
        
        EnglishVietnamese el = new EnglishVietnamese();
        el.readFileEL();
//        el.AddEV("car",);
        System.out.println(el.searchEnglish("arms"));
    }

}
