# CAnimationDemo

## CAnimation是什么?

一个执行动画的帮助类,让开发者只关心动画的结果,而不关心动画的过程

如果开发过ios,一定`UIView.animateWithDuration`这个函数不陌生,这是对它的简单实现

### Gradle
```
compile 'com.ciy:canimation:1.0.0'
```
```
<dependency>
  <groupId>com.ciy</groupId>
  <artifactId>canimation</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

### 看效果图

![Image text](https://github.com/CiyLei/CAnimationDemo/blob/master/gif/20180503211037.gif)

![Image text](https://github.com/CiyLei/CAnimationDemo/blob/master/gif/20180503211119.gif)

### 就是普通的动画嘛,我们再看代码

```

<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context="com.example.chenlei.canimation_githun_demo.MainActivity">

    <TextView
        android:id="@+id/textView1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:background="#fff"
        android:text="Test"/>

    <Button
        android:id="@+id/button1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="变大变红"/>

    <Button
        android:id="@+id/button2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="缩小变黄"/>

    <Button
        android:id="@+id/button3"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="卸载字体大小支持"/>

    <Button
        android:id="@+id/button4"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="加载字体大小支持"/>

</LinearLayout>

```

### MainActivity

```

CAnimationHelp cah = new CAnimationHelp(this);

mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cah.startWithDuration(2000, new COnExecutionFinshListener() {
                    @Override
                    public void onExecution() {
                        ViewGroup.LayoutParams lp = mTextView1.getLayoutParams();
                        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        lp.height = 300;
                        mTextView1.setLayoutParams(lp);
                        mTextView1.setBackgroundColor(Color.RED);
                        mTextView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
                    }
                });
            }
        });
        
```

在button1的单击事件里面,我们调用了`CAnimationHel`对象的一个`startWithDuration`方法,传递了3个参数

第一个参数 代表持续的时间

第二个参数 传递了一个匿名类,实现了一个`onExecution`方法,在里面设置了textView的宽占满父布局,高300px,背景色变红,字体大小设为50sp

中间的过渡动画全部由框架实现,是不是很奇妙

那自然就知道button2里面写了什么代码

```

mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cah.startWithDuration(2000, new COnExecutionFinshListener() {
                    @Override
                    public void onExecution() {
                        ViewGroup.LayoutParams lp = mTextView1.getLayoutParams();
                        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        mTextView1.setLayoutParams(lp);
                        mTextView1.setBackgroundColor(Color.YELLOW);
                        mTextView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    }
                }, new BounceInterpolator());
            }
        });
       
```

不同的是,在`startWithDuration1`方法中传递了第三个参数,一定不会陌生,让动画有了回弹的效果

## 原理

其实原理很Low,就是监测页面上的哪些view改变了哪些属性,然后框架在逐步的完成,所以只支持一下属性

* `Alpha` 透明度

* `BackgroupColor` 背景色

* `Left`,`Top`,`Right`,`Bottom` 边界

* `MarginStart`,`MarginEnd`,`MarginLeft`,`MarginTop`,`MarginRight`,`MarginBottom` 外边距

* `PaddingStart`,`PaddingEnd`,`PaddingLeft`,`PaddingTop`,`PaddingBottom`,`PaddingBottom` 内边距

* `Rotation`,`RotationX`,`RotationY` 旋转

* `ScaleX`,`ScalY` 缩放

* `TextColor` 字体颜色

* `TextSize` 字体大小

* `TranslateX`,`TranslatY`,`TranslatZ` 移动

* `Visibility` 可见度

* `LayoutParams.Width`,`LayoutParams.Height` 高宽

## 如何扩展

先看看效果

![Image text](https://github.com/CiyLei/CAnimationDemo/blob/master/gif/20180503211127.gif)

![Image text](https://github.com/CiyLei/CAnimationDemo/blob/master/gif/20180503211145.gif)

先看看button3,button4是如何卸载和加载字体大小的支持的

```

mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //卸载字体大小支持
                CAnimationHelp.mViewAttrClass.remove(CTextSizeViewAttr.class);
            }
        });

        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加载字体大小支持
                CAnimationHelp.mViewAttrClass.add(CTextSizeViewAttr.class);
            }
        });
        
```

非常简单,就是普通的列表函数,这样,如果有你不喜欢的扩展支持也可以删除,那如果我有很多自定义的view,都是自定义的属性,该怎么办,所以我们尝试如何添加扩展吧

就假设添加一个支持字体大小的扩展吧,我们已经有了,所以直接看把.

### CTextSizeViewAttr

```

public class CTextSizeViewAttr extends CViewAttr<Float> {

    @Override
    public Float getAttrValue(View view) {
        TextView tv = (TextView) view;
        return tv.getTextSize();
    }

    @Override
    public void setAttrValue(View view, Float value) {
        TextView tv = (TextView) view;
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, value);
    }

    @Override
    public boolean isFilter(View view) {
        return !(view instanceof TextView);
    }
}

```

如果需要添加属性的扩展,那么需要继承`CViewAttr`这个类,指定一个属性返回类型的泛型.然后需要实现实现以下几个方法.

`getAttrValue(View)` 这个方法用来返回需要监测的属性的值 (必须重写)

`setAttrValue(View,Value)` 如何设置属性值 (必须重写)

`isFilter(View)` 是否过滤,因为一个属性并不是全部的view都有的,所以这里返回True表示过滤 (不一定要重写,默认返回False)

`attrProgress(progress,View)` 这里是动画更新进度会触发的方法,第一个参数`progress`表示进度,从0-100.如果属性的值是一个直线函数的变化,那么框架默认实现了,那如果你属性的值根据进度会进行不按套路的变化的话,请重写这个方法,具体可以看`CBackgroupColorViewAttr`这个类是怎么进行颜色的变化支持的

### CBackgroupColorViewAttr

```

public class CBackgroupColorViewAttr extends CViewAttr<Integer> {

    @Override
    public Integer getAttrValue(View view) {
        if (view.getBackground() instanceof ColorDrawable) {
            ColorDrawable cd = (ColorDrawable) view.getBackground();
            return cd.getColor();
        }
        return Color.TRANSPARENT;
    }

    @Override
    public void attrProgress(int progress, View view) {
        //计算原来和现在属性的差距
        int currentAttrValue = getCurrentAttrValue();
        int previousAttrValue = getPreviousAttrValue();
        view.setBackgroundColor(CViewAttrUtil.transitionColor(progress / 100.00f, previousAttrValue, currentAttrValue));
    }

    @Override
    public void setAttrValue(View view, Integer value) {
        view.setBackgroundColor(value);
    }

}

```

在`attrProgress`函数中,`getCurrentAttrValue()`方法是返回要成为的属性值,`getPreviousAttrValue()`方法是返回原本的属性值

如果要从红色变为黄色,那么`getCurrentAttrValue()`返回的是黄色的argb值,`getPreviousAttrValue()`返回的是红色的argb值
