package com.example.myrecyclerviewex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 샘플 데이터 대입
        ProductAdapter adapter = new ProductAdapter(Product.getProductList(), this);
        // xml
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        // 레이아웃 매니저가 필요함
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(linearLayoutManager);

        // divider 생성
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getApplicationContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}

class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    ArrayList<Product> products;
    Context context;

    public ProductAdapter(ArrayList<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    // 내부 클래스
    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productImageView;
        TextView titleTextView;
        TextView subTitleTextView;
        TextView footerTextView;
        TextView commentCount;
        TextView heartCount;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            productImageView = view.findViewById(R.id.productImageView);

            titleTextView = view.findViewById(R.id.titleTextView);
            subTitleTextView = view.findViewById(R.id.subTitleTextView);
            footerTextView = view.findViewById(R.id.footerTextView);
            commentCount = view.findViewById(R.id.commentCount);
            heartCount = view.findViewById(R.id.heartCount);

            view.setOnClickListener(view1 -> {
                String temp = getLayoutPosition() + " : " + products.get(getLayoutPosition()).productName;
                Toast.makeText(view.getContext(), temp + " : ", Toast.LENGTH_SHORT).show();
            });
        }


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 아이템 하나를 만들어 줄 뷰
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Product item = products.get(position);

//        holder.productImageView
        // centerCrop
        Glide.with(holder.itemView.getContext())
                .load(item.imageUri)
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.productImageView);
        holder.titleTextView.setText(item.productName);
        holder.subTitleTextView.setText(products.get(position).location + " · " + products.get(position).date);
        holder.footerTextView.setText(products.get(position).price);
        holder.commentCount.setText(products.get(position).commentCount + "");
        holder.heartCount.setText(products.get(position).likeCount + "");
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


}