package com.lab.certoplast.parser;

import com.google.gson.JsonParseException;
import com.lab.certoplast.bean.ResponseResult;

/**
 * Created by lxyjyy on 17/12/5.
 */

public class ResponseParser extends BaseParser<ResponseResult> {

    @Override
    public ResponseResult parse(String paramString) throws JsonParseException{
        if (paramString != null)
        {
            ResponseResult responseResult = new ResponseResult();
            responseResult.setResponse(paramString);

            return responseResult;
        }

        return null;
    }
}
