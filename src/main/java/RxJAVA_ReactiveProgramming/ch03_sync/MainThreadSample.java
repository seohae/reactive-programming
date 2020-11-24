package RxJAVA_ReactiveProgramming.ch03_sync;

import io.reactivex.Flowable;
import io.reactivex.subscribers.ResourceSubscriber;

public class MainThreadSample {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("start");

        /* Flowable : 메인스레드에서 작동한다 */
        Flowable.just(1, 2, 3)
               /* 구독한다 */
                .subscribe(new ResourceSubscriber<Integer>() {
                    @Override
                    public void onNext(Integer data) {
                        String threadName = Thread.currentThread().getName();
                        System.out.println(threadName + ": " + data);
                    }

                    @Override
                    public void onError(Throwable t) {
                       t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        String threadName = Thread.currentThread().getName();
                        System.out.println(threadName + ": 완료");
                    }
                });

        /* Flowable이 메인스레드에서 작동하기 때문에 Flowable 처리가 끝난 이후에 실행된다 */
        System.out.println("end");

        /* 잠시 기다린다 */
        Thread.sleep(1000L);
    }
}
