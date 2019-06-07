package com.reachcorp.reach.nerdetecthon.service.utils;

import com.reachcorp.reach.nerdetecthon.dto.entities.insight.*;
import com.reachcorp.reach.nerdetecthon.dto.source.SimpleRawData;
import com.reachcorp.reach.nerdetecthon.dto.source.ner.Entity;
import com.reachcorp.reach.nerdetecthon.dto.source.ner.NerJsonObjectResponse;
import com.reachcorp.reach.nerdetecthon.dto.source.ner.Term;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NerResponseHandler {

    private final NerJsonObjectResponse nerResponse;
    private final SimpleRawData simpleRawData;

    private List<InsightEntity> insightEntities;
    private RawData rawData;

    public NerResponseHandler(final NerJsonObjectResponse nerResponse, final SimpleRawData simple) {
        this.nerResponse = nerResponse;
        this.simpleRawData = simple;
        this.rawData = buildRawDataDto();
        this.insightEntities = buildResponseEntities();
    }

    private RawData buildRawDataDto() {
        final RawData dto = new RawData();
        dto.setRawDataName(this.simpleRawData.getSourceName());
        dto.setRawDataCreationDate(Instant.now());
        dto.setRawDataType("OSINT");
        dto.setRawDataSubType(this.simpleRawData.getSourceType());
        dto.setRawDataSourceType(this.simpleRawData.getSourceType());
        dto.setRawDataSourceUri(this.simpleRawData.getSourceUrl());
        dto.setRawDataContent(this.simpleRawData.getText());
        if (this.nerResponse != null) {
            // dto.setRawDataAnnotations(this.nerResponse.getContent());
            dto.setRawDataDataContentType(this.nerResponse.getLanguage());
        }
        return dto;
    }

    //methode pour construire une liste d'InsightEntity a partir de la reponse de NER
    private List<InsightEntity> buildResponseEntities() {
        if (this.nerResponse == null || this.nerResponse.getEntities() == null)
            return new ArrayList<>();
        return extractInsightEntites(this.nerResponse);
    }

    //methode pour extraire une liste d'InsightEntity a partir de la reponse de NER
    public static List<InsightEntity> extractInsightEntites(final NerJsonObjectResponse response) {

        //Recuperation des entités de la Reponse de NER avec parfois(souvent) les memes entités
        List<InsightEntity> insightEntitiesAvecDoublons = response.getEntities().values().stream()
                .map(dto -> mapToInsightEntityDto(dto, response)).filter(dto -> dto != null)
                .collect(Collectors.toList());

        //liste de string pour mettre les InsightEntity.ToString
        final List<String> insightEntitiesString=new ArrayList<>();
        //liste d'InsightEntity sans doublons
        final List<InsightEntity> insightEntitiesSansDoublons =new ArrayList<>();

        //algo pour dedoublonner la liste d'entités qui arrive de NER
        //les InsightEntity n'ont pas d'id, on peut pas utiliser la method equals des objets, ni donc le contains des listes d'objets
        //on va donc passer par le toString qui fait une String avec tous les attributs de l'objet, on considere que
        //deux entity sont différentes si leur toString est différent
        for (InsightEntity entity:insightEntitiesAvecDoublons
                ) {

            if (!insightEntitiesString.contains(entity.toString()))
            {
                //si l'entity.toString n'est pas dans la liste des InsightEntity.ToString
                //ce n'est pas un doublon, on l'ajoute dans la liste des InsightEntity.ToString
                insightEntitiesString.add(entity.toString());
                //et donc aussi dans la liste des InsightEntity sans doublons
                insightEntitiesSansDoublons.add(entity);
            }
        }
        //on renvoi la liste des InsightEntity sans doublons
        return insightEntitiesSansDoublons;
    }

    private static InsightEntity mapToInsightEntityDto(Entity entity, final NerJsonObjectResponse nerResponse) {
        switch (entity.getType()) {
            case LOCATION:
            case LOC:
                final Location location = new Location();
                location.setLocationName(entity.getText());

                setEntitiesPositions(location, entity, nerResponse);

                return location;
            case ORGANIZATION:
            case ORG:
                final Organisation organisation = new Organisation();
                organisation.setOrganisationName(entity.getText());

                setEntitiesPositions(organisation, entity, nerResponse);

                return organisation;
            case PERSON:
            case PER:
                final Biographics biographics = new Biographics();
                biographics.setBiographicsName(entity.getText());
                biographics.setBiographicsFirstname(" ");

                setEntitiesPositions(biographics, entity, nerResponse);

                return biographics;
            default:
                return null;
        }
    }

    private static void setEntitiesPositions(final InsightEntity insEntity, final Entity entity, final NerJsonObjectResponse nerResponse) {
        List<Integer> positions = new ArrayList<>();
        List<String> terms = entity.getTerms();
        for (String id : terms) {
            Term term = nerResponse.getTerms().get(id);
            if (term!=null)
            {
                positions.add(term.getOffset());
            }
        }
        insEntity.setTextPositionInfo(positions);
    }

    public List<InsightEntity> getInsightEntities() {
        return insightEntities;
    }

    public RawData getRawData() {
        return rawData;
    }
}
