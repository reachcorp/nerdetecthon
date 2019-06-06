package com.reachcorp.reach.nerdetecthon;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reachcorp.reach.nerdetecthon.dto.entities.Identifiers;
import com.reachcorp.reach.nerdetecthon.dto.entities.insight.Biographics;
import com.reachcorp.reach.nerdetecthon.dto.entities.insight.InsightEntity;
import com.reachcorp.reach.nerdetecthon.dto.entities.insight.Location;
import com.reachcorp.reach.nerdetecthon.dto.entities.insight.RawData;
import com.reachcorp.reach.nerdetecthon.dto.source.SimpleRawData;
import com.reachcorp.reach.nerdetecthon.dto.source.ner.NerJsonObjectResponse;
import com.reachcorp.reach.nerdetecthon.dto.source.rss.RssSourceMessage;
import com.reachcorp.reach.nerdetecthon.dto.source.twitter.TwitterSourceMessage;
import com.reachcorp.reach.nerdetecthon.dto.source.twitter.NerDetecthonSourceMessage;
import com.reachcorp.reach.nerdetecthon.service.InsightService;
import com.reachcorp.reach.nerdetecthon.service.NerService;
import com.reachcorp.reach.nerdetecthon.service.utils.NerResponseHandler;
import com.reachcorp.reach.nerdetecthon.service.utils.RefGeoUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NERdetecthonApplicationTests {

    private final Logger log = LoggerFactory.getLogger(NERdetecthonApplicationTests.class);

    @Value("${urlgeotrouvethon}")
    private String urlgeotrouvethon;

    @Autowired
    private NerService nerService;

    @Autowired
    private InsightService insightClientService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void nerJsonObjectResponseTest() throws IOException {
        final Logger log = LoggerFactory.getLogger(NERdetecthonApplicationTests.class);
        ObjectMapper mapper = new ObjectMapper();
        final InputStream resourceAsStream = NERdetecthonApplicationTests.class.getResourceAsStream("/bidou.json");
        final NerJsonObjectResponse nerJsonObjectResponse = mapper.readValue(resourceAsStream, NerJsonObjectResponse.class);
        assertThat(nerJsonObjectResponse.getText()).isNotEmpty();
        log.info(nerJsonObjectResponse.getText());
        assertThat(nerJsonObjectResponse.getTerms().get("t1") != null).isTrue();
    }

    @Test
    public void twitterSourceMessageTest() throws IOException {
        final Logger log = LoggerFactory.getLogger(NERdetecthonApplicationTests.class);
        ObjectMapper mapper = new ObjectMapper();
        final InputStream resourceAsStream = NERdetecthonApplicationTests.class.getResourceAsStream("/sample_twitter.json");
        final TwitterSourceMessage twitterSourceMessage = mapper.readValue(resourceAsStream, TwitterSourceMessage.class);
        assertThat(twitterSourceMessage.getText()).isNotEmpty();
        log.info(twitterSourceMessage.getText());
        assertThat(twitterSourceMessage.getCreatedAt()).isNotNull();
    }

    @Test
    public void NerDetecthonSourceMessageTest() throws IOException {
        final Logger log = LoggerFactory.getLogger(NERdetecthonApplicationTests.class);
        ObjectMapper mapper = new ObjectMapper();
        final InputStream resourceAsStream = NERdetecthonApplicationTests.class.getResourceAsStream("/sample_nerdetecton_withlocation.json");
        final NerDetecthonSourceMessage nerDetecthonSourceMessage = mapper.readValue(resourceAsStream, NerDetecthonSourceMessage.class);
        final TwitterSourceMessage twitterSourceMessage =nerDetecthonSourceMessage.getTweet();
        String tweetCoordinates = this.nerService.getLocationFromTweet(twitterSourceMessage);
        final SimpleRawData simpleRawData = SimpleRawData.fromTwitterSourceMessage(twitterSourceMessage, tweetCoordinates);
        final NerJsonObjectResponse nerJsonObjectResponse = this.nerService.submitNerRequest(simpleRawData);
        final String content = nerJsonObjectResponse.getContent();
        Assert.assertNotNull(content);
        this.log.info(content);
        final List<InsightEntity> objects = NerResponseHandler.extractInsightEntites(nerJsonObjectResponse);
        for (final InsightEntity object : objects) {
            this.log.info(object.toString());
            System.out.println(object.toString());
            if (object instanceof Location)
            {
                Location location= (Location)object;
                String coordinates = RefGeoUtils.getRefGeoCoordinates(location.getLocationName(), urlgeotrouvethon);
                System.out.println(coordinates);
            }
        }
    }

    @Test
    public void NerDetecthonSourceMessageInsightTest() throws IOException {
        final Logger log = LoggerFactory.getLogger(NERdetecthonApplicationTests.class);
        ObjectMapper mapper = new ObjectMapper();
        final InputStream resourceAsStream = NERdetecthonApplicationTests.class.getResourceAsStream("/sample_nerdetecton_withlocation.json");
        final NerDetecthonSourceMessage nerDetecthonSourceMessage = mapper.readValue(resourceAsStream, NerDetecthonSourceMessage.class);
        try {
            this.nerService.doSend(nerDetecthonSourceMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void twitterMessageToNerTest() throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final InputStream resourceAsStream = NERdetecthonApplicationTests.class.getResourceAsStream("/sample_twitter.json");
        final TwitterSourceMessage twitterSourceMessage = mapper.readValue(resourceAsStream, TwitterSourceMessage.class);
        final SimpleRawData simpleRawData = SimpleRawData.fromTwitterSourceMessage(twitterSourceMessage);
        final NerJsonObjectResponse nerJsonObjectResponse = this.nerService.submitNerRequest(simpleRawData);
        final String content = nerJsonObjectResponse.getContent();
        Assert.assertNotNull(content);
        this.log.info(content);
        final List<InsightEntity> objects = NerResponseHandler.extractInsightEntites(nerJsonObjectResponse);
        for (final InsightEntity object : objects) {
            this.log.info(object.toString());
        }
    }

    @Test
    public void rssMessageToNerToInsightTest() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        final InputStream resourceAsStream = NERdetecthonApplicationTests.class.getResourceAsStream("/sample.json");
        final RssSourceMessage mess = mapper.readValue(resourceAsStream, RssSourceMessage.class);
        this.nerService.doSend(mess);
    }

    @Test
    public void insightPostmanTest() throws IOException {
        final RawData rawData = new RawData();
        rawData.setRawDataName("test");
        rawData.setRawDataContent("test");
        rawData.setRawDataCreationDate(Instant.now());
        final Biographics biographics = new Biographics();
        biographics.setBiographicsFirstname("testFirstName");
        biographics.setBiographicsName("testName");
        final Identifiers rawDataIds = this.insightClientService.create(rawData);
        final Identifiers bioIds = this.insightClientService.create(biographics);
        Assert.assertNotNull(rawDataIds);
        Assert.assertNotNull(bioIds);
        final Identifiers rawDataNewIds = this.insightClientService.create(rawData);
        final Identifiers bioNewIds = this.insightClientService.create(biographics);
        Assert.assertNotNull(rawDataNewIds);
        Assert.assertNotNull(bioNewIds);
    }

    @Test
    public void graphPostTest() throws IOException {
        final RawData rawData = new RawData();
        rawData.setRawDataContent("test");
        rawData.setRawDataCreationDate(Instant.now());
        this.insightClientService.create(rawData);
    }

    @Test
    public void getCoordinateFromLocation() throws IOException {
        String coordinates = RefGeoUtils.getRefGeoCoordinates("Paris", urlgeotrouvethon);
        Assert.assertEquals("48.8566101,2.3514992", coordinates);
        coordinates = RefGeoUtils.getRefGeoCoordinates("Palais de l'élysée", urlgeotrouvethon);
        Assert.assertEquals("48.87037435,2.3160687345508", coordinates);
        coordinates = RefGeoUtils.getRefGeoCoordinates("zzzzzzzzzzz", urlgeotrouvethon);
        Assert.assertEquals("-99,99", coordinates);
    }
}
