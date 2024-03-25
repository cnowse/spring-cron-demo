package cn.cnowse.web;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 项目启动，数据初始化
 *
 * @author Jeong Geol
 */
@Component // 放入容器，方便调用容器中其他 bean 来处理业务
@RequiredArgsConstructor
public class DataInitializer {

    @Transactional // 如果调用了多个 bean，来初始化数据，可以在这里统一进行事务管理
    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        // 监听 ApplicationReadyEvent 事件，该事件发出后，Spring 基本初始化完毕，但项目还没有真正启动。
        // 这时候可以在这里初始化一些数据。比如，从 csv 文件中读出数据，直接入库。
    }

}
