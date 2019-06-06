package com.reachcorp.reach.nerdetecthon.dto.source;

import com.reachcorp.reach.nerdetecthon.dto.source.rawtext.RawTextMessage;
import com.reachcorp.reach.nerdetecthon.dto.source.rss.RssSourceMessage;
import com.reachcorp.reach.nerdetecthon.dto.source.scrapy.ScrapyResult;
import com.reachcorp.reach.nerdetecthon.dto.source.twitter.TwitterSourceMessage;
import com.reachcorp.reach.nerdetecthon.service.NerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;


public class SimpleRawData {

    @Autowired
    private NerService nerService;

    private String sourceUrl;
    private String sourceName;
    private String sourceType;
    private String text;
    private String coordinates;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceType() {
        return sourceType;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public static SimpleRawData fromTwitterSourceMessage(final TwitterSourceMessage twitterSourceMessage) {
        return fromTwitterSourceMessage(twitterSourceMessage, null);
    }

    public static SimpleRawData fromTwitterSourceMessage(final TwitterSourceMessage twitterSourceMessage, String tweetCoordinates) {
        final SimpleRawData simpleRawData = new SimpleRawData();
        simpleRawData.setSourceName(twitterSourceMessage.getUser().getScreenName());
        simpleRawData.setSourceType("TWITTER");
        simpleRawData.setSourceUrl(twitterSourceMessage.getSource());
        simpleRawData.setText(twitterSourceMessage.getText());
        // Coordonnées valides, Geotrouvethon n'a pas renvoyé -99,99
        if (tweetCoordinates != null && !tweetCoordinates.equals("-99,99")) {
            simpleRawData.setCoordinates(tweetCoordinates);
        }

        return simpleRawData;
    }

    public static SimpleRawData fromScrapyResult(final ScrapyResult scrapyResult)
    {
        final SimpleRawData simpleRawData = new SimpleRawData();
        simpleRawData.setSourceUrl(scrapyResult.getUrl());
        simpleRawData.setText(scrapyResult.getContent());
        simpleRawData.setSourceName(scrapyResult.getDomainFromUrl());
        simpleRawData.setSourceType("GOOGLESEARCH");
        return simpleRawData;
    }

    public static SimpleRawData fromRssSourceMessage(final RssSourceMessage rssSourceMessage) {
        final SimpleRawData simpleRawData = new SimpleRawData();
        simpleRawData.setSourceName(rssSourceMessage.getChannel().getTitle());
        simpleRawData.setSourceType("RSS");
        try {
            if (rssSourceMessage.getChannel().getLink() instanceof ArrayList) {
                simpleRawData.setSourceUrl(((ArrayList) rssSourceMessage.getChannel().getLink()).get(0).toString());
            }
            if (rssSourceMessage.getChannel().getLink() instanceof String) {
                simpleRawData.setSourceUrl((String) rssSourceMessage.getChannel().getLink());
            }
        } catch (Exception e) {
            // nothing
        }
        return simpleRawData;
    }

    public static SimpleRawData fromRawTextSourceMessage(final RawTextMessage rawTextSourceMessage) {
        final SimpleRawData simpleRawData = new SimpleRawData();
        simpleRawData.setSourceName(rawTextSourceMessage.getTitle());
        simpleRawData.setSourceType("RAWTEXT");
        simpleRawData.setText(rawTextSourceMessage.getRawText());
        return simpleRawData;
    }

}
