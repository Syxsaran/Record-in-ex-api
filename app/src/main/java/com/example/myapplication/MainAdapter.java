package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, final int position, @NonNull MainModel model) {
        holder.name.setText(model.getName());
        holder.date.setText(model.getDate());
        holder.type.setText(model.getType());
        holder.amount.setText(model.getAmount() + " bath");

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true, 1200)
                        .create();

                View view = dialogPlus.getHolderView();

                EditText name = view.findViewById(R.id.txtname);
                EditText date = view.findViewById(R.id.txtdate);
                Spinner spinnerType = view.findViewById(R.id.spinnerType); // เปลี่ยน EditText เป็น Spinner
                EditText amount = view.findViewById(R.id.txtamount);

                Button btnUpdate = view.findViewById(R.id.btnUpdate);

                // ตั้งค่า Spinner โดยใช้ข้อมูลจาก MainModel
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(holder.name.getContext(),
                        R.array.type_array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerType.setAdapter(adapter);
                int typePosition = adapter.getPosition(model.getType()); // หาตำแหน่งของประเภทใน Spinner
                spinnerType.setSelection(typePosition); // เลือกประเภทใน Spinner ตามตำแหน่งที่หาได้

                name.setText(model.getName());
                date.setText(model.getDate());
                amount.setText(String.valueOf(model.getAmount()));

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int amountValue = Integer.parseInt(amount.getText().toString());

                        Map<String, Object> map = new HashMap<>();
                        map.put("name", name.getText().toString());
                        map.put("date", date.getText().toString());
                        map.put("type", spinnerType.getSelectedItem().toString()); // ใช้ Spinner แทน EditText
                        map.put("amount", amountValue);

                        FirebaseDatabase.getInstance().getReference().child("location")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.name.getContext(), "Data Update Successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(holder.name.getContext(), "Error Update", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are You Sure?");
                builder.setMessage("Deleted");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("location")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.name.getContext(), "Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView name,date,type,amount;

        Button btnEdit,btnDelete;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.nametext);
            date = (TextView)itemView.findViewById(R.id.datetext);
            type = (TextView)itemView.findViewById(R.id.typetext);
            amount = (TextView)itemView.findViewById(R.id.amounttext);

            btnEdit = (Button)itemView.findViewById(R.id.btnEdit);
            btnDelete = (Button)itemView.findViewById(R.id.btnDelete);

        }
    }
}
