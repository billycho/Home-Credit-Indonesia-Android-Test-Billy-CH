package com.billych.homecredit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.Toast;

import com.billych.homecredit.adapter.ArticleAdapter;
import com.billych.homecredit.model.Article;
import com.billych.homecredit.model.Product;
import com.billych.homecredit.network.DataService;
import com.billych.homecredit.network.RetrofitClientInstance;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ArticleAdapter articleAdapter;

    private String sectionTitle;
    private List<Product> productList;
    private List<Article> articleList;

    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.home_refreshLayout);
        refreshLayout.setRefreshing(true);
        fetchData();

    }

    private void fetchData()
    {
        DataService service = RetrofitClientInstance.getRetrofitInstance().create(DataService.class);
        Call<JsonElement> call = null;


        call = service.getAllData();




        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                if (response.body() != null) {
                        JsonElement jsonElement = new JsonParser().parse(response.body().toString());
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        JsonArray jsonArray = jsonObject.getAsJsonArray("data");

                        JsonElement articles = jsonArray.get(0);
                        JsonElement products = jsonArray.get(1);

                        //article json
                        jsonObject = articles.getAsJsonObject();
                        sectionTitle = jsonObject.get("section").toString();
                        jsonArray = jsonObject.getAsJsonArray("items");
                        articleList = new ArrayList<Article>();

                        for(int i = 0;i<jsonArray.size();i++)
                        {
                            jsonObject = jsonArray.get(i).getAsJsonObject();
                            String article_title =  jsonObject.get("article_title").getAsString();
                            String article_image = jsonObject.get("article_image").getAsString();
                            String link = jsonObject.get("link").getAsString();

                            Article article = new Article(article_title,article_image,link);
                            articleList.add(article);
                        }

                    //product json
                    jsonObject = products.getAsJsonObject();
                    sectionTitle = jsonObject.get("section").toString();
                    jsonArray = jsonObject.getAsJsonArray("items");
                    productList = new ArrayList<Product>();

                    for(int i = 0;i<jsonArray.size();i++)
                    {
                        jsonObject = jsonArray.get(i).getAsJsonObject();
                        String product_name =  jsonObject.get("product_name").getAsString();
                        String product_image = jsonObject.get("product_image").getAsString();
                        String link = jsonObject.get("link").getAsString();

                        Product product = new Product(product_name, link, product_image);
                        productList.add(product);
                    }
                        setupAdapter();
                }


            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Mohon cek internet anda", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupAdapter()
    {

        refreshLayout.setRefreshing(false);
        RecyclerView articleRV = findViewById(R.id.articleListRV);
        articleRV.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        articleRV.setLayoutManager(layoutManager);

        articleAdapter = new ArticleAdapter(articleList,productList,sectionTitle,this);
        articleRV.setAdapter(articleAdapter);
    }
}
