package weber.kaden.ticketToRide.ui.turnmenu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import weber.kaden.common.model.TrainCardType;
import weber.kaden.ticketToRide.R;
import weber.kaden.ticketToRide.model.ClientFacade;

public class ChooseRouteTypeFragment extends DialogFragment {
    private ClientFacade client;
    private ChooseRouteTypePresenter presenter;
    private RecyclerView buttonList;
    private ChooseRouteTypeAdapter mAdapter;

    private String city1;
    private String city2;
    private TrainCardType type;
    private Integer cost;
    private boolean isSecondRoute;

    public ChooseRouteTypeFragment() {}

    public void setParams(String city1, String city2, TrainCardType type, Integer cost, boolean isSecondRoute) {
        this.city1 = city1;
        this.city2 = city2;
        this.type = type;
        this.cost = cost;
        this.isSecondRoute = isSecondRoute;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        client = new ClientFacade();
        presenter = new ChooseRouteTypePresenter(this, client);
        View view = inflater.inflate(R.layout.fragment_choose_route_type,
                container, false);
        //setup view here
        buttonList = view.findViewById(R.id.type_list);
        buttonList.setLayoutManager(new LinearLayoutManager(getContext()));

        List<TrainCardType> types = presenter.getValidTypes(cost);
        mAdapter = new ChooseRouteTypeAdapter(getContext(), types, this);
        buttonList.setAdapter(mAdapter);

        return view;
    }

    void claimRoute(TrainCardType type) {
        ClaimRouteTask task = new ClaimRouteTask();
        task.execute((Void) null);
    }

    public class ClaimRouteTask extends AsyncTask<Void, Void, Boolean> {
        private String errorMessage;

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                presenter.claimRoute(city1, city2, type, cost, isSecondRoute);
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

    public void printError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        getDialog().dismiss();
    }
}
