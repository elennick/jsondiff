package com.evanlennick.jsondiff;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.flipkart.zjsonpatch.JsonDiff;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            throw new IllegalArgumentException("Requires two arguments!");
        }

        String path1 = args[0];
        String path2 = args[1];

        String json1 = getFileContents(path1);
        String json2 = getFileContents(path2);

        System.out.println("\n-----------------------------------------------------");
        System.out.println("Diff when going from " + path1 + " to " + path2 + "\n");
        printDiff(json1, json2);

        System.out.println("\n-----------------------------------------------------");
        System.out.println("Diff when going from " + path2 + " to " + path1 + "\n");
        printDiff(json2, json1);
    }

    public static void printDiff(String json1, String json2) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        JsonNode from = mapper.readTree(json1);
        JsonNode to = mapper.readTree(json2);
        JsonNode patch = JsonDiff.asJson(from, to);

        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(patch));
    }

    public static String getFileContents(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

}
