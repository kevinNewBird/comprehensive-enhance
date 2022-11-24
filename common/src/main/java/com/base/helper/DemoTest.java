package com.base.helper;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DemoTest {

    public static void main(String[] args) {

        Optional<List<String>> optional = Optional.of(Stream.of("name", "name2").collect(Collectors.toList()));
        System.out.println(optional.filter(list->list.stream().filter(name->name.equalsIgnoreCase("name2")).findAny().isPresent()).isPresent());
        String colType = "char(10)";
        String pattern = "char($1)";
        String replacementPattern = "varchar($1)";
        String nColType = colType.replaceAll(pattern, colType);
        System.out.println(nColType);
//        if (colType.replaceAll(pattern,)) {
//            System.out.println(true);
//        }
//        xxx();
    }

    private static void xxx() {
        String sql = "return CURRENT_TIMESTAMP();";
        String key = "CURRENT_TIMESTAMP()";
        String replacement = "REPALCE";

        if (Character.isUpperCase(key.charAt(0))) {
            key = "\\b" + key;
        }

        if (Character.isUpperCase(key.charAt(key.length() - 1))) {
            key += "\\b";
        }
        key = "(?i)(\\s*)" + key.replaceAll("\\s+", "\\\\s+") + "(\\s*)";
        System.out.println("final regex: " + key);
        String fReplacementValue = sql.replaceAll(key, "$1" + replacement + "$2");
        System.out.println("final result: " + fReplacementValue);
    }
}
