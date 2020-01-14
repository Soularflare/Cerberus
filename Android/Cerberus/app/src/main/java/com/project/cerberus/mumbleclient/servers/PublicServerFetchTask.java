package com.project.cerberus.mumbleclient.servers;

import android.os.AsyncTask;
import android.util.Xml;
import ch.boye.httpclientandroidlib.cookie.ClientCookie;
import ch.boye.httpclientandroidlib.protocol.HTTP;
import com.project.cerberus.jumble.Constants;
import com.project.cerberus.mumbleclient.db.PlumbleSQLiteDatabase;
import com.project.cerberus.mumbleclient.db.PublicServer;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

class PublicServerFetchTask extends AsyncTask<Void, Void, List<PublicServer>> {
    private static final String MUMBLE_PUBLIC_URL = "http://mumble.info/list2.cgi";

    PublicServerFetchTask() {
    }

    protected List<PublicServer> doInBackground(Void... params) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(MUMBLE_PUBLIC_URL).openConnection();
            connection.setRequestMethod("GET");
            connection.addRequestProperty(ClientCookie.VERSION_ATTR, Constants.PROTOCOL_STRING);
            connection.connect();
            InputStream stream = connection.getInputStream();
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", false);
            parser.setInput(stream, HTTP.UTF_8);
            parser.nextTag();
            List<PublicServer> arrayList = new ArrayList();
            parser.require(2, null, "servers");
            while (parser.next() != 3) {
                if (parser.getEventType() == 2) {
                    arrayList.add(readEntry(parser));
                }
            }
            parser.require(3, null, "servers");
            return arrayList;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        } catch (ProtocolException e2) {
            e2.printStackTrace();
            return null;
        } catch (MalformedURLException e3) {
            e3.printStackTrace();
            return null;
        } catch (IOException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    private PublicServer readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        String name = parser.getAttributeValue(null, PlumbleSQLiteDatabase.SERVER_NAME);
        String ca = parser.getAttributeValue(null, "ca");
        String continentCode = parser.getAttributeValue(null, "continent_code");
        String country = parser.getAttributeValue(null, "country");
        String countryCode = parser.getAttributeValue(null, "country_code");
        String ip = parser.getAttributeValue(null, "ip");
        String port = parser.getAttributeValue(null, "port");
        String region = parser.getAttributeValue(null, "region");
        String url = parser.getAttributeValue(null, "url");
        parser.nextTag();
        return new PublicServer(name, ca, continentCode, country, countryCode, ip, Integer.valueOf(Integer.parseInt(port)), region, url);
    }
}
