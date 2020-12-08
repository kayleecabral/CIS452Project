//  Establish a connection to a mSQL database using JDBC
import java.sql.*; 
import java. util. Scanner;


public class Connector { 

  public static void main (String[] args) { 
	  Scanner input = new Scanner(System. in);
	  
	  System.out.println("If you'd like to add a new accident enter 1: ");
	  System.out.println("If you'd like to see the details of an accident enter 2: ");
	  System.out.println("If you'd like to see the details of an accident based off a range enter 3: ");
	  int ans = input.nextInt();
	  
	  if(ans == 1){
		  addAccident();
	  }else if (ans == 2){
		  System.out.println("Enter the aid number of the accident you'd like to look up: ");
		   int aid = input.nextInt();
		  getAccident(aid);
	  }else if (ans == 3) {
	  	 accidentRange();
	  }
	  

	  input.close(); 
  }
    
  
  public static void addAccident() {
	 try
	    {
	      // Step 1: "Load" the JDBC driver
	      Class.forName("com.mysql.jdbc.Driver"); 

	      // Step 2: Establish the connection to the database 
	      String url = "jdbc:mysql://localhost:3306/autosDB"; 
	      Connection conn = DriverManager.getConnection(url,"root","Kaylee99!");  
	      
	      
	      //create new scanner
	      Scanner input = new Scanner(System. in);
	      
	      //ask user for data for accidents table
	      System.out.println("Enter the aid number: ");
		  int aid = input.nextInt();
		  
	      System.out.println("Enter date of accident in year-month-date format: ");
		  String date = input.next();
		  
		  System.out.println("Enter the city of the accident: ");
		  String city = input.next();
		  
		  System.out.println("Enter the state of the accident: ");
		  String state = input.next();
		 

	      //ask user for data for involvements table
		  System.out.println("Enter the vin number of the car: ");
		  String vin = input.next();
		  
		  System.out.println("Enter the ssn number of the driver: ");
		  String ssn = input.next();
		  
		  System.out.println("Enter the amount of damages: ");
		  int damages = input.nextInt();
		  
		  
		  //mysql insert statement for accidents table
		  String acc = "INSERT INTO accidents (aid, accident_date, city, state)" + "VALUES (?, ?, ?, ?)";
		  
	      //mysql insert statement for involvements table
	      String involve = "INSERT INTO involvements (aid, vin, damages, driver_ssn)" + "VALUES (?, ?, ?, ?)";
	  

	      // mysql insert preparedstatement for involvements
	      PreparedStatement inv = conn.prepareStatement(involve);
	      inv.setInt (1, aid);
	      inv.setString (2, vin);
	      inv.setInt   (3, damages);
	      inv.setString(4, ssn);
	     
	      // mysql insert preparedstatement for accidents 
	      PreparedStatement ac = conn.prepareStatement(acc);
	      ac.setInt (1, aid);
	      ac.setString (2, date);
	      ac.setString   (3, city);
	      ac.setString(4, state);
	     

	      // execute the prepared statement
	      inv.execute(); 
	      ac.execute();
	      
	      //close connection
	      conn.close(); 
	      
	      //close scanner
	      input.close();
	      
	      //confirmation statement 
	      System.out.println("You've successfully entered a new accident.");
	      
	      }catch(Exception e){ 
	    	  System.out.println(e);
	   }  
   }
 
