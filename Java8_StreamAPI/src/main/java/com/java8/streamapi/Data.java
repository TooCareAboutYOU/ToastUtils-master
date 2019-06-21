package com.java8.streamapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Data {

    private static List<PersonModel> mList=new ArrayList<>();

    static {
        PersonModel model1=new PersonModel("One","男","16");
        PersonModel model2=new PersonModel("Two","女","17");
        PersonModel model3=new PersonModel("Three","男","18");
        PersonModel model4=new PersonModel("Four","女","19");
        PersonModel model5=new PersonModel("Five","女","20");
        mList= Arrays.asList(model1,model2,model3,model4,model5);
    }

    public static List<PersonModel> getDataList(){
        return mList;
    }
}
