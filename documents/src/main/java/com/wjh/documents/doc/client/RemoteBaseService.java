package com.wjh.documents.doc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId = "baseService",value = "wjh-base-server")
public interface RemoteBaseService {
    @PostMapping("/get")
    String get(RequestBody Object);
}
