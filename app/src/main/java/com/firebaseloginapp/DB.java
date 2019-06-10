package com.firebaseloginapp;

public class DB {
    public static String[] getData(int id){
        if(id ==R.id.action_en_ku){
            return getEnKu();
        }else if(id ==R.id.action_ku_en){
            return getKuEn();
        }else if(id ==R.id.action_en_en){
            return getEnEn();
        }

        return new String[0];
    }


    public static String[] getEnKu(){
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

    public static String[] getKuEn(){
        String[] source=new String[]{
                "ئەی"
                ,"بی"
                ,"سی"
                ,"دی"
                ,"ئی"
                ,"ئێف"
                ,"جی"
                ,"ئیچ"
                ,"ئای"
                ,"جی"
                ,"کەی"
                ,"ئێڵ"
                ,"ئێم"
                ,"ئێن"
                ,"ئۆ"
                ,"توانا"
        };
        return source;

    }

    public static String[] getEnEn(){
        String[] source=new String[]{
                "aaaaa"
                ,"bbbbb"
                ,"ccccccc"
                ,"dddddd"
                ,"eeeee"
                ,"fffff"
                ,"ggggg"
                ,"hhhhh"
                ,"iiiii"
                ,"gggg"
                ,"kkkk"
                ,"llll"
                ,"mmmm"
                ,"nnnnn"
                ,"oooo"
                ,"TwanatwanatwanaTwanaTweanaNarimanQader"
        };
        return source;
    }

}
