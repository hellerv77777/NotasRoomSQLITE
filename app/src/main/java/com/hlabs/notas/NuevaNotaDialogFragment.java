package com.hlabs.notas;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.hlabs.notas.db.entity.NotaEntity;

public class NuevaNotaDialogFragment extends DialogFragment {


    private View view;
    private EditText titulo,contenido;
    private RadioGroup color;
    private Switch favorita;
    public static NuevaNotaDialogFragment newInstance() {
        return new NuevaNotaDialogFragment();
    }




    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Nueva Nota");
        builder.setMessage(R.string.dialog_fire_missiles)
                .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String mTitulo = titulo.getText().toString();
                        String mContenido=contenido.getText().toString();
                        String mColor ="Azul";
                        switch ((color.getCheckedRadioButtonId())){
                            case R.id.radioButtonAzul:

                                mColor ="Azul";

                            break;

                            case R.id.radioButtonRojo:

                                mColor ="Rojo";

                            break;

                            case R.id.radioButtonVerde:

                                mColor ="Verde";
                            break;




                        }

                        boolean Mfavorita = favorita.isChecked();

                         NuevaNotaDialogViewModel mViewModel = ViewModelProviders.of(getActivity()).get(NuevaNotaDialogViewModel.class);
                         mViewModel.insertNota(new NotaEntity(mTitulo,mContenido,Mfavorita,mColor));
                         dialog.dismiss();

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();

                    }
                });

        LayoutInflater layoutInflate = getActivity().getLayoutInflater();
        view = layoutInflate.inflate(R.layout.nueva_nota_dialog_fragment,null);

        titulo=view.findViewById(R.id.editTextTitulo);
        contenido=view.findViewById(R.id.editTextContenido);
        color=view.findViewById(R.id.radioGroup);
        favorita=view.findViewById(R.id.switchFav);


        builder.setView(view);

        return builder.create();
    }

}
