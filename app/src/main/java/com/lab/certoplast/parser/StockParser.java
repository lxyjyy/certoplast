package com.lab.certoplast.parser;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.lab.certoplast.bean.Response;
import com.lab.certoplast.bean.Stock;

import java.lang.reflect.Type;

/**
 * Created by lxyjyy on 17/11/17.
 */

public class StockParser extends BaseParser<Response> {

    @Override
    public Response parse(String paramString) throws JsonParseException {
        if (paramString != null)
        {

            Type founderListType = new TypeToken<Response<Stock>>(){}.getType();

            Response<Stock> response = new Gson().fromJson(paramString, founderListType);

            return response;

        }

        return null;
    }
}
