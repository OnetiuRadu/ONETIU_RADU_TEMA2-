package com.example.tema2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<UserModel> noteList = new ArrayList<>();
    private RecyclerView recyclerView;
    private UserAdapter mAdapter;
    private EditText markEditText, nameEditText;
    ArrayList<User> users = new ArrayList<>();

    public void setData(){
        if(users != null){
            users.clear();
            users.addAll(new ApplicationController().getAppDatabase().userDao().getAll());
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        markEditText = findViewById(R.id.markEditText);
        nameEditText = findViewById(R.id.nameEditText);

        recyclerView = findViewById(R.id.recyclerView);

        mAdapter = new UserAdapter(noteList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        setData();
        UserModel userModel;
        for(int i=0; i<users.size(); i++){
            String mark = String.valueOf(users.get(i).mark);
            userModel = new UserModel(users.get(i).firstName + " " +users.get(i).lastName, mark);
            noteList.add(userModel);
            mAdapter.notifyDataSetChanged();
        }



    }

    public void onClickAddUser(View view){
        final int mark = Integer.parseInt(markEditText.getText().toString());
        String[] split = nameEditText.getText().toString().split(" ");
        final String first = split[0];
        final String second = split[1];
        //UserModel userModel =  new UserModel(nameEditText.getText().toString(), markEditText.getText().toString());
        //noteList.add(userModel);
        //mAdapter.notifyDataSetChanged();
        User user = new User(first, second, Integer.parseInt(markEditText.getText().toString()));
        new UserRepository(getApplicationContext()).insertTask(user, new OnUserRepositoryActionListener() {
            @Override
            public void actionSucces() {
                Toast.makeText(getApplicationContext(), "Adaugat cu succes", Toast.LENGTH_SHORT).show();
                setData();
                //usersAdapter.notifyDataSetChanged();
                UserModel userModel;
                userModel = new UserModel(first + " " + second, String.valueOf(mark));
                noteList.add(userModel);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void actionFailed() {
                Toast.makeText(getApplicationContext(), "Nu a putut fi adaugat!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickRemoveUser(View view){
        /*for(int i=0; i<noteList.size(); i++){
            if(noteList.get(i).getNume().equals(nameEditText.getText().toString())){
                noteList.remove(i);
            }
        }*/
        String[] split = nameEditText.getText().toString().split(" ");
        final String first = split[0];
        final String second = split[1];
        User user = new UserRepository(getApplicationContext()).getUserByNameString(first, second);

        if (user != null) {
            new UserRepository(getApplicationContext()).deleteTask(user, new OnUserRepositoryActionListener() {
                @Override
                public void actionSucces() {
                    Toast.makeText(getApplicationContext(), "Sters cu succes", Toast.LENGTH_SHORT).show();
                    setData();
                    for(int i=0; i<noteList.size(); i++){
                        if(noteList.get(i).getNume().equals(nameEditText.getText().toString())){
                            noteList.remove(i);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void actionFailed() {
                    Toast.makeText(getApplicationContext(), "Nu a putut fi sters!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
            Toast.makeText(getApplicationContext(), "Nu exista!", Toast.LENGTH_SHORT).show();
    }
}


