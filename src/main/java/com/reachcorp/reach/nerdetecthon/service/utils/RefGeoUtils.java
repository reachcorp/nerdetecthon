package com.reachcorp.reach.nerdetecthon.service.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.reachcorp.reach.nerdetecthon.service.NerService;

/**
 * Created by cpoullot on 21/01/2019.
 * classe pour interroger le reférentiel géographique via ElasticSearchService
 * et recuperer les coordonnées
 */
public class RefGeoUtils {
    private final static Logger log = LoggerFactory.getLogger(RefGeoUtils.class);

    /*   methode pour interroger le reférentiel géographique via ElasticSearchService
     et recuperer les coordonnées de locationName*/
    public static String getRefGeoCoordinates(String locationName, String urlgeotrouvethon) throws IOException {
        log.info("Getting coordinates for " + locationName);
        String coordinates = null;

        try {
            URL url = new URL(urlgeotrouvethon+"/locate/paris");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String response = br.readLine();

            coordinates = response.substring(1, response.length() - 1);

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return coordinates;
    }
}
