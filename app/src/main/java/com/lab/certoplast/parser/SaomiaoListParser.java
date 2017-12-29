package com.lab.certoplast.parser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lab.certoplast.bean.SaoMiao;
import com.lab.certoplast.utils.StringUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by lxyjyy on 17/11/23.
 */

public class SaomiaoListParser extends BaseParser<List<SaoMiao>> {

    @Override
    public List<SaoMiao> parse(String paramString) throws IOException, XmlPullParserException {


        if (paramString != null)
        {
            String result = StringUtils.xmlParser(paramString);

            Type founderListType = new TypeToken<List<SaoMiao>>(){}.getType();

            List<SaoMiao> saoMiaos = new Gson().fromJson(result, founderListType);

            if (saoMiaos.size() > 0)
                return saoMiaos;

        }

        return null;

    }
}
