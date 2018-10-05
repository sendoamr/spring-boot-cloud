package com.sendoa.opendata.model.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.sendoa.opendata.model.Model;

import java.io.IOException;

public class ModelDeserializer extends JsonDeserializer<Model> {

    @Override
    public Model deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);
        final String code = node.get("code").asText();
        final String description = node.get("notes_translated").get("en").asText();
        final String url = node.get("url_tornada").get("en").asText();
        final String organization = node.get("organization").get("description").asText();

        return new Model(code, description, url, organization);
    }
}
