import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * description:TODO
 *
 * @author caiqiang.wu
 * @create 2020/03/07
 **/
public class ReentryLockTest {

    static final Lock lock = new ReentrantLock();
    static final Condition condition = lock.newCondition();

    public static void main(String[] args) {
        startThread();
    }
    public static void forTest(){
        for(;;){
            System.out.println("======");
        }
    }

    public static void startThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        lock.lock();
                        System.out.println("thread-1 run ...");
                        try {
                            Thread.sleep(5000);
                            condition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    } finally {
                        lock.unlock();
                    }
                }
            }
        }, "thread-1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        lock.lock();
                        System.out.println("thread-2 run ...");
                        /*try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }*/
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }, "thread-2").start();
    }

}
