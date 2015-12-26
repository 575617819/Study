#[dagger的笔记](http://google.github.io/dagger/)

##对构造函数进行注解
使用@Inject这个注解对一个构造函数进行注解的时候，表示dagger应该使用这个构造函数来生成一个实例。
当需要这个类的一个实例的时候，dagger将会获取必要的参数来生成这个对象。


##对属性值进行注解
假如存在注解需要注入对象，但是没有对应的注解的构造函数，
dagger会对那些需要被注入的属性进行注入，但是并不是创建新的实例。
也可以用注解添加一个无参的构造函数来通知dagger创建新的实例。
dagger还支持对方法的注解，尽管属性或者构造函数更加典型。
对于那些没用注解的类，是不能被dagger正确的构造的。

##注解不适用的情况
1.对于接口是不能注解；
1.对于第三方的类是不能注解的；
1.可配置的对象必须被配置好(不知道啥意思)
对于以上的三种情况，dagger是比较尴尬的，体验也是比较糟糕的，所以可以使用 @Provides这个注解来满足要求。
这个方法的返回值是那些被依赖的类型。
具体做法是在一个Module类中通过 @Provides来注解一个方法。
约定俗成，被 @Provides注解的方法名以provide开头，类名以Module 结束。





