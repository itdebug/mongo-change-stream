package com.itdebug.demo.listener;

import com.itdebug.demo.models.CustomerData;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmployeeChangeFeedListener extends AbstractChangeFeedListener<CustomerData> {

    public EmployeeChangeFeedListener(ReactiveMongoTemplate reactiveMongoTemplate) {
        super(reactiveMongoTemplate);
        this.collectionName = "elc_member";
    }
}

