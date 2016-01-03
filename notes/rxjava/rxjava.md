#RxJava的使用笔记

----------

##操作符
1. from()方法，它接收一个集合作为输入，然后每次输出一个元素给subscriber；
2. Observable.flatMap()接收一个Observable的输出作为输入，同时输出另外一个Observable。atMap输出的新的Observable正是我们在Subscriber想要接收的。
3. filter()输出和输入相同的元素，并且会过滤掉那些不满足检查条件的。
4. take()输出最多指定数量的结果。
5. doOnNext()允许我们在每次输出一个元素之前做一些额外的事情.

--------
##优点
1. 只要有异常发生onError()一定会被调用，只需要在一个地方处理错误即可以。
2. Observerable的操作符调用链中一旦有一个抛出了异常，就会直接执行onError()方法。
3. 你能够知道什么时候订阅者已经接收了全部的数据。
-------
##运行线程
1. subscribeOn()指定事件源Obserable发送事件代码运行的线程，使用observerOn()指定事件接收处理者者运行的线程。
--------
##订阅关系
当调用Observable.subscribe()，会返回一个Subscription对象。这个对象代表了被观察者和订阅者之间的联系。
可以通过这个对象取消订阅或者获取订阅关系。
RxJava的另外一个好处就是它处理unsubscribing的时候，会停止整个调用链。如果你使用了一串很复杂的操作符，调用unsubscribe将会在他当前执行的地方终止。不需要做任何额外的工作！

##在Android中的使用
RxAndroid是RxJava的一个针对Android平台的扩展。
1. AndroidSchedulers提供了针对Android的线程系统的调度器。
1. AndroidObservable，它提供了跟多的功能来配合Android的生命周期。bindActivity()和bindFragment()方法默认使用AndroidSchedulers.mainThread()来执行事件消费观察者的代码，这两个方法会在Activity或者Fragment声明周期结束的时候通知被事件源AndroidObservable停止发出新的消息。
1. AndroidObservable.fromBroadcast()方法，它允许你创建一个类似BroadcastReceiver的Observable对象。
1. ViewObservable,使用它可以给View添加了一些绑定。如果你想在每次点击view的时候都收到一个事件，可以使用ViewObservable.clicks()，或者你想监听TextView的内容变化，可以使用ViewObservable.text()。


##一些笔记
1. Observable 的contact是将一些observable连接在一起，按照不交错的发射两个或多个Observable的发射物。第一个发射完才会发射第二个。。。。
1. 使用Merge操作符你可以将多个Observables的输出合并，就好像它们是一个单个的Observable一样。(界面需要等到多个接口并发取完数据，再更新)
1. flatMap解决嵌套回调的问题。
1. throttleFirst放置按钮被连续点击等操作。
