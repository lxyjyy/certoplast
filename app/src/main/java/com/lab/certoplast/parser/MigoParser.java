package com.lab.certoplast.parser;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.lab.certoplast.bean.Migo;
import com.lab.certoplast.bean.Response;

import java.lang.reflect.Type;

/**
 * Created by lxyjyy on 17/11/17.
 */

public class MigoParser extends BaseParser<Response> {

    @Override
    public Response parse(String paramString) throws JsonParseException {
        if (paramString != null)
        {

            Type founderListType = new TypeToken<Response<Migo>>(){}.getType();

            Response<Migo> migo = new Gson().fromJson(paramString, founderListType);

            return migo;

        }

        return null;
    }
}
