/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package campmanager;

import com.googlecode.charts4j.AxisLabels;
import com.googlecode.charts4j.AxisLabelsFactory;
import com.googlecode.charts4j.AxisStyle;
import com.googlecode.charts4j.AxisTextAlignment;
import com.googlecode.charts4j.Color;
import static com.googlecode.charts4j.Color.BLACK;
import static com.googlecode.charts4j.Color.WHITE;
import com.googlecode.charts4j.Data;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.LegendPosition;
import com.googlecode.charts4j.LineChart;
import com.googlecode.charts4j.LineStyle;
import com.googlecode.charts4j.PieChart;
import com.googlecode.charts4j.Plot;
import com.googlecode.charts4j.Plots;
import com.googlecode.charts4j.RadarChart;
import com.googlecode.charts4j.RadarPlot;
import com.googlecode.charts4j.RadialAxisLabels;
import com.googlecode.charts4j.Shape;
import com.googlecode.charts4j.Slice;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 *
 * @author Akshaya
 */
public class GetStats {
    LinkedList camperLL;
    LinkedList roomLL;
    
    public GetStats(LinkedList camperLL, LinkedList roomLL){
        this.camperLL=camperLL;
        this.roomLL=roomLL;
        
    }
    
    public boolean containsRoom(String room_name){
        ListIterator itr=roomLL.listIterator();
        Room here=new Room();
        while(itr.hasNext()){
            here=(Room)itr.next();
            if(here.getRoom_no().equals(room_name))
                return true;
        }
        return false;
    }
    
    LinkedList getAvailableRooms(){        
        ListIterator itr_room=roomLL.listIterator();
        LinkedList<Room> available_rooms=new LinkedList<Room>();
        
        Room room=new Room();
        while(itr_room.hasNext()){
            room=(Room)itr_room.next();
            if(!room.isFull()){
                available_rooms.add(room);
            }
            
        }
        
        return available_rooms;
    }
   public String chart_pie_nationality(){
       ListIterator itr=camperLL.listIterator();
       Camper camper=new Camper();
       HashMap<String,Integer>map=new HashMap<String,Integer>();
       
       while(itr.hasNext()){
           camper=(Camper)itr.next();
           if(!map.containsKey(camper.nationality)){
               map.put(camper.nationality,1);
               System.out.println("Added "+camper.nationality);
               System.out.println("if");
           }
           else{
               int value=map.get(camper.nationality);
               map.replace(camper.nationality, value+1);
               
               System.out.println("else");
           }
           
           
       }     
       final List<Slice> slices=new ArrayList<Slice>();
       
       Iterator<String> itr_key=map.keySet().iterator();
       while(itr_key.hasNext()){
          String key= itr_key.next();
           //slices.add(Slice.newSlice(map.get(key), Color.newColor("CACACA"), "Safari", "Apple"));   
          
           slices.add(Slice.newSlice(map.get(key),null, key,key));
           
       }    
       PieChart chart=GCharts.newPieChart(slices);
       chart.setTitle("Nationalities", BLACK, 16);
        
        chart.setLegendPosition(LegendPosition.BOTTOM);
        //chart.setLegendMargins(0, 0);
       chart.setSize(470, 170);
        chart.setThreeD(true);

       return chart.toURLString();
       

   }
  
   
   
   public String chart_radar_camps(){
       
       
       ListIterator itr=camperLL.listIterator();
       Camper camper=new Camper();
       int a=0,b=0,c=0,d=0,e=0;
       while(itr.hasNext()){
           camper=(Camper)itr.next();
           //{"Junior-NE","Senior-NE","Labour-NE","Labour-S","Junior-S"};
           if(camper.getCampLoc().equals("Junior-NE"))
               a++;
           else if(camper.getCampLoc().equals("Senior-NE"))
               b++;
           else if(camper.getCampLoc().equals("Labour-NE"))
               c++;
           else if (camper.getCampLoc().equals("Labour-S"))
               d++;
           else if(camper.getCampLoc().equals("Junior-S"))
               e++;
                       
                   
       }
       
        RadarPlot plot = Plots.newRadarPlot(Data.newData(a, b, c, d, e));
        Color plotColor = Color.newColor("CC3366");
        plot.addShapeMarkers(Shape.SQUARE, plotColor, 12);
        plot.addShapeMarkers(Shape.SQUARE, WHITE, 8);
        plot.setColor(plotColor);
        plot.setLineStyle(LineStyle.newLineStyle(4, 1, 0));
        RadarChart chart = GCharts.newRadarChart(plot);
        chart.setTitle("Camp Distribution", BLACK, 15);
        chart.setSize(400, 400);
        RadialAxisLabels radialAxisLabels = AxisLabelsFactory.newRadialAxisLabels("Junior North-East", "Senior North-East", "Labour North-East", "Labour South", "Junior South");
        radialAxisLabels.setRadialAxisStyle(BLACK, 12);
        chart.addRadialAxisLabels(radialAxisLabels);
        AxisLabels contrentricAxisLabels = AxisLabelsFactory.newNumericAxisLabels(Arrays.asList(0, 20, 40, 60, 80, 100));
        contrentricAxisLabels.setAxisStyle(AxisStyle.newAxisStyle(BLACK, 12, AxisTextAlignment.RIGHT));
        chart.addConcentricAxisLabels(contrentricAxisLabels);
        String url = chart.toURLString();

       return url;
   }
    public LinkedList getCamperLL() {
        return camperLL;
    }

    public void setCamperLL(LinkedList camperLL) {
        this.camperLL = camperLL;
    }
    
    
    
}
