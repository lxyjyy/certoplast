package com.lab.certoplast.parser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lab.certoplast.bean.WareHouse;
import com.lab.certoplast.utils.StringUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxyjyy on 17/11/16.
 */

public class WareHouseListParser extends BaseParser<List<WareHouse>>{

    @Override
    public List<WareHouse> parse(String paramString) throws IOException, XmlPullParserException {

        if (paramString != null)
        {
            String result = StringUtils.xmlParser(paramString);

            Type founderListType = new TypeToken<ArrayList<WareHouse>>(){}.getType();

            List<WareHouse> migoList = new Gson().fromJson(result, founderListType);

            return migoList;
        }

        return null;
    }
}
