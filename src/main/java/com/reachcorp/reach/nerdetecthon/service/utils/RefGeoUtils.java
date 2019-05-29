package com.reachcorp.reach.nerdetecthon.service.utils;

import com.reachcorp.reach.nerdetecthon.dto.source.elasticearch.EsResponse;
import com.reachcorp.reach.nerdetecthon.dto.source.elasticearch.EsSource;
import com.reachcorp.reach.nerdetecthon.service.ElasticSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.io.IOException;
import java.util.List;

/**
 * Created by cpoullot on 21/01/2019.
 * classe pour interroger le reférentiel géographique via ElasticSearchService
 * et recuperer les coordonnées
 */
public class RefGeoUtils {
    private final static Logger log = LoggerFactory.getLogger(RefGeoUtils.class);

    /*   methode pour interroger le reférentiel géographique via ElasticSearchService
     et recuperer les coordonnées de locationName*/
    public static String getRefGeoCoordinates(String locationName) throws IOException {
        log.info("Getting coordinates for " + locationName);
        String coordinates = null;

        try {
            //TODO : variabilisé ca !!!
            URL url = new URL("http://192.168.0.30:9966/locate/paris");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String response = br.readLine();

            coordinates = response.substring(1, response.length() -1);

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return coordinates;
    }
}
