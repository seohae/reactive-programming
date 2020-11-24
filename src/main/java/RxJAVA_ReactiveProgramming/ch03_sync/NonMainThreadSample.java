package RxJAVA_ReactiveProgramming.ch03_sync;

import io.reactivex.Flowable;
import io.reactivex.subscribers.ResourceSubscriber;

import java.util.concurrent.TimeUnit;

/**
 RxJAVA에서 비동기 처리를 할 때에는 생산자가 처리를 실행하는 스레드와
 데이터를 받는 측의 스레드를 모두 관리해야한다.
 이를 위해 RxJAVA는 생산자가 처리 작업을 실행하는 스레드의 종류를 설정할 수 있게 subscribeOn 메서드를,
 데이터를 받는 측이 스레드 종류를 설정할 수 있게 observeOn 메서드를 제공한다.
 이 스레드 종류를 설정할 때는 용도에 따라 스레드를 관리하는 스케줄러를 지정한다.
 */
public class NonMainThreadSample {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("start");

        /* Flowable : 메인스레드가 아닌 별도 스레드에서 작동한다
                      호출 이후, Flowable의 로직은 별도 스레드에 맡기고, 메인스레드의 바로 다음 로직을 실행한다.
        * */
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
               /* 구독한다 */
                .subscribe(new ResourceSubscriber<Long>() {
                    @Override
                    public void onNext(Long data) {
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

        /* Flowable의 호출이 끝나면 바로 실행된다 (Flowable은 별도 스레드에서 실행되어 영향이 없다) */
        System.out.println("end");

        /* 잠시 기다린다 */
        Thread.sleep(1000L);
    }
}
