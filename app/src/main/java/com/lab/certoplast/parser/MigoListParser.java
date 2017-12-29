package com.lab.certoplast.parser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lab.certoplast.bean.Migo;
import com.lab.certoplast.utils.StringUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxyjyy on 17/11/16.
 */

public class MigoListParser extends BaseParser<List<Migo>>{

    @Override
    public List<Migo> parse(String paramString) throws IOException, XmlPullParserException {

        if (paramString != null)
        {

            String param = StringUtils.xmlParser(paramString);

            Type founderListType = new TypeToken<ArrayList<Migo>>(){}.getType();

            List<Migo> migoList = new Gson().fromJson(param, founderListType);

            return migoList;
        }

        return null;
    }
}
