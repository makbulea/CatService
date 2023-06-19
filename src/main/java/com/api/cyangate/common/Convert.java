package com.api.cyangate.common;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Convert {

    public static HashMap<String, List<String>> hashMap = new HashMap<>();
    public static int count=0;

    public static HashMap<String, List<String>> toHashInDirectoryPath(String directoryPath) {
        File directoryFile = new File(directoryPath);
        Arrays.stream(directoryFile.listFiles()).forEach(m -> {
            if (m.isDirectory()) {
                toHashInDirectoryPath(m.getAbsolutePath());
            } else {
                if (!hashMap.containsKey(m.getParent())) {
                    List<String> list = new ArrayList<>();
                    list.add(m.getName());
                    hashMap.put(m.getParent(), list);
                    count++;
                } else {
                    List<String> list = hashMap.get(m.getParent());
                    list.add(m.getName());
                    hashMap.put(m.getParent(), list);
                    count++;
                }
            }
        });
        return hashMap;
    }
}
