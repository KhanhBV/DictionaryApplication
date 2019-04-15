/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import sun.util.locale.StringTokenIterator;

/**
 *
 * @author Admin
 */
public class VietnameseEnglish {
    TreeMap<String, LinkedList<String>> vE = new TreeMap();
    String fileName = "VietnameseEnglish.txt";
    File file = new File(fileName);
    public void readFileVE() {
        try {
            
            if (!file.exists())
                return;
            FileReader fr = new FileReader(file);
            String details = "";
            BufferedReader br = new BufferedReader(fr);
            while((details = br.readLine())!= null){
                StringTokenizer stk = new StringTokenizer(details, ":");
                String key = stk.nextToken().trim();
                String value = stk.nextToken().trim();
                LinkedList<String> tmpLinkedList = new LinkedList<>();
                StringTokenizer stk1 = new StringTokenizer(value, ",");
                while(stk1.hasMoreElements()){
                    tmpLinkedList.add(stk1.nextToken().trim());
                }
                vE.put(key, tmpLinkedList);
           }
            br.close();
            fr.close();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
}
    public String searchVietnamese(String word) {
        if(!vE.containsKey(word))
            return "We did not find the word you requested!";
        String meanList = "";
        for (String x : vE.get(word)) {
            if(meanList.equals(""))
                    meanList = x;
            else meanList = meanList + ", " + x;
        }
        return meanList;
    }
    public void addVietnameseEnglish(String Vword, String Eword){
        LinkedList<String> menLinkedList = new LinkedList();
        try {
            File file = new File(fileName);
            if (!file.exists())
                return;
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw =  new PrintWriter(bw);
            String meanList = "";
            StringTokenizer stk = new StringTokenizer(Eword, ",");
            while(stk.hasMoreElements())
                menLinkedList.add(stk.nextToken().trim());    

            String key = Vword.toString();
            for (String x : menLinkedList) {
                if(meanList.equals(""))
                    meanList = x;
                else meanList = meanList + ", " + x;
            }
            String value = Eword.toString();
            String formatData = String.format("%s:%s", key, meanList);
            pw.println(formatData);
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public LinkedList<String> checkVE(String key) {
        return vE.get(key);
    }
    public void addVE(String Vword, String Eword){
         readFileVE();
        if (checkVE(Vword) == null) {
            addVietnameseEnglish(Vword, Eword);
        }
        else{
            LinkedList<String> tmpLinklist = checkVE(Vword);
            LinkedList<String> meanLinkedList = new LinkedList<>();
            StringTokenizer stk = new StringTokenizer(Eword, ",");
            
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
            vE.remove(Vword);
            vE.put(Vword, tmpLinklist); 
            
            
             
                
              
                try {
                    File file = new File(fileName);
                    if (!file.exists()) {
                        return;
                    }
                    FileWriter fw = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter pw = new PrintWriter(bw);

                    for (Map.Entry<String, LinkedList<String>> entry : vE.entrySet()) {
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
        VietnameseEnglish ve = new VietnameseEnglish();
        ve.readFileVE();
//        ve.addVE("con chuột", "trước,tiger, tigers");
        System.out.println(ve.checkVE("vũ khí"));
    }
}
 
