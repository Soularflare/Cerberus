package com.project.cerberus.jumble.util;

import com.project.cerberus.jumble.Constants;
import com.project.cerberus.jumble.model.Server;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MumbleURLParser {
    private static final Pattern URL_PATTERN = Pattern.compile("mumble://((.+?)(:(.+?))?@)?(.+?)(:([0-9]+?))?/");

    public static Server parseURL(String url) throws MalformedURLException {
        Matcher matcher = URL_PATTERN.matcher(url);
        if (matcher.find()) {
            String username = matcher.group(2);
            String password = matcher.group(4);
            String host = matcher.group(5);
            String portString = matcher.group(7);
            return new Server(-1, null, host, portString == null ? Constants.DEFAULT_PORT : Integer.parseInt(portString), username, password);
        }
        throw new MalformedURLException();
    }
}
