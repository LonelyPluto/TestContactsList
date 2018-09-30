package com.lonelypluto.testcontactslist.utils;

import com.lonelypluto.testcontactslist.bean.ContactsBean;

import java.util.Comparator;

/**
 * @Description: 根据字母排序
 * @author: ZhangYW
 * @time: 2018/9/30 9:14
 */
public class PinyinComparator implements Comparator<ContactsBean> {

	public int compare(ContactsBean o1, ContactsBean o2) {
		if (o1.getLetters().equals("@")
				|| o2.getLetters().equals("#")) {
			return -1;
		} else if (o1.getLetters().equals("#")
				|| o2.getLetters().equals("@")) {
			return 1;
		} else {
			return o1.getLetters().compareTo(o2.getLetters());
		}
	}

}
