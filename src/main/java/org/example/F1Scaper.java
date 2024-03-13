package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class F1Scaper {
    /*
    TODO scraper ces sites
https://fr.wikipedia.org/wiki/Palmar%C3%A8s_du_championnat_du_monde_de_Formule_1
https://www.formula1.com/en/results.html/2021/drivers.html
     */

    //region Formula1
    private final String URL = "https://www.formula1.com/en/results.html/%s/drivers.html";
    private final String TABLE_DIV = "div.resultsarchive-wrapper table.resultsarchive-table tbody tr";
    //endregion

    //region Wikipedia
    private final String URL_WIKI = "https://fr.wikipedia.org/wiki/Palmar%C3%A8s_du_championnat_du_monde_de_Formule_1";
    private final String TABLE_WIKI = "//*[@id='mw-content-text']/div[1]/table[1]";

    //endregion
    public void scrapeDrivers() {
        try {
            for (int year = 1950; year < 1951; year++) {
                Document doc = Jsoup.connect(String.format(URL, year)).get();
                Elements rows = doc.select(TABLE_DIV);
                for (Element row : rows) {
                    String position = row.select("td").get(1).text();
                    String name = row.select("td").get(2).text();
                    String nationality = row.select("td").get(3).text();
                    String team = row.select("td").get(4).text();
                    String points = row.select("td").get(5).text();
                    //print all
                    System.out.println(position + " | " + name + " | " + nationality + " | " + team + " | " + points);
                }
                Thread.sleep(5000);
                System.out.println("-------------------");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void scrapeWiki() {
        try {
            Document doc = Jsoup.connect(URL_WIKI).get();
            Elements rows = doc.selectXpath(TABLE_WIKI);
//            System.out.println(rows);
//            for (Element row : rows) {
//                String position = row.select("td").get(1).text();
//                String name = row.select("td").get(2).text();
//            }
            Elements res = rows.select("table.wikitable tbody tr");
            //pop the first element
            for (int i = 0; i < res.size(); i++) {
                if (i == 0) continue;

                Element row = res.get(i);
                Elements columns = row.select("td");
//                System.out.println(columns);

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
                    String constructeur = columns.size() > 6 ? columns.get(6).text() : ""; // Ajustez selon la structure
                    String moteur = columns.size() > 7 ? columns.get(7).text() : ""; // Ajustez selon la structure

                    System.out.println("Année: " + annee + ", Vainqueur: " + vainqueur + " (" + vainqueurPays + ")"
                            + ", Automobile: " + automobile + ", Pneus: " + pneus
                            + ", Deuxième: " + deuxieme + " (" + deuxiemePays + ")"
                            + ", Troisième: " + troisieme + " (" + troisiemePays + ")"
                            + ", Constructeur: " + constructeur + ", Moteur: " + moteur);

                }
//                break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        F1Scaper f1Scaper = new F1Scaper();
//        f1Scaper.scrapeDrivers();
        f1Scaper.scrapeWiki();
    }

}
