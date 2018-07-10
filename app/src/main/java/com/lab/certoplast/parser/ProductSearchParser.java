package com.lab.certoplast.parser;

import com.google.gson.JsonParseException;
import com.lab.certoplast.bean.ProductSearch;

/**
 * Created by lxyjyy on 17/11/16.
 */

public class ProductSearchParser extends BaseParser<ProductSearch>{

    @Override
    public ProductSearch parse(String paramString) throws JsonParseException{

//        if (paramString != null)
//        {
//            String result = StringUtils.xmlParser(paramString);
//
//            Type founderListType = new TypeToken<ArrayList<ProductSearch>>(){}.getType();
//
//            List<ProductSearch> productSearches = new Gson().fromJson(result, founderListType);
//
//            if (productSearches.size() > 0)
//                return productSearches.get(0);
//        }

        return null;
    }
}
