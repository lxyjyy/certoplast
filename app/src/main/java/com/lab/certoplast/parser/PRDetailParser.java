package com.lab.certoplast.parser;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.lab.certoplast.bean.PRDetail;
import com.lab.certoplast.bean.Response;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by lxyjyy on 17/11/27.
 */

public class PRDetailParser extends BaseParser<Response> {

    @Override
    public Response parse(String paramString) throws JsonParseException {

//
        if (paramString != null)
        {
            Type founderListType = new TypeToken<Response<List<PRDetail>>>(){}.getType();

            Response response = new Gson().fromJson(paramString, founderListType);

            return response;
        }

        return null;

    }

}
