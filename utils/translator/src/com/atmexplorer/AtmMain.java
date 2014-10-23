package com.atmexplorer;



public class AtmMain {
    
    
    
    public static void main(String[] args) {

        AtmMain main = new AtmMain();
        TranslatorAddress transator = new TranslatorAddress();
        Locator locator = new Locator();
        CityProvider cityProvider = new CityProvider();
        
        
        System.out.println(" start proccess.");
        try {
//            cityProvider.translate();
//            cityProvider.readCities();
            
            
            System.out.println("start translate.");
//            transator.translate();
            System.out.println("finished translate.");
            
            System.out.println("start filling locations.");
//            locator.fillLocations(cityProvider);
            System.out.println("finished filling locations.");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("finished proccess.");

    }
}
