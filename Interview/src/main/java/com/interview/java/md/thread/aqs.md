## 一、什么是AQS

AQS(AbstractQueuedSynchronizer 抽象队列同步器)，AQS是用来构建锁和同步器的框架。例如：ReentrantLock、Semaphore。

## 二、AQS的核心思想

AQS的核心思想是，如果被请求的共享资源（锁）处于空闲状态，则将改线程设置为有效工作线程，并将共享资源设置为锁定状态；如果被请求的共享
资源被占用，则需要提供一套线程阻塞等待及唤醒时分配锁的机制，这个机制AQS使用CLH队列锁实现，将暂时获取不到锁的线程加入到等待队列。

> CLH(Craig,Landin,and Hagersten)是一个虚拟的双向队列（即不存在队列实例，仅存在节点之间的关联关系）。AQS是将请求共享资源的线程
> 封装成一个CLH锁队列的一个节点实现锁的分配。

AQS使用一个int变量来表示同步状态，通过内置的FIFO队列实现获取资源线程的排队工作，AQS使用CAS对同步状态进行原子修改。

```java

private volatile int state; //共享变量，使用volatile修饰保证线程可见性

//返回同步状态的当前值
protected final int getState() {
        return state;
        }
// 设置同步状态的值
protected final void setState(int newState) {
        state = newState;
        }
//原子地(CAS操作)将同步状态值设置为给定值update如果当前同步状态的值等于expect(期望值)
protected final boolean compareAndSetState(int expect, int update) {
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
        }

```
## 三、AQS对资源的共享方式

AQS定了两种资源共享方式：

1. 独占式（Exclusive）：对于共享资源同一时刻，只能有一个线程进行操作，如：ReentryLock。独占式又分为两种

- 公平锁：按照线程在队列中的排队顺序，先到者先拿到锁

- 非公平锁：当线程要获取锁时，无视队列顺序直接去抢锁，谁抢到就是谁的

2. 共享式（Share）：

多个线程可同时执行，如：CountDownLatch。Semaphore、CountDownLatCh、 CyclicBarrier、ReadWriteLock 我们都会在后面讲到。


# 参(chao)考(xi)

> 著作权归@pdai所有
> 
> 原文链接：https://pdai.tech/md/java/thread/java-thread-x-lock-AbstractQueuedSynchronizer.html