package edu.csustan.cs4950.weatherclient;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Task extends AsyncTask<String, Void, String> implements Configuration { //button,<zip>,<degree:String,weather info:string
    private TextView lblMsg;


    private static final String PROTOCOL = "http://";
    private static final String IP = "35.247.31.46";
    private static final String PORT = "8080";

    public Task(TextView lblMsg) {
        this.lblMsg = lblMsg;
    }

    private String getRestURL() {
        String url = PROTOCOL + IP + ":" + PORT + "/weather";
        Log.i("Task", url);
        return url;
    }

    private String getRestURL(String zip) {
        String url = PROTOCOL + IP + ":" + PORT + "/weather/" + zip;
        Log.i("Task", url);
        return url;
    }

    private String getRestURL(int degree) {
        String url = PROTOCOL + IP + ":" + PORT + "/weather/" +
                "warmerthan?degree=" + degree;
        Log.i("Task", url);
        return url;
    }

    private String getData(String restURL) {
        String ret = "";
        try {
            // get weather JSON data
            URL url = new URL(restURL);
            URLConnection urlConn = url.openConnection();
            InputStream inputStream = urlConn.getInputStream();
            BufferedReader in = new BufferedReader
                    (new InputStreamReader(inputStream));
            String line = in.readLine();
            in.close();

            // parse JSON data and show parsed data
            JSONObject obj = new JSONObject(line);
            JSONArray weather = obj.getJSONArray("weather");
            for (int i = 0; i < weather.length(); i++) {
                JSONObject entry = weather.getJSONObject(i);
                ret += "zip: " + entry.getInt("zip") + ", ";
                ret += "degree: " + entry.getInt("degree") + ", ";
                ret += "city: " + entry.getString("city") + ", ";
                ret += "state: " + entry.getString("state") + "\n";
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return ret;
    }

    private String getWeatherAll() {
        return getData(getRestURL());
    }

    private String getWeatherByCity(String zip) {
        return getData(getRestURL(zip));
    }

    private String getWeatherByWarmerThan(int degree) {
        return getData(getRestURL(degree));
    }


    @Override
    protected String doInBackground(String... params) {
        String btnType = params[0];
        String result;

        if (btnType.equals(BUTTON_ALL)) {
            result = getWeatherAll();
        } else if (btnType.equals(BUTTON_CITY)) {
            String zip = params[1];
            result = getWeatherByCity(zip);
        } else if (btnType.equals(BUTTON_WARMER_THAN)) {
            Integer degree = Integer.parseInt(params[2]);
            result = getWeatherByWarmerThan(degree);
        } else {
            result = "none of buttons clicked";
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        lblMsg.setText(result);
    }




}

