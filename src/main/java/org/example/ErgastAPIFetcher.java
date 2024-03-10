package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.example.DBObjects.Constructor;
import org.example.DBObjects.Driver;
import org.example.DBObjects.Race;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ErgastAPIFetcher {

    //CONSTRUCTOR
    public ErgastAPIFetcher() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    //Fill f1 constructors founds from the API in the Database
    public void fillConstructors() {

        //Build the http request with the endpoint URL
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ERGAST_API_CONSTRUCTOR_JSON_ENDPOINT)).GET().build();

        try (Session session = new ORMSession().getSession()) {

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) { //Check 1 to see if the http response is okay

                String responseBody = response.body();
                JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);
                JsonArray constructorsJson = jsonObject
                        .getAsJsonObject(ERGAST_API_JSON_HEADER)
                        .getAsJsonObject(ERGAST_API_CONSTRUCTOR_JSON_TABLE_NAME)
                        .getAsJsonArray(ERGAST_API_CONSTRUCTOR_JSON_ARRAY_NAME);

                for (JsonElement element : constructorsJson) {

                    JsonObject constructorJson = element.getAsJsonObject();
                    String name = constructorJson.get(ERGAST_API_CONSTRUCTOR_JSON_NAME_ATTRIBUTE_NAME).getAsString();
                    String nationality = constructorJson.get(ERGAST_API_CONSTRUCTOR_JSON_NATIONALITY_ATTRIBUTE_NAME).getAsString();

                    if(element.isJsonObject()) { //Check 2 to ensure that each element in the array is indeed a JsonObject
                        Constructor constructor = new Constructor(name, nationality);
                        session.save(constructor);
                    }

                    //Start the transaction here
                    Transaction tx = session.beginTransaction();
                    tx.commit(); //Commit the transaction
                }
            } else {
                System.err.println("Failed to fetch constructors. HTTP error code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error fetching constructors: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public void fillDrivers() {

        //Build the http request with the endpoint URL
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ERGAST_API_DRIVER_JSON_ENDPOINT)).GET().build();

        try (Session session = new ORMSession().getSession()) {

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) { //Check 1 to see if the http response is okay

                String responseBody = response.body();
                JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);
                JsonArray driversJson = jsonObject
                        .getAsJsonObject(ERGAST_API_JSON_HEADER)
                        .getAsJsonObject(ERGAST_API_DRIVER_JSON_TABLE_NAME)
                        .getAsJsonArray(ERGAST_API_DRIVER_JSON_ARRAY_NAME);


                for (JsonElement element : driversJson) {

                    JsonObject driverJson = element.getAsJsonObject();
                    String firstName = driverJson.get(ERGAST_API_DRIVER_JSON_FIRSTNAME_ATTRIBUTE_NAME).getAsString();
                    String lastName = driverJson.get(ERGAST_API_DRIVER_JSON_LASTNAME_ATTRIBUTE_NAME).getAsString();
                    String nationality = driverJson.get(ERGAST_API_DRIVER_JSON_NATIONALITY_ATTRIBUTE_NAME).getAsString();

                    if (element.isJsonObject()) {
                        Driver driver = new Driver(lastName, firstName, nationality);
                        session.save(driver);
                    }
                }

                Transaction tx = session.beginTransaction();
                tx.commit();
            } else {
                System.err.println("Failed to fetch drivers. HTTP error code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error fetching drivers: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public void fillRaces() {

        //Build the http request with the endpoint URL
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ERGAST_API_RACE_JSON_ENDPOINT)).GET().build();

        try (Session session = new ORMSession().getSession()) {

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) { //Check 1 to see if the http response is okay

                String responseBody = response.body();
                JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);
                JsonArray racesJson = jsonObject
                        .getAsJsonObject(ERGAST_API_JSON_HEADER)
                        .getAsJsonObject(ERGAST_API_RACE_JSON_TABLE_NAME)
                        .getAsJsonArray(ERGAST_API_RACE_JSON_ARRAY_NAME);


                for (JsonElement element : racesJson) {

                    JsonObject raceJson = element.getAsJsonObject();
                    JsonObject raceLocationJson = raceJson.getAsJsonObject(ERGAST_API_RACE_LOCATION_JSON_ARRAY_NAME);
                    String city = raceLocationJson.get(ERGAST_API_RACE_JSON_CITY_ATTRIBUTE_NAME).getAsString();
                    String country = raceLocationJson.get(ERGAST_API_RACE_JSON_COUNTRY_ATTRIBUTE_NAME).getAsString();

                    if (element.isJsonObject()) {
                        Race race = new Race(city, country);
                        session.save(race);
                    }
                }

                Transaction tx = session.beginTransaction();
                tx.commit();
            } else {
                System.err.println("Failed to fetch drivers. HTTP error code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error fetching drivers: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    //PRIVATE CONSTANTS

    private final String ERGAST_API_JSON_HEADER = "MRData";

    private final String ERGAST_API_CONSTRUCTOR_JSON_ENDPOINT = "http://ergast.com/api/f1/constructors.json";
    private final String ERGAST_API_CONSTRUCTOR_JSON_TABLE_NAME = "ConstructorTable";
    private final String ERGAST_API_CONSTRUCTOR_JSON_ARRAY_NAME = "Constructors";
    private final String ERGAST_API_CONSTRUCTOR_JSON_NAME_ATTRIBUTE_NAME = "name";
    private final String ERGAST_API_CONSTRUCTOR_JSON_NATIONALITY_ATTRIBUTE_NAME = "nationality";

    private final String ERGAST_API_DRIVER_JSON_ENDPOINT = "http://ergast.com/api/f1/drivers.json";
    private final String ERGAST_API_DRIVER_JSON_TABLE_NAME = "DriverTable";
    private final String ERGAST_API_DRIVER_JSON_ARRAY_NAME = "Drivers";
    private final String ERGAST_API_DRIVER_JSON_FIRSTNAME_ATTRIBUTE_NAME = "givenName";
    private final String ERGAST_API_DRIVER_JSON_LASTNAME_ATTRIBUTE_NAME = "familyName";
    private final String ERGAST_API_DRIVER_JSON_NATIONALITY_ATTRIBUTE_NAME = "nationality";

    private final String ERGAST_API_RACE_JSON_ENDPOINT = "http://ergast.com/api/f1/circuits.json";
    private final String ERGAST_API_RACE_JSON_TABLE_NAME = "CircuitTable";
    private final String ERGAST_API_RACE_JSON_ARRAY_NAME = "Circuits";
    private final String ERGAST_API_RACE_LOCATION_JSON_ARRAY_NAME = "Location";
    private final String ERGAST_API_RACE_JSON_CITY_ATTRIBUTE_NAME = "locality";
    private final String ERGAST_API_RACE_JSON_COUNTRY_ATTRIBUTE_NAME = "country";

    //PRIVATE CONSTANTS ATTRIBUTES
    private final HttpClient    client;
    private final Gson          gson;
}