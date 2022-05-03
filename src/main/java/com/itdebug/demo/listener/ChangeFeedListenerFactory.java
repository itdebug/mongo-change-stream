package com.itdebug.demo.listener;

public interface ChangeFeedListenerFactory {

    void establishAndSubscribeToChangeStream();
    void cancelSubscription();
    String subscriptionStatus();

}

