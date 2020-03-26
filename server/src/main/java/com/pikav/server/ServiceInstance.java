package com.pikav.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.lang.reflect.Method;

/**
 * 表示一个具体服务
 *
 * @author pikav
 */

@Data
@AllArgsConstructor
public class ServiceInstance {

    private Object target;

    private Method method;

}
