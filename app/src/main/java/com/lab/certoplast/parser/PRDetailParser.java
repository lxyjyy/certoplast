package com.lab.certoplast.parser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lab.certoplast.bean.PRDetail;
import com.lab.certoplast.utils.StringUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxyjyy on 17/11/27.
 */

public class PRDetailParser extends BaseParser<List<PRDetail>> {

    @Override
    public List<PRDetail> parse(String paramString) throws IOException, XmlPullParserException {


        if (paramString != null)
        {
            String result = StringUtils.xmlParser(paramString);
            Type founderListType = new TypeToken<ArrayList<PRDetail>>(){}.getType();

            List<PRDetail> list = new Gson().fromJson(result, founderListType);

            return list;
        }

        return null;

    }

}
