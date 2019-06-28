# YcUtils
android开发常用的工具类

YcCalculator
高精度计算（避免java小数加减乘除时的精度问题）

YcUtilRxPermission
动态申请权限（基于rxpermissions2改造）

YcReleaseLayoutUtils
布局的替换，主要用于显示网络请求时多种状态的页面(加载中、没有数据、网络异常等等)
优点：不用修改原有的xml布局，只要YcReleaseLayoutUtils.showLayout(View originalView, @LayoutRes int releaseLayout)替换布局。
也可以统一设置YcUtilsInit.setEmptyIdRes( @LayoutRes int releaseLayout),后调用YcReleaseLayoutUtils.showEmptyLayout(View originalView)，就可以实现替换布局。
恢复原来布局YcUtilsInit.recovery(View originalView)。

YcForResult
带返回值的Activity跳转
优点：统一处理，不用在onActivityResult(){}方法里处理返回的数据，便于代码的阅读。
YcForResult forResult = new YcForResult(thisActivity);
Intent intent  = new Intent(thisActivity,tagActivity);
forResult.start(intent)
        .subscribe(new Consumer<YcForResultBean>() {
            @Override
            public void accept(YcForResultBean ycForResultBean) throws Exception {
                   //这里处理返回数据
            }
        });

BannerView
轮播图

YcUtilVersion
获取包名、版本名、版本号、IMSI（sim卡序列号）、Sn（设备序列号）、IMEI(设备id)、获取状态栏高度、安装apk。
