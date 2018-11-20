package weber.kaden.ticketToRide.ui.turnmenu;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import weber.kaden.common.model.TrainCardType;
import weber.kaden.ticketToRide.R;
import weber.kaden.ticketToRide.model.ClientFacade;

public class ChooseRouteTypeFragment extends DialogFragment {
    private ClientFacade client;
    private ChooseRouteTypePresenter presenter;
    private RecyclerView buttonList;
    private ChooseRouteTypeAdapter mAdapter;

    private Integer cost;

    public ChooseRouteTypeFragment() {}

    public void setParams(Integer cost){
        this.cost = cost;
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
        mAdapter = new ChooseRouteTypeAdapter(getContext(), types);
        buttonList.setAdapter(mAdapter);

        return view;
    }
}
