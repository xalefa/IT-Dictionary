package com.firebaseloginapp;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class BookmarkFragment extends Fragment {
    AlertDialog.Builder o;
    AlertDialog startAlert;

   private FragmentListener listener;
   private DBHelper mDbHelper;
    public BookmarkFragment() {
        // Required empty public constructor
    }

    public static BookmarkFragment getNewInstance(DBHelper dbHelper){
        BookmarkFragment fragment=new BookmarkFragment();
        fragment.mDbHelper=dbHelper;
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
        return inflater.inflate(R.layout.fragment_bookmark, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        ListView bookmarkList=(ListView) view.findViewById(R.id.bookmarkList);
        final BookmarkAdapter adapter=new BookmarkAdapter(getActivity(),mDbHelper.getAllWordFromBookmark());
        bookmarkList.setAdapter(adapter);

        adapter.setOnItemClick(new ListItemListner() {
            @Override
            public void onItemClick(int position) {
                if(listener != null)
                    listener.onItemClick(String.valueOf(adapter.getItem(position)));
            }
        });

        adapter.setOnItemDeleteClick(new ListItemListner() {
            @Override
            public void onItemClick(final int position) {

                o=new AlertDialog.Builder(getContext());
                o.setMessage("Are you sure you want Detele this? ");
                o.setTitle("Confirm Delete");
                o.setIcon(R.drawable.ic_delete_alirt_dialog);
                o.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                o.setPositiveButton("Yas", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.removeItem(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(),"Successfully Deleted ",Toast.LENGTH_SHORT).show();
                    }
                });
                startAlert=o.create();
                startAlert.show();

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

    public void setOnFragmnetListener(FragmentListener listener){
        this.listener=listener;

    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

/*
///word


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
