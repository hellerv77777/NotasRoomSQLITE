package com.hlabs.notas.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hlabs.notas.NuevaNotaDialogFragment;
import com.hlabs.notas.NuevaNotaDialogViewModel;
import com.hlabs.notas.db.entity.NotaEntity;
import com.hlabs.notas.R;
import com.hlabs.notas.ui.adapters.MyNotaRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class NotaFragment extends Fragment {


    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 2;
    private List<NotaEntity> notaEntityList;
    private RecyclerView recyclerView;
    private MyNotaRecyclerViewAdapter myNotaRecyclerViewAdapter;
    private NuevaNotaDialogViewModel nuevaNotaDialogViewModel;

    public NotaFragment() {
    }


    public static NotaFragment newInstance(int columnCount) {
        NotaFragment fragment = new NotaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nota_list, container, false);


        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(mColumnCount,StaggeredGridLayoutManager.VERTICAL));
            }


            notaEntityList = new ArrayList<>();

            myNotaRecyclerViewAdapter = new MyNotaRecyclerViewAdapter(notaEntityList,getActivity());
            recyclerView.setAdapter(myNotaRecyclerViewAdapter);
            lanzarViewMode();


        }
        return view;
    }

    private void lanzarViewMode() {

        nuevaNotaDialogViewModel = ViewModelProviders.of(getActivity()).get(NuevaNotaDialogViewModel.class);
        nuevaNotaDialogViewModel.getAllNotas().observe(getActivity(), new Observer<List<NotaEntity>>() {
            @Override
            public void onChanged(List<NotaEntity> notaEntities) {

                myNotaRecyclerViewAdapter.setNuevasNotas(notaEntities);

            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       switch (item.getItemId())
       {
           case R.id.action_add_nota:
               mostrarDialogoNuevaNota();
           break;
       }

       return super.onOptionsItemSelected(item);
    }

    private void mostrarDialogoNuevaNota() {

        FragmentManager fm = getActivity().getSupportFragmentManager();
        NuevaNotaDialogFragment dialogFragment = new NuevaNotaDialogFragment();
        dialogFragment.show(fm,"Nueva Nota DialogFragment");
    }
}
