package org.example;

import lombok.Builder;
import lombok.Data;
import org.example.DBObjects.Constructor;
import org.example.DBObjects.Driver;
import org.example.DBObjects.Teamed;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class F1Scaper {

    //PRIVATE ATTRIBUTES
    private static ORMSession session;

    /*
    TODO scraper ces sites
https://fr.wikipedia.org/wiki/Palmar%C3%A8s_du_championnat_du_monde_de_Formule_1
https://www.formula1.com/en/results.html/2021/drivers.html
     */

    //PRIVATE CONSTANTS

    //region Formula1
    private static final String URL = "https://www.formula1.com/en/results.html/%s/drivers.html";
    private static final String TABLE_DIV = "div.resultsarchive-wrapper table.resultsarchive-table tbody tr";
    //endregion

    //region Wikipedia
    private static final String URL_WIKI = "https://fr.wikipedia.org/wiki/Palmar%C3%A8s_du_championnat_du_monde_de_Formule_1";
    private static final String TABLE_WIKI = "//*[@id='mw-content-text']/div[1]/table[1]";

    private static final String SOURCE_SCRAPPER = "Scrapper";

    //PUBLIC INTERFACE
    public static void init()
    {
        session = new ORMSession(SOURCE_SCRAPPER);
    }

    //endregion

    /**
     * Scrape the drivers from the Formula1 website
     * @param fromYear the year to start scraping from (min 1950)
     * @param toYear the year to stop scraping at (max 2024)
     */
    public static void scrapeDrivers(int fromYear, int toYear) {
        if (fromYear < 1950 || toYear > 2024) {
            throw new IllegalArgumentException("Invalid year range");
        }
        try {
            Map<String, List<Driver>> driversCurrentYear = new HashMap<>();
            for (int year = fromYear; year < toYear; year++) {
                Document doc = Jsoup.connect(String.format(URL, year)).get();
                Elements rows = doc.select(TABLE_DIV);
                for (Element row : rows) {
                    String position = row.select("td").get(1).text().trim();

                    // Identify firstname and lastname based on the info in the html > some names or firstnames are composed
                    String fullName = row.select("td").get(2).text();
                    String[] split = fullName.split(" ");
                    String shortenedName = split[split.length - 1];
                    String firstName = "", lastName = "";
                    String searchString = fullName.substring(0, fullName.indexOf(shortenedName));
                    Pattern pattern = Pattern.compile("(?i) "+shortenedName+"(.+)");
                    Matcher matcher = pattern.matcher(searchString);
                    if (matcher.find()) {
                        lastName = matcher.group(0).trim();
                        firstName = searchString.substring(0, searchString.indexOf(lastName)).trim();
                    }
                    if (firstName.isEmpty() || lastName.isEmpty()) continue;


                    String nationality = row.select("td").get(3).text();
                    String team = row.select("td").get(4).text();
                    String points = row.select("td").get(5).text();

                    //This is the part that adds it in the database
                    Driver driver = new Driver(lastName, firstName, nationality);
                    session.controlAndSave(driver);
                    driversCurrentYear.merge(
                            team,
                            List.of(driver),
                            (existing, replacement) -> {
                                List<Driver> newList = new ArrayList<>(existing);
                                newList.addAll(replacement);
                                return newList;
                            }
                    );

                    //

                    //print all
                    System.out.println(position + " | " + firstName + " | "+ lastName +" | " + nationality + " | " + team + " | " + points);
                }
                Thread.sleep(5000);
                System.out.println("-------------------");
                //Filling the teamed table
                for (Map.Entry<String, List<Driver>> entry : driversCurrentYear.entrySet()) {
                    List<Driver> drivers = entry.getValue();
//                    int idConstructor = session.getIdConstructor(entry.getKey());
                    Constructor constructor = session.getConstructor(entry.getKey());
                    if (constructor != null ) {
                        drivers.forEach(driver -> {
                            int idDriver = session.getIdDriver(driver.getFirstnameDriver(), driver.getLastnameDriver());
                            if (idDriver != -1) {
                                driver.setIdDriver(idDriver);
                                System.out.println("Saving team: " + entry.getKey() + " " + driver.getFirstnameDriver() + " id:" + idDriver + " " +
                                        driver.getLastnameDriver() + " " + entry.getKey() + " " + year);
//                                Teamed teamed = new Teamed(idDriver, idConstructor, String.valueOf(year)); //old version of the model
//                                session.controlAndSaveTeamed(teamed);
                                Teamed teamed = new Teamed(driver, constructor, String.valueOf(year)); //old version of the model
                                session.controlAndSave(teamed);
                            }
                        });
                    } else {
                        System.out.println("Error trying to save team: " + entry.getKey() + " " + drivers.size());
                    }
                }
                break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void scrapeWiki() {
        try {
            Document doc = Jsoup.connect(URL_WIKI).get();
            Elements rows = doc.selectXpath(TABLE_WIKI);
            Elements res = rows.select("table.wikitable tbody tr");
            for (int i = 0; i < res.size(); i++) {
                if (i == 0) continue; //header row

                Element row = res.get(i);
                Elements columns = row.select("td");

                if (columns.size() > 1) { // S'assurer que ce n'est pas une ligne d'en-tête
                    String annee = columns.get(0).text();
                    String vainqueur = columns.get(1).text();
                    String vainqueurPays = columns.get(1).select("a").attr("title").replace("Drapeau : ", "");
                    String automobile = columns.get(2).select("a").text();

                    String pneus = columns.get(3).text();
                    String deuxieme = columns.get(4).text();
                    String deuxiemePays = columns.get(4).select("a").attr("title").replace("Drapeau : ", "");
                    String troisieme = columns.get(5).text();
                    String troisiemePays = columns.get(5).select("a").attr("title").replace("Drapeau : ", "");
                    String constructeur = columns.size() > 6 ? columns.get(6).text() : "";
                    String moteur = columns.size() > 7 ? columns.get(7).text() : "";

                    System.out.println("Année: " + annee + ", Vainqueur: " + vainqueur + " (" + vainqueurPays + ")"
                            + ", Automobile: " + automobile + ", Pneus: " + pneus
                            + ", Deuxième: " + deuxieme + " (" + deuxiemePays + ")"
                            + ", Troisième: " + troisieme + " (" + troisiemePays + ")"
                            + ", Constructeur: " + constructeur + ", Moteur: " + moteur);
                    // pas assez d'info pour Earned, manque des informations pour la race et les points par personne

                }
//                break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Builder
    @Data
    static class DriverAndPoints {
        private Driver driver;
        private int points;
    }

}

