package com.ashwini.refreshlistapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<DataModel> imageModelArrayList;

    public DataListAdapter(Context ctx, ArrayList<DataModel> imageModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.imageModelArrayList = imageModelArrayList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.image_list_details, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.id.setText("Id : "+imageModelArrayList.get(position).getId());
        holder.author.setText("Author : "+imageModelArrayList.get(position).getAuthor());
        holder.width.setText("Width : "+imageModelArrayList.get(position).getWidth());
        holder.height.setText("height : "+imageModelArrayList.get(position).getHeight());
        Picasso.get().load(imageModelArrayList.get(position).getDownload_url()).resize(400,400).centerInside().into(holder.download_url);
        holder.img_url.setClickable(true);
        holder.img_url.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='"+imageModelArrayList.get(holder.getAdapterPosition()).getUrl()+"'> "+imageModelArrayList.get(position).getUrl() +"</a>";
        holder.img_url.setText("URL : "+ Html.fromHtml(text));

        //on clicklistner to open url in webview
        holder.img_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(inflater.getContext(), UrlWebviewActivity.class);
                i.putExtra("img_url",imageModelArrayList.get(holder.getAdapterPosition()).getUrl().toString());
                inflater.getContext().startActivity(i);
            }
        });

        //Onclicklistner to open in alertdialog
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
                alertDialog.setTitle("Picsum Image Details");
                LinearLayout diagLayout = new LinearLayout(view.getContext());
                diagLayout.setOrientation(LinearLayout.VERTICAL);
                diagLayout.setBackground(inflater.getContext().getDrawable(R.drawable.rounded_corner));
                alertDialog.setCancelable(false);

                final TextView id = new TextView(view.getContext());
                id.setText("Id : "+holder.id.getText());
                id.setPadding(10, 10, 10, 10);
                id.setGravity(Gravity.LEFT);
                id.setTextSize(20);
                diagLayout.addView(id);

                final TextView author = new TextView(view.getContext());
                author.setText("Author : "+holder.author.getText());
                author.setPadding(10, 10, 10, 10);
                author.setGravity(Gravity.LEFT);
                author.setTextSize(20);
                diagLayout.addView(author);

                final TextView width = new TextView(view.getContext());
                width.setText("Width : "+holder.width.getText());
                width.setPadding(10, 10, 10, 10);
                width.setGravity(Gravity.LEFT);
                width.setTextSize(20);
                diagLayout.addView(width);

                final TextView height = new TextView(view.getContext());
                height.setText("height : "+holder.height.getText());
                height.setPadding(10, 10, 10, 10);
                height.setGravity(Gravity.LEFT);
                height.setTextSize(20);
                diagLayout.addView(height);

                final TextView img_url = new TextView(view.getContext());
                img_url.setText("URL : "+holder.img_url.getText());
                img_url.setPadding(10, 10, 10, 10);
                img_url.setGravity(Gravity.LEFT);
                img_url.setTextSize(20);
                diagLayout.addView(img_url);

                final TextView download_url = new TextView(view.getContext());
                download_url.setText("Download URL : "+imageModelArrayList.get(holder.getAdapterPosition()).getDownload_url());
                download_url.setPadding(10, 10, 10, 10);
                download_url.setGravity(Gravity.LEFT);
                download_url.setTextSize(20);
                diagLayout.addView(download_url);

                alertDialog.setView(diagLayout);
                alertDialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

            }
        });

    }



    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView id,author,width,height,img_url;
        ImageView download_url;

        public MyViewHolder(View itemView) {
            super(itemView);

            id = (TextView)itemView.findViewById(R.id.tv_id);
            author = (TextView) itemView.findViewById(R.id.tv_author);
            width = (TextView) itemView.findViewById(R.id.tv_width);
            height = (TextView) itemView.findViewById(R.id.tv_height);
            img_url = (TextView) itemView.findViewById(R.id.tv_img_url);
            download_url = (ImageView) itemView.findViewById(R.id.img_download_url);



        }


    }
}
