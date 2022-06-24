# NIO （No Blocking io）
    nio面向缓冲块编程
## nio三大核心
    nio三大核心：
    1.channel 通道
    2.buffer  缓冲区
    3.selector 选择器
    一个channel对应一个buffer，客户端每次和buffer交互，服务器每次通过selector和channel通信
    nio的buffer是双向的与bio的单向流不同，可读可写，需要读写切换flip（）
![](E:/pictures/notes/nio.png)

## nio和零拷贝

[NIO好文](https://www.cnblogs.com/detectiveHLH/p/15927650.html#:~:text=%E6%8B%BF%E5%88%B0%E4%BA%86ServerSocketChannel%20%E6%88%91%E4%BB%AC%E5%B0%B1,%E5%B0%B1%E7%BB%AAIO%20%E4%BA%8B%E4%BB%B6%E6%89%8D%E8%83%BD%E9%80%9A%E8%BF%87)