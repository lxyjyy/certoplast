package com.lab.certoplast.parser;

import com.google.gson.JsonParseException;

public abstract class BaseParser<T> {

	public abstract T parse(String paramString) throws JsonParseException;
}
