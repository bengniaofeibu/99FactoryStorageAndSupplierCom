package com.qiyuan.entity;

import com.qiyuan.Base.BaseRepMessge;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: tonghengzhen
 * Date: 2017/10/20
 * Time: 9:59
 */
public class LianTongSoapapiMessage {


    public static final String USER_NAME="panjuanjuan";

    public static final String PASS_WORD="shjj123456";

    public static final String REQUEST_LICENSE_KEY="23079646-c276-47e1-bf50-3200a7ce842e";

    public static final String NAME_SPACE_URI="http://api.jasperwireless.com/ws/schema";

    public static final String PREFIX="jws";

    public static final String REQUEST_MESSAGE_ID="TCE-100-ABC-34084";

    public static final String REQUEST_VERSION="1.0";

    public BaseRepMessge baseRepMessge;

    public BaseRepMessge getBaseRepMessge() {
        return baseRepMessge;
    }

    public void setBaseRepMessge(BaseRepMessge baseRepMessge) {
        this.baseRepMessge = baseRepMessge;
    }

    public LianTongSoapapiMessage(BaseRepMessge baseRepMessge) {
        this.baseRepMessge = baseRepMessge;
    }
}
