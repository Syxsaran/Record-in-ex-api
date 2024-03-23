package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MainAdapter mainAdapter;
    FloatingActionButton floatingActionButton;
    TextView tvItemCount, tvTotalIncome, tvTotalExpense, tvBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // กำหนด LayoutManager ให้กับ RecyclerView
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // อ้างอิง TextView ที่ใช้แสดงข้อมูลเกี่ยวกับยอดรวมรายการ รายรับ รายจ่าย และยอดคงเหลือ
        tvItemCount = findViewById(R.id.tvItemCount);
        tvTotalIncome = findViewById(R.id.tvTotalIncome);
        tvTotalExpense = findViewById(R.id.tvTotalExpense);
        tvBalance = findViewById(R.id.tvBalance);
        // คำนวณและแสดงรวมรายการ รายรับ และรายจ่าย และยอดคงเหลือ
        calculateTotalIncomeAndExpense();
        // กำหนดค่า FirebaseRecyclerOptions และสร้าง Adapter สำหรับ RecyclerView
        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("record"), MainModel.class)
                        .build();
        mainAdapter = new MainAdapter(options);
        recyclerView.setAdapter(mainAdapter);
        // กำหนดการทำงานเมื่อผู้ใช้คลิกปุ่ม FloatingActionButton
        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddActivity.class));
            }
        });
        // นับจำนวนรวมของรายการทั้งหมด
        countTotalItems();
    }
    // นับจำนวนรวมของรายการทั้งหมดและแสดงผลบน TextView
    private void countTotalItems() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long itemCount = dataSnapshot.getChildrenCount();
                String itemCountText = "Total Items: " + itemCount;
                tvItemCount.setText(itemCountText);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error fetching data", databaseError.toException());
            }
        };
        FirebaseDatabase.getInstance().getReference().child("record").addValueEventListener(valueEventListener);
    }
    // คำนวณและแสดงรวมรายรับ และรายจ่าย และยอดคงเหลือ
    private void calculateTotalIncomeAndExpense() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long totalIncome = 0;
                long totalExpense = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MainModel model = snapshot.getValue(MainModel.class);
                    if (model != null) {
                        if ("income".equals(model.getType())) {
                            totalIncome += model.getAmount();
                        }
                        if ("expenses".equals(model.getType())) {
                            totalExpense += model.getAmount();
                        }
                    }
                }
                String totalIncomeText = "Total Income: " + totalIncome + " bath";
                String totalExpenseText = "Total Expenses: " + totalExpense + " bath";
                tvTotalIncome.setText(totalIncomeText);
                tvTotalExpense.setText(totalExpenseText);
                long balance = totalIncome - totalExpense;
                String balanceText = "Balance: " + balance + " bath";
                tvBalance.setText(balanceText);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error fetching data", databaseError.toException());
            }
        };
        FirebaseDatabase.getInstance().getReference().child("record").addValueEventListener(valueEventListener);
    }
    // เมื่อ Activity ได้สร้างและแสดงผลบนหน้าจอแล้ว เริ่มต้นการอัปเดตข้อมูลใน Adapter
    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }
    // เมื่อ Activity หยุดทำงาน หยุดการอัปเดตข้อมูลใน Adapter
    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }
    // สร้างเมนูค้นหาและตั้งค่าการค้นหาข้อมูล
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtsearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtsearch(query);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    // ค้นหาข้อมูลจากฐานข้อมูล Firebase และอัปเดตข้อมูลใน RecyclerView
    private void txtsearch(String str) {
        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(
                                FirebaseDatabase.getInstance().getReference()
                                        .child("record")
                                        .orderByChild("name")
                                        .startAt(str)
                                        .endAt(str + "\uf8ff"),
                                MainModel.class)
                        .build();

        mainAdapter = new MainAdapter(options);
        mainAdapter.startListening();
        recyclerView.setAdapter(mainAdapter);
    }
}
