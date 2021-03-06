package weber.kaden.ticketToRide.ui.turnmenu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import weber.kaden.common.model.TrainCardType;
import weber.kaden.ticketToRide.R;

public class ChooseRouteTypeAdapter extends RecyclerView.Adapter<ChooseRouteTypeAdapter.ViewHolder> {
    private List<TrainCardType> mAvailableTypes;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ChooseRouteTypeFragment mFragment;

    ChooseRouteTypeAdapter(Context context, List<TrainCardType> types, ChooseRouteTypeFragment fragment) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mAvailableTypes = types;
        mFragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final View view = mLayoutInflater.inflate(R.layout.card_type_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TrainCardType type = mAvailableTypes.get(position);
        //add logic here
        holder.cardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragment.claimRoute(type);
            }
        });

        switch (type) {
            case BOX:
                holder.cardButton.setBackgroundColor(mContext.getResources().getColor(R.color.Box));
                break;
            case PASSENGER:
                holder.cardButton.setBackgroundColor(mContext.getResources().getColor(R.color.Passenger));
                break;
            case TANKER:
                holder.cardButton.setBackgroundColor(mContext.getResources().getColor(R.color.Tanker));
                break;
            case REEFER:
                holder.cardButton.setBackgroundColor(mContext.getResources().getColor(R.color.Reefer));
                break;
            case FREIGHT:
                holder.cardButton.setBackgroundColor(mContext.getResources().getColor(R.color.Freight));
                break;
            case HOPPER:
                holder.cardButton.setBackgroundColor(mContext.getResources().getColor(R.color.Hopper));
                break;
            case COAL:
                holder.cardButton.setBackgroundColor(mContext.getResources().getColor(R.color.Coal));
                break;
            case CABOOSE:
                holder.cardButton.setBackgroundColor(mContext.getResources().getColor(R.color.Caboose));
                break;
            case LOCOMOTIVE:
                holder.cardButton.setBackground(mContext.getResources().getDrawable(R.drawable.locomotive_button));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mAvailableTypes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button cardButton;

        ViewHolder(View view){
            super(view);
            cardButton = view.findViewById(R.id.card_button);
        }
        @Override
        public void onClick(View v) {}
    }


}
