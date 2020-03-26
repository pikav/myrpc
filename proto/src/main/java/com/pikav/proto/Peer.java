package com.pikav.proto;

import lombok.AllArgsConstructor;
import lombok.Data;

/***
 * 表示网络传输的一个端点
 *
 * @author pikav
 */

@Data
@AllArgsConstructor
public class Peer {

    private String host;

    private int port;

}
