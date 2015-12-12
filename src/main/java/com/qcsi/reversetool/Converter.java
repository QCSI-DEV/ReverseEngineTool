package com.qcsi.reversetool;
import java.util.HashMap;

public class Converter {
    private static HashMap<String, String> typesMappings = new HashMap<>();
    static {
        typesMappings.put("bool", "boolean");
        typesMappings.put("\"char\"", "byte");
        typesMappings.put("integer", "int");
        typesMappings.put("int2", "short");
        typesMappings.put("int4", "int");
        typesMappings.put("int8", "long");
        typesMappings.put("float4", "float");
        typesMappings.put("float8", "double");
        typesMappings.put("char", "String");
        typesMappings.put("varchar", "String");
        typesMappings.put("text", "String");
        typesMappings.put("name", "String");
        typesMappings.put("bytea", "byte{}");
        typesMappings.put("date", "java.sql.Date");
        typesMappings.put("time", "java.sql.Time");
        typesMappings.put("timez", "java.sql.Time");
        typesMappings.put("timestamp", "java.sql.Timestamp");
        typesMappings.put("timestampz", "java.sql.Timestamp");

        typesMappings.put("bool[]", "boolean[]");
        typesMappings.put("\"char\"[]", "byte[]");
        typesMappings.put("int2[]", "short[]");
        typesMappings.put("int4[]", "int[]");
        typesMappings.put("int8[]", "long []");
        typesMappings.put("float4[]", "float[]");
        typesMappings.put("float8[]", "double[]");
        typesMappings.put("char[]", "java.lang.String[]");
        typesMappings.put("varchar[]", "java.lang.String[]");
        typesMappings.put("text[]", "java.lang.String[]");
        typesMappings.put("name[]", "java.lang.String[]");
        typesMappings.put("bytea[]", "byte[][]");
        typesMappings.put("date[]", "java.sql.Date[]");
        typesMappings.put("time[]", "java.sql.Time[]");
        typesMappings.put("timetz[]", "java.sql.Time[]");
        typesMappings.put("timestamp[]", "java.sql.Timestamp[]");
        typesMappings.put("timestamptz[]", "java.sql.Timestamp[]");

        typesMappings.put("\"any\"", "java.lang.Object");
        typesMappings.put("anyelement", "java.lang.Object");
        typesMappings.put("anyarray", "java.lang.Object[]");
        typesMappings.put("cstring", "java.lang.String");
        typesMappings.put("record", "java.sql.ResultSet");
    }

    public static String toJavaType(String dbType){
        return typesMappings.get(dbType);
    }

    public static String toCamelCase(String name){
        String pascalCaseName = toPascalCase(name);
        return Character.toLowerCase(pascalCaseName.charAt(0)) +
                pascalCaseName.substring(1);
    }

    public static String toPascalCase(String name){
        int start = 0;
        StringBuilder parsedName = new StringBuilder();
        boolean isIteratingWord = false;
        for (int i = 0; i < name.length(); i++){
            if (Character.isLetter(name.charAt(i))){
                if (!isIteratingWord){
                    start = i;
                    isIteratingWord = true;
                }
            } else {
                parsedName.append(Character.toUpperCase(name.charAt(start)) +
                        name.substring(start + 1, i));
                isIteratingWord = false;
            }
        }
        parsedName.append(Character.toUpperCase(name.charAt(start)) +
                name.substring(start + 1, name.length()));
        return parsedName.toString();
    }
}
