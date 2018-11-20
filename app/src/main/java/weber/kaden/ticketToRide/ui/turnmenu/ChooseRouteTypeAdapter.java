package weber.kaden.ticketToRide.ui.turnmenu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import weber.kaden.common.model.TrainCardType;
import weber.kaden.ticketToRide.R;

public class ChooseRouteTypeAdapter extends RecyclerView.Adapter<ChooseRouteTypeAdapter.ViewHolder> {
    private List<TrainCardType> mAvailableTypes;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    ChooseRouteTypeAdapter(Context context, List<TrainCardType> types) {
        mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        mAvailableTypes = types;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.card_type_button, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrainCardType type = mAvailableTypes.get(position);
        //add logic here
        switch (type) {
            case BOX:
                holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.Box));
                break;
            case PASSENGER:
                holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.Passenger));
                break;
            case TANKER:
                holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.Tanker));
                break;
            case REEFER:
                holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.Reefer));
                break;
            case FREIGHT:
                holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.Freight));
                break;
            case HOPPER:
                holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.Hopper));
                break;
            case COAL:
                holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.Coal));
                break;
            case CABOOSE:
                holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.Caboose));
                break;
            case LOCOMOTIVE:
                holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.locomotive_button));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mAvailableTypes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ViewHolder(View view){
            super(view);
            //??
        }
        @Override
        public void onClick(View v) {}
    }
}
