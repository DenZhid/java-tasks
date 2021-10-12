package ru.mail.polis.homework.processor;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SquashWhiteSpacesProcessor implements TextProcessor {

    private static final ProcessingStage STAGE = ProcessingStage.PREPROCESSING;

    public String processText(String text) {
        Pattern pattern = Pattern.compile("\\s+");
        Matcher matcher = pattern.matcher(text);
        return matcher.replaceAll(" ");
    }

    public ProcessingStage getStage() {
        return STAGE;
    }
}
