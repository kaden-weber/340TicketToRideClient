package weber.kaden.ticketToRide.ui;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import weber.kaden.common.model.TrainCardType;
import weber.kaden.ticketToRide.R;
import weber.kaden.ticketToRide.model.ClientFacade;

public class ClaimRouteFragment extends DialogFragment {

    private Button actionCancel;
    private Button actionConfirm;
    private ClientFacade client;
    private ClaimRoutePresenter presenter;
    private String city1;
    private String city2;
    private TrainCardType type;
    private Integer cost;

    private OnFragmentInteractionListener mListener;

    public ClaimRouteFragment() {}

    public void setParams(String city1, String city2, TrainCardType type, Integer cost) {
        this.city1 = city1;
        this.city2 = city2;
        this.type = type;
        this.cost = cost;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        client = new ClientFacade();
        presenter = new ClaimRoutePresenter(this, client);
        View view = inflater.inflate(R.layout.fragment_dialog_claim_route, container, false);
        //TextView cityNames
        actionCancel = view.findViewById(R.id.claim_route_cancel_button);
        actionConfirm = view.findViewById(R.id.claim_route_confirm_button);

        actionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        actionConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClaimRouteTask task = new ClaimRouteTask();
                task.execute((Void) null);
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void printError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        getDialog().dismiss();
    }

    public class ClaimRouteTask extends AsyncTask<Void, Void, Boolean> {
        private String errorMessage;

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                presenter.claimRoute(city1, city2, type, cost);
            } catch (Exception e) {
                errorMessage = e.getMessage();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            if (!success) printError(errorMessage);
            else getDialog().dismiss();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
