package weber.kaden.myapplication.ui;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import weber.kaden.myapplication.R;
import weber.kaden.myapplication.ui.turnmenu.ChooseDestinationCardsFragment;

public class FragmentSetup extends DialogFragment {
    Button submit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_setup_question, container, false);
        submit = view.findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO SEND INFO TO SERVER

                getDialog().dismiss();
                android.support.v4.app.DialogFragment chooseCards = new ChooseDestinationCardsFragment();
                chooseCards.show(getFragmentManager(), "ChooseCardFragment");

            }
        });

        return view;
    }
}
