## 多线程

### 线程池

线程池各个接口

```java
public interface Executor {
    void execute(Runnable command);
}

public interface ExecutorService extends Executor {
    void shutdown();
    List<Runnable> shutdownNow();

    boolean isShutdown();
    boolean isTerminated();

    <T> Future<T> submit(Callable<T> task);
    <T> Future<T> submit(Runnable task, T result);
    Future<?> submit(Runnable task);
}

public class Executors {
    public static ExecutorService newCachedThreadPool() {
            return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, 
                            new SynchronousQueue<Runnable>());
    }
}
```

Executors 提供四种线程池

- newCachedThreadPool

    是一个可根据需要创建新线程的线程池，在以前构造的线程可用时将重用它们。对于执行很多短期异步任务的程序而言，这些线程池通常可提高程序性能。
    调用 execute() 将重用之前构造的线程（如果线程可用）。如果现有线程没有可用的，则创建一个新线程并添加到池中。
    终止并从缓存中移除那些已有 60 秒钟未被使用的线程。因此，长时间保持空闲的线程池不会使用任何资源
- newSingleThreadExecutor

    创建一个单线程池，也就是该线程池只有一个线程在工作，所有的任务是串行执行的，如果这个唯一的线程因为异常结束，
    那么会有一个新的线程来替代它，此线程池保证所有任务的执行顺序按照任务的提交顺序执行
- newFixedThreadPool

    固定大小的线程池，每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小，
    线程池的大小一旦达到最大值就会保持不变，如果某个线程因为执行异常而结束，那么线程池会补充一个新线程
- newScheduledThreadPool

    一个大小无限的线程池，支持定时以及周期性执行任务
    

ThreadPoolExecutor 线程池相关参数

```
public ThreadPoolExecutor(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveTime,
                          TimeUnit unit,
                          BlockingQueue<Runnable> workQueue,
                          ThreadFactory threadFactory,
                          RejectedExecutionHandler handler) {}
```

- corePoolSize

    线程池的核心线程数，一般情况下不管有没有任务都会一直在线程池中一直存活，只有在 ThreadPoolExecutor 中的方法 allowCoreThreadTimeOut(boolean value) 设置为 true 时，
    闲置的核心线程会存在超时机制，如果在指定时间没有新任务来时，核心线程也会被终止，而这个时间间隔由第3个属性 keepAliveTime 指定
- maximumPoolSize

    线程池所能容纳的最大线程数，当活动的线程数达到这个值后，后续的新任务将会被阻塞
- keepAliveTime
    
    控制线程闲置时的超时时长，超过则终止该线程。一般情况下用于非核心线程，只有在 ThreadPoolExecutor 中的方法 
    allowCoreThreadTimeOut(boolean value) 设置为 true时，也作用于核心线程
- unit

    用于指定 keepAliveTime 参数的时间单位
- workQueue
    
    线程池的任务队列，通过线程池的 execute(Runnable command) 方法会将任务 Runnable 存储在队列中
- threadFactory 线程工厂

    可以定制线程工厂，例如添加线程异常处理(UncaughtExceptionHandler)，设置守护线程
- handler 任务队列满了的拒绝策略（默认抛出异常）

### BlockingQueue

阻塞队列

- ArrayBlockingQueue
- LinkedBlockingQueue
- PriorityBlockingQueue
- SynchronousQueue

方法

|方法名|作用|是否阻塞|行为|
|----|------|----|----|
|add|添加元素到队列|否|没有多余空间会抛异常。成功添加返回false|
|put|添加元素到队列|是|一直等待，直到有可以的空间|
|offer|添加元素到队列|否|如果当前有空间就添加成功，否则返回false|
|offer(超时)|添加元素到队列|否|如果当前没有可用空间，则等待指定的时间。超时返回false|
|take|获取并删除队列队首元素|是|如果当前队列为空，则一直等待，直到队列非空|
|poll|获取并删除队列队首元素|是|如果当前队列为空，则等待指定的时间，超时返回null|


