package com.zzlin.performance.async;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * @author zlin
 * @date 20220325
 */
@Slf4j
@RestController
@RequestMapping("platform")
public class RpcAsyncTestController {

    @Autowired
    private WebClient webClient;

    @Autowired
    private AsyncRestTemplate asyncRestTemplate;

    @GetMapping("test-webclient")
    public String testWebClient() {
        Mono<String> mono = webClient.get()
                .uri("http://localhost:8088/index/carousel")
                .retrieve()
                .bodyToMono(String.class);
        return mono.block();
    }

    @GetMapping("test-asyncRestTemplate")
    public String testAsyncRestTemplate() throws ExecutionException, InterruptedException {
        ListenableFuture<ResponseEntity<String>> future = asyncRestTemplate.getForEntity(
                "http://localhost:8088/index/carousel",
                String.class,
                1
        );
//        BodyInserters.fromObject()
//        ResponseEntity<String> stringResponseEntity = future.get();
//        String body = stringResponseEntity.getBody();
        future.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("调用失败");
            }

            @Override
            public void onSuccess(ResponseEntity<String> result) {
                log.error("调用成功 result = {}", result.getBody());
            }
        });
        return "end" + new Date().toString();
    }

}
