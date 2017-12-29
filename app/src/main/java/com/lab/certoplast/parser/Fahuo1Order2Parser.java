package com.lab.certoplast.parser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lab.certoplast.bean.Fahuo1Order2;
import com.lab.certoplast.utils.StringUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by lxyjyy on 17/11/23.
 */

public class Fahuo1Order2Parser extends BaseParser<Fahuo1Order2> {

    @Override
    public Fahuo1Order2 parse(String paramString) throws IOException, XmlPullParserException {


        if (paramString != null)
        {
            String result = StringUtils.xmlParser(paramString);

            Type founderListType = new TypeToken<List<Fahuo1Order2>>(){}.getType();

            List<Fahuo1Order2> migos = new Gson().fromJson(result, founderListType);

            if (migos.size() > 0)
                return migos.get(0);

        }

        return null;

    }
}
