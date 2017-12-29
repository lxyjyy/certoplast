package com.lab.certoplast.parser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lab.certoplast.bean.ProductSearch;
import com.lab.certoplast.utils.StringUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxyjyy on 17/11/16.
 */

public class ProductSearchParser extends BaseParser<ProductSearch>{

    @Override
    public ProductSearch parse(String paramString) throws IOException, XmlPullParserException {

        if (paramString != null)
        {
            String result = StringUtils.xmlParser(paramString);

            Type founderListType = new TypeToken<ArrayList<ProductSearch>>(){}.getType();

            List<ProductSearch> productSearches = new Gson().fromJson(result, founderListType);

            if (productSearches.size() > 0)
                return productSearches.get(0);
        }

        return null;
    }
}
