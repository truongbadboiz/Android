package com.example.baiitaplonandroid.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.baiitaplonandroid.Domain.Foods;
import com.example.baiitaplonandroid.Hepler.ManagmentCart;
import com.example.baiitaplonandroid.R;
import com.example.baiitaplonandroid.databinding.ActivityDetailBinding;

public class DetailActivity extends BaseActivity {
ActivityDetailBinding binding;
private Foods object;
private int num=1;
private ManagmentCart managmentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        getIntentExtra();
        setVariable();
    }

    private void setVariable() {
        managmentCart= new ManagmentCart(this);
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Glide.with(DetailActivity.this)
                .load(object.getImagePath())
                .into(binding.pic);
        binding.priceTxt.setText("$"+object.getPrice());
        binding.titleTxt.setText(object.getTitle());
        binding.descriptionTxt.setText(object.getDescription());
        binding.rateTxt.setText(object.getStar()+"Rating");
        binding.ratingBar.setRating((float) object.getStar());
        binding.totalTxt.setText((num*object.getPrice()+"$"));
        binding.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num=num+1;
                binding.numTxt.setText(num+" ");
                binding.totalTxt.setText("$"+(num* object.getPrice()));

            }
        });
        binding.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num>1)
                {
                    num=num-1;
                    binding.numTxt.setText(num+"");
                    binding.totalTxt.setText("$"+(num*object.getPrice()));
                }
            }
        });
        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             object.setNumberInCart(num);
             managmentCart.insertFood(object);
            }
        });
    }

    private void getIntentExtra() {
        object=(Foods)getIntent().getSerializableExtra("object");
    }
}