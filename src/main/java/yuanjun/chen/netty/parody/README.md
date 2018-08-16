# netty.parody
用nio实现的伪netty框架  

### 概要设计
boss是引导ACCEPT的角色，将任务分配到worker，一般而言1个足够，但支持多个  
worker是具体执行io读写的角色  
这里采用了RoundRobin的分片策略，对boss和worker进行轮询  

★★★boss注册registerAcceptChannelTask动作在bind的时候已经指定，此动作将注册selector为ACCEPT  
★★★worker注册registerNewChannelTask动作在process的时候，也就是如果有新客户端连接接入，才把新连接的channel注册到worker  

AbstractNioSelector是具体NioServerWorker和Boss的抽象类，其功能主要是：  
1.自身就是线程并且在初始化完成后就启动自己  
2.run指定了完整的运行流程，其中select和process均由子类worker和boss实现  

