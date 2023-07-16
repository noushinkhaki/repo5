package net.betvictor.loripsum.service;

import net.betvictor.loripsum.setting.LoripsumSetting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class LoremIpsumFetcher {

    private static final Logger LOGGER = LogManager.getLogger(TextAnalyserService.class);

    private static final String REQUEST_METHOD = "GET";

    @Autowired
    LoripsumSetting loripsumSetting;

    public String getText(int p, String l) {

        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(loripsumSetting.getUrl() + p + "/" + l);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(REQUEST_METHOD);
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                LOGGER.info(response.toString());
            } else {
                LOGGER.error(responseCode);
            }
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }
}
