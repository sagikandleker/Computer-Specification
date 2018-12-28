package com.example.ronig.myapplication.Memory;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ronig.myapplication.Activities.MainActivity;
import com.example.ronig.myapplication.Database.DataBaseHelper;
import com.example.ronig.myapplication.GPU.GPU_Main_Tab;
import com.example.ronig.myapplication.Objects.Memory_Object;
import com.example.ronig.myapplication.R;
import com.example.ronig.myapplication.SSD.SSD_Main_Tab;

public class Memory_Tab_1 extends Fragment {

    Button Add_Ram_Tab_1_Button;
    TextView text;
    TextView price;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_memory_tab_1, container, false);


        Add_Ram_Tab_1_Button = (Button) rootView.findViewById(R.id.Add_Ram_Tab1);
        text = (TextView) rootView.findViewById(R.id.Text_Ram_Tab1);
        price =(TextView) rootView.findViewById(R.id.Price_Ram_Tab1);

        Memory_Main_Tab.Insert(getActivity(), 1, text, price);

        Add_Ram_Tab_1_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Ram Selected",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(),MainActivity.class);
                startActivity(i);
                getActivity().finish();
                MainActivity.user_ram= new Memory_Object(text.getText().toString(),price.getText().toString());

            }
        });



        return rootView;
    }


}
