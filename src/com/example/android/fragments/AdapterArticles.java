package com.example.android.fragments;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterArticles extends BaseAdapter{
	Context ctx;
	LayoutInflater inflater;
	public static ArrayList<ArticleContent> objects;
	ImageView image = null;
	AdapterArticles(Context context){
		ctx = context;
		inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	AdapterArticles(Context context,ArrayList<ArticleContent> articles){
		ctx = context;
		objects = articles;
		inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	public int getCount(){
		return objects.size();
	}
	public static final class ViewHolder {
		  TextView title;
		  ImageView img;
	}
	public View getView(int position,View convertView, ViewGroup parent){
		ViewHolder holder;
		if (convertView==null){
			convertView = inflater.inflate(R.layout.article_list_item,parent,false);			
			holder = new ViewHolder();
			holder.title = (TextView)convertView.findViewById(R.id.textViewTitle);
			holder.img = (ImageView)convertView.findViewById(R.id.imageViewImg);
			convertView.setTag(holder);			
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(objects.get(position).title) ;
		holder.img.setImageResource((objects.get(position).img));		
		return convertView;
	}
	@Override
	public Object getItem(int position) {
		return objects.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	public void clearData(){
		objects.clear();
	}
	public void setArrayList(ArrayList<ArticleContent> articles){
		objects = articles;
	}
	public ArticleContent getBook(int position){
		return ((ArticleContent)getItem(position));
	}
}
