package com.adss.demo.xml.util;

import java.io.File;
import java.util.Base64;

import org.apache.commons.io.FileUtils;

public class CommonUtils {
	public static String encodeFileToBase64Binary(String filePath) {
        try {
            File file = new File(filePath);
            return Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
        } catch (Exception e) {
            return null;
        }
    }
}
