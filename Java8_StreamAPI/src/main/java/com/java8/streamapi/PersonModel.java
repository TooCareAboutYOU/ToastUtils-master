package com.java8.streamapi;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

public class PersonModel implements Parcelable {


    private String name;
    private String sex;
    private String age;

    public PersonModel() {
    }

    public PersonModel(String name, String sex, String age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    protected PersonModel(Parcel in) {
        name = in.readString();
        sex = in.readString();
        age = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(sex);
        dest.writeString(age);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PersonModel> CREATOR = new Creator<PersonModel>() {
        @Override
        public PersonModel createFromParcel(Parcel in) {
            return new PersonModel(in);
        }

        @Override
        public PersonModel[] newArray(int size) {
            return new PersonModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"sex\":\"")
                .append(sex).append('\"');
        sb.append(",\"age\":\"")
                .append(age).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
