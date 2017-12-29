package com.lab.certoplast.bean;


import com.lab.certoplast.app.AppException;

public abstract interface DataCallback<T> {
	public abstract void processData(T paramObject, boolean paramBoolean);

	public abstract void onFailure(AppException e);

	public abstract void onError(ErrorMessage error);

}
