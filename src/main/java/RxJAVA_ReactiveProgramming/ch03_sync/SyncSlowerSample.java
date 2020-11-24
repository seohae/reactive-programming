package RxJAVA_ReactiveProgramming.ch03_sync;

import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

/**
 [RxJAVA의 비동기처리]
 RxJAVA에서는 각각의 처리 작업을 같은 스레드에서 처리할시, 데이터를 통지하는 측은
 데이터를 받아 처리하는 측의 처리 속도에 영향을 받게된다.
 데이터를 받는 측의 처리 속도가 느릴때는 데이터를 통지하는 측의 처리 속도에도 영향을 미친다.
 */
public class SyncSlowerSample {
    public static void main(String[] args) throws InterruptedException {
        /* 1000 밀리초마다 테이블을 통지하는Flowable을 interval 메서드로 실행한다해도,
           통지된 데이터를 받는 측의 처리 작업이 1000밀리초보다 길어지면 Flowable은 1000 밀리초마다
           데이터를 통지할 수 없다 */
        Flowable.interval(1000L, TimeUnit.MILLISECONDS)
                /* 데이터를 통지할 때의 시스템 시각을 출력 */
                .doOnNext(data -> System.out.println("emit :" + System.currentTimeMillis() + "밀리초 : " + data))
                /* 구독한다 */
                .subscribe(data -> Thread.sleep(2000L)); /* 데이터 통지에 영향을 준다 */

        /* 잠시 기다린다 */
        Thread.sleep(5000L);
    }
}
