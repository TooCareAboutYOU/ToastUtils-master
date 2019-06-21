package com.java8.streamapi;

import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.java8.streamapi.beans.EarthModel;
import com.java8.streamapi.beans.SubModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author zhangshuai
 * Java8 的 Stream API
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MMainActivity";

    List<PersonModel> mList = null;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv_info);
        mList = Data.getDataList();
        List<PersonModel> list = mList.stream().filter(personModel -> "男".equals(personModel.getSex())).collect(toList());
        printInfo(list);

        //lambda表达式 ： PersonModel::getName
        List<String> mapList = mList.stream().map(personModel -> personModel.getName()).collect(toList());

        List<String> flatMapList = mList.stream().flatMap(personModel -> Arrays.stream(personModel.getName().split(" "))).collect(toList());
        printInfoStr(flatMapList);


        List<Stream<String>> streamStrList = mList.stream().map(personModel -> Arrays.stream(personModel.getName().split(" "))).collect(toList());
        List<String> strList = null;
        for (Stream<String> stringStream : streamStrList) {
            strList = stringStream.map(s -> s).collect(toList());
            if (BuildConfig.DEBUG) {
                Log.i(TAG, "String<String>: " + strList.toString());
            }
        }

        List<String> stringsList = mList.stream().map(personModel -> personModel.getName().split(" ")).flatMap(Arrays::stream).collect(toList());
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "stringsList:" + stringsList);
        }
        reduceTest();
        collectTest();
        optionalTest();
        parallelTest();
        test();

    }

    private void reduceTest() {
        //累加，初始化值为10
        Integer integer = Stream.of(1, 2, 3, 4, 5).reduce(10, (count, item) -> {
            Log.i(TAG, "打印: count=" + count + "\titem=" + item);
            return count + item;
        });
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "reduceTest: " + integer);
        }
    }

    private void collectTest() {
        //toList()
        List<String> list = mList.stream().map(PersonModel::getName).collect(toList());
        //toSet()
        Set<String> setList = mList.stream().map(PersonModel::getName).collect(Collectors.toSet());

        //toMap()
        Map<String, String> map1 = mList.stream().collect(Collectors.toMap(PersonModel::getName, PersonModel::getSex));
        Map<String, String> map2 = mList.stream().collect(Collectors.toMap(PersonModel::getName, value -> value + "1"));

        //指定类型
//       TreeSet<PersonModel> treeSet= mList.stream().collect(Collectors.toCollection(TreeSet::new));

        //分组
        Map<Boolean, List<PersonModel>> mapBoolList = mList.stream().collect(Collectors.groupingBy(personModel -> "男".equals(personModel.getSex())));

        //分隔
        String str = mList.stream().map(PersonModel::getName).collect(Collectors.joining(",", "{", "}"));

        //自定义
        List<String> strs = Stream.of("1", "2", "3").collect(
                Collectors.reducing(new ArrayList<String>(),
                        x -> Arrays.asList(x),
                        (y, z) -> {
                            y.addAll(z);
                            return y;
                        }
                ));

        if (BuildConfig.DEBUG) {
            Log.w(TAG, "集合: " + list.toString() + "\n" +
                    setList.toString() + "\n" +
                    map1.toString() + "\n" +
                    map2.toString() + "\n" +
                    mapBoolList.toString() + "\n" +
                    str + "\n" +
                    strs.toString()
            );
        }
    }

    private void optionalTest() {
        PersonModel personModel = mList.get(0);

        Log.i(TAG, "对象为空则打出: ");
        Optional<Object> optionalO = Optional.of(personModel);
        if (BuildConfig.DEBUG) {
            System.out.println(optionalO.isPresent() ? optionalO.get() : "-");
        }

        Log.i(TAG, "名称为空则打出: ");
        Optional<String> optionalS = Optional.of(personModel.getName());
        if (BuildConfig.DEBUG) {
            System.out.println(optionalS.isPresent() ? optionalS.get() : "哈哈哈");
        }

        Optional.ofNullable("text").ifPresent(new Consumer<String>() {
            @Override
            public void accept(String s) {
                if (BuildConfig.DEBUG) {
                    Log.i(TAG, "如果不为空，则打出: " + s);
                }
            }
        });

        if (BuildConfig.DEBUG) {
            Log.i(TAG, "如果为空，则返回指定字符串: " + Optional.ofNullable(null).orElse("_"));
            Log.i(TAG, "如果为空，则返回指定字符串: " + Optional.ofNullable("14").orElse("_"));
            Log.i(TAG, "如果为空，则返回指定方法，或者代码: " + Optional.ofNullable(null).orElseGet(new Supplier<String>() {
                @Override
                public String get() {
                    return "哈哈哈哈";
                }
            }));
            Log.i(TAG, "如果为空，则返回指定方法，或者代码: " + Optional.ofNullable("123").orElseGet(new Supplier<String>() {
                @Override
                public String get() {
                    return "哈哈哈哈";
                }
            }));
            try {
                Log.i(TAG, "如果为空，则可以抛出异常: " + Optional.ofNullable(null).orElseThrow(new Supplier<Throwable>() {
                    @Override
                    public Throwable get() {
                        return new RuntimeException("Hello World!!");
                    }
                }));
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                Log.i(TAG, "处理异常: " + throwable.getMessage());
            }
        }

        EarthModel earthModel = new EarthModel();
        earthModel.setPersonModel(mList);
        boolean isNull = Optional.ofNullable(earthModel).map(EarthModel::getModel).map(SubModel::getName).isPresent();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Optional多级判断: " + isNull);
        }

        Optional.ofNullable(earthModel)
                .map(EarthModel::getPersonModel)
                .map(personModelList -> personModelList.stream().map(PersonModel::getName).collect(toList())
                ).ifPresent(per -> {
                    Log.i(TAG, "判断对象中的list: " + per);
                }
        );
    }

    private void parallelTest() {
        Log.i(TAG, "并发: parallelStream");

        List<String> stringsLists = mList.parallelStream()
                .map(personModel -> personModel.getName().split(" "))
                .flatMap(strings -> Arrays.asList(strings).stream())
                .collect(toList());
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "stringsLists:" + stringsLists);
        }
    }

    private void test(){
        Log.e(TAG, "测试: peek操作符, 查看每个值，同时能继续操作流");
        mList.stream().map(new Function<PersonModel, String>() {
            @Override
            public String apply(PersonModel personModel) {
                return personModel.getName();
            }
        })
        .peek(new Consumer<String>() {
            @Override
            public void accept(String s) {
                Log.e(TAG, "当前值:" + s);
            }
        })
        .collect(toList());
    }


    private void printInfo(List<PersonModel> list) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "onCreate: " + list.toString());
            tv.setText(list.toString());
        }
    }

    private void printInfoStr(List<String> list) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "onCreate: " + list.toString());
            tv.setText(list.toString());
        }
    }

}
