package weber.kaden.myapplication.ui.turnmenu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import weber.kaden.myapplication.R;

public class ChooseTrainCardsFragment extends DialogFragment {
    //widgets
    private Button mActionCancel, mActionOk;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_choose_train_cards, container, false);
        mActionCancel = view.findViewById(R.id.choose_train_cancel);
        mActionOk = view.findViewById(R.id.choose_train_ok);

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Make this send data back
                getDialog().dismiss();
            }
        });

        return view;
    }
}
