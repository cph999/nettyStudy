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