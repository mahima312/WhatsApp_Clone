package com.mahima.whatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mahima.whatsapp.databinding.ActivityGroupChatBinding;
import com.mahima.whatsapp.databinding.ActivityMainBinding;

public class GroupChatActivity extends AppCompatActivity {

    ActivityGroupChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}