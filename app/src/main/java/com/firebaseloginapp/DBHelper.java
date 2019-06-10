package com.firebaseloginapp;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper{
    private Context mContext;
    public static final String DATABASE_NAME="my_dic.db";
    public static final int DATABASE_VERSION=1;

    private String DATABASE_LOCATION="";
    private String DATABASE_FULL_PATH="";

    private final String TBL_EN_KU="en_ku";
    private final String TBL_KU_EN="ku_en";
    private final String TBL_EN_EN="en_en";
    private final String TBL_BOOKMARK="bookmark";

    private final String COL_KEY="key";
    private final String COL_VALUE="value";



    public SQLiteDatabase mDB;

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        mContext=context;
        DATABASE_LOCATION="data/data/"+mContext.getPackageName()+"/database/";
        DATABASE_FULL_PATH=DATABASE_LOCATION+DATABASE_NAME;

        if (!isExistingDB()){
            try {
                File dbLocation=new File(DATABASE_LOCATION);
                dbLocation.mkdir();

                extractAssetToDatabaseDirectory(DATABASE_NAME);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mDB=SQLiteDatabase.openOrCreateDatabase(DATABASE_FULL_PATH,null);
    }

    boolean isExistingDB(){
        File file=new File(DATABASE_FULL_PATH);
        return file.exists();
    }

    public void extractAssetToDatabaseDirectory(String fileName)
            throws IOException{
        int length;
        InputStream sourceDatabase=this.mContext.getAssets().open(fileName);
        File destinationPath=new File(DATABASE_FULL_PATH);
        OutputStream destination=new FileOutputStream(destinationPath);

        byte[] buffer=new byte[4096];
        while ((length=sourceDatabase.read(buffer))>0){
            destination.write(buffer,0,length);
        }
        sourceDatabase.close();
        destination.flush();
        destination.close();

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<String> getWord(int dicType){
        String tableName=getTableName(dicType);
    String q="select * from " + tableName;
    Cursor resulr=mDB.rawQuery(q,null);

    ArrayList<String> source=new ArrayList<>();
    while (resulr.moveToNext()){
        source.add(resulr.getString(resulr.getColumnIndex(COL_KEY)));
    }
    return source;
    }

    //
    public Word getWord(String key,int dictype){
        String tableName=getTableName(dictype);
        String q="select * from "+tableName+" where upper([key]) = upper(?)";
        Cursor resulr=mDB.rawQuery(q,new String[]{key});

       Word word=new Word();
        while (resulr.moveToNext()){
            word.key=resulr.getString(resulr.getColumnIndex(COL_KEY));
            word.value=resulr.getString(resulr.getColumnIndex(COL_VALUE));
        }
        return word;

    }

    public void addBookmark(Word word){
        try {
            String q = "insert into bookmark([" + COL_KEY + "],[" + COL_VALUE + "]) values (?,?);";
            mDB.execSQL(q, new Object[]{word.key, word.value});
        }catch (SQLException ex){

        }
    }

    public void removeBookmark(Word word){
        try {
            String q = "delete from bookmark where upper(["+COL_KEY+"]) = upper(?) and ["+COL_VALUE+"]=?;";
            mDB.execSQL(q, new Object[]{word.key, word.value});
        }catch (SQLException ex){

        }
    }

    public ArrayList<String> getAllWordFromBookmark(){
        String q="select * from bookmark order by [date] desc;";
        Cursor resulr=mDB.rawQuery(q,null);

        ArrayList<String> source=new ArrayList<>();
        while (resulr.moveToNext()){
            source.add(resulr.getString(resulr.getColumnIndex(COL_KEY)));
        }
        return source;

    }

    public boolean isWordMark(Word word){
        String q="select * from bookmark where upper([key]) = upper(?) and [value]=? ;";
        Cursor resulr=mDB.rawQuery(q,new String[]{word.key, word.value});
        return resulr.getCount() > 0;
    }

    public Word getWordFromBookmark(String key){

        String q="select * from bookmark where upper([key]) = upper(?) ";
        Cursor resulr=mDB.rawQuery(q,new String[]{key});

        Word word=null;
        while (resulr.moveToNext()){
            word=new Word();
            word.key=resulr.getString(resulr.getColumnIndex(COL_KEY));
            word.value=resulr.getString(resulr.getColumnIndex(COL_VALUE));
        }
        return word;
    }

    public String getTableName(int dicTyp){
        String tableName="";
        if (dicTyp == R.id.action_ku_en){
            tableName=TBL_KU_EN;
        }else if (dicTyp == R.id.action_en_en){
            tableName=TBL_EN_EN;
        }else if (dicTyp == R.id.action_en_ku){
            tableName=TBL_EN_KU;
        }
        return tableName;
    }
}
