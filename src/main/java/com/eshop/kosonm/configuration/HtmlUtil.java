package com.eshop.kosonm.configuration;

import org.springframework.web.util.HtmlUtils;

public class HtmlUtil {
    /**
     * Verify if a string contains any HTML characters by comparing its
     * HTML-escaped version with the original.
     * @param String input  the input String
     * @return boolean  True if the String contains HTML characters
     */
    public static boolean isHtml(String input) {
        boolean isHtml = false;
        if (input != null) {
            if (!input.equals(HtmlUtils.htmlEscape(input))) {
                isHtml = true;
            }
        }
        return isHtml;
    }
}