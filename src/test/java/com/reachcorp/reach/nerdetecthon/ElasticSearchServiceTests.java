package com.reachcorp.reach.nerdetecthon;

import com.reachcorp.reach.nerdetecthon.dto.source.elasticearch.EsHit;
import com.reachcorp.reach.nerdetecthon.dto.source.elasticearch.EsResponse;
import com.reachcorp.reach.nerdetecthon.service.ElasticSearchService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticSearchServiceTests {

    private final Logger log = LoggerFactory.getLogger(ElasticSearchServiceTests.class);

    @Autowired
    private ElasticSearchService elasticSearchService;

    @Test
    public void getBiographicsByName() {
        this.log.info("searching by name criteria");
        final EsResponse result = this.elasticSearchService.getByNameCriteria("biographicsName", "Donald Trump", "biographics");
        final EsHit esHit = result.getHits().getHits().stream().findFirst().get();
        Assert.assertNotNull(esHit);
        Assert.assertNotNull(esHit.getSource().getAdditionalProperties().get("externalId"));
        Assert.assertEquals("biographics", esHit.getType());
    }

    @Test
    public void instantTest() {
        this.log.info(Instant.now().toString());
    }
}
