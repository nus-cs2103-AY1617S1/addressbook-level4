# A0139915Wreused
###### \java\seedu\savvytasker\commons\util\StringUtil.java
``` java
    // reused original implementation of 'containsIgnoreCase' to find exact matches
    public static boolean containsExactIgnoreCase(String source, String query) {
        List<String> strings = Arrays.asList(source);
        return strings.stream().filter(s -> s.equalsIgnoreCase(query)).count() > 0;
    }

    // reused original implementation of 'containsIgnoreCase' to find partial matches
    public static boolean containsPartialIgnoreCase(String source, String query) {
        if (source == null) return false;
        String[] split = source.toLowerCase().split("\\s+");
        List<String> strings = Arrays.asList(split);
        return strings.stream().filter(s -> s.contains(query.toLowerCase())).count() > 0;
    }
```
