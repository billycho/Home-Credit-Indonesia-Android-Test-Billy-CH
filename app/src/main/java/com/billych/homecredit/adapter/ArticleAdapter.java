package com.billych.homecredit.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.billych.homecredit.R;
import com.billych.homecredit.model.Article;
import com.billych.homecredit.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Article> articleList;
    private List<Product> productList;
    private String sectionTitle;

    private Context context;
    public ArticleAdapter(List<Article> articles, List<Product> products, String sectionTitle, Context context)
    {
        super();
        this.articleList = articles;
        this.productList = products;
        this.sectionTitle = sectionTitle;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;

        if(viewType == 0)
        {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_rv, parent,false);
            ArticleAdapter.ProductViewHolder viewHolder = new ArticleAdapter.ProductViewHolder(v);

            return viewHolder;
        }
        else
        {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item, parent,false);
            ArticleAdapter.ViewHolder viewHolder = new ArticleAdapter.ViewHolder(v);

            return viewHolder;

        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(position == 0)
        {
            ProductAdapter productAdapter = new ProductAdapter(productList,context);
            ((ProductViewHolder) holder).rv.setLayoutManager(new GridLayoutManager(context,3));
            ((ProductViewHolder) holder).rv.setAdapter(productAdapter);
        }
        else
        {
            Picasso.with(context).load(articleList.get(position).getImage()).into(((ViewHolder) holder).image);
            ((ViewHolder) holder).title.setText(articleList.get(position).getTitle());
            ((ViewHolder) holder).image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = articleList.get(position).getLink();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return  position;
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        private TextView title;
        public ViewHolder(View itemView)
        {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.article_image);
            title = (TextView) itemView.findViewById(R.id.article_title);
        }

    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        public RecyclerView rv;

        public ProductViewHolder(View itemView) {
            super(itemView);
            rv = (RecyclerView) itemView.findViewById(R.id.productRV);
        }
    }
}
