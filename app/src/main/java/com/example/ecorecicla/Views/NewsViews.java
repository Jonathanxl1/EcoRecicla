package com.example.ecorecicla.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ecorecicla.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NewsViews extends AppCompatActivity {

    private BottomAppBar bottomAppBar;

    private Drawable[] imagesResources ;
    private ListView newsList;

    private JsonArray arrayObjectNews;

    private Gson gson = new Gson();

    private TextView txvTitleNews,txvDescriptionNews;
    private ImageView imvPortraitNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_views);
        bottomAppBar = findViewById(R.id.bottomAppBar);
        newsList = findViewById(R.id.lvNews);
        Resources res = getResources();
        imagesResources = new Drawable[] {ResourcesCompat.getDrawable(res,R.drawable.canecas,null),
                ResourcesCompat.getDrawable(res,R.drawable.saveplanet,null),
                ResourcesCompat.getDrawable(res,R.drawable.forest,null)};


        InputStream inputStream = getResources().openRawResource(R.raw.news);
        String objArrayNews = readJsonFromInputStream(inputStream);
        arrayObjectNews = gson.fromJson(objArrayNews, JsonArray.class);

        AdapterNews adapterNews = new AdapterNews();
        newsList.setAdapter(adapterNews);

        bottomBarConfiguration();
    }

    private void bottomBarConfiguration() {
        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //keep this position
            }
        });
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int option = item.getItemId();
                if (option == R.id.profileButton) {
                    Intent profile = new Intent(getApplicationContext(), ProfileView.class);
                    startActivity(profile);
                    return true;
                }else{
                    return false;
                }
            }
        });
    }

    private String readJsonFromInputStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private String getTitleNews(int position){
        String title = arrayObjectNews.get(position).getAsJsonObject().get("titulo").getAsString();
        return title;
    }

    private String getDescripionNews(int position){
        String descripcion = arrayObjectNews.get(position).getAsJsonObject().get("descripcion").getAsString();
        return descripcion;
    }



    public class AdapterNews extends BaseAdapter{

        @Override
        public int getCount() {
            return arrayObjectNews.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayObjectNews.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.cardview_news, parent, false);
                txvTitleNews = convertView.findViewById(R.id.txvTitle);
                txvDescriptionNews = convertView.findViewById(R.id.txvDescription);
                imvPortraitNews = convertView.findViewById(R.id.imgNews);

                imvPortraitNews.setImageDrawable(getImagesResource());
                txvTitleNews.setText(getTitleNews(position));
                txvDescriptionNews.setText(getDescripionNews(position));
            }

            return convertView;
        }


    }

    private Drawable getImagesResource() {
        return imagesResources[(int) Math.round((imagesResources.length - 1) * Math.random())];
    }
}