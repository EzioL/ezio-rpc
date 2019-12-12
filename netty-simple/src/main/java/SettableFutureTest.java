import com.google.common.util.concurrent.SettableFuture;

import java.util.concurrent.ExecutionException;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/12/12 4:02 下午
 * @desc:
 */
public class SettableFutureTest {

    public static void main(String[] args) throws InterruptedException {


        SettableFuture<String> settableFuture = SettableFuture.create();

        new Thread(() -> {
            try {
                System.out.println("--Thread1 start--");
                System.out.println("Thread1 " + settableFuture.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(2000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("--Thread2 start--");
                settableFuture.set("ezio");
            }
        }).start();
    }
}
