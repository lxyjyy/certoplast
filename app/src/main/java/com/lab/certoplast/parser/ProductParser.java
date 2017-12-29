package com.lab.certoplast.parser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lab.certoplast.bean.Product;
import com.lab.certoplast.utils.StringUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxyjyy on 17/11/12.
 * 成品入库
 */

public class ProductParser extends BaseParser<Product> {

    @Override
    public Product parse(String paramString) throws IOException, XmlPullParserException {

        if (paramString != null)
        {
            String result = StringUtils.xmlParser(paramString);
            Type founderListType = new TypeToken<ArrayList<Product>>(){}.getType();

            List<Product> list = new Gson().fromJson(result, founderListType);

            if (list.size() > 0){
                return list.get(0);
            }
        }

        return null;

    }
}
