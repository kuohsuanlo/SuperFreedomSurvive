package SuperFreedomSurvive.SYSTEM;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

final public class Http {

    // 網頁訪問
    static final public JSONObject Call(String http) {
        try {
            URL url = new URL(http);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream is = url.openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();

            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }

            is.close();
            return (JSONObject) (new JSONParser()).parse(sb.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
