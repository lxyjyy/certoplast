package com.lab.certoplast.parser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lab.certoplast.bean.Migo;
import com.lab.certoplast.utils.StringUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by lxyjyy on 17/11/17.
 */

public class MigoParser extends BaseParser<Migo> {

    @Override
    public Migo parse(String paramString) throws IOException, XmlPullParserException {
        if (paramString != null)
        {
            String result = StringUtils.xmlParser(paramString);

            Type founderListType = new TypeToken<List<Migo>>(){}.getType();

            List<Migo> migos = new Gson().fromJson(result, founderListType);

            if (migos.size() > 0)
                return migos.get(0);

        }

        return null;
    }
}
