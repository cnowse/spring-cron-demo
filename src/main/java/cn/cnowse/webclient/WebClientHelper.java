package cn.cnowse.webclient;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * WebClient 增强
 *
 * @author Jeong Geol
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebClientHelper {

    private final WebClient webClient;

    /**
     * 发送 post 请求，没有返回值。
     *
     * @param uri 请求的完整 url
     * @param body 请求体
     * @author Jeong Geol
     */
    public void post(String uri, Object body) {
        try {
            webClient.post().uri(uri).bodyValue(body).retrieve().bodyToMono(Void.class).block();
        } catch (Exception e) {
            log.error("调用服务失败 [uri={}]", uri);
        }
    }

    /**
     * 发送 post 请求，指定返回值。
     *
     * @param uri 请求的完整 url
     * @param body 请求体
     * @param type 响应数据类型
     * @return type 参数指定的类型
     * @author Jeong Geol
     */
    public <T> T post(String uri, Object body, Class<T> type) {
        try {
            return webClient.post().uri(uri).bodyValue(body).retrieve().bodyToMono(type).block();
        } catch (Exception e) {
            log.error("调用服务失败 [uri={}]", uri);
            return null;
        }
    }

}
