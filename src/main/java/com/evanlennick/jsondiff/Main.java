package com.evanlennick.jsondiff;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class Main {

    public static void main(String[] args) throws IOException {
        String json1 = getFileContents("aim-dev.json");
        String json2 = getFileContents("aim-dev-d0.json");
        printDiff(json1, json2);
        printDiff(json2, json1);
    }

    public static void printDiff(String json1, String json2) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode from = objectMapper.readTree(json1);
        JsonNode to = objectMapper.readTree(json2);
        JsonNode patch = JsonDiff.asJson(from, to);

        System.out.println(patch);
    }

    public static String getFileContents(String filename) throws IOException {
        InputStream resourceAsStream = Main.class.getClassLoader().getResourceAsStream(filename);
        String contents = IOUtils.toString(resourceAsStream, "UTF-8");
        IOUtils.closeQuietly(resourceAsStream);
        return contents;
    }

}
