package pl.norbit.exampleplugin;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LicenseHandler {
    private final String licenseKey;
    private final String host;
    private final String token;
    private final Gson gson;
    private final boolean isValid;
    private String serverKey;

    public LicenseHandler(String licenseKey, String host, String token) {
        this.licenseKey = licenseKey;
        this.host = host;
        this.token = token;
        this.gson  = new Gson();

        this.isValid = this.generateServerKey();
    }

    public boolean isValid(){
        return this.isValid;
    }

    private boolean generateServerKey(){
        String json = request("generateServerKey/" + licenseKey, null).body();

        if (json == null) return false;

        License license = this.gson.fromJson(json, License.class);

        this.serverKey = license.serverKey();
        return true;
    }

    public boolean isValidServerKey(){
        License license = new License(licenseKey, serverKey);

        return this.request("isValidServerKey", this.gson.toJson(license)).success();
    }

private ApiResponse request(String endpoint, String jsonBody) {
    String apiUrl = host + endpoint;

    try {
        var url = new URL(apiUrl);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", token);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        if(jsonBody != null){
            conn.setRequestMethod("PUT");
            OutputStream outputStream = conn.getOutputStream();
            byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);
        }

        var inputStreamReader = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
        var bufferedReader = new BufferedReader(inputStreamReader);

        StringBuilder response = new StringBuilder();

        String responseLine;

        while ((responseLine = bufferedReader.readLine()) != null) {
            response.append(responseLine.trim());
        }

        if(conn.getResponseCode() != 200){
            return new ApiResponse(false, null);
        }

        return new ApiResponse(true, response.toString());

    } catch (Exception e) {
        e.printStackTrace();
    }
    return new ApiResponse(false, null);
}
}
