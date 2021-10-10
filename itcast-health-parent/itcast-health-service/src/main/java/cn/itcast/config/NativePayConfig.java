package cn.itcast.config;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class NativePayConfig implements InitializingBean{
    @Value("${pay.wxpay.native.appID}")
    private String appID;
    @Value("${pay.wxpay.native.mchID}")
    private String mchID;
    @Value("${pay.wxpay.native.key}")
    private String key;
    @Value("${pay.wxpay.native.payNotifyUrl}")
    private String  payNotifyUrl;

    public static  String WX_PAY_APPID;
    public static String WX_PAY_MACHID;
    public static  String WX_PAY_KEY;
    public static  String WX_PAY_PAYNOTIFY_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        WX_PAY_APPID=appID;
        WX_PAY_MACHID=mchID;
        WX_PAY_KEY=key;
        WX_PAY_PAYNOTIFY_URL=payNotifyUrl;
    }
}
