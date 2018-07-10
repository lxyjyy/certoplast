package com.lab.certoplast.parser;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.lab.certoplast.bean.Response;
import com.lab.certoplast.bean.SaoMiao;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by lxyjyy on 17/11/23.
 */

public class SaomiaoListParser extends BaseParser<Response> {

    @Override
    public Response parse(String paramString) throws JsonParseException {

//
        if (paramString != null)
        {

            Type founderListType = new TypeToken<Response<List<SaoMiao>>>(){}.getType();

           Response saoMiaos = new Gson().fromJson(paramString, founderListType);

            return saoMiaos;

        }

        return null;

    }
}
