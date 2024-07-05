# basic-crawler
playing around with Java concurrency and multithreading in a simple crawler project.

Java has rich tools for concurrency and multithreading. We can easily create threads by extending the Thread class or implementing the Runnable interface. Alternatively, we can use the ExecutorService from the "java.util.concurrent" package to create a fixed pool of threads as I have done in this project (refer to class WebCrawler.java). CompletableFuture is a library for implementing asynchronous functionality. 
Java also provides thread-safe data structures like ConcurrentHashMap, CopyOnWriteList, ConcurrentLinkedQueue etc.
