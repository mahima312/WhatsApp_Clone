package com.mahima.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.mahima.whatsapp.Adapters.FragmentAdapter;
import com.mahima.whatsapp.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        binding.viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
      binding.tablayout.setupWithViewPager(binding.viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.settings) {
            Toast.makeText(MainActivity.this, "Settings Clicked", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.logout) {
            firebaseAuth.signOut();
            Intent intent = new Intent(MainActivity.this,  SignInActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.groupChat) {
            Intent intent = new Intent(MainActivity.this,  GroupChatActivity.class);
            startActivity(intent);
        }
        return true;
    }
}