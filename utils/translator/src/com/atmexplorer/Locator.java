package com.atmexplorer;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import sun.net.www.protocol.https.HttpsURLConnectionImpl;

import com.google.gson.stream.JsonReader;

public class Locator {
    private final static String USER_AGENT = "Mozilla/5.0";

    private Map<Integer, String> mCities = new HashMap<Integer, String>();
    
    public void fillLocations(CityProvider cityProvider) throws IOException {
//        mCities = cities;

        XSSFWorkbook workbook = null;
        FileInputStream file = new FileInputStream(new File(Constants.IN_ATMS_FILE_NAME_LOCATION));

        // Create Workbook instance holding reference to .xlsx file
        workbook = new XSSFWorkbook(file);

        // Get first/desired sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);

        // Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();

        int counter = 0;

        while (rowIterator.hasNext()) {
            counter++;
            System.out.println(counter);
            Row row = rowIterator.next();
            if (counter >= 2188) {

                // get location
                Integer cityId = Double.valueOf(row.getCell(2).getNumericCellValue()).intValue();
                String city = mCities.get(cityId);
                String data = null;
                
                if (counter <= 2871) {
                    mCities = cityProvider.getCitiesRu();
                    data = row.getCell(3).getStringCellValue();
                }else {
                    mCities = cityProvider.getCitiesUa();
                    data = row.getCell(4).getStringCellValue();
                }
                Pair<Double, Double> coordinates = getLocation(city + ", " + data);
                // Location
                Cell cellLocationLat = row.createCell(15);
                Cell cellLocationLong = row.createCell(16);

                if (coordinates != null) {
                    cellLocationLat.setCellValue(coordinates.getFirst() == null ? 0 : coordinates.getFirst());
                    cellLocationLong.setCellValue(coordinates.getSecond() == null ? 0 : coordinates.getSecond());
                }
            }

        }

        file.close();
        // Write the workbook in file system
        FileOutputStream out = new FileOutputStream(new File(Constants.OUT_ATMS_FILE_NAME_LOCATION));
        workbook.write(out);
        out.close();

    }
    
    
    private Pair<Double, Double> getLocation(String address) throws IOException {
//      HttpClient client = new DefaultHttpClient();
//      HttpPost post = new HttpPost("https://maps.googleapis.com/maps/api/geocode/json?address=%D0%9A%D0%B8%D0%B5%D0%B2,%20%D0%BF%D1%80%D0%BE%D1%81%D0%BF%D0%B5%D0%BA%D1%82%20%D0%91%D0%B0%D0%B6%D0%B0%D0%BD%D0%B0%205");
//      post.setHeader("Content-type", "application/json");
      
      String addReplaced = address.replace(" ", "%20");
      
      String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + addReplaced;
      URL obj = new URL(url);
      HttpsURLConnectionImpl con = (HttpsURLConnectionImpl) obj.openConnection();
      
     //add request header
      con.setRequestMethod("POST");
      con.setRequestProperty("User-Agent", USER_AGENT);
      con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
      con.setDoOutput(true);
      
      DataOutputStream wr = new DataOutputStream(con.getOutputStream());
      wr.flush();
      wr.close();
      
     
      JsonReader reader = new JsonReader(new InputStreamReader(con.getInputStream()));

      reader.beginObject();
      while (reader.hasNext()) {
          return getCoordinates(reader);
      }
      reader.endObject();
      return null;
  }
  
  private Pair<Double, Double> getCoordinates(JsonReader reader)  throws IOException {
      Pair<Double, Double> coordinates = new Pair<Double, Double>();  
      try {
          read(reader, coordinates);
      }catch(Exception e) {
          
      }
      return coordinates;
    }
  
  private void read(JsonReader reader, Pair<Double, Double> coordinates) throws IOException {
      while (reader.hasNext()) {
          String name = reader.nextName();
          if (name.equals("results")) {

              reader.beginArray();
              reader.beginObject();

              while (reader.hasNext()) {
                  read(reader, coordinates);
              }
              reader.endObject();
              reader.endArray();
          } else if (name.equals("geometry") || name.equals("location")) {
              reader.beginObject();
              read(reader, coordinates);
              reader.endObject();
          } else if (name.equals("lat")) {
              Double value = reader.nextDouble();
              coordinates.setFirst(value);
              System.out.println("lat " + value);
          } else if (name.equals("lng")) {
              Double value = reader.nextDouble();
              coordinates.setSecond(value);
              System.out.println("lng " + value);
          } else {
              reader.skipValue();
          }
      }
  }
}
