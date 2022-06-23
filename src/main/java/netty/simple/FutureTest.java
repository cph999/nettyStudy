package netty.simple;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

/**
 * 开启10个线程计算0-100的和
 */
public class FutureTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService pool = Executors.newFixedThreadPool(5);
        ArrayList<FutureTask<AtomicReference<Integer>>> futureTasks = new ArrayList<>();
        IntStream.range(0,10).forEach((i) -> {
            FutureTask<AtomicReference<Integer>> task = new FutureTask<>(() -> {
                System.out.println(i + "：线程启动");
                AtomicReference<Integer> result = new AtomicReference<>(0);
                IntStream.range(i * 10,(i + 1)* 10).forEach((j) -> {
                    result.updateAndGet(v -> v +  j);
                });
                return result;
            });
            futureTasks.add(task);
            pool.submit(task);
        });

        AtomicReference<Integer> result = new AtomicReference<>(0);
        for(FutureTask<AtomicReference<Integer>> task: futureTasks){
            //FutureTask的get方法会自动阻塞,直到获取计算结果为止
            result.updateAndGet(v -> {
                try {
                    System.out.println(task.get().get());
                    return v + task.get().get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        System.out.println(result.get() + 100);
        Thread.sleep(1000);
        pool.shutdown();
    }
}
