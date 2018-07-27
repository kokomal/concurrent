# concurrent
多线程，高并发相关实例demo  

### SynchronizedDemo.java
java内置重量锁的示例，synchronized关键字是对象锁，支持锁定指定变量（或者类）和对方法进行锁定，尤其注意，对于自动装箱类（Integer，Double之类）的instance  
并且此instance会有变化，那么非常不建议对此进行锁定（因为对此对象的修改，可能会导致此对象的内存地址变化，从而导致锁定失败的情况）  

