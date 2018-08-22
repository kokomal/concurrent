# concurrent
多线程，高并发相关实例demo（亟待更新MD文件中...）  

---

# sync包下  
### SynchronizedDemo.java
java内置重量锁的示例，synchronized关键字是对象锁，支持锁定指定变量（或者类）和对方法进行锁定，尤其注意，对于自动装箱类（Integer，Double之类）的instance  
并且此instance会有变化，那么非常不建议对此进行锁定（因为对此对象的修改，可能会导致此对象的内存地址变化，从而导致锁定失败的情况）  

---

# lock包下
juc包外置可重入锁的应用  

# serialize包下
序列化框架的一些实例  
ProtocolBuffer protobuf3是google出品的序列化框架，需要预先编辑proto文件，来指定通讯pojo的字段，并且可以用protobuf的exe执行脚本来实现（命令参数存在bat文件里面）
当然也可以用mvn来实现pojo的生成    
maven命令为mvn protobuf:compile  
也可以在Windows下执行脚本（参见项目路径下proto_build.bat）值得注意的是protobuf3废除了通配符的命令  
