package com.example.smo.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        //set progress bar visibility to view
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        ForecastQuery forecastQuery = new ForecastQuery();
        forecastQuery.execute(); //run thread

    } //end onCreate

    protected class ForecastQuery extends AsyncTask<String, Integer, String> {
        String currentTemp, min, max, tagName, iconName, speed;
        Bitmap picture;

        //@Override
        //protected void onPreExecute(){
          //  super.onPreExecute();
       // }

        //for long-lasting computations
        @Override
        protected String doInBackground(String ... args) {
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
                //to connect to HTTP server
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream(); //read response from server

                //set up reader -> pull parser
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(inputStream, "UTF-8");

                //iterate through xml tags
                while (( xpp.getEventType()) != XmlPullParser.END_DOCUMENT) {

                     if(xpp.getEventType() == XmlPullParser.START_TAG){
                        tagName = xpp.getName();
                        //pull data from site

                        if (tagName.equals("temperature")) {
                            currentTemp = xpp.getAttributeValue(null, "value");
                            Thread.sleep(1000);
                            Log.i("value of current temp: ", currentTemp);
                            publishProgress(20);

                            min = xpp.getAttributeValue(null, "min");
                            Thread.sleep(1000);
                            Log.i("min temp: ", min);
                            publishProgress(40);

                            max = xpp.getAttributeValue(null, "max");
                            Thread.sleep(1000);
                            Log.i("max temp: ", max);
                            publishProgress(60);

                        }else if (tagName.equals("wind")) {
                        }else if(tagName.equals("speed")) {
                            speed = xpp.getAttributeValue(null, "value");
                            Thread.sleep(1000);
                            Log.i("the wind speed is: ", speed);
                            publishProgress(70);
                        //}
                        } else if (tagName.equals("weather")) {
                            iconName = xpp.getAttributeValue(null, "icon");
                            Thread.sleep(1000);
                            publishProgress(80);
                            Log.i("icon name: ", iconName);
                        } //end temp/weather if
                    } //end main if
                    xpp.next();
                } //end while

                //download an image -> from link
                class HTTPUtils {
                    public Bitmap getImage(URL url) {
                        HttpURLConnection httpURLConnection = null;
                        try {
                            httpURLConnection = (HttpURLConnection) url.openConnection();
                            httpURLConnection.connect();
                            int responseCode = httpURLConnection.getResponseCode();
                            if (responseCode == 200) {
                                return BitmapFactory.decodeStream(httpURLConnection.getInputStream());
                            } else
                                return null;
                        } catch (Exception e) {
                            return null;
                        } finally {
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                        }
                    } //end getImage()

                    public Bitmap getImage(String urlString) {
                        try {
                            URL url = new URL(urlString);
                            return getImage(url);
                        } catch (MalformedURLException e) {
                            return null;
                        }
                    } //end getImage
                }//end HTTPUtils

               String fileName = iconName + ".png";
                picture = new HTTPUtils().getImage("http://openweathermap.org/img/w/" + fileName);
                FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                picture.compress(Bitmap.CompressFormat.PNG, 80, fileOutputStream);

                fileOutputStream.flush();
                fileOutputStream.close();
                Thread.sleep(1000);
                publishProgress(100);
                //read in the image
                if (fileExistence(fileName)) {  //if it exists don't download, call original one
                    FileInputStream fileInputStream = null;

                    try {
                        fileInputStream = openFileInput(fileName);
                        picture = BitmapFactory.decodeStream(fileInputStream);
                        Log.i("file name is: ", iconName);

                    } catch (FileNotFoundException fnf) {
                        fnf.printStackTrace();
                    }

                } else {//if(fileExistence(fileName)){ //if the image doesn't exist download it
                    Log.i("Notice:","file does not exist, needs to be downloaded.......");
//???????????????????????????????????????????????
                    picture = new HTTPUtils().getImage("http://openweathermap.org/img/w/" + fileName);

                    FileInputStream fileInputStream = null;
                    fileInputStream = openFileInput(fileName );
                    picture = BitmapFactory.decodeStream(fileInputStream);
                    fileInputStream.close();

                    //picture = new HTTPUtils().getImage("http://openweathermap.org/img/w/" + fileName);
                    //store the image***************

                    // URL imageURL = new URL("http://openweathermap.org/img/w/" + fileName);
                    //String iconURL = "http://openweathermap.org/img/w/" + fileName;
                    //picture = getImage(new URL(iconURL));
                    //Bitmap bitmapImage = getImage(imageURL);
                    //FileOutputStream fileOutputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);


                    Thread.sleep(1000);
                    publishProgress(100);

                    // } catch (FileNotFoundException fnf) {
                    //    Log.e("fnf", "cannot find the file");
                    // }

                } //end inner if
            } catch (XmlPullParserException xppe){
                xppe.printStackTrace();
            } catch (MalformedURLException mue) {
                mue.printStackTrace();
            }catch (InterruptedException ie){
                ie.printStackTrace();
            } catch (IOException ioe){
                ioe.printStackTrace();
            }

            return "all done";
        } //end doInBackground

        //check if images are already present in storage directory
        public boolean fileExistence(String fileName) {
            File file = getBaseContext().getFileStreamPath(fileName);
            return file.exists();
        }

        //to update GUI
        public void onProgressUpdate(Integer ... values){
            //set progress bar visibility to view
            ProgressBar progressBar = findViewById(R.id.progressBar);
            progressBar.setProgress(values[0]);
            progressBar.setVisibility(View.VISIBLE);
        }

        //update progress indicators
        public void onPostExecute(String result){
            TextView currentText = findViewById(R.id.currentText);
            currentText.setText("Current temp is " + currentTemp + "°C");

            TextView minText = findViewById(R.id.minText);
            minText.setText("Min temp is " + min + "°C");

            TextView maxText = findViewById(R.id.maxText);
            maxText.setText("Max temp is " + max + "°C");

            TextView windSpeed = findViewById(R.id.windSpeed);
            windSpeed.setText("The wind speed is " + speed + " m/s");

            ImageView weatherImage = findViewById(R.id.weatherImage);
            weatherImage.setImageBitmap(picture);

            ProgressBar progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.INVISIBLE);
        }
    } //end forecastQuery
} //end class
