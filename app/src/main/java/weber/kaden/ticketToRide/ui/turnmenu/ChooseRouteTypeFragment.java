package weber.kaden.ticketToRide.ui.turnmenu;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import weber.kaden.ticketToRide.R;
import weber.kaden.ticketToRide.model.ClientFacade;

public class ChooseRouteTypeFragment extends DialogFragment {
    private ClientFacade client;
    private ChooseRouteTypePresenter presenter;

    public ChooseRouteTypeFragment() {}

    public void setParams(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        client = new ClientFacade();
        presenter = new ChooseRouteTypePresenter(this, client);
        View view = inflater.inflate(R.layout.fragment_choose_route_type,
                container, false);
        //setup view here

        return view;
    }
}
