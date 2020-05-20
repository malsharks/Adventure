package com.example.adventure.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adventure.Model.Choice;
import com.example.adventure.Model.Page;
import com.example.adventure.Model.Story;
import com.example.adventure.R;

import java.util.Stack;

public class StoryActivity extends AppCompatActivity {

    private static final String TAG = StoryActivity.class.getSimpleName();

    private Story story;

    private ImageView storyImageView;
    private TextView storyTextView;
    private Button choice1Button;
    private Button choice2Button;
    private String name;
    private Stack<Integer> pageStack = new Stack<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        storyImageView = findViewById(R.id.storyImageView);
        storyTextView = findViewById(R.id.storyTextView);
        choice1Button = findViewById(R.id.choice1Buttton);
        choice2Button = findViewById(R.id.choice2Button);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        if (name == null || name.isEmpty()){
            name = "Player";
        }

        Log.d(TAG, name);

        story = new Story();

        loadPage(0);
    }

    private void loadPage(int pageNumber) {
        pageStack.push(pageNumber);

        Page page = story.getPage(pageNumber);

        Drawable image = ContextCompat.getDrawable(this, page.getImageId());
        storyImageView.setImageDrawable(image);

        String pageText = getString(page.getTextId());

        pageText = String.format(pageText, name);
        storyTextView.setText(pageText);

        if (page.isFinalPage()){
            choice1Button.setVisibility(View.INVISIBLE);
            choice2Button.setText(R.string.play_again_button_text);
            choice2Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadPage(0);
                }
            });
        } else {
            loadButtons(page);
        }
    }

    private void  loadButtons(final Page page) {
        choice1Button.setVisibility(View.VISIBLE);
        choice1Button.setText(page.getChoice1().getTextId());
        choice1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        choice2Button.setVisibility(View.VISIBLE);
        choice2Button.setText(page.getChoice2().getTextId());
        choice2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        pageStack.pop();
        if (pageStack.isEmpty()) {
            super.onBackPressed();
        } else {
            loadPage(pageStack.pop());
        }
    }
}
