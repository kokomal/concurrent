这里比较了三种常规手段序列化和反序列化List<Integer>的方法  
RawSerializeHelper粗暴的把每一个int转换成4个byte，采用BigEndian编码方式，因此存放的空间高位0的占比相当多
前提是需要预估byte[]容量    
NioByteBufferAuxSerializeHelper类似，只不过其用了Nio里面的ByteBuffer的api进行基础数据的读写操作，前提是
ByteBuffer需要预先设置capacity，这个局限比较大  
NettyChannelBufferSerializeHelper采用了netty5的Unpooled进行byte的动态扩容，不用考虑buffer的动态扩容问题  