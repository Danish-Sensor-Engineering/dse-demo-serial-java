package dse.cli.serial;

import dse.lib.Measurement;
import java.util.concurrent.Flow;


public class DataSubscriber implements Flow.Subscriber<Measurement> {

    private Flow.Subscription subscription;


    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        System.out.println("onSubscribe");
        this.subscription = subscription;
        subscription.request(1);    // Ask for next result
    }


    @Override
    public void onNext(Measurement measurement) {

        // Business logic with measurement result

        if(measurement.hasError()) {
            System.err.printf("error: %d%n", measurement.errorCode());
        } else if(measurement.hasDistance()){
            System.out.printf("distance: %s%n", measurement.distance());
        } else {
            System.out.printf("profiles: %s%n", measurement.getCoordinates().length);
        }

        // Ask for next result
        subscription.request(1);
    }


    @Override
    public void onError(Throwable throwable) {
        System.err.println(throwable.getMessage());
    }


    @Override
    public void onComplete() {
        // Cleanup
    }

}
