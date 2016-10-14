/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geolocation;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
/**
 *
 * @author HamzaAli
 */
public class Geolocation {
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        HashMap<String,String> city = new HashMap<String,String>();
         int i =0;
        try{
            Scanner input = new Scanner(new FileReader("GeoLiteCity-Location.csv"));
            
            input.nextLine();
            input.nextLine();
           
            while (input.hasNextLine()) 
            {
                String data = input.nextLine();
                String[] values = data.split(",");
                values[3] = values[3].replace("\"", "");
                city.put( values[5]+"," +values[6],values[3]);
                
                if (i == 29340) 
                    System.out.println(values[3]);
                i++;
            }
            input.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        
        System.out.println("Enter the City name to search: ");
        Scanner reader = new Scanner(System.in);
        String cit = reader.next();
        System.out.println("How many nearby cities do you want: ");
        Scanner inpu = new Scanner(System.in);
        int input = inpu.nextInt();
        
        String key = "Not Found";
        String value = null;
        int x = 0;
        
        for (String o : city.keySet()) {
            if (city.get(o).equals(cit)) {
              System.out.println("City: " + cit + "\t Lat,Long: " + o);
              value = o;
              x++;
            }
          }
        
        ArrayList close = new ArrayList();
        HashMap<Double,String> near = new HashMap<Double,String>();
        Iterator it = city.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            
            String[] latlong = value.split(",");
            String l = (String) pair.getKey();
            String[] latlong1 = l.split(",");
            try{
            double lat = Math.toRadians(Double.parseDouble(latlong[0]));
            double lon = Math.toRadians(Double.parseDouble(latlong[1]));
             double lat1 = Math.toRadians(Double.parseDouble(latlong1[0]));
            double long1 = Math.toRadians(Double.parseDouble(latlong1[1]));
            
            double angle = Math.acos(Math.sin(lat) * Math.sin(lat1) + Math.cos(lat) * Math.cos(lat1) * Math.cos(Math.abs(lon - long1)));
            angle = Math.toDegrees(angle);
            double distance = angle * 60 * 1.1515 * 1.609344;
            if (distance > 150){ // distance greater than 150
                close.add(distance);
                near.put(distance, pair.getValue().toString());
            }
                
            } catch (NumberFormatException ex){
                System.out.println("Some Values were empty!!!");
            }
            //System.out.println(distance);
            it.remove();
        }
        Collections.sort(close);
         for (int z=0;z < input;z++){
                System.out.print("Closest cities: " + close.get(z));
               System.out.println("\t City Name: " + near.get(close.get(z)));
            }
    }  
}