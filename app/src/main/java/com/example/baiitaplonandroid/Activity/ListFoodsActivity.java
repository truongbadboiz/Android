package com.example.baiitaplonandroid.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baiitaplonandroid.Adapter.FoodListAdapter;
import com.example.baiitaplonandroid.Domain.Foods;
import com.example.baiitaplonandroid.R;
import com.example.baiitaplonandroid.databinding.ActivityListFoodsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListFoodsActivity extends BaseActivity {
    ActivityListFoodsBinding binding;
    private RecyclerView.Adapter adapterListFood;
    private int categoryId;
    private String categoryName;
    private String searchText;
    private boolean isSearch;
    private int locationId;
    private String locationName;
    private int timeId;
    private String timeName;
    private int priceId;
    private String priceName;


    public ListFoodsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityListFoodsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getIntentExtra();
        initList();
        setVariables();
    }

    private void setVariables() {

    }

    private void initList() {
        DatabaseReference myRef = database.getReference("Foods");
        binding.progressBar.setVisibility(View.VISIBLE);
        ArrayList<Foods> list = new ArrayList<>();
        Query query;
        if(isSearch){
            query = myRef.orderByChild("Title").startAt(searchText).endAt(searchText + "/uf8ff");
        }else if(categoryId != -1){
            query = myRef.orderByChild("CategoryId").equalTo(categoryId);
        }else if(locationId != -1){
            query = myRef.orderByChild("LocationId").equalTo(locationId);
        } else if(timeId != -1){
            query = myRef.orderByChild("TimeId").equalTo(timeId);
        } else if(priceId != -1){
            query = myRef.orderByChild("PriceId").equalTo(priceId);
        } else {
            query = myRef;
        }
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue : snapshot.getChildren()){
                        list.add(issue.getValue(Foods.class));
                    }
                    if(!list.isEmpty()){
                        binding.foodListView.setLayoutManager(new GridLayoutManager(ListFoodsActivity.this, 2));
                        adapterListFood = new FoodListAdapter(list);
                        binding.foodListView.setAdapter(adapterListFood);
                    }
                    binding.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getIntentExtra() {
        categoryId = getIntent().getIntExtra("CategoryId", 0);
        categoryName = getIntent().getStringExtra("CategoryName");
        searchText = getIntent().getStringExtra("text");
        isSearch = getIntent().getBooleanExtra("isSearch", false);
        locationId = getIntent().getIntExtra("LocationId", 1);
        locationName = getIntent().getStringExtra("LocationName");
        timeId = getIntent().getIntExtra("TimeId", 2);
        timeName = getIntent().getStringExtra("TimeName");
        priceId = getIntent().getIntExtra("PriceId", 3);
        priceName = getIntent().getStringExtra("PriceName");
        if(isSearch){
            binding.titleTxt.setText(searchText);
        }else if(categoryId != -1){
            binding.titleTxt.setText(categoryName);
        } else if (locationId != -1){
            binding.titleTxt.setText(locationName);
        } else if (timeId != -1){
            binding.titleTxt.setText(timeName);
        } else if (priceId != -1){
            binding.titleTxt.setText(priceName);
        }
        binding.backBtn.setOnClickListener(v -> finish());
    }
}