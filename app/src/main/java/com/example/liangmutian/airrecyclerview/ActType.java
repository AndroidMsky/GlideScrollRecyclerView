package com.example.liangmutian.airrecyclerview;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wuduogen838 on 16/10/8.
 */

public class ActType implements Serializable {
    public static final int SHOW_TYPE_1=1;
    public static final int SHOW_TYPE_2=2;
    public int showType;
    public String name;
    public String code;
    public ArrayList<ActType> actTypeList=new ArrayList<>();
    public ActType(String code,int showType){
        this.showType=showType;
        this.name= code;
        this.code=code;
    }

}
