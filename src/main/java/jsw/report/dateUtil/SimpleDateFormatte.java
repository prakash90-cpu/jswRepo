package jsw.report.dateUtil;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SimpleDateFormatte {
	
	
	
	public SimpleDateFormatte() {
		super();
		// TODO Auto-generated constructor stub
	}



	public JSONObject convertDateToFormat(String startDate, int Duration) throws JSONException{
		JSONObject storeDate=new JSONObject();
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		 Date currentDate=null;
		 Date endDate=null;
		 try {
			currentDate=formatter.parse(startDate);
			endDate=formatter.parse(startDate);
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(Duration==7){
			JSONArray storeDay=new JSONArray();
			for (int day=0;day<7;day++)
			{
			
				currentDate.setDate(currentDate.getDate()+day);
				
				storeDay.put(formatter.format(currentDate).toString());
				currentDate.setDate(currentDate.getDate()-day);
			}
			storeDate.put("days", storeDay);
			
			
			
		}
		else if(Duration==4){
			
			JSONArray storeWeek=new JSONArray();
			JSONArray endWeek=new JSONArray();
			
			for (int week=0;week<28;week=week+7)
			{
				System.out.println(currentDate+"  "+week);
				currentDate.setDate(currentDate.getDate()+week);
				System.out.println(currentDate);
				storeWeek.put(formatter.format(currentDate).toString());
				currentDate.setDate(currentDate.getDate()-week);
				
				endDate.setDate(endDate.getDate()+week+6);
				endWeek.put(formatter.format(endDate).toString());
				endDate.setDate(endDate.getDate()-(week+6));
				
				
			}
			storeDate.put("startWeek", storeWeek);
			storeDate.put("endWeek", endWeek);
		}
		else if(Duration==3){
			JSONArray startMonth=new JSONArray();
			JSONArray endMonth=new JSONArray();
			
			for (int month=0;month<3;month++)
			{
				
				try {
					currentDate=formatter.parse(startDate);
					endDate=formatter.parse(startDate);
					
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				currentDate.setMonth(currentDate.getMonth()+month);
				System.out.println(currentDate+"  "+formatter.format(currentDate));
				startMonth.put(formatter.format(currentDate).toString());
				/*currentDate.setMonth(currentDate.getMonth()-month);*/
				
				//System.out.println("EndDate Before"+endDate);
				endDate.setMonth(endDate.getMonth()+month+1);
				//System.out.println("EndDate After"+endDate);
				endDate.setDate(endDate.getDate()-1);
				
				endMonth.put(formatter.format(endDate).toString());
				/*endDate.setMonth(endDate.getMonth()-(month+1));
				System.out.println("Final "+endDate);
				endDate.setDate(endDate.getDate()+1);
				System.out.println("Final"+endDate);*/
				
			}
			
			storeDate.put("startMonth", startMonth);
			storeDate.put("endMonth", endMonth);
		}
		 
		 
		System.out.println("%%%%%%%%%%"+storeDate);
		return storeDate;
	}
	
	
	public List getAllPeriodDisplay(String selectedDate,String periodFormat) throws SQLException {

		 ArrayList<String> listPeriod=new ArrayList<String>();
		 try{
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		  SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		  String dateInString = selectedDate;

		   
		      if(periodFormat.contains("7")){
		    	  for(int countPeriod=0;countPeriod<7;countPeriod++)
		    	  {
		    	
		    		   Date date = formatter.parse(dateInString);       	
		    		  date.setDate(date.getDate()+countPeriod);
		    	
		    		  listPeriod.add("DAY "+(countPeriod+1)+"<br> "+format.format(date));
		    	  }  
		    		 
		      }
		      else if(periodFormat.contains("4")){
		    	  for(int countPeriod=0;countPeriod<28;countPeriod=countPeriod+7){
		    	              		 
		    		  Date startDate=formatter.parse(dateInString);   Date endDate=formatter.parse(dateInString); 
		    		  startDate.setDate(startDate.getDate()+countPeriod);
		    		  endDate.setDate(endDate.getDate()+countPeriod+6);
		    		  System.out.println(startDate +"   "+countPeriod);
		    		  System.out.println(endDate +"   "+countPeriod+6);
		    		  listPeriod.add("WEEK "+(countPeriod/7+1)+"<br> "+format.format(startDate)+" - "+format.format(endDate));
		   		  
		    	  }
		    		 
		    	  
		      }
		      else{
		    	  for(int countPeriod=0;countPeriod<3;countPeriod++){
		    		  Date startDate=formatter.parse(dateInString);   Date endDate=formatter.parse(dateInString); 
		    		  startDate.setMonth(startDate.getMonth()+countPeriod);
		    		  endDate.setMonth(endDate.getMonth()+countPeriod+1);
		    		  endDate.setDate(endDate.getDate()-1);
		    		  System.out.println(startDate +"   "+countPeriod);
		    		  System.out.println(endDate +"   "+countPeriod+1);
		    		  listPeriod.add("Month "+(countPeriod+1)+"<br> "+format.format(startDate)+" - "+format.format(endDate));
			  
		    	  }
		    		
		      }

		}

		catch(Exception ee){
		 ee.printStackTrace();
		}


		return listPeriod;
		}
	
	
	
}