   public static void getAccident(int aid) {
		
		try
	    {
		  
     	 //mysql insert statement for accidents table
 		String acc = "SELECT * FROM accidents WHERE aid=" + aid;
			   
		//mysql insert statement for involvements table
		 String inv = "SELECT * FROM involvements WHERE aid=" + aid;	
			
	      // Step 1: "Load" the JDBC driver
	      Class.forName("com.mysql.jdbc.Driver"); 

	      // Step 2: Establish the connection to the database 
	      String url = "jdbc:mysql://localhost:3306/autosDB"; 
	      Connection conn = DriverManager.getConnection(url,"root","Kaylee99!");  
	     
	      // create the java statement
	      Statement st = conn.createStatement();
	      
	      // execute the query, and get a java resultset
	      ResultSet rs = st.executeQuery(acc);
	      
	      
	   // iterate through the java resultset
	      while (rs.next())
	      {
	        int a = rs.getInt("aid");
	        String ad = rs.getString("accident_date");
	        String c = rs.getString("city");
	        String s = rs.getString("state");
	      
	        // print the results
	        System.out.format("%s, %s, %s, %s\n", a, ad, c, s);
	      }	  
	      
	     // execute the query, and get a java resultset
	      ResultSet is = st.executeQuery(inv);
	      
	      while(is.next()){
	    	  int d = is.getInt("aid");
		      String v = is.getString("vin");
		      int dm = is.getInt("damages");
		      String ssn = is.getString("driver_ssn");
		        
		      System.out.format("%s, %s, %s, %s\n", d, v, dm, ssn);
	      }
	      //close connection
	      conn.close(); 
	      
	      //close statement
	      st.close();
	      
	      
	      }catch(Exception e){ 
	    	  System.out.println(e);} 
	}
   
   public static void accidentRange() {
	   //create new scanner
	   Scanner input = new Scanner(System. in);
	   
	   //Ask user for values 
	   System.out.println("If you'd like to search given a range of dates enter 1:");
	   System.out.println("If you'd like to search given a range of total damages enter 2:");
 	   int y = input.nextInt();
 		
 		if(y == 1) {
 			System.out.println("enter upper range: ");
 			String upperA = input.next();
 			
 			System.out.println("enter lower range: ");
 			String lowerA = input.next();
 			
 			searchAccidentsbyDates(upperA, lowerA);
 			
 			
 		}if(y == 2) {
  			System.out.println("enter upper range: ");
  			int upperD = input.nextInt();
  			
  			System.out.println("enter lower range: ");
  			int lowerD = input.nextInt();
  			
  			searchAccidentsbyDamages(upperD, lowerD);
  		}
  		
  		input.close();

   }
   
   public static void searchAccidentsbyDates(String upper, String lower) {
	   try
	    {
		  
    	 //mysql insert statement for accidents table
		String acc = "SELECT * FROM accidents WHERE accident_date BETWEEN " + lower + " AND " + upper;
			   
			
	      // Step 1: "Load" the JDBC driver
	      Class.forName("com.mysql.jdbc.Driver"); 

	      // Step 2: Establish the connection to the database 
	      String url = "jdbc:mysql://localhost:3306/autosDB"; 
	      Connection conn = DriverManager.getConnection(url,"root","Kaylee99!");  
	     
	      // create the java statement
	      Statement st = conn.createStatement();
	      
	      // execute the query, and get a java resultset
	      ResultSet rs = st.executeQuery(acc);
	      
	      
	   // iterate through the java resultset
	      while (rs.next())
	      {
	        int a = rs.getInt("aid");
	        String ad = rs.getString("accident_date");
	        String c = rs.getString("city");
	        String s = rs.getString("state");
	      
	        // print the results
	        System.out.format("%s, %s, %s, %s\n", a, ad, c, s);
	      }	  
	 
	      //close connection
	      conn.close(); 
	      
	      //close statement
	      st.close();

	      
	      
	      }catch(Exception e){ 
	    	  System.out.println(e);} 
	}
  		 
   
   public static void searchAccidentsbyDamages(int upper, int lower) {
	   try
	    {
		  
   	 //mysql insert statement for accidents table
		String inv = "SELECT * FROM involvements WHERE damages BETWEEN " + lower + " AND " + upper;
			   
			
	      // Step 1: "Load" the JDBC driver
	      Class.forName("com.mysql.jdbc.Driver"); 

	      // Step 2: Establish the connection to the database 
	      String url = "jdbc:mysql://localhost:3306/autosDB"; 
	      Connection conn = DriverManager.getConnection(url,"root","Kaylee99!");  
	     
	      // create the java statement
	      Statement st = conn.createStatement();
	      
	      // execute the query, and get a java resultset
	      ResultSet is = st.executeQuery(inv);
	      
	      while(is.next()){
	    	  int d = is.getInt("aid");
		        
		      getAccident(d);
	    	  
	      }  
	 
	      //close connection
	      conn.close(); 
	      
	      //close statement
	      st.close();

	      
	      
	      }catch(Exception e){ 
	    	  System.out.println(e);} 		
   }
   
  }  

