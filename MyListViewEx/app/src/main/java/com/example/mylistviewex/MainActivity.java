package com.example.mylistviewex;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Car> carArrayList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            carArrayList.add(new Car("자동차 " + i, "engine " + i));
        }


        ListViewAdapter adapter = new ListViewAdapter(carArrayList, this);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        // 아이템리스너를 사용하는 방법
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("TAG", "selected Id : " + i);
                Toast.makeText(view.getContext(), "selected item : " + i, LENGTH_SHORT).show();
            }
        });

    }
}


class ListViewAdapter extends BaseAdapter {

    private ArrayList<Car> carArrayList;
    private Context context;

    public ListViewAdapter(ArrayList<Car> carArrayList, Context context) {
        this.carArrayList = carArrayList;
        this.context = context;
    }

    // 전체 갯수를 알려 준다.
    @Override
    public int getCount() {
        return carArrayList.size();
    }

    // object 하나를 알려 준다. -> 화면에 표시할 데이터
    @Override
    public Object getItem(int i) {
        return carArrayList.get(i);
    }

    // 해당 item 의 position 또는  id
    @Override
    public long getItemId(int i) {

        return i;
    }

    // 그리고자 하는 아이템 리스트의 하나
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        // 재활용하기 위한 로직
        View myItemView;
        CarViewHolder carViewHolder;
        if (view == null) {
            Log.d("TAG", "실행확인 !!!! : " + i);
            carViewHolder = new CarViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            myItemView = inflater.inflate(R.layout.item_view, null);
            carViewHolder.carNameTv = myItemView.findViewById(R.id.carName);
            carViewHolder.engineTv = myItemView.findViewById(R.id.engine);
            // 중요 ! 구분할 수 있다록 태그를 달아 놓는다.
            myItemView.setTag(carViewHolder);
        } else {
            carViewHolder = (CarViewHolder) view.getTag();
            myItemView = view;
        }

        carViewHolder.carNameTv.setText(carArrayList.get(i).name);
        carViewHolder.engineTv.setText(carArrayList.get(i).engine);


//        Log.d("TAG", "실행확인 !!!! : " + i);
//        LayoutInflater inflater = LayoutInflater.from(context);
//        view = inflater.inflate(R.layout.item_view, null);
//
//        TextView carNameTextView = view.findViewById(R.id.carName);
//        TextView engineTextView = view.findViewById(R.id.engine);
//
//        carNameTextView.setText(carArrayList.get(i).name);
//        engineTextView.setText(carArrayList.get(i).engine);
        return myItemView;
    }
}

// 뷰 홀더 사용 사용 - 생성된 뷰를 담아 놓고 사용 (재사용하기 위해 만든다)
class CarViewHolder {
    TextView carNameTv;
    TextView engineTv;
}
