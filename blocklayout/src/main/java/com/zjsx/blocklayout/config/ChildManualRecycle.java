package com.zjsx.blocklayout.config;

/**
 * Created by eddie on 2017-08-13.
 * 如果一个布局里面的item采用addView，removeView的方式来动态改变布局，可以实现这个接口，那么里面的holder即可被回收利用。
 * recyclerView自己实现了Holder的复用，所以使用recyclerview来实现的布局不需要实现该类
 */

public interface ChildManualRecycle {
}
