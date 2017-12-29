package com.lab.certoplast.parser;

import com.lab.certoplast.bean.ResponseResult;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by lxyjyy on 17/12/5.
 */

public class ResponseParser extends BaseParser<ResponseResult> {

    @Override
    public ResponseResult parse(String paramString) throws IOException, XmlPullParserException {
        if (paramString != null)
        {
            ResponseResult responseResult = new ResponseResult();
            responseResult.setResponse(paramString);

            return responseResult;
        }

        return null;
    }
}
