package com.lonelypluto.testcontactslist.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lonelypluto.testcontactslist.R;
import com.lonelypluto.testcontactslist.bean.ContactsBean;
import java.util.List;

/**
 * @Description: adapter
 * @author: ZhangYW
 * @time: 2018/9/11 14:54
 */
public class ContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<ContactsBean> mList;
    public ContactsAdapter(Context context, List<ContactsBean> list){
        this.mContext = context;
        this.mList = list;

    }

    /**
     * 提供给Activity刷新数据
     * @param list
     */
    public void updateList(List<ContactsBean> list){
        this.mList = list;
        notifyDataSetChanged();
    }

    /**
     * 渲染具体的ViewHolder
     * @param parent ViewHolder的容器
     * @param viewType 一个标志，我们根据该标志可以实现渲染不同类型的ViewHolder
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.contacts_recycler_item,parent,false);
        return new ContactsViewHolder(itemView);
    }
    /**
     * 绑定ViewHolder的数据。
     * @param holder
     * @param position 数据源list的下标
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ContactsBean bean = mList.get(position);
        if (null == bean)
            return;
        ContactsViewHolder viewHolder = (ContactsViewHolder) holder;
        int section = getSectionForPosition(position);
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.tv_tag.setVisibility(View.VISIBLE);
            viewHolder.tv_tag.setText(bean.getLetters());
        } else {
            viewHolder.tv_tag.setVisibility(View.GONE);
        }

        viewHolder.tv_name.setText(bean.getName());

        viewHolder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,bean.getName(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    public int getSectionForPosition(int position) {
        return mList.get(position).getLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = mList.get(i).getLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    public class  ContactsViewHolder extends  RecyclerView.ViewHolder {
       private TextView tv_tag;
       private RelativeLayout rl;
       private ImageView iv_head;
       private TextView tv_name;

        public ContactsViewHolder(View itemView){
            super(itemView);
            tv_tag = itemView.findViewById(R.id.recycler_item_tv_tag);
            rl = itemView.findViewById(R.id.recycler_item_rl);
            iv_head = itemView.findViewById(R.id.recycler_item_iv_header);
            tv_name = itemView.findViewById(R.id.recycler_item_tv_name);
        }
    }
}
