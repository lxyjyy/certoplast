package com.lab.certoplast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lab.certoplast.R;
import com.lab.certoplast.bean.WareHouse;
import com.lab.certoplast.utils.ActivityUtil;

import java.util.List;

public class ShowWareHouseAdapter extends BaseContentAdapter<WareHouse> {

	private Context context;
	private List<WareHouse> list;

	public ShowWareHouseAdapter(Context context, List<WareHouse> list) {
		super(context, list);

		this.context = context;
		this.list = list;

	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.show_area_item, parent, false);
		}

		TextView tv_title = ActivityUtil.get(convertView, R.id.mytextview);
		ImageView iv_select = ActivityUtil.get(convertView, R.id.iv_select);
//		TextView tv_ishas = ActivityUtil.get(convertView, R.id.tv_ishas);

		final WareHouse area = list.get(position);
		// 区域选择
		if (area.isFlag()) {// 选中
			iv_select.setBackgroundResource(R.mipmap.button_check);
		} else {
			// 未选中
			iv_select.setBackgroundResource(R.mipmap.button_uncheck);
		}

		tv_title.setText(area.getWareHouse_Set());

		return convertView;
	}

}
