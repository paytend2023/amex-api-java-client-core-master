package com.paytend.models.trans;

/**
 * @author Sunny
 * @create 2023/8/22 21:45
 */
public interface XmlRequest {
    /**
     * 生成请求的xml
     *
     * @return xmlStr
     */
    String toXml();
}
