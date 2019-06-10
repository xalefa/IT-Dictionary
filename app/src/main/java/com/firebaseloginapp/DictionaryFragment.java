package com.firebaseloginapp;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


public class DictionaryFragment extends Fragment {
    String valueSpech;
    TextView txt;

    AlertDialog.Builder obj;
    AlertDialog startAlertDialog;

    private String value="Slaw fata";
    private FragmentListener listener;
    ArrayAdapter<String> adapter;
    ListView dicList;

    EditText se;

    View view;

    FloatingActionMenu floatingActionMenu;
    FloatingActionButton speechToWord,scanWordCamera,speechWord;

    private ArrayList<String> mSource=new ArrayList<String>();

    public DictionaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_dictionary, container, false);

        floatingActionMenu=view.findViewById(R.id.floatinActionMenu);
        speechToWord=view.findViewById(R.id.sppechToText);
        scanWordCamera=view.findViewById(R.id.scan);
        speechWord=view.findViewById(R.id.speechWord);

        speechToWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt=view.findViewById(R.id.testText);
                Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
                try {
                    startActivityForResult(intent,200);
                }catch (ActivityNotFoundException e){
                    Toast.makeText(getContext(),"Error !!! ",Toast.LENGTH_SHORT).show();

                }
               // Toast.makeText(getContext(),"get word"+valueSpech,Toast.LENGTH_SHORT).show();
            }
        });

        scanWordCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent obIntent=new Intent(getContext(), OCRMain.class);
                startActivity(obIntent);

                Toast.makeText(getContext(),"Scan Camera",Toast.LENGTH_SHORT).show();
            }
        });


        speechWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),SpeechActivity.class);
                startActivity(intent);
                //Toast.makeText(getContext(),"Speech word",Toast.LENGTH_SHORT).show();
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    ////


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==200) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                //تێکستەکەمان لیرەیایا
                valueSpech=result.get(0);
                Intent sendVoiceTextIntent=new Intent(getContext(),ShowActivity.class);
                sendVoiceTextIntent.putExtra("getTextVoice",valueSpech);
                startActivity(sendVoiceTextIntent);

                //txt.setText(result.get(0));
                Toast.makeText(getContext(),"Speech word: "+valueSpech,Toast.LENGTH_SHORT).show();

            }

        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


         dicList=view.findViewById(R.id.dictionaryList);
        adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,mSource);
        dicList.setAdapter(adapter);
        dicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listener != null)
                listener.onItemClick(mSource.get(position));
            }
        });
    }

    public void resetDataSource(ArrayList<String> source){
        mSource=source;
        adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,mSource);
        dicList.setAdapter(adapter);

    }

    public void filterValue(String value){
        //adapter.getFilter().filter(value);
        int size=adapter.getCount();
        for (int i=0;i<size;i++){
            if(adapter.getItem(i).startsWith(value)){
                dicList.setSelection(i);
                break;
            }
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public void setOnFragmnetListener(FragmentListener listener){
        this.listener=listener;

    }

    /*
    //

    String[] getListOfWords(){
        String[] source=new String[]{
                 "a"
                ,"b"
                ,"c"
                ,"d"
                ,"e"
                ,"f"
                ,"g"
                ,"h"
                ,"i"
                ,"g"
                ,"k"
                ,"l"
                ,"m"
                ,"n"
                ,"o"
                ,"Twana"
                };
        return source;
        }

     */

}
