package weber.kaden.myapplication.ui.turnmenu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import weber.kaden.common.model.TrainCard;
import weber.kaden.common.model.TrainCardType;
import weber.kaden.myapplication.R;
import weber.kaden.myapplication.model.ClientFacade;

public class ChooseTrainCardsFragment extends DialogFragment {

	private ChooseTrainCardsFragment instance = this;

	//widgets
    private Button mActionCancel, mActionOk;
    private Button mTrainCard0, mTrainCard1, mTrainCard2, mTrainCard3, mTrainCard4;
    private Button mDeckButton;

    List<TrainCard> faceUpCards;

    int numCardsChosen = 0;

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

        mTrainCard0 = view.findViewById(R.id.choose_train_card0);
	    mTrainCard1 = view.findViewById(R.id.choose_train_card1);
	    mTrainCard2 = view.findViewById(R.id.choose_train_card2);
	    mTrainCard3 = view.findViewById(R.id.choose_train_card3);
	    mTrainCard4 = view.findViewById(R.id.choose_train_card4);
	    mDeckButton = view.findViewById(R.id.choose_train_deck);

	    faceUpCards = (List<TrainCard>) getArguments().getSerializable("faceUpCards");

	    mTrainCard0.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
				v.setVisibility(View.INVISIBLE);
				chooseCard(0);
		    }
	    });
	    mTrainCard1.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
				v.setVisibility(View.INVISIBLE);
				chooseCard(1);
		    }
	    });
	    mTrainCard2.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
				v.setVisibility(View.INVISIBLE);
				chooseCard(2);
		    }
	    });
	    mTrainCard3.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
				v.setVisibility(View.INVISIBLE);
		    	chooseCard(3);
		    }
	    });
	    mTrainCard4.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
				v.setVisibility(View.INVISIBLE);
				chooseCard(4);
		    }
	    });
	    mDeckButton.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    chooseDeck();
		    }
	    });

	    updateCards(faceUpCards);

        return view;
    }

    public void chooseCard(int cardIndex) {
    	int cardValue = 1;
    	if (faceUpCards.get(cardIndex).getType() == TrainCardType.LOCOMOTIVE) {
    		cardValue = 2;
		}

		if (numCardsChosen + cardValue > 2) {
    		Toast.makeText(getActivity(), "Can't choose card", Toast.LENGTH_SHORT).show();
		}
		else {
			ClientFacade client = new ClientFacade();
			ChooseFaceUpTrainCardTask chooseFaceUpTrainCards = new ChooseFaceUpTrainCardTask(client.getCurrentUser(), client.getCurrentGame().getID(), cardIndex, cardValue);
			chooseFaceUpTrainCards.execute();
		}
	}

	public void chooseDeck() {
		ClientFacade client = new ClientFacade();
		ChooseTrainCardDeckTask chooseTrainCardDeck = new ChooseTrainCardDeckTask(client.getCurrentUser(), client.getCurrentGame().getID());
		chooseTrainCardDeck.execute();
	}

    public void updateCards(List<TrainCard> newCards) {
    	faceUpCards = newCards;

    	updateCard(newCards.get(0), mTrainCard0);
		updateCard(newCards.get(1), mTrainCard1);
		updateCard(newCards.get(2), mTrainCard2);
		updateCard(newCards.get(3), mTrainCard3);
		updateCard(newCards.get(4), mTrainCard4);
	}

	private void updateCard(TrainCard newCard, Button cardButton) {
		switch (newCard.getType()) {
			case BOX:
				cardButton.setBackgroundColor(getResources().getColor(R.color.Box));
				break;
			case PASSENGER:
				cardButton.setBackgroundColor(getResources().getColor(R.color.Passenger));
				break;
			case TANKER:
				cardButton.setBackgroundColor(getResources().getColor(R.color.Tanker));
				break;
			case REEFER:
				cardButton.setBackgroundColor(getResources().getColor(R.color.Reefer));
				break;
			case FREIGHT:
				cardButton.setBackgroundColor(getResources().getColor(R.color.Freight));
				break;
			case HOPPER:
				cardButton.setBackgroundColor(getResources().getColor(R.color.Hopper));
				break;
			case COAL:
				cardButton.setBackgroundColor(getResources().getColor(R.color.Coal));
				break;
			case CABOOSE:
				cardButton.setBackgroundColor(getResources().getColor(R.color.Caboose));
				break;
			case LOCOMOTIVE:
				cardButton.setBackground(getResources().getDrawable(R.drawable.locomotive_button));
				break;
		}

		cardButton.setVisibility(View.VISIBLE);
	}

    public class ChooseFaceUpTrainCardTask extends AsyncTask<Void, Void, Boolean> {
    	private String username;
    	private String gameId;
    	private int cardIndex;
    	private int cardValue;

    	private ClientFacade client;
    	private ChooseTrainCardsPresenter presenter;

    	private String errormessage;

    	public ChooseFaceUpTrainCardTask(String username, String gameId, int cardIndex, int value) {
			this.username = username;
			this.gameId = gameId;
			this.cardIndex = cardIndex;
			this.cardValue = value;

			client = new ClientFacade();
			presenter = new ChooseTrainCardsPresenter(instance, client);
		}

		@Override
		protected Boolean doInBackground(Void... voids) {
			try {
				presenter.drawFaceUpTrainCard(gameId, username, cardIndex);
			} catch (Exception e) {
				errormessage = e.getMessage();
				return false;
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean success) {
			if (success) {
				numCardsChosen += cardValue;
				mActionCancel.setEnabled(false);

				if (numCardsChosen == 2) {
					getDialog().dismiss();
				}
				else {
					updateCards(presenter.getFaceUpTrainCards());
				}
			}
			else {
				Toast.makeText(getActivity(), errormessage, Toast.LENGTH_SHORT).show();
			}
		}
	}

	public class ChooseTrainCardDeckTask extends AsyncTask<Void, Void, Boolean> {
    	private String username;
    	private String gameId;

    	private String errormessage;

    	public ChooseTrainCardDeckTask(String username, String gameId) {
    		this.username = username;
    		this.gameId	= gameId;
		}

		@Override
		protected Boolean doInBackground(Void... voids) {
			ClientFacade client = new ClientFacade();
			ChooseTrainCardsPresenter presenter = new ChooseTrainCardsPresenter(instance, client);
			try {
				presenter.drawTrainCardFromDeck(gameId, username);
			} catch (Exception e) {
				errormessage = e.getMessage();
				return false;
			}

			return true;
		}

		@Override
		protected void onPostExecute(Boolean success) {
			if (success) {
				numCardsChosen += 1;

				if (numCardsChosen == 2) {
					getDialog().dismiss();
				}
			}
			else {
				Toast.makeText(getActivity(), errormessage, Toast.LENGTH_SHORT).show();
			}
		}
	}
}
