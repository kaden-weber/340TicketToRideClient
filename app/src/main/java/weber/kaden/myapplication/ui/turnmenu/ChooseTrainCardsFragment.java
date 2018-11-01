package weber.kaden.myapplication.ui.turnmenu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import weber.kaden.common.model.TrainCard;
import weber.kaden.myapplication.R;

public class ChooseTrainCardsFragment extends DialogFragment {
    //widgets
    private Button mActionCancel, mActionOk;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_choose_train_cards, container, false);
        setCancelable(false);
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

        Button mTrainCard0 = view.findViewById(R.id.choose_train_card0);
	    Button mTrainCard1 = view.findViewById(R.id.choose_train_card1);
	    Button mTrainCard2 = view.findViewById(R.id.choose_train_card2);
	    Button mTrainCard3 = view.findViewById(R.id.choose_train_card3);
	    Button mTrainCard4 = view.findViewById(R.id.choose_train_card4);
	    Button mDeckButton = view.findViewById(R.id.choose_train_deck);

	    final List<TrainCard> faceUpCards = (List<TrainCard>) getArguments().getSerializable("faceUpCards");
	    mTrainCard0.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {

		    }
	    });
	    mTrainCard1.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {

		    }
	    });
	    mTrainCard2.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {

		    }
	    });
	    mTrainCard3.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {

		    }
	    });
	    mTrainCard4.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {

		    }
	    });
	    mDeckButton.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    
		    }
	    });

        return view;
    }
}
