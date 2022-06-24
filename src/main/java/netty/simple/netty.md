# netty原理
![netty原理图](E:/pictures/notes/7mf0rtmuyh.jpg)

如上图所示，MainReactor对应bossGroup，subReactor对应workGroup，bossGroup只负责处理accept请求并生成selectionKey
返回给workGroup中的子线程中，并将其注册到selector。
    workGroup中的子线程包含pipeline（管道），每个管道都对应很多handler（一个handler链条，双端队列），当客户端发出非连接请求（读取/写入数据时）
bossGroup将请求直接给到workGroup中的线程，key.getChannel获取当前通道，通道进行处理。

1）Reactor 主线程 MainReactor 对象通过 select 监听客户端连接事件，收到事件后，通过 Acceptor 处理客户端连接事件。

2）当 Acceptor 处理完客户端连接事件之后（与客户端建立好 Socket 连接），MainReactor 将连接分配给 SubReactor。（即：MainReactor 只负责监听客户端连接请求，和客户端建立连接之后将连接交由 SubReactor 监听后面的 IO 事件。）

3）SubReactor 将连接加入到自己的连接队列进行监听，并创建 Handler 对各种事件进行处理。

4）当连接上有新事件发生的时候，SubReactor 就会调用对应的 Handler 处理。

5）Handler 通过 read 从连接上读取请求数据，将请求数据分发给 Worker 线程池进行业务处理。

6）Worker 线程池会分配独立线程来完成真正的业务处理，并将处理结果返回给 Handler。Handler 通过 send 向客户端发送响应数据。

7）一个 MainReactor 可以对应多个 SubReactor，即一个 MainReactor 线程可以对应多个 SubReactor 线程。

这种模式的优点是：

1）MainReactor 线程与 SubReactor 线程的数据交互简单职责明确，MainReactor 线程只需要接收新连接，SubReactor 线程完成后续的业务处理。

2）MainReactor 线程与 SubReactor 线程的数据交互简单， MainReactor 线程只需要把新连接传给 SubReactor 线程，SubReactor 线程无需返回数据。

3）多个 SubReactor 线程能够应对更高的并发请求。

这种模式的缺点是编程复杂度较高。但是由于其优点明显，在许多项目中被广泛使用，包括 Nginx、Memcached、Netty 等。

这种模式也被叫做服务器的 1+M+N 线程模式，即使用该模式开发的服务器包含一个（或多个，1 只是表示相对较少）连接建立线程+M 个 IO 线程+N 个业务处理线程。这是业界成熟的服务器程序设计模式。

![](C:/Users/17963/AppData/Local/Temp/ocmrm2pw9j.png)