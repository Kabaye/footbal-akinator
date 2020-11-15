package edu.bsu.footbal.akinator.utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

/**
 * created by @Kabaye
 * date 15.11.2020
 */
public class ResourceLoader {
    public static Image getImage(final String pathAndFileName) {
        final URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);
        return Toolkit.getDefaultToolkit().getImage(url);
    }
}