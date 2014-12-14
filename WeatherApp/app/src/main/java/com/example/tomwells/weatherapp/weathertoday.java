package com.example.tomwells.weatherapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.json.JSONException;
import java.util.Calendar;


public class weathertoday extends Activity {

    private TextView cityText;
    private TextView condDescr;
    private TextView temp;
    private TextView windSpeed;

    RelativeLayout hLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weathertoday);

        hLayout = (RelativeLayout)findViewById(R.id.weathertoday);

        hLayout.setBackgroundResource(R.drawable.weather_night);

        int Hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        switch(Hour){
            case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7: case 22: case 23: case 24:
                hLayout.setBackgroundResource(R.drawable.weather_night);
                break;
            case 8: case 9: case 10: case 11: case 12: case 13: case 14: case 15: case 16: case 17:
                hLayout.setBackgroundResource(R.drawable.weather_daysunny);
                break;
            case 18: case 19: case 20: case 21:
                hLayout.setBackgroundResource(R.drawable.weather_sunset);
                break;
        }


        Bundle gotbasket = getIntent().getExtras();

        String location = gotbasket.getString("location");


        String city = location +",UK";

        cityText = (TextView) findViewById(R.id.cityText);
        condDescr = (TextView) findViewById(R.id.condDescr);
        temp = (TextView) findViewById(R.id.temp);
        windSpeed = (TextView) findViewById(R.id.windSpeed);

        cityText.setText(gotbasket.getString("location"));
        JSONWeatherTask task = new JSONWeatherTask();
        task.execute(new String[]{city});
    }

    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            Weather weather = new Weather();
            String data = ((new WeatherHttpClient()).getWeatherData(params[0]));

            try {
                weather = JSONWeatherParser.getWeather(data);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;

        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            ImageView img = (ImageView) findViewById(R.id.imageView);
            String Description = weather.currentCondition.getCondition();
            condDescr.setText(Description + "(" + weather.currentCondition.getDescr() + ")");
            temp.setText("" + Math.round((weather.temperature.getTemp() - 273.15)) + (char) 0x00B0 +"C");
            windSpeed.setText("Wind Speed: " + weather.wind.getSpeed() + " mps");


            if(Description.equals("Clear")){
                int Hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

                switch(Hour){
                    case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7: case 22: case 23: case 24:
                        img.setImageResource(R.drawable.moon);
                        break;
                    case 8: case 9: case 10: case 11: case 12: case 13: case 14: case 15: case 16: case 17:  case 18: case 19: case 20: case 21:
                        img.setImageResource(R.drawable.sunny);
                        break;
                }
            }else if(Description.equals("Clouds")){
                img.setImageResource(R.drawable.clouds);
            }else if(Description.equals("Rain")){
                img.setImageResource(R.drawable.rain);
            }else if(Description.equals("Snow")){
                img.setImageResource(R.drawable.snow);
            }else if(Description.equals("sleet")){
                img.setImageResource(R.drawable.sleet);
            }else if(Description.equals("Sun")){
                img.setImageResource(R.drawable.sunny);
            }

        }
    }
}
