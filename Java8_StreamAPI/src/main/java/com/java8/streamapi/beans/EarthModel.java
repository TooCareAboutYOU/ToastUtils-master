package com.java8.streamapi.beans;

import com.java8.streamapi.PersonModel;

import java.util.List;

/**
 * @author zhangshuai
 */
public class EarthModel {

    private String name;
    private SubModel model;
    private List<PersonModel> mPersonModel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubModel getModel() {
        return model;
    }

    public void setModel(SubModel model) {
        this.model = model;
    }

    public List<PersonModel> getPersonModel() {
        return mPersonModel;
    }

    public void setPersonModel(List<PersonModel> personModel) {
        mPersonModel = personModel;
    }
}
