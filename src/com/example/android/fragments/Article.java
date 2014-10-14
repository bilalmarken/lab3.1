/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Article extends Activity {
	Intent intent;
	String title,text;
	int img;
	TextView tvTitle,tvText;
	ImageView ivImg;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article);
        intent = getIntent();
        title = intent.getStringExtra("title");
        text = intent.getStringExtra("text");
        img = intent.getIntExtra("img", R.drawable.ic_launcher);
        tvTitle = (TextView)findViewById(R.id.textViewTitle);
        tvText = (TextView)findViewById(R.id.textViewText);
        ivImg = (ImageView)findViewById(R.id.imageViewArticle);
        tvTitle.setText(title);
        tvText.setText(text);
        ivImg.setImageResource(img);
    }

    
}