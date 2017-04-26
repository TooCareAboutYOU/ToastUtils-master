本项目主要实现类似Toast弹出信息提示框，暂时实现两种效果：

1、顶部弹框：使用自定义依赖包，依赖包配置如下：

    （1）在project根目录的build.gradle中添加(注：可各version自定义，也可直接复制项目生成的vertion)

        ext{
            configs=[
                    buildToolsVersion : "25.0.2",
                    compileSdkVersion : 25,
                    targetSdkVersion : 25
            ]
            libs=[
                    supportVersion : "25.3.1"
            ]
        }

    （2）在Module根目录的build.gradle中添加或修改对相应的version:

        def config = rootProject.ext.configs;
        def lib = rootProject.ext.libs;

        android{
                compileSdkVersion config.compileSdkVersion
                buildToolsVersion config.buildToolsVersion
                defaultConfig {
                        ...
                        targetSdkVersion config.targetSdkVersion
                        ...
                    }
        }

        dependencies {
            ...
            (注：千万别忘了这俩位置的version也要替换，否则编译不通过)
            compile "com.android.support:appcompat-v7:${libs.supportVersion}"
            compile "com.android.support:design:${libs.supportVersion}"
            ...
        }

    （3）最后别忘了在dependencies中添加依赖 compile project(':library')

    （4）上述步骤完成即可使用，例如：
        xml布局：
            <iammert.com.library.ConnectionStatusView
                    android:id="@+id/sv_status"
                    android:layout_width="match_parent"
                    android:layout_height="48dp"
                    app:dismissOnComplete="true" />
        实现类：

            private ConnectionStatusView mStatusView;
            mStatusView= (ConnectionStatusView) findViewById(R.id.sv_status);
            mStatusView.setStatus(Status.COMPLETE);
            (目前Status中已有加载、错误、完成效果，支持自定义视图)

2、底部弹框：Snackbar，这是design依赖包内置的，如果之前已经导过无需再导了。具体可见 Android Support Design详解