package com.gymapp.shared;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class JsonTestUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void assertJsonIgnoringFields(String expectedJson, String actualJson, List<String> fieldsToIgnore) throws Exception {
        JsonNode expectedNode = mapper.readTree(expectedJson);
        JsonNode actualNode = mapper.readTree(actualJson);

        removeFieldsRecursively(expectedNode, fieldsToIgnore);
        removeFieldsRecursively(actualNode, fieldsToIgnore);

        Assertions.assertEquals(expectedNode, actualNode);
    }

    private static void removeFieldsRecursively(JsonNode node, List<String> fieldsToIgnore) {
        if (node.isObject()) {
            ObjectNode objNode = (ObjectNode) node;
            fieldsToIgnore.forEach(objNode::remove);
            objNode.fields().forEachRemaining(entry -> removeFieldsRecursively(entry.getValue(), fieldsToIgnore));
        } else if (node.isArray()) {
            node.forEach(child -> removeFieldsRecursively(child, fieldsToIgnore));
        }
    }
}
