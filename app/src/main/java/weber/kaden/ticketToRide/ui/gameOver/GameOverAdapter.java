package weber.kaden.ticketToRide.ui.gameOver;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import weber.kaden.common.model.Player;
import weber.kaden.ticketToRide.R;

public class GameOverAdapter extends RecyclerView.Adapter<GameOverAdapter.ViewHolder> {

	private List<Player> mPlayers;
	private LayoutInflater mInflater;

	GameOverAdapter(Context context, List<Player> data) {
		this.mInflater = LayoutInflater.from(context);
		this.mPlayers = data;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = mInflater.inflate(R.layout.activity_game_over_row, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		switch (position) {
			case 1:
				holder.podiumText.setText("1st place: ");
				break;
			case 2:
				holder.podiumText.setText("2nd place: ");
				break;
			case 3:
				holder.podiumText.setText("3rd place: ");
				break;
			case 4:
				holder.podiumText.setText("4th place: ");
				break;
			case 5:
				holder.podiumText.setText("5th place: ");
				break;
			case 6:
				holder.podiumText.setText("6th place: ");
				break;
		}

		holder.playerNameText.setText(getPlayerName(position));
		holder.totalPointsText.setText("Total points: " + mPlayers.get(position).getFinalScore());

		if (mPlayers.get(position).isHasLongestPath()) {
			holder.longestRouteStar.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public int getItemCount() {
		return mPlayers.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		TextView podiumText, playerNameText, pointsLostText, pointsGainedText, totalPointsText;
		ImageView longestRouteStar;

		public ViewHolder(View itemView) {
			super(itemView);
			podiumText = itemView.findViewById(R.id.game_over_player_place);
			playerNameText = itemView.findViewById(R.id.game_over_player_name);
			pointsLostText = itemView.findViewById(R.id.game_over_player_points_lost);
			pointsGainedText = itemView.findViewById(R.id.game_over_player_points_gained);
			totalPointsText = itemView.findViewById(R.id.game_over_player_points_total);
			longestRouteStar = itemView.findViewById(R.id.game_over_player_longest_award);
		}
	}

	Player getPlayer(int id) {
		return mPlayers.get(id);
	}

	String getPlayerName(int id) {
		return mPlayers.get(id).getID();
	}
}
