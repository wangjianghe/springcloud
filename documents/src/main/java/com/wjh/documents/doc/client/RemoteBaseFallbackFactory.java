package com.wjh.documents.doc.client;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteBaseFallbackFactory implements FallbackFactory<RemoteBaseService> {

    @Override
    public RemoteBaseService create(Throwable cause) {
        return null;
    }
}
