package com.api.cyangate.common;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Variable {
    public static final Path PUBLIC_DIR = Paths.get(System.getProperty("user.dir"), "images");
    public static final String catExternalClientUrl = "https://cataas.com/cat";
}
