package com.anxpp.commonintents.utils;

/**
 * SDK版本低于实际所需
 * 其实不应该出现这种异常，app设计时就应考虑兼容性并在UI做调整，此处仅作测试用
 * Created by anxpp on 2016/3/8.
 * @author anxpp.com
 */
public class SdkException extends Exception{
    public SdkException(String e){
        super(e);
    }
}
