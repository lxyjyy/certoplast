package com.lab.certoplast.parser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.lab.certoplast.bean.Scan;
import com.lab.certoplast.utils.StringUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxyjyy on 17/11/30.
 */

public class ScanParser extends BaseParser<List<Scan>> {

    @Override
    public List<Scan> parse(String paramString) throws IOException, XmlPullParserException {
        if (paramString != null)
        {
            String result = StringUtils.xmlParser(paramString);
            Type founderListType = new TypeToken<ArrayList<Scan>>(){}.getType();

            List<Scan> list = new Gson().fromJson(result, founderListType);
            if (list.size() > 0)
                return list;

        }

        return null;

    }
}
