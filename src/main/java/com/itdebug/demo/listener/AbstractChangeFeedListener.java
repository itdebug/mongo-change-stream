package com.itdebug.demo.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ChangeStreamOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.util.StringUtils;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class AbstractChangeFeedListener<T> extends GenericOperationSuper<T> implements
        ApplicationListener<ContextRefreshedEvent>, ChangeFeedListenerFactory {

    private static Disposable disposable = null;
    private static List<String> operationType = new ArrayList<>();
    protected String collectionName;


    static {
        operationType.add("insert");
        operationType.add("update");
        operationType.add("replace");
    }

    private ReactiveMongoTemplate reactiveMongoTemplate;

    public AbstractChangeFeedListener(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        establishAndSubscribeToChangeStream();
    }

    public void establishAndSubscribeToChangeStream() {
        if(StringUtils.isEmpty(collectionName)) {
            return;
        }

        try {
            Flux<ChangeStreamEvent<T>> flux = reactiveMongoTemplate
                    .changeStream(collectionName, ChangeStreamOptions.
                            builder().filter(
                            Aggregation.newAggregation(
                                    Aggregation.match(Criteria.where("operationType").in(operationType)),
                                    Aggregation.project("_id", "fullDocument", "ns", "documentKey")
                            )
                    ).build(), getEntityClass());

            disposable = flux.subscribe(membershipChangeStreamEvent -> {
                System.out.println(membershipChangeStreamEvent.getRaw().toString());
                System.out.println(membershipChangeStreamEvent.getBody());

            });
            System.out.println("is disposable null ? "+(null == disposable));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    public void cancelSubscription() {

        if(null != disposable && !disposable.isDisposed()) {
            disposable.dispose();
            System.out.println("Membership changes Subscription listener cancelled. Actual Status "+disposable.isDisposed());
        }

    }

    public String subscriptionStatus() {
        if(null != disposable ) {
            return disposable.isDisposed() + " -- " + disposable.toString();
        }
        return "Not Subscribed";
    }
}

