package com.example.ontapandroid_lasttime.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ontapandroid_lasttime.Adapter.DongVatAdapter;
import com.example.ontapandroid_lasttime.DAO.DongVatDAO;
import com.example.ontapandroid_lasttime.DTO.DongVatDTO;
import com.example.ontapandroid_lasttime.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Fragment_DongVat extends Fragment {

    FloatingActionButton fb_add;
    RecyclerView rv_dv;
    DongVatAdapter dongVatAdapter;
    DongVatDAO dongVatDAO;
    LinearLayoutManager linearLayoutManager;
    ArrayList<DongVatDTO> listDV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dongvat_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv_dv = view.findViewById(R.id.rv_dv);
        fb_add = view.findViewById(R.id.fb_add);

        linearLayoutManager = new LinearLayoutManager(getContext());
        rv_dv.setLayoutManager(linearLayoutManager);

        dongVatDAO = new DongVatDAO(getContext());
        listDV = dongVatDAO.getList();

        dongVatAdapter = new DongVatAdapter(getContext(),listDV);
        rv_dv.setAdapter(dongVatAdapter);

        fb_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view1 = getLayoutInflater().inflate(R.layout.dialog_them, null);
                builder.setView(view1);
                Dialog dialog = builder.show();
                dialog.create();

                EditText edt_ten = view1.findViewById(R.id.edt_ten);
                EditText edt_loai = view1.findViewById(R.id.edt_loai);
                EditText edt_gioiTinh = view1.findViewById(R.id.edt_gioiTinh);
                EditText edt_soLuong = view1.findViewById(R.id.edt_soLuong);
                Button btn_ThemDialog = view1.findViewById(R.id.btn_ThemDialog);
                Button btn_HuyDialog = view1.findViewById(R.id.btn_HuyDialog);

                btn_ThemDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ten = edt_ten.getText().toString().trim();
                        String loai = edt_loai.getText().toString().trim();
                        String giotinh = edt_gioiTinh.getText().toString().trim();
                        String soluong = edt_soLuong.getText().toString().trim();

                        if (ten.isEmpty()||loai.isEmpty()||giotinh.isEmpty()||soluong.isEmpty()) {
                            Toast.makeText(getContext(), "Không bỏ trống dữ liệu", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        try {
                            int sl = Integer.parseInt(soluong);
                            if (sl <= 0) {
                                Toast.makeText(getContext(), "Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            int kq = dongVatDAO.addRow(new DongVatDTO(ten, loai, giotinh,sl));
                            if (kq>0) {
                                listDV.clear();
                                listDV.addAll(dongVatDAO.getList());
                                dongVatAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (NumberFormatException e) {
                            Toast.makeText(getContext(), "Số lượng phải là số", Toast.LENGTH_SHORT).show();
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
}
