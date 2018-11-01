package weber.kaden.myapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

import weber.kaden.common.model.DestinationCard;
import weber.kaden.common.model.TrainCard;
import weber.kaden.myapplication.R;

public class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.ViewHolder>{
    private  List<Integer> points;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    PointsAdapter(Context context, List<Integer> points) {
        this.mInflater = LayoutInflater.from(context);
        this.points = points;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.points_layout, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Integer point =  points.get(position);
        String point_str = point.toString();
        holder.pointsView.setText(point_str);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return points.size();
    }
    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pointsView;
        ViewHolder(View itemView) {
            super(itemView);
            pointsView = itemView.findViewById(R.id.points);
        }
    }
}
