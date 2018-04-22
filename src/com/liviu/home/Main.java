package com.liviu.home;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Liviu Catuneanu Cica
 */
public class Main {
    public static void main(String[] args){
        String posClass, negClass;
        
        // Get the classes (negative followed by positive comma separated)
        Scanner opt = null;
        try{
            opt = new Scanner(new File("classes.txt"));
        } catch(Exception e){
            System.out.println("No classes.txt file found");
            System.exit(0);
        }
        String line = opt.nextLine();
        System.out.println(line);
        opt.close();
        String[] data = line.split(",");
        posClass = data[1];
        negClass = data[0];
        
        // Arraylist to store clusters
        Map<String, Cluster> clusters = new HashMap<>();
        String clss, cluster;
        
        Scanner in = null;
        try{
            in = new Scanner(new File("Results.arff"));
        } catch(Exception e){
            System.out.println("No Results.arff file found");
            System.exit(0);
        }
        
        // Skip past attributes
        while(in.hasNextLine() && !line.equalsIgnoreCase("@data")){
            line = in.nextLine();
        }
        
        // Read all records
        while(in.hasNextLine()){
            line = in.nextLine();
            data = line.split(",");
            
            // Make sure that it is an actual data line and not empty space
            if(data.length >= 2){
                // Get the class
                clss = data[data.length-2];
                
                // Get the cluster
                cluster = data[data.length-1];
                
                // If cluster hasn't been encountered yet, make it
                if(!clusters.containsKey(cluster)){
                    Cluster c = new Cluster(cluster);
                    clusters.put(cluster, c);
                }
                
                // Increment either positive class for cluster or negative
                if(clss.equalsIgnoreCase(posClass))
                    clusters.get(cluster).pos++;
                else
                    clusters.get(cluster).neg++;
            }
        }
        
        in.close();
        
        Collection<Cluster> all = clusters.values();
        
        // Assign class to each cluster
        for(Cluster c: all){
            if(c.pos > c.neg)
                c.assignedClass = posClass;
            else
                c.assignedClass = negClass;
        }
        
        try{
            // Export to GoodBad.csv
            PrintWriter out = new PrintWriter(new File("GoodBad.csv"));
            for(Cluster c: all){
                for(int pos = 0; pos<c.pos; pos++){
                    out.println(posClass + "," + c.assignedClass);
                }
                for(int neg = 0; neg<c.neg; neg++){
                    out.println(negClass + "," + c.assignedClass);
                }
            }
            out.close();
        } catch(Exception e){}
    }
}
