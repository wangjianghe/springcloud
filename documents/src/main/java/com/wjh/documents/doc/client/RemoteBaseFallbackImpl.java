package com.wjh.documents.doc.client;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Component
public class RemoteBaseFallbackImpl implements RemoteBaseService {
    @Setter
    private Throwable cause;

    @Override
    public String get(RequestBody Object) {
        log.error("msg",cause);
        return "-1";
    }
}
