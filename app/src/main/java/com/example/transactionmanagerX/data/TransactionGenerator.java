package com.example.transactionmanagerX.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.lang.Math;

public class TransactionGenerator {
    private static Random random = new Random();
    private static DateMath dateMath = new DateMath();
    private List<String> payees = Arrays.asList("Aimco","Air Methods","American Furniture Warehouse","American Medical Response","Antero Resources","Arrow Electronics","Arrowhead Mills","Aspen Skiing Company","Avery Brewing Company","Ball Corporation","Best Cellular","Boston Market","Boulder Brands","Celestial Seasonings","CH2M Hill","Cherwell Software","Chocolove","City Market","Colorado Interstate Gas","Coors Brewing Company","CraftWorks Restaurants & Breweries","Crocs","CSG International","Datavail","DaVita Medical Group","Denver Rock Island Railroad","Digital First Media","DigitalGlobe","Dish Network","Dynamic Materials Corporation","eBags","EchoStar","Einstein Bros. Bagels","Estes Industries","Exede","Executive Recycling","Elevations Credit Union","FreeWave Technologies","Frontier Airlines","Gaia, Inc","Gates Corporation","Golden Software","Gray Line Worldwide","Great Divide Brewing Company","Great Lakes Aircraft Company","Ibotta","JBS USA","Jeppesen","Key Lime Air","King Soopers","Kong Company","Kroenke Sports & Entertainment","LaMar's Donuts","Leopold Bros.","Level 3 Communications","Liberty Global","Liberty Media","Liberty Skis","Loaf 'N Jug","Love Grown Foods","Matchstick Productions","MDC Holdings","Molson Coors Brewing Company","Museum Store Company","name.com","National CineMedia","Navis Logistics Network","Never Summer","New Belgium Brewing Company","Newmont Mining Corporation","Noodles & Company","Novus Biologicals","Odell Brewing Company","OtterBox","PCL Construction","Peach Street Distillers","Pearl Izumi","PostNet","Quark","Quiznos","RE/MAX","Recondo Technology","Red Robin","Rocky Mountain Chocolate Factory","Saga Petroleum LLC","SBR Creative Media","SCI Fidelity Records","Smashburger","Software Bisque","SpotX","Spyder","Spyderco","Stranahan's Colorado Whiskey","Thanasi Foods","TransMontaigne","Vail Resorts","Verio","Water Pik, Inc.","Webroot","Western Sugar Cooperative","Western Union","WhiteWave Foods","Wing-Time","Woody's Chicago Style","Worker Studio","Xero Shoes","Zynex","Companies formerly based in Colorado","Aerocar 2000","Big O Tires","Budget Truck Rental","Chipotle Mexican Grill","Ciber","Corporate Express","Cox Models","Dex Media","Discovery Holding Company","Envision Healthcare","Flying Dog Brewery","Janus Capital Group","Jolly Rancher","Lärabar","Level 3 Communications","Magpul Industries","Mushkin","NextMedia Group","Paladin Press","Qdoba Mexican Grill","Range Fuels","Samsonite","Sports Authority","Terra Soft Solutions","Village Inn");
    JSONObject generateTransaction(Long from, Long to, Boolean deposit) {
        JSONObject result=new JSONObject();

        Double temp = new Double(0);
        if(deposit) {
            temp = new Double(Math.abs(random.nextDouble())*400+300);
        }else{
            temp = new Double(-1*Math.abs(random.nextDouble())*100);
        }
        try {
            result.put("date", ((Double)(random.nextDouble() * (to - from) + from)).longValue());
            result.put("amount", temp);
            result.put("payee",payees.get(random.nextInt(payees.size())));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    JSONObject getTransactions(String login, Long last_sync, String account_number, int count){
        JSONObject result = new JSONObject();
        JSONArray transactions = new JSONArray();
        Long to = dateMath.getNow();
        Long from;
        if (last_sync!=0) {
            from=last_sync;
        }else {
            from=dateMath.getSomeDaysAgo(30);
        }
        for(int i =0; i<count; ++i) {
            transactions.put(generateTransaction(from, to, i % 5 == 0));
        }
        try {
            result.put("date",to);
            result.put("account_number",account_number);
            result.put("transactions",transactions);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;

    }
    JSONObject generateAccount(){
        JSONObject account = new JSONObject();

        try {
            account.put("last_sync",new Long(0));

            account.put("number",String.valueOf(random.nextInt(100000))+fillLeft(String.valueOf(random.nextInt(100000)),"0",5));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return account;
    }

    JSONObject getAccounts(String login, String institution, int count)
    {
        JSONObject result = new JSONObject();
        JSONArray accounts = new JSONArray();;
        for(int i=0;i<count;++i){
            accounts.put(generateAccount());
        }
        try {
            result.put("institution",institution);
            result.put("login", login);
            result.put("acconts",accounts);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
    private String fillLeft(String input, String padding, int length){
        String result = new String();
        for(int i=0; i< (length - input.length()); ++i){
            result +=padding;
        }
        return result+input;
    }
}
