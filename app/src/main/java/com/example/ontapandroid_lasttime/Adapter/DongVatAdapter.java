package com.example.ontapandroid_lasttime.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ontapandroid_lasttime.DAO.DongVatDAO;
import com.example.ontapandroid_lasttime.DTO.DongVatDTO;
import com.example.ontapandroid_lasttime.NotificationUtils;
import com.example.ontapandroid_lasttime.R;
import com.example.ontapandroid_lasttime.RingService;

import java.util.ArrayList;

public class DongVatAdapter extends RecyclerView.Adapter<DongVatAdapter.viewHolder> {

    Context context;
    ArrayList<DongVatDTO> listDV;


    public DongVatAdapter(Context context, ArrayList<DongVatDTO> listDV) {
        this.context = context;
        this.listDV = listDV;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_dongvat, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        DongVatDTO dongVatDTO = listDV.get(position);
        DongVatDAO dongVatDAO = new DongVatDAO(context);


        holder.txt_ten.setText("Tên: " + dongVatDTO.getTen());
        holder.txt_loai.setText("Loại: " + dongVatDTO.getLoai());
        holder.txt_gioiTinh.setText("Giới tính: " + dongVatDTO.getGioiTinh());
        holder.txt_soLuong.setText("Số lượng: " + dongVatDTO.getSoLuong());

        holder.btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int kq = dongVatDAO.deleteRow(dongVatDTO);
                if (kq > 0) {
                    listDV.remove(dongVatDTO);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Đã xoá thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, RingService.class);
                    context.startService(intent);
                }
            }
        });

        holder.btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_sua, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();

                EditText edt_ten = view.findViewById(R.id.edt_ten);
                EditText edt_loai = view.findViewById(R.id.edt_loai);
                EditText edt_gioiTinh = view.findViewById(R.id.edt_gioiTinh);
                EditText edt_soLuong = view.findViewById(R.id.edt_soLuong);

                Button btn_SuaDialog = view.findViewById(R.id.btn_SuaDialog);
                Button btn_HuyDialog = view.findViewById(R.id.btn_HuyDialog);

                edt_ten.setText(dongVatDTO.getTen());
                edt_loai.setText(dongVatDTO.getLoai());
                edt_gioiTinh.setText(dongVatDTO.getGioiTinh());
                edt_soLuong.setText(dongVatDTO.getSoLuong()+"");

                btn_SuaDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ten = edt_ten.getText().toString().trim();
                        String loai = edt_loai.getText().toString().trim();
                        String giotinh = edt_gioiTinh.getText().toString().trim();
                        String soluong = edt_soLuong.getText().toString().trim();

                        dongVatDTO.setLoai(loai);
                        dongVatDTO.setGioiTinh(giotinh);
                        dongVatDTO.setTen(ten);

                        if (ten.isEmpty()||loai.isEmpty()||giotinh.isEmpty()||soluong.isEmpty()) {
                            Toast.makeText(context, "Không bỏ trống dữ liệu", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        try {
                            int sl = Integer.parseInt(soluong);
                            if (sl <= 0) {
                                Toast.makeText(context, "Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            dongVatDTO.setSoLuong(sl);

                            int kq = dongVatDAO.upadateRow(dongVatDTO);
                            if (kq>0) {
                                notifyDataSetChanged();
                                dialog.dismiss();
                                Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                                NotificationUtils.GuiThongBao(context, "thong bao", "da sua");
                            }
                        }
                        catch (NumberFormatException e) {
                            Toast.makeText(context, "Số lượng phải là số", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                btn_HuyDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return listDV.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView txt_ten, txt_loai, txt_gioiTinh, txt_soLuong;
        Button btn_sua, btn_xoa;

        DongVatAdapter dongVatAdapter;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            dongVatAdapter = new DongVatAdapter(context, listDV);

            txt_ten = itemView.findViewById(R.id.txt_ten);
            txt_gioiTinh = itemView.findViewById(R.id.txt_gioiTinh);
            txt_loai = itemView.findViewById(R.id.txt_loai);
            txt_soLuong = itemView.findViewById(R.id.txt_soLuong);
            btn_sua = itemView.findViewById(R.id.btn_sua);
            btn_xoa = itemView.findViewById(R.id.btn_xoa);
        }
    }
}
