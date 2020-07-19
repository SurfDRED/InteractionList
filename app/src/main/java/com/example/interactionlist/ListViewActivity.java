package com.example.interactionlist;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ListViewActivity extends AppCompatActivity  {
    SharedPreferences myBook;
    static final String MY_BOOK = "my_book";
    static final String MY_BOOK_TEXT = "my_text";
    private String eText;    //текст сохраненный в Strings как эталон
    private static SimpleAdapter simpleAdapter;
    private static ListView list;
    private static SwipeRefreshLayout swipeLayout;
    DataList dataList = new DataList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        list = findViewById(R.id.list);
        swipeLayout = findViewById(R.id.swipe_refresh_layout);
        myBook = getSharedPreferences(MY_BOOK, MODE_PRIVATE);
        dataList.sText = myBook.getString(MY_BOOK_TEXT, "");
        eText = getString(R.string.large_text);
        // сравним эталон с SharedPreferences
        if(!dataList.sText.equals(eText)){
            SharedPreferences.Editor myEditor = myBook.edit();
            myEditor.putString(MY_BOOK_TEXT, eText);
            myEditor.apply();
        }
        // игнорируем при повороте
        if(dataList.content.size() == 0){
            dataList.updateList();
        }
        simpleAdapter = new SimpleAdapter(this, dataList.content, R.layout.block, new String[] {dataList.TITLE,dataList.SUBTITLE}, new int[] {R.id.top_block, R.id.bottom_block});
        list.setAdapter(simpleAdapter);
        // обработка свайпа
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dataList.content.clear();
                dataList.updateList();
                simpleAdapter.notifyDataSetChanged();
                swipeLayout.setRefreshing(false);
            }
        });
        // обработка выбранного элемента
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dataList.content.remove(position);
                simpleAdapter.notifyDataSetChanged();
            }
        });
    }
}