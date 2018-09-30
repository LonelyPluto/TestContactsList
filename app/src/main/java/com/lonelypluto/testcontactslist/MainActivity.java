package com.lonelypluto.testcontactslist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.TextView;

import com.lonelypluto.testcontactslist.adapter.ContactsAdapter;
import com.lonelypluto.testcontactslist.bean.ContactsBean;
import com.lonelypluto.testcontactslist.utils.PinyinComparator;
import com.lonelypluto.testcontactslist.utils.PinyinUtil;
import com.lonelypluto.testcontactslist.widget.SearchEditText;
import com.lonelypluto.testcontactslist.widget.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description: 通讯录
 * @author: ZhangYW
 * @time: 2018/9/30 9:13
 */
public class MainActivity extends AppCompatActivity {

    private SideBar sideBar;// 右侧字母
    private TextView tv_dialog;// 滑动字母提示
    private SearchEditText searchEditText;// 搜索
    private RecyclerView rv;// 人员列表
    private LinearLayoutManager linearLayoutManager;
    private ContactsAdapter adapter;// 人员adapter
    private List<ContactsBean> list;// 人员数组
    private PinyinComparator pinyinComparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {

        pinyinComparator = new PinyinComparator();

        tv_dialog = (TextView) findViewById(R.id.main_tv_dialog);
        sideBar = (SideBar) findViewById(R.id.main_sideBar);
        sideBar.setTextView(tv_dialog);
        //设置右侧SideBar触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    linearLayoutManager.scrollToPositionWithOffset(position, 0);
                }

            }
        });

        list = loadData(getResources().getStringArray(R.array.data_list));
        // 根据a-z进行排序源数据
        Collections.sort(list, pinyinComparator);
        //创建人员列表adapter
        adapter = new ContactsAdapter(this, list);
        rv = (RecyclerView) findViewById(R.id.main_rv);
        rv.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapter);

        searchEditText = (SearchEditText) findViewById(R.id.main_filter_edit);
        //根据输入框输入值的改变来过滤搜索
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 为RecyclerView填充数据
     *
     * @param date
     * @return
     */
    private List<ContactsBean> loadData(String[] date) {
        List<ContactsBean> mContactsList = new ArrayList<>();
        for (int i = 0; i < date.length; i++) {
            ContactsBean contactsBean = new ContactsBean();
            contactsBean.setName(date[i]);
            //汉字转换成拼音
            String pinyin = PinyinUtil.getPingYin(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                contactsBean.setLetters(sortString.toUpperCase());
            } else {
                contactsBean.setLetters("#");
            }
            mContactsList.add(contactsBean);
        }
        return mContactsList;
    }

    /**
     * 根据输入框中的值来过滤数据并更新RecyclerView
     *
     * @param filterStr 搜索的内容
     */
    private void filterData(String filterStr) {
        List<ContactsBean> filterDateList = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = list;
        } else {
            filterDateList.clear();
            for (ContactsBean contactsBean : list) {
                String name = contactsBean.getName();
                if (name.indexOf(filterStr.toString()) != -1 ||
                        PinyinUtil.getFirstSpell(name).startsWith(filterStr.toString())
                        // 不区分大小写
                        || PinyinUtil.getFirstSpell(name).toLowerCase().startsWith(filterStr.toString())
                        || PinyinUtil.getFirstSpell(name).toUpperCase().startsWith(filterStr.toString())
                        ) {
                    filterDateList.add(contactsBean);
                }
            }
        }
        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateList(filterDateList);
    }
}
