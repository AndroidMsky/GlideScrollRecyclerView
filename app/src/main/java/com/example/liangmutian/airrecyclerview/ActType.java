package com.example.liangmutian.airrecyclerview;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wuduogen838 on 16/10/8.
 */

public class ActType implements Serializable {
    public String name;
    public String code;
    public ArrayList<ActType> actTypeList=new ArrayList<>();
    public ActType(String code){
        this.name= code;
        this.code=code;
    }

}
