package com.example.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    // Constructor ของคลาส MainAdapter
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }// เรียก constructor ของ FirebaseRecyclerAdapter

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, final int position, @NonNull MainModel model) {
        // ตั้งค่าข้อมูลในแต่ละส่วนของ ViewHolder จาก Firebase Realtime Database
        holder.name.setText(model.getName());
        holder.date.setText(model.getDate());
        holder.type.setText(model.getType());
        holder.amount.setText(model.getAmount() + " bath");
        // เมื่อปุ่ม "Edit" ถูกคลิก
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // สร้าง DialogPlus เพื่อแสดงฟอร์มการอัปเดตข้อมูล
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true, 1200)
                        .create();
                // อ้างอิง View จาก DialogPlus
                View view = dialogPlus.getHolderView();
                // อ้างอิงฟิลด์และปุ่มต่างๆ ในฟอร์ม
                EditText name = view.findViewById(R.id.txtname);
                EditText date = view.findViewById(R.id.txtdate);
                Spinner spinnerType = view.findViewById(R.id.spinnerType);
                EditText amount = view.findViewById(R.id.txtamount);
                Button btnUpdate = view.findViewById(R.id.btnUpdate);
                // กำหนดข้อมูลเริ่มต้นในฟอร์ม
                date.setText(model.getDate());
                name.setText(model.getName());
                amount.setText(String.valueOf(model.getAmount()));

                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDatePickerDialog(date);
                    }
                });

                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(holder.name.getContext(),
                        R.array.type_array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerType.setAdapter(adapter);
                int typePosition = adapter.getPosition(model.getType());
                spinnerType.setSelection(typePosition);


                // แสดง DialogPlus
                dialogPlus.show();
                // เมื่อปุ่ม "Update" ใน DialogPlus ถูกคลิก
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // ดึงข้อมูลจากฟอร์ม
                        int amountValue = Integer.parseInt(amount.getText().toString());
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", name.getText().toString());
                        map.put("date", date.getText().toString());
                        map.put("type", spinnerType.getSelectedItem().toString());
                        map.put("amount", amountValue);
                        // อัปเดตข้อมูลใน Firebase Realtime Database
                        FirebaseDatabase.getInstance().getReference().child("record")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.name.getContext(), "Data Update Success", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(holder.name.getContext(), "Error Data Update", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });
        // เมื่อปุ่ม "Delete" ถูกคลิก
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // สร้าง AlertDialog เพื่อยืนยันการลบข้อมูล
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Want to delete?");
                builder.setMessage("Are you sure to delete the selected items?");
                // ตอบ "Yes" ใน AlertDialog
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("record")
                                .child(getRef(position).getKey()).removeValue();
                        Toast.makeText(holder.name.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                // ตอบ "No" ใน AlertDialog
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.name.getContext(), "Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                // แสดง AlertDialog
                builder.show();
            }
        });
    }



    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // สร้าง ViewHolder ใหม่โดย main_item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);// สร้างและคืนค่า ViewHolder ใหม่
    }
    // เรียกใช้งาน ViewHolder ซึ่งเป็นชุดขององค์ประกอบ UI ที่ถูกผูกกับข้อมูล
    class myViewHolder extends RecyclerView.ViewHolder{

        TextView name,date,type,amount;// ข้อมูลที่จะแสดงใน ViewHolder

        Button btnEdit,btnDelete;// ปุ่มแก้ไขและลบ

        // เมื่อสร้าง ViewHolder ใหม่
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            // ผูกองค์ประกอบ UI กับตัวแปร
            name = (TextView)itemView.findViewById(R.id.nametext);
            date = (TextView)itemView.findViewById(R.id.datetext);
            type = (TextView)itemView.findViewById(R.id.typetext);
            amount = (TextView)itemView.findViewById(R.id.amounttext);

            btnEdit = (Button)itemView.findViewById(R.id.btnEdit);
            btnDelete = (Button)itemView.findViewById(R.id.btnDelete);

        }
    }
    // เมื่อคลิกที่ EditText วันที่ เพื่อแสดงกล่องโต้ตอบสำหรับการเลือกวันที่
    private void showDatePickerDialog(EditText dateEditText) {
        final Calendar calendar = Calendar.getInstance();// สร้างอ็อบเจ็กต์ Calendar เพื่อเก็บวันที่และเวลาปัจจุบัน
        int year = calendar.get(Calendar.YEAR);// ดึงปีปัจจุบัน
        int month = calendar.get(Calendar.MONTH);// ดึงเดือนปัจจุบัน
        int day = calendar.get(Calendar.DAY_OF_MONTH);// ดึงวันที่ปัจจุบัน
        // สร้างกล่องโต้ตอบสำหรับการเลือกวันที่
        DatePickerDialog datePickerDialog = new DatePickerDialog(dateEditText.getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    // เมื่อผู้ใช้เลือกวันที่
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // กำหนดวันที่ที่ผู้ใช้เลือกในอ็อบเจ็กต์ Calendar
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        // กำหนดรูปแบบของวันที่และเวลา
                        String myFormat = "dd/MM/yyyy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                        // แสดงวันที่ที่ผู้ใช้เลือกใน EditText ที่เกี่ยวข้อง
                        dateEditText.setText(sdf.format(calendar.getTime()));
                    }
                }, year, month, day);// กำหนดวันที่และเวลาเริ่มต้นให้กับ DatePickerDialog

        datePickerDialog.show();// แสดงกล่องโต้ตอบสำหรับการเลือกวันที่
    }

}
