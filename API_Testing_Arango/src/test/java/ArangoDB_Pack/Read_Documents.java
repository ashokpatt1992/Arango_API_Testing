package ArangoDB_Pack;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class Read_Documents {
   public static ExtentTest EX_Rep;
   public static ExtentReports report;
	
	@Test
	public static void MyGETRequest() throws IOException {
		
		report = new ExtentReports("ExtentReportResults.html");
		EX_Rep = report.startTest("Read Documents API Test");
		report.addSystemInfo("User Name","Ashok.A");
		
		File file = new File("./File_Properties/Data_File.xlsx");   //creating a new file instance  
		FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
		
		//creating Workbook instance that refers to .xlsx file  
		XSSFWorkbook wb = new XSSFWorkbook(fis);  
		XSSFSheet sheet = wb.getSheetAt(0);

		
		//Get Excel Value
		String Read_Doc_API_URL=sheet.getRow(1).getCell(2).getStringCellValue();
		
	           String query_url = Read_Doc_API_URL;
	           String json = sheet.getRow(1).getCell(4).getStringCellValue();
	           
	          System.out.println(json);
	           try {
	           URL url = new URL(query_url);
	           HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	          
	           conn.setConnectTimeout(5000);
	           conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	           conn.setDoOutput(true);
	           conn.setDoInput(true);
	          
	           String Read_Doc_API_Method=sheet.getRow(1).getCell(3).getStringCellValue();
	           System.out.println(Read_Doc_API_Method);
	           
	           conn.setRequestMethod("POST");
	           conn.connect();
	           OutputStream os = conn.getOutputStream();
	           os.write(json.getBytes("UTF-8"));
	           os.close(); 
	           
	           // read the response
	           InputStream in = new BufferedInputStream(conn.getInputStream());
	           String result = IOUtils.toString(in, "UTF-8");
	          
	           //Get Status Code
	           int statusCode = conn.getResponseCode();
	           System.out.println(statusCode);
	           
	           String  Fin_Result= result.toString();
	           org.json.JSONObject Test_Val = new org.json.JSONObject(Fin_Result);
	           
	         //  System.out.println(Test_Val);
	           Test_Val.getJSONArray("result").getJSONObject(0);

	            if (statusCode==200){
	            System.out.println(Test_Val.getJSONArray("result"));
	            EX_Rep.log(LogStatus.PASS,"Verified status code"+statusCode+"-"+Test_Val);

	            }else {
	            	EX_Rep.log(LogStatus.FAIL,"Check Read Documents API");
	            }
	           
	          
	           in.close();
	           conn.disconnect();
	           
	           report.endTest(EX_Rep);
	           report.flush();
	           } catch (Exception e) {
	   			System.out.println(e);
	   		}
		
		
	
		
		
	}

	

}
