package com.firebaseloginapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class DetailFragment extends Fragment {

    private String value="";
    private TextView tvWord;
    private ImageButton btnBookmakr,btnVolume;
    private WebView tvWordTranslate;
    private DBHelper mDBHelper;
    private int mDicType;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment getNewInstance(String value,DBHelper dbHelper,int dictype){
        DetailFragment fragment=new DetailFragment();
        fragment.value=value;
        fragment.mDBHelper=dbHelper;
        fragment.mDicType=dictype;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detialk, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvWord=view.findViewById(R.id.tvWord);
        tvWordTranslate=view.findViewById(R.id.tvWordTranslate);
        btnBookmakr=view.findViewById(R.id.btnBookmark);
        btnVolume=view.findViewById(R.id.btnVolume);

        final Word word=mDBHelper.getWord(value,mDicType);
        tvWord.setText(word.key);
        tvWordTranslate.loadDataWithBaseURL(null,word.value,"text/html","utf-8",null);

        Word bookmarkWord= mDBHelper.getWordFromBookmark(value);
        int isMark=bookmarkWord==null? 0:1;
        btnBookmakr.setTag(isMark);

        int icon=bookmarkWord ==null? R.drawable.ic_bookmark_border:R.drawable.ic_bookmark_fill;
        btnBookmakr.setImageResource(icon);

        btnBookmakr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=(int)btnBookmakr.getTag();
                if (i==0){
                    btnBookmakr.setImageResource(R.drawable.ic_bookmark_fill);
                    btnBookmakr.setTag(1);
                    mDBHelper.addBookmark(word);
                    Toast.makeText(getContext(),"Add Bookmark list",Toast.LENGTH_SHORT).show();
                }else if(i==1){
                    btnBookmakr.setImageResource(R.drawable.ic_bookmark_border);
                    btnBookmakr.setTag(0);
                    mDBHelper.removeBookmark(word );
                    Toast.makeText(getContext(),"Remove Bookmark list",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
